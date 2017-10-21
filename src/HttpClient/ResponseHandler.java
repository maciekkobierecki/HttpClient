package HttpClient;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

public class ResponseHandler implements org.apache.http.client.ResponseHandler<String> {

	@Override
	public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		int status=response.getStatusLine().getStatusCode();
		if(status>=200 && status<300)
			System.out.println("OK");
		else 
			System.out.println("Sth went wrong:"+ status);
		return null;
	}

}
