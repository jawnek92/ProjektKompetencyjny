package App.data;

import App.WebSerching.OlxElements;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class OlxData {
    private static OlxData instance = new OlxData();
    private String webLink = "https://www.olx.pl/motoryzacja/samochody/q-Honda-crx/";
    private List<Advert> olxDataList;

    public static OlxData getInstance() { return instance; }

    public OlxData() {
        this.olxDataList = new ArrayList<>();
    }

    public List<Advert> getOlxDataList(String link) {
        loadOlxData(link);
        return olxDataList;
    }

    private void loadOlxData(String link) {
        OlxElements olxElements = new OlxElements(link);
        List<String> olxTitles = olxElements.getTitlesFromOlx();
        List<String> olxLinks = olxElements.getLinkToItem();
        List<String> olxImages = olxElements.getImageItemsOlx();
        List<String> olxPrices = olxElements.getPricesFromOlx();

//        olxDataList.add(new Advert("Sprzedam Astre", "7500", "https://olx.pl", "https://www.google.com/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwiI69qyq5XdAhVIZFAKHVQXB0QQjRx6BAgBEAU&url=https%3A%2F%2Fwww.otomoto.pl%2Foferta%2Fopel-astra-sedan-1-4-turbo-140km-fabryczne-lpg-letnia-wyprzedaz-opla-ID6yU9zb.html&psig=AOvVaw23WT_n0zKGGAJqOWA0Rn-T&ust=1535737833052171"));

        for(int i=0; i<olxTitles.size(); i++){
            Advert tempAd = new Advert(olxTitles.get(i), olxPrices.get(i), olxLinks.get(i), olxImages.get(i), olxImages.get(i));
//            System.out.println("Advert "+ (i+1) +": "+ tempAd.getTitle()+", "+tempAd.getPrice());
            olxDataList.add(tempAd);
        }
    }
}
