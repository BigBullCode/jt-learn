package com.jt.manage.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jt.common.service.BaseService;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.EasyUITree;
import com.jt.common.vo.ItemCatData;
import com.jt.common.vo.ItemCatResult;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;

import redis.clients.jedis.JedisCluster;


@Service
public class ItemCatServiceImpl extends BaseService<ItemCat> implements ItemCatService{
	//关联ItemCatMapper
	@Autowired
	private ItemCatMapper itemCatMapper;
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private HttpClientService httpClientService;
//	@Autowired
//	private Jedis jedis;
	
//	@Autowired
//	private RedisService redisService;
	
	
	//json内部定义用来转对象的工具类
	private static final ObjectMapper objectMapper
									=new ObjectMapper();
	
	public List<ItemCat> findAll(Integer page, Integer rows){
		//return itemCatMapper.findAll();
		//分页支持，startPage方法是静态
		//内部就调用拦截器，startPage相当于事务开启begin，开启分页操作
		//它下面第一条的执行的查询的SQL语句
		PageHelper.startPage(page, rows); 	
		//第一条查询SQL被拦截，SQL语句拼接 limit page, rows
		List<ItemCat> itemCatList = itemCatMapper.findAll();
		
		//返回值不能直接返回，必须放在PageInfo对象中
		//这里和线程安全有关！直接返回方式它会产生线程安全问题
		//怎么解决？利用ThreadLocal，把当前对象和当前线程绑定，每个用户独立线程，
		PageInfo<ItemCat> pageInfo = new PageInfo<ItemCat>(itemCatList);
		
		return pageInfo.getList();
		
	}

	@Override
	public String findNameById(Long itemId) {
		
		return itemCatMapper.selectByPrimaryKey(itemId).getName();
	}

	/**
	 * 1.根据条件查询需要的结果 where parent_id = 0
	 * 2.需要将ItemCat集合转化为List<EasyUITree>
	 * 3.通过循环遍历的方式实现List赋值.
	 * 
	 * state  "open"/"closed"
	 */
	@Override
	public List<EasyUITree> findItemCatByParentId(Long parentId) {
		ItemCat itemCat = new ItemCat();
		itemCat.setParentId(parentId);
		//查询需要的结果
		List<ItemCat> itemCatList = 
						itemCatMapper.select(itemCat);
		//2.创建返回集合对象
		List<EasyUITree> treeList = new ArrayList<EasyUITree>();
		
		//3.将集合进行转化
		for (ItemCat itemCatTemp : itemCatList) {
			EasyUITree easyUITree = new EasyUITree();
			easyUITree.setId(itemCatTemp.getId());
			easyUITree.setText(itemCatTemp.getName());
			//如果是父级则暂时先关闭,用户需要时在展开
			String state = 
	itemCatTemp.getIsParent() ? "closed" : "open";
			easyUITree.setState(state);
			treeList.add(easyUITree);
		}
		return treeList;
	}

	@Override
	public List<EasyUITree> findCacheByParentId(Long parentId) {
		String key="ITEM_CAT_"+parentId;//动态获取key
		String result = jedisCluster.get(key);
		try {	
			List<EasyUITree> easyUITreeList=null;
			if(StringUtils.isEmpty(result)){
				easyUITreeList=findItemCatByParentId(parentId);
				//将数据list转化为json串
				String jsonData = objectMapper.writeValueAsString(easyUITreeList);
				//将数据保存到缓存中
				//jedis.set(key, jsonData);
				jedisCluster.set(key, jsonData);
				return easyUITreeList;
			}else{//表示缓存数据存在
				EasyUITree[] easyUITrees=objectMapper.readValue(result, EasyUITree[].class);
				easyUITreeList=Arrays.asList(easyUITrees);//集合转数组
				return easyUITreeList;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ItemCatResult findItemCatAll() {
		/*通过一级查二级,通过二级查三级的方式查询效率太低
		 * 可以通过将所有的父级id组成key,以map方式进行查询
		 */
		//实现商品分类划分
		Map<Long,List<ItemCat>> map=new HashMap<Long,List<ItemCat>>();
		//2获取全部的商品分类信息
		ItemCat itemCatDB=new ItemCat();
		itemCatDB.setStatus(1);//表示正常状态的数据
		List<ItemCat> itemCats=itemCatMapper.select(null);
		//3.实现商品数据的封装
		/**
		 * 将所有的分类信息以key=0.1.2 value=itemcat的形式封装
		 * 1.如果map中还没有该分类信息中的parentId,则先把这条商品添加到结果集中,
		 *   然后拿到它的parentId,和itemCat信息保存到map中.
		 * 2.如果map中有这条分类信息的parentId,
		 * 		则直接在map中parentId的key中的信息集合追加上遍历到的这条数据
		 */
		for (ItemCat itemCatTemp:itemCats){
			//如果Map中已经包含了该商品分类的父级Id
		    if(map.containsKey(itemCatTemp.getParentId())){
		    	map.get(itemCatTemp.getParentId()).add(itemCatTemp);
		    }else{
		    	List<ItemCat> itemCatTempList=new ArrayList<ItemCat>();
		    	itemCatTempList.add(itemCatTemp);
		    	map.put(itemCatTemp.getParentId(), itemCatTempList);
		    }
		}
		ItemCatResult result=new ItemCatResult();
		//4.准备一级商品分类的list集合信息
		List<ItemCatData> itemCatDataList1=new ArrayList<>();		
		//5为一级商品分类赋值
		for(ItemCat itemCat1 : map.get(0L)){
			ItemCatData itemCatData1=new ItemCatData();
			itemCatData1.setUrl("/products/"+itemCat1.getId()+".html");
			itemCatData1.setName("<a href='"+itemCatData1.getUrl()+"'>"+itemCat1.getName()+"</a>");
			
			
			//6.准备2级商品分类的list集合信息
			List<ItemCatData> itemCatDataList2=new ArrayList<>();
			
			//循环遍历2级商品分类信息
			for (ItemCat itemCat2 : map.get(itemCat1.getId())) {
				ItemCatData itemCatData2=new ItemCatData();
				itemCatData2.setUrl("/products/"+itemCat2.getId());
				itemCatData2.setName(itemCat2.getName());
				
				//实现三级商品分类信息维护
				List<String> itemCatDataList3=new ArrayList<String>();
				for (ItemCat itemCat3 : map.get(itemCat2.getId())) {
					itemCatDataList3.add("/product/"+itemCat3.getId()+"|"+itemCat3.getName());				
				}
				itemCatData2.setItems(itemCatDataList3);
				itemCatDataList2.add(itemCatData2);
			}			
			itemCatData1.setItems(itemCatDataList2);
			itemCatDataList1.add(itemCatData1);
			
			if(itemCatDataList1.size()>13){
				break;
			}
		}
		result.setItemCats(itemCatDataList1);

		return result;
	}
	/**
	 * 先查询缓存的数据
	 * 如果数据不为null,需要将缓存中的数据转化为Java对象
	 * 如果数据为null,需要 查询数据库,之后将数据保存到缓存中
	 */
	@Override
	public ItemCatResult findTtemCatCache() {
		String key="ITEM_CAT_ALL";
		String jsonData = jedisCluster.get(key);
		ItemCatResult itemCatResult=null;
		try {			
			if(StringUtils.isEmpty(jsonData)){
				itemCatResult=findItemCatAll();
				//将数据保存到缓存中
				String itemCatJSON = objectMapper.writeValueAsString(itemCatResult);
				jedisCluster.set(key, itemCatJSON);
				
			}else{//表示缓存数据存在
				itemCatResult = objectMapper.readValue(jsonData, ItemCatResult.class);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return itemCatResult;
	}
	
	
	
	
	
	
	
	
	

}












