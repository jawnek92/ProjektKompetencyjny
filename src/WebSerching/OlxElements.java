import java.util.ArrayList;
import java.util.List;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;

public class OlxElements {

	private String WebSite;

	UserAgent myAgent = new UserAgent();

	
	// Tworze konstruktor w ktorym podczas tworzenia jest podawany link do strony. 
	
	public OlxElements(String webSite) {
		super();
		WebSite = webSite;

		try {
			myAgent.visit(WebSite);
		} catch (JauntException e) {
			
			System.out.println("Error" + e);

		}

	}
	
	
	public List<String> getTitlesFromOlx() 
	{
		List <String> titlesList = new ArrayList(); 
		
		Elements Items = myAgent.doc.findEvery("<a class='marginright5 link linkWithHash detailsLink'>");   
		
		for(Element table : Items){ 
			
			
			
		  // System.out.println(table.innerText());
		   
		   	titlesList.add(table.innerText().replaceAll("\\s+"," "));
		   
		  } 
		  
		  
		  return titlesList; 
	}
	
	public List<String> getPricesFromOlx() 
	{
		List <String> titlesList = new ArrayList(); 
		
		Elements Items = myAgent.doc.findEvery("<p class='price'>");   
		
		for(Element table : Items){ 
			
			
			
		  // System.out.println(table.innerText());
		   
		   	titlesList.add(table.innerText().replaceAll("\\s+"," "));
		   
		  } 
		  
		  
		  return titlesList; 
	}
	
	
	public List<String> getLinkToItem()
	{
		List <String> titlesList = new ArrayList(); 
		
		Elements Items = myAgent.doc.findEvery("<a class='marginright5 link linkWithHash detailsLink'>");   
		
		for(Element table : Items){ 
			
			
			
		  // System.out.println(table.innerText());
		   
		   	titlesList.add(table.getAtString("href").replaceAll("\\s+"," "));
		   
		  } 
		  
		  
		  return titlesList; 
	}
	
	public List<String> getImageItemsOlx()
	{
		List <String> titlesList = new ArrayList(); 
		
		Elements Items = myAgent.doc.findEvery("<img class='fleft'>\"");   
		
		for(Element table : Items){ 
			
			
			
		  // System.out.println(table.innerText());
		   
		   	titlesList.add(table.getAtString("src").replaceAll("\\s+"," "));
		   
		  } 
		  
		  
		  return titlesList; 
	}


}
