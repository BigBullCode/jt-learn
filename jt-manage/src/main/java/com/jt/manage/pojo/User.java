package com.jt.manage.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jt.common.po.BasePojo;
//java持久Api，注解或XML描述对象-关系表映射关系，并将运行期的实体对象持久化到数据库中，SunJavaEE规范
@Table(name="user")//类和表创建关联关系
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3145879185377377413L;
	
	
	@Id  //主键,自增，strategy策略：IDENTITY自增
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	//@Column(name="user_name")一般不配置，可以设置一个固定的书写格式，比如驼峰规则
	private String name;
	private Date birthday;
	private String address;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
