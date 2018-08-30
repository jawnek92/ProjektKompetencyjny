package App;

import App.WebSerching.OlxElements;
import App.data.Advert;
import App.data.OlxData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private ComboBox<String> categoriesComboBox;

    @FXML
    private ListView<Advert> leftListView;

    private List<Advert> advertsList;

    @FXML
    public void initialize(){
        OlxData olxData = new OlxData();
        advertsList = olxData.getOlxDataList();

        ObservableList<Advert> olxObservableList = FXCollections.observableList(advertsList);

        leftListView.setItems(olxObservableList);
        leftListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        leftListView.getSelectionModel().selectFirst();
        leftListView.setStyle("-webkit-fx-font-size: 20;-moz-fx-font-size: 20;-ms-fx-font-size: 20;-o-fx-font-size: 20;-khtml-fx-font-size: 20;fx-font-size: 20; -fx-font-weight: bold");
        leftListView.setCellFactory(new Callback<ListView<Advert>, ListCell<Advert>>() {
            @Override
            public ListCell<Advert> call(ListView<Advert> param) {
                ListCell<Advert> cell = new ListCell<Advert>() {
                    @Override
                    public void updateItem(Advert item, boolean empty){
                        super.updateItem(item, empty);
                        if(empty){
                            setItem(null);
                        }else{
                            setText(item.getTitle() + item.getPrice());
                        }
                    }
                };
                return cell;
            }
        });



//        leftListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Advert>() {
//            @Override
//            public void changed(ObservableValue<? extends Advert> observable, Advert oldValue, Advert newValue) {
//                Advert newAdvert = leftListView.getSelectionModel().getSelectedItem();
//
//            }
//        });

    }
}
