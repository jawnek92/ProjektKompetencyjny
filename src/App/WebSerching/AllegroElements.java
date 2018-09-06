package App.WebSerching;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.*;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.NotFound;
import com.jaunt.UserAgent;

public class AllegroElements {

	private String WebSite;

	UserAgent myAgent = new UserAgent();
	HtmlCleaner cleaner = new HtmlCleaner();
	CleanerProperties props = cleaner.getProperties();

	// Tworze konstruktor w ktorym podczas tworzenia jest podawany link do strony.

	public AllegroElements(String webSite) {
		super();

		File file = null;
		WebSite = webSite;

		props.setTranslateSpecialEntities(true);
		props.setTransResCharsToNCR(true);
		props.setOmitComments(true);

		// do parsing
		try {
			TagNode tagNode = new HtmlCleaner(props).clean(new URL(WebSite));

			// serialize to xml file
			new PrettyXmlSerializer(props).writeToFile(tagNode, "myWebsite.xml", "utf-8");

			file = new File("myWebsite.xml");

		} catch (Exception e) {

		}

		try {
			myAgent.open(file);
			// myAgent.visit(WebSite);

		} catch (Exception e) {

			// System.out.println("Error" + e);

		}

	}

	public List<String> getTitlesFromOAllegro() {
		List<String> titlesList = new ArrayList();

		Elements Items = myAgent.doc.findEvery("<div class=\"_8ce3910\">");

		for (Element table : Items) {

			// String temporary = table.innerText().replaceAll("\\s+"," ");

			String temp1 = null;
			try {
				temp1 = table.findFirst("<a>").innerText();
			} catch (NotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println(table.innerText());

			titlesList.add(temp1);

		}

		return titlesList;
	}

	public List<String> getPricesFromAllegro() {
		List<String> titlesList = new ArrayList();

		Elements Items = myAgent.doc.findEvery("<span class=\"ecb7eff\">");

		for (Element table : Items) {

			String temp1 = table.innerText().replaceAll("\\s+", " ");

			// System.out.println(table.innerText());

			titlesList.add(temp1.substring(0, temp1.length() - 8));

		}

		return titlesList;
	}

	public List<String> getLinkToItem() {
		List<String> titlesList = new ArrayList();

		Elements Items = myAgent.doc.findEvery("<a class=\"_8f1726f\">");

		for (Element table : Items) {

			// System.out.println(table.innerText());

			titlesList.add(table.getAtString("href").replaceAll("\\s+", " "));

		}

		return titlesList;
	}

	public List<String> getImageItemsAllegro() {
		List<String> titlesList = new ArrayList();

		Elements Items = myAgent.doc.findEvery("<a class=\"_8f1726f\">");
		String temp1 = null;

		for (Element table : Items) {

			try {
				temp1 = table.findFirst("<img>").getAtString("data-src");
			} catch (NotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			titlesList.add(temp1);

		}

		return titlesList;
	}

}
