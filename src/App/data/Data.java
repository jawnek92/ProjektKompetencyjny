package App.data;

import App.WebSerching.GratkaElements;
import App.WebSerching.OlxElements;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private static Data instance = new Data();
    private List<Advert> dataList;

    public static Data getInstance() { return instance; }

    public Data() {
        this.dataList = new ArrayList<>();
    }

    public List<Advert> getGratkaDataList(String link){
        dataList = new ArrayList<>();
        GratkaElements gratkaElements = new GratkaElements(link);
        List<String> gratkaTitles = gratkaElements.getTitlesFromGratka();
        List<String> gratkaLinks = gratkaElements.getLinkToItem();
        List<String> gratkaImages = gratkaElements.getImageFromGratka();
        List<String> gratkaPrices = gratkaElements.getPricesFromGratka();
        int size = gratkaTitles.size();
        int[] sizes = {gratkaPrices.size(), gratkaImages.size(), gratkaLinks.size()};
        for (int i : sizes){
            System.out.println(i);
            if(size > i)
                size = i;
        }
        for(int i=0; i<size; i++){
            Advert tempAd = new Advert(gratkaTitles.get(i), gratkaPrices.get(i), gratkaLinks.get(i), gratkaImages.get(i));
//            System.out.println("Advert: " + (i + 1) + gratkaTitles.get(i) + gratkaPrices.get(i));
            dataList.add(tempAd);
        }
//        System.out.println(gratkaTitles.size() +" "+ gratkaImages.size() +" "+ gratkaLinks.size() +" "+ gratkaPrices.size());
        return dataList;
    }

    public List<Advert> getOlxDataList(String link) {
        dataList = new ArrayList<>();
        OlxElements olxElements = new OlxElements(link);
        List<String> olxTitles = olxElements.getTitlesFromOlx();
        List<String> olxLinks = olxElements.getLinkToItem();
        List<String> olxImages = olxElements.getImageItemsOlx();
        List<String> olxPrices = olxElements.getPricesFromOlx();

//        dataList.add(new Advert("Sprzedam Astre", "7500", "https://olx.pl", "https://www.google.com/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwiI69qyq5XdAhVIZFAKHVQXB0QQjRx6BAgBEAU&url=https%3A%2F%2Fwww.otomoto.pl%2Foferta%2Fopel-astra-sedan-1-4-turbo-140km-fabryczne-lpg-letnia-wyprzedaz-opla-ID6yU9zb.html&psig=AOvVaw23WT_n0zKGGAJqOWA0Rn-T&ust=1535737833052171"));

        for(int i=0; i<olxTitles.size(); i++){
            Advert tempAd = new Advert(olxTitles.get(i), olxPrices.get(i), olxLinks.get(i), olxImages.get(i));
//            System.out.println("Advert "+ (i+1) +": "+ tempAd.getTitle()+", "+tempAd.getPrice());
            dataList.add(tempAd);
        }
        return dataList;
    }
}
