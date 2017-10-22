package HttpClient;

import java.io.IOException;
import java.util.Scanner;

public class main {
	
	
	
	public static void main(String[] args) {
		Config.init();
		HttpClient hc=new HttpClient();
	try{	
		Scanner userInput=new Scanner(System.in);
		while(true){
			System.out.println(">");
			String input = userInput.nextLine();
			switch(input){
			case "getinfo":
				hc.sendGetInfoRequest();
				break;
			case "insert":
				System.out.println("Podaj: \nNazwa Tabeli|"
						+ "wartoœæ kolumny1|wartoœæ kolumny2|...|wartoœæ kolumny n\n>");
				input=userInput.nextLine();
				hc.sendPostRequest(Config.getProperty("insert"),input.split("|"));
				break;
			case "create table":
				System.out.println("Podaj: \nNazwa Tabeli|"
						+ "wartoœæ kolumny1|wartoœæ kolumny2|...|wartoœæ kolumny n\n>");
				input=userInput.nextLine();
				hc.sendPostRequest(Config.getProperty("create"),input.split("|"));
				break;
			case "ls":
				System.out.println(Config.getProperty("ls"));
				
			}
			if(input.equals("exit"))
				System.exit(0);
			}
		}
		catch(Exception e){
			System.out.println("error");
		}
		
		

	}

}
