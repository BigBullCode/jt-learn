package httpClient;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestHttpClient {
	/**
	 * 测试步骤
	 * 1.实例化HttpClient对象
	 * 2编辑网址
	 * 3创建请求对象
	 * 4发起请求获取响应结果
	 * 5判断结果是否正确,获取状态码信息,判断是否等于200
	 * 6获取服务器返回值数据
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@Test
	public void testGet() throws ClientProtocolException, IOException{
//		CloseableHttpClient httpClient=HttpClients.createDefault();//可以访问任何网址
//		
//		String url="http://www.weather.com.cn/weather/101010100.shtml";
//		
//		HttpGet httpGet=new HttpGet(url);
//		
//		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
//		
//		if(httpResponse.getStatusLine().getStatusCode()==200){
//			//获取返回值的全部信息
//			String result=
//					EntityUtils.toString(httpResponse.getEntity());
//			System.out.println(result);
//		}
		HttpClient httpClient = HttpClients.createDefault();
		String url="http://www.weather.com.cn/weather/101010100.shtml";
		HttpGet httpGet=new HttpGet(url);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		if(httpResponse.getStatusLine().getStatusCode()==200){
			//获取返回值的全部信息
			String result=EntityUtils.toString(httpResponse.getEntity());
			System.out.println(result);
		}
	}
}
