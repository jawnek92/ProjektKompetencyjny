package App.WebSerching;
import java.util.ArrayList;
import java.util.List;

import com.jaunt.*;

public class SprzedajemyElements {

	private String WebSite;

	UserAgent myAgent = new UserAgent();

	
	// Tworze konstruktor w ktorym podczas tworzenia jest podawany link do strony. 
	
	public SprzedajemyElements(String webSite) {
		super();
		WebSite = webSite;

		try {
			myAgent.visit(WebSite);
		} catch (JauntException e) {
			
			System.out.println("Error" + e);

		}

	}
	
	
	public List<String> getTitlesFromSprzedajemy() 
	{
		List <String> titlesList = new ArrayList(); 
		
		Elements Items = myAgent.doc.findEvery("<h2 class=\"title\">");   
		
		for(Element table : Items){ 
			
			
			
		  // System.out.println(table.innerText());
		   
		   	titlesList.add(table.innerText().replaceAll("\\s+"," "));
		   
		  } 
		  
		  
		  return titlesList; 
	}
	
	public List<String> getPricesFromSprzedajemy() 
	{
		List <String> titlesList = new ArrayList(); 
		
		Elements Items = myAgent.doc.findEvery("<span class=\"price\">");   
		
		for(Element table : Items){ 
			
			
			
		  // System.out.println(table.innerText());
		   
		   	titlesList.add(table.innerText().replaceAll("\\s+"," "));
		   
		  } 
		  
		  
		  return titlesList; 
	}
	
	
	public List<String> getLinkToItem()
	{
		List <String> titlesList = new ArrayList(); 
		
		Elements Items = myAgent.doc.findEvery("<a class='offerLink'>");   
		
		for(Element table : Items){ 
			
			
		  //System.out.println(table.innerXML());
			
			
		   
		   	titlesList.add(table.getAtString("href").replaceAll("\\s+"," "));
		   
		  } 
		  
		  
		  return titlesList; 
	}
	
	public List<String> getImageSprzedajemy()
	{
		List <String> titlesList = new ArrayList(); 
		
		Elements Items = myAgent.doc.findEvery(" <span class=\"listImgWrp\">");   
		
		for(Element table : Items){ 
			
			
			
			//System.out.println(table.innerXML());
		   
		   	//titlesList.add(table.getAtString("").replaceAll("\\s+"," "));
		   	
		   	String list[] = table.innerXML().split(" ");
		   			
		   	for (String s : list)
		   	{
		   		System.out.println(s);
		   		
		   		if(s.startsWith("src"))
		   		{
		   			s = s.substring(5,s.length()-1);
		   			titlesList.add(s); 
		   		}
		   	}
		   
		  } 
		  
		  
		  return titlesList; 
	}



}
