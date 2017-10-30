package HttpClient;

import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.*;


//This class is using HTTP POST request to communicate with 
//HttpServer and provides functions:
//getLogInfo - using for request server about existing log events
//createEventOnServer - using for create event table on server (if not exists)
//addEvent - for add row in existing table 
public class HttpClient {
	private CloseableHttpClient httpClient;
	private HttpGet request;
	CloseableHttpResponse response;
	private ResponseHandler responseHandler;
	private Integer clientId;
	
	public HttpClient(){
		httpClient=HttpClients.createDefault();
		responseHandler=new ResponseHandler();
		String idString=Config.getProperty("id");
		if(idString==null)
			try {
				sendGiveMeIdRequest();
			} catch (Exception e) {
				System.out.println("Unable to get client id. Can't start client");
			}
			
		
	}
	public void sendGiveMeIdRequest() throws Exception{
		request=new HttpGet(Config.getURL("giveMeId"));
		response=httpClient.execute(request);
		BufferedReader br;
		br=new BufferedReader(new InputStreamReader(response
                .getEntity().getContent()));
		String line=br.readLine();
		JSONObject idObject=new JSONObject(line);
		Integer id=(Integer) idObject.get("id");
		Config.setProperty("id", id.toString());
		
	}
	
	public void sendGetInfoRequest()throws Exception{
		request=new HttpGet(Config.getURL("getInfo"));
		response=httpClient.execute(request);
		BufferedReader br;
		br=new BufferedReader(new InputStreamReader(response
                .getEntity().getContent()));
		String line;
		StringBuilder stringBuilder=new StringBuilder();
		while((line=br.readLine())!=null)
			stringBuilder.append(line);
		String response=stringBuilder.toString();
		displayTables(response);
	}
	public void displayTables(String response){
		try {
			JSONObject responseJSON=new JSONObject(response);
			Iterator<String> tableIterator=responseJSON.keys();
			while(tableIterator.hasNext()){
				JSONObject tableJSON=responseJSON.getJSONObject(tableIterator.next());
				JSONObject columnsJSON=tableJSON.getJSONObject("Columns");
				System.out.println("Event name: " +tableJSON.getString("tableName")+"\nColumns:");
				Iterator<String>columnIterator=columnsJSON.keys();
				while(columnIterator.hasNext()){
					JSONObject columnJSON=columnsJSON.getJSONObject(columnIterator.next());
					System.out.println(columnJSON.getString("columnName")+" "+ columnJSON.getString("columnType")+" "+columnJSON.get("size").toString());
				}
				System.out.println("\n");
				}
	
			
		} catch (JSONException e) {
			System.out.println("unable to display metadata");
		}
		
	}
	public void sendPostRequest(String function, ArrayList<String>values)throws Exception{
		HttpPost post=new HttpPost(Config.getURL(function));
		JSONObject responseBodyJSON=new JSONObject();
		responseBodyJSON.put("tableName",values.get(0));
		String[] keyValuePair;
		for(int i=1; i<values.size(); i++){
			keyValuePair=values.get(i).split("=");
			responseBodyJSON.put(keyValuePair[0],keyValuePair[1]);
		}
		HttpEntity entity=new ByteArrayEntity(responseBodyJSON.toString().getBytes());
		post.setEntity(entity);
		httpClient.execute(post, responseHandler);
				
	}

	
}
