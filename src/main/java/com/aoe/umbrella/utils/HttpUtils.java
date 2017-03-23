package com.aoe.umbrella.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpUtils {
	public static String doGet(String url) {
        StringBuffer response = new StringBuffer();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = null ;
        BufferedReader reader = null ;
        try {
        	httpResponse = httpClient.execute(httpGet);      	
        	if(httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200){
                 reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                 if(reader != null){
                     String inputLine;
                     while ((inputLine = reader.readLine()) != null) {
                         response.append(inputLine);
                     }
                 }
        	}
		} catch(Exception e){
			e.printStackTrace();
		}finally {
            try {
            	if(httpResponse != null){
                	httpResponse.close();
            	}
            	if(reader != null){
            		reader.close();
            	}
	            httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return response.toString() ;
	}
	
	public static String doPost(String url, Map<String, Object> entry) {
        StringBuffer response = new StringBuffer();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost   post = new HttpPost(url);
        CloseableHttpResponse httpResponse = null ;
        BufferedReader reader = null ;
        try {
        	StringEntity postingString = new StringEntity(new JsonMapper().toJson(entry), "utf-8");
        	post.setEntity(postingString);
        	post.setHeader("Content-type", "application/json");
        	httpResponse = httpClient.execute(post);      	
        	if(httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200){
                 reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                 if(reader != null){
                     String inputLine;
                     while ((inputLine = reader.readLine()) != null) {
                         response.append(inputLine);
                     }
                 }
        	}
		} catch(Exception e){
			e.printStackTrace();
		}finally {
            try {
            	if(httpResponse != null){
                	httpResponse.close();
            	}
            	if(reader != null){
            		reader.close();
            	}
	            httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return response.toString() ;
	}
	

	public static void main(String[] args) {
		Map<String, Object> entry = new HashMap<String, Object>() ;
		entry.put("touser", "owjrr0Db96eBLqnFnhRUe10tYL9s") ;
		entry.put("template_id", "9jh4CdfiS9_I8bBp1GYQijSWn7mlzcnvSrpKM-S_DdM") ;
		entry.put("page", "index") ;
		entry.put("form_id", "1484199931056") ;
		
		Map<String, Object> data = new HashMap<String, Object>() ;
		
		Map<String, Object> keyword1 = new HashMap<String, Object>() ;
		keyword1.put("value", "339208499") ;
		keyword1.put("color", "#173177") ;
		data.put("keyword1", keyword1) ;
		
		Map<String, Object> keyword2 = new HashMap<String, Object>() ;
		keyword2.put("value", "2015年01月05日 12:30") ;
		keyword2.put("color", "#173177") ;
		data.put("keyword2", keyword2) ;
		
		entry.put("data", data) ;
		entry.put("emphasis_keyword", "keyword1.DATA") ;
		System.out.println(new Date().getTime());
		//System.out.println(HttpUtils.doPost("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=XAFENpfPCjoT-m_mw_DwJ4xHr-BKObIVH6cjTh-qxBp2TTeTR5XCtWTyVr-fsebGz4-SdaPCLVvgMJ1Wp0KzjxXK9V-H3aLmp-DQcr4GUR7WTi7D6qwpnfAjLem64hgdTVVaAHAHJJ", entry));
	}
}
