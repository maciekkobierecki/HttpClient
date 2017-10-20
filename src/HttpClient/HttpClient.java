package HttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


//This class is using HTTP POST request to communicate with 
//HttpServer and provides functions:
//getLogInfo - using for request server about existing log events
//createEventOnServer - using for create event table on server (if not exists)
//addEvent - for add row in existing table 
public class HttpClient {
	private CloseableHttpClient httpClient;
	private HttpGet request;
	CloseableHttpResponse response;
	
	
	public HttpClient(){
		httpClient=HttpClients.createDefault();
	}
	
	public void sendGetInfoRequest()throws Exception{
		//sparametryzowaæ
		request=new HttpGet("http://localhost:5020/getInfo");
		response=httpClient.execute(request);
		BufferedReader br;
		br=new BufferedReader(new InputStreamReader(response
                .getEntity().getContent()));
		String line;
		while((line=br.readLine())!=null)
			System.out.println(line);
	}
}
