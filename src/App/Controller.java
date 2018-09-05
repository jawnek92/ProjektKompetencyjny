package App;

import App.WebSerching.OlxElements;
import App.data.Advert;
import App.data.OlxData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Controller {
    /*Categories line*/
    @FXML
    private ComboBox<String> categoriesComboBox;
    @FXML
    private ComboBox<String> secondCategoriesComboBox;

    /*Searching*/
    @FXML
    private TextField searchTextField;


    @FXML
    private ListView<Advert> leftListView;
    @FXML
    private ImageView leftImageView;

    private List<Advert> advertsList;

    @FXML
    public void initialize(){
        /*Categories line*/
        categoriesComboBox.getSelectionModel().select(0);
        secondCategoriesComboBox.getSelectionModel().select(0);
        categoriesComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                switch(categoriesComboBox.getValue()){
                    case "Motoryzacja":
                        secondCategoriesComboBox.getItems().remove(0, secondCategoriesComboBox.getItems().size());
                        secondCategoriesComboBox.getItems().addAll("Samochody", "Motocykle");
                        break;
                    case "Nieruchomosci":
                        secondCategoriesComboBox.getItems().remove(0, secondCategoriesComboBox.getItems().size());
                        secondCategoriesComboBox.getItems().addAll("Mieszkania", "Domy", "Stancje i pokoje");
                        break;
                    case "Elektronika":
                        secondCategoriesComboBox.getItems().remove(0, secondCategoriesComboBox.getItems().size());
                        secondCategoriesComboBox.getItems().addAll("Telefony", "Tablety", "Telewizory", "Komputery");
                        break;
                    case "Moda":
                        secondCategoriesComboBox.getItems().remove(0, secondCategoriesComboBox.getItems().size());
                        secondCategoriesComboBox.getItems().addAll("Ubrania", "Buty", "Bielizna", "Zegarki");
                        break;
                }
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

    @FXML
    private void handleSearch(){
        /*Left list view*/
        String category = categoriesComboBox.getValue().toLowerCase(new Locale("PL", "pl"));
        String secondCategory = secondCategoriesComboBox.getValue().toLowerCase();

        String pattern = "https://www.olx.pl/"; //motoryzacja/samochody/q-
        OlxData olxData = new OlxData();
        String searchText = searchTextField.getText();
        String[] strings = searchText.split(" ");
        StringBuilder link = new StringBuilder();
        link.append(pattern);
        if(secondCategory.equals("stancje i pokoje"))
            secondCategory = "stancje-pokoje";
        link.append(category).append("/").append(secondCategory).append("/q-");
        for(int i=0; i<strings.length; i++){
            if (i<strings.length-1)
                link.append(strings[i].toLowerCase(new Locale("PL", "pl"))).append("-");
            else
                link.append(strings[i].toLowerCase(new Locale("PL", "pl"))).append("/");
        }
        advertsList = olxData.getOlxDataList(link.toString());
        ObservableList<Advert> olxObservableList = FXCollections.observableList(advertsList);

        leftListView.setItems(olxObservableList);
        leftListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        leftListView.getSelectionModel().selectFirst();
        leftListView.setStyle("-webkit-fx-font-size: 20;-moz-fx-font-size: 20;-ms-fx-font-size: 20;-o-fx-font-size: 20;-khtml-fx-font-size: 20;fx-font-size: 20; -fx-font-weight: bold");
        leftListView.setFixedCellSize(30);
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
//                            setHeight(50);
                            setText(item.getTitle() + item.getPrice());//ToDO dodac wyswietlanie zdjecia, zmienic wysokosc wyswietlanych komorek;
                        }
                    }

                };
                return cell;
            }
        });
    }
}
