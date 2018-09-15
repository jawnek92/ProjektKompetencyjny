package App;

import App.data.Advert;
import App.data.Data;

import java.util.List;

public class OlxController implements Runnable {
    String link;
    Data data;
    List<Advert> list;

    public OlxController(String link) {
        this.link = link;
        data = new Data();
    }
    @Override
    public void run() {
        list = this.data.getOlxDataList(this.link);
    }
    public List<Advert> getList() {
        return list;
    }
}
