package HttpClient;

import java.awt.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
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
	
	
	public HttpClient(){
		httpClient=HttpClients.createDefault();
		responseHandler=new ResponseHandler();
	}
	
	public void sendGetInfoRequest()throws Exception{
		//sparametryzowaæ
		request=new HttpGet(Config.getURL("getInfo"));
		response=httpClient.execute(request);
		BufferedReader br;
		br=new BufferedReader(new InputStreamReader(response
                .getEntity().getContent()));
		String line;
		while((line=br.readLine())!=null)
			System.out.println(line);
	}
	public void sendPostRequest(String function, String[] values)throws Exception{
		HttpPost post=new HttpPost(Config.getURL(function));
		ArrayList<BasicNameValuePair> params=new ArrayList<>();
		params.add(new BasicNameValuePair("tableName", values[0]));
		for(int i=1; i<values.length; i++)
			params.add(new BasicNameValuePair("col"+i, values[i]));
		post.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
		httpClient.execute(post, responseHandler);
				
	}

	
}
