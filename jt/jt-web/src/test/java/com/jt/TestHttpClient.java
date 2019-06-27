package com.jt;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.RequestLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.util.HttpClientService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHttpClient {
	
	@Autowired
	private CloseableHttpClient httpClient;
	
	
	@Test
	public void testGet() throws Throwable, IOException {
		//定义httpClient实例
		//CloseableHttpClient httpClient = HttpClients.createDefault();
		String url = "https://www.baidu.com";
		HttpGet httpGet = new HttpGet(url);

		//获取请求的全部参数   GET https://item.jd.com/5236335.html?dist=jd HTTP/1.1
		RequestLine requestLine = httpGet.getRequestLine();

		//获取HTTP响应
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

		if(httpResponse.getStatusLine().getStatusCode() == 200){
			System.out.println("获取请成功");
			//获取HTML
			String  html = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
			System.out.println(html);
		}
	}
	
	@Autowired
	private HttpClientService httpClientService;
	@Test
	public void testUtil() {
		String url = "https://www.baidu.com";
		String result = httpClientService.doGet(url);
		System.out.println("获取结果:"+result);
	}
}
