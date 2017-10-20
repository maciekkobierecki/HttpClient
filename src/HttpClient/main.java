package HttpClient;

public class main {

	public static void main(String[] args) {
		HttpClient hc=new HttpClient();
		try{
		hc.sendGetInfoRequest();
		}
		catch(Exception e){
			System.out.println("error");
		}

	}

}
