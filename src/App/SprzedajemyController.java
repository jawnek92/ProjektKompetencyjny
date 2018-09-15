package App;

import App.data.Advert;
import App.data.Data;

import java.util.List;

public class SprzedajemyController implements Runnable {
    String link;
    Data data;
    List<Advert> list;

    public SprzedajemyController(String link) {
        this.link = link;
        data = new Data();
    }
    @Override
    public void run() {
        list = this.data.getSprzedajemyDataList(this.link);
    }
    public List<Advert> getList() {
        return list;
    }
}
