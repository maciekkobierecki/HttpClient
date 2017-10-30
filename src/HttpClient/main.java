package HttpClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
				ArrayList<String>parameters=new ArrayList<>();
				String line;
				System.out.println("Pass information in format:\n EventName \n ColumnName1=...\n ColumnName2=...\n ...\n ColumnNameN=...;\n"
						+ "Attention! date and id column are automatic filled. Please don't input parameters: id=.. and date=..");
				sendRequest(Config.getProperty("insert"), userInput, hc);
				break;
			case "create table":
				System.out.println("Pass information in format:\n EventName \n Column1=name1\n ColumnName2=name2\n ...\n ColumnNameN=nameN;\n"
						+ "Attention! date and id column are created automatically. Please don't input id and date columns");
				sendRequest(Config.getProperty("create"), userInput, hc);
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
	public static String removeEndCharacter(String str){
		if (str != null && str.length() > 0) 
	        str = str.substring(0, str.length() - 1);
	    return str;
	}
	public static void sendRequest(String function, Scanner userInput, HttpClient hc) throws Exception{
		String line;
		ArrayList<String>parameters=new ArrayList<>();
		while(true){
			line=userInput.nextLine();				
			if(line.substring(line.length()-1).equals(";")){
				line=removeEndCharacter(line);
				parameters.add(line);
				break;
			}
			else
				parameters.add(line);
			
		}
		if(function.equals("insert")){
			String date="date="+getCurrentDate();
			parameters.add(date);
		}
		hc.sendPostRequest(Config.getProperty(function),parameters);
	}
	public static String getCurrentDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
