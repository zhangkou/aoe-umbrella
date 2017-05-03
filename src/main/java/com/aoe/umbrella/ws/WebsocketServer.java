package com.aoe.umbrella.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.aoe.umbrella.utils.JsonMapper;

@Component
@ServerEndpoint(value = "/ws/WxWebsocketServer/{openId}")
public class WebsocketServer {	
	protected static Map<String, Session> clients = Collections.synchronizedMap(new HashMap<String, Session>()) ;
	protected static Map<String, List<Object>> mq = Collections.synchronizedMap(new HashMap<String, List<Object>>()) ;
	protected static Map<String, Date> mqDate = Collections.synchronizedMap(new HashMap<String, Date>()) ;

	@OnOpen
	public void OnOpen (Session session, @PathParam("openId")String openId) {
		exit(openId) ;
		clients.put(openId, session) ;
		sendToClient(openId, "WebSocket opened") ;
		sendCacheMessage(openId) ;
		System.out.println ("WebSocket opened: "+session.getId() + " " + openId);
		System.out.println (clients);
		countClient() ;
	}
	
	@OnMessage
	public void OnMessage (Session session,@PathParam("openId")String openId,String txt) {
		sendAll(clients, txt);
		System.out.println (session.getId() + " WebSocket received message: "+txt + " " + openId);
		countClient() ;
	}
	
	@OnClose
	public void OnClose (Session session,@PathParam("openId")String openId, CloseReason reason) {
		clients.remove(openId) ;
		System.out.println(session.getId() + " Closing a WebSocket due to "+reason.getReasonPhrase() + " " + openId);
		countClient() ;
	}
	
	@OnError
	public void OnError (Session session,@PathParam("openId")String openId,Throwable throwable){
		System.out.println("error " + throwable.getMessage() + " " + openId);
		countClient();
	}
	

	public void sendAll(Map<String, Session> clients,String txt){
		for(Map.Entry<String, Session> client : clients.entrySet()){
			Session session = client.getValue() ;
			session.getAsyncRemote().sendText(txt) ;
		}
	}
	
	public static boolean sendToClient(String clientID, String message){
		boolean 	  sendSuccess = false ;
		StringBuilder info = new StringBuilder() ;		
		if(StringUtils.isNotEmpty(clientID) && StringUtils.isNotEmpty(message)){
			if(clients != null && !clients.isEmpty()){
				Session client = clients.get(clientID) ;
				if(client != null){
					if(client.isOpen()){
						client.getAsyncRemote().sendText(message) ;
						info.append(" send message to client successfully ") ;
						sendSuccess = true ;
					}else{
						clients.remove(clientID) ;
						info.append(" client is closed ") ;
					}
				}else{
					info.append(" can not find the client ") ;
				}
			}else{
				info.append(" not active clients.") ;
			}
		}else{
			info.append(" clientID or message is empty ") ;
			sendSuccess = true ;
		}
		System.out.println(info.toString());
		return sendSuccess ;
	}
	
	public static boolean sendToClient(String clientID, Object message){
		boolean sendSuccess = sendToClient(clientID, new JsonMapper().toJson(message)) ;
		if(!sendSuccess){
			cacheMessage(clientID, message, 50) ;
		}
		return sendSuccess ;
	}
	
	public static void cacheMessage(String clientID, Object message, int cacheSize){
		List<Object> cacheMessage = mq.get(clientID) ;
		if(cacheMessage == null){
			cacheMessage = new ArrayList<Object>() ;
		}
		if(cacheMessage.size() <= cacheSize){
			cacheMessage.add(message) ;
			mqDate.put(clientID, new Date()) ;
			mq.put(clientID, cacheMessage) ;
		}
	}
	
	public static boolean sendCacheMessage(String clientID){
		boolean sendStatus = false ;
		List<Object> cacheMessage = mq.get(clientID) ;
		if(cacheMessage != null && !cacheMessage.isEmpty()){
			if(clients != null && !clients.isEmpty()){
				Session client = clients.get(clientID) ;
				if(client != null){
					if(client.isOpen()){
						sendStatus = true ;
						Map<String, Object> sendMessage = new HashMap<String, Object>() ;
						sendMessage.put("timestamp", mqDate.get(clientID)) ;
						sendMessage.put("messages", 	 cacheMessage) ;
						client.getAsyncRemote().sendText(new JsonMapper().toJson(sendMessage)) ;
					}
				}
			}
		}
		if(sendStatus){
			clearCacheMessage(clientID);
		}
		return sendStatus ;
	}
	
	public static void clearCacheMessage(String clientID){
		List<Object> cacheMessage = mq.get(clientID) ;
		if(cacheMessage != null && !cacheMessage.isEmpty()){
			mq.put(clientID, null) ;
			mqDate.put(clientID, null) ;
		}
	}
	
	public void countClient(){
		System.out.println("active clients count : " + clients.size());
	}
	
	public boolean exit(String companyId){
		Boolean exit = false ;
		if(StringUtils.isNotEmpty(companyId) && clients != null && !clients.isEmpty()){
			if(clients.containsKey(companyId)){
				Session session = clients.get(companyId) ;
				if(session != null){
					try {
						sendToClient(companyId, "your account login on another device.") ;
						session.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				clients.remove(companyId) ;
				System.out.println("close the old client ...");
			}
		}
		return exit ;
	}
	
	public static Boolean isOpen(String companyId){
		Boolean isOpen = false ;
		if(StringUtils.isNotEmpty(companyId) && clients != null && !clients.isEmpty()){
			if(clients.containsKey(companyId)){
				Session session = clients.get(companyId) ;
				if(session != null){
					isOpen = session.isOpen() ;
				}
			}
		}
		return isOpen ;
	}
}
