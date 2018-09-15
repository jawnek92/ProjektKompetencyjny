package App;

import App.data.Advert;
import App.data.Data;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
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
    private VBox rightVBox;
    @FXML
    private HBox rightHBox;

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
                        secondCategoriesComboBox.getSelectionModel().select(0);
                        break;
                    case "Nieruchomosci":
                        secondCategoriesComboBox.getItems().remove(0, secondCategoriesComboBox.getItems().size());
                        secondCategoriesComboBox.getItems().addAll("Mieszkania", "Domy", "Stancje i pokoje");
                        secondCategoriesComboBox.getSelectionModel().select(0);
                        break;
                    case "Elektronika":
                        secondCategoriesComboBox.getItems().remove(0, secondCategoriesComboBox.getItems().size());
                        secondCategoriesComboBox.getItems().addAll("Telefony", "Tablety", "Telewizory", "Komputery");
                        secondCategoriesComboBox.getSelectionModel().select(0);
                        break;
                    case "Moda":
                        secondCategoriesComboBox.getItems().remove(0, secondCategoriesComboBox.getItems().size());
                        secondCategoriesComboBox.getItems().addAll("Ubrania", "Buty", "Bielizna", "Zegarki");
                        secondCategoriesComboBox.getSelectionModel().select(0);
                        break;
                }
            }
        });
    }

    @FXML
    private void handleClickLeftListView(){
        Advert advert = leftListView.getSelectionModel().getSelectedItem();
        if(advert == null)
            System.out.println("NO items.");
        else{
            ImageView image = new ImageView(advert.getImage());
            image.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        Desktop.getDesktop().browse(new URL(advert.getLinkToAdvertisment()).toURI());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            });
            Label label = new Label(advert.getTitle());
            label.setWrapText(true);
            label.setFont(Font.font("Times New Roman", 20));
            Label label2 = new Label("Cena: " + advert.getPrice());
            label2.setWrapText(true);
            if(rightHBox.getChildren().isEmpty()){
                rightHBox.getChildren().setAll(label, image);
                rightVBox.getChildren().setAll(rightHBox, label2);
            }else{
                rightHBox = new HBox();
                rightHBox.getChildren().retainAll(label, image);
                rightVBox.getChildren().retainAll(rightHBox, label2);
                rightHBox.getChildren().setAll(label, image);
                rightVBox.getChildren().setAll(rightHBox, label2);
            }
        }
    }
    @FXML
    private void handleSearch(){
        /*Left list view*/

        advertsList = getOlxData();
        advertsList.addAll(getGratkaData());



        ObservableList<Advert> observableList = FXCollections.observableList(advertsList);
        leftListView.setItems(observableList);
        leftListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
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
                            setText((leftListView.getItems().indexOf(item)+1) +". "+ item.getTitle() + item.getPrice());
                        }
                    }

                };
                return cell;
            }
        });
    }

    class OlxTask extends Task<ObservableList<Advert>>{
        @Override
        protected ObservableList<Advert> call() throws Exception {
            ObservableList<Advert> observableList = FXCollections.observableList(getOlxData());
            return observableList;
        }
    }
    private List<Advert> getOlxData(){
        String category = categoriesComboBox.getValue().toLowerCase();
        String secondCategory = secondCategoriesComboBox.getValue().toLowerCase();

        String pattern = "https://www.olx.pl/"; //motoryzacja/samochody/q-
        Data olxData = new Data();
        String searchText = searchTextField.getText();
        String[] strings = searchText.split(" ");
        StringBuilder link = new StringBuilder();
        link.append(pattern);
        if(secondCategory.equals("stancje i pokoje"))
            secondCategory = "stancje-pokoje";
        else if(secondCategory.equals("motocykle"))
            secondCategory = "motocykle-skutery";
        link.append(category).append("/").append(secondCategory).append("/q-");
        for(int i=0; i<strings.length; i++){
            if (i<strings.length-1)
                link.append(strings[i].toLowerCase(new Locale("PL", "pl"))).append("-");
            else
                link.append(strings[i].toLowerCase(new Locale("PL", "pl"))).append("/");
        }

        OlxController olxController = new OlxController(link.toString());
        Thread thread = new Thread(olxController);
        thread.start();
        while(!thread.getState().equals(Thread.State.TERMINATED)){}
        try{
            return olxController.getList();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private List<Advert> getGratkaData(){
        String category = categoriesComboBox.getValue().toLowerCase();
        String secondCategory = secondCategoriesComboBox.getValue().toLowerCase();

        String pattern = "https://gratka.pl/";//motoryzacja/motocykle?q"
        Data gratkaData = new Data();
        String searchText = searchTextField.getText();
        String[] strings = searchText.split(" ");
        StringBuilder link = new StringBuilder();
        if(secondCategory.equals("stancje i pokoje"))
            secondCategory = "pokoje";
        else if(secondCategory.equals("samochody"))
            secondCategory = "osobowe";
        link.append(pattern).append(category).append("/").append(secondCategory);
        if(strings.length!=0) {
            link.append("?q=");
            for (int i = 0; i < strings.length; i++) {
                if (i < strings.length - 1) {
                    link.append(strings[i].toLowerCase(new Locale("PL", "pl"))).append("+");
                } else
                    link.append(strings[i].toLowerCase(new Locale("PL", "pl")));
            }
        }

        GratkaController gratkaController = new GratkaController(link.toString());
        Thread thread = new Thread(gratkaController);
        thread.start();
        while(!thread.getState().equals(Thread.State.TERMINATED)){}
        try{
            return gratkaController.getList();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

//    private List<Advert> getSprzedajemyData() {
//        //https://sprzedajemy.pl/szukaj?inp_text=&inp_category_id=2&catCode=6bea9f&inp_location_id=1 - motoryzacja
//        //https://sprzedajemy.pl/szukaj?inp_text=&inp_category_id=6&catCode=6bea9f&inp_location_id=1 - samochody osobowe
//        //https://sprzedajemy.pl/szukaj?inp_text=&inp_category_id=452&catCode=6bea9f&inp_location_id=1 - motocykle
//        //https://sprzedajemy.pl/szukaj?inp_text=&inp_category_id=36&catCode=1b8e55&inp_location_id=1 - mieszkania
//        //https://sprzedajemy.pl/szukaj?inp_text=&inp_category_id=3&catCode=1b8e55&inp_location_id=1 - nieruchomosci
//        //https://sprzedajemy.pl/szukaj?inp_text=&inp_category_id=1088&catCode=0010c9&inp_location_id=1 - telefony
//        //https://sprzedajemy.pl/szukaj?inp_text=&inp_category_id=50&catCode=0010c9&inp_location_id=1 - komputery
//        //https://sprzedajemy.pl/szukaj?inp_text=&inp_category_id=1124&catCode=0010c9&inp_location_id=1 tv
//        //https://sprzedajemy.pl/szukaj?inp_text=&inp_category_id=35&catCode=1b8e55&inp_location_id=1 - domy
//        //https://sprzedajemy.pl/szukaj?inp_text=&inp_category_id=293&catCode=1b8e55&inp_location_id=1 - lokale uzytkowe
//
//        String category = categoriesComboBox.getValue().toLowerCase();
//        String secondCategory = secondCategoriesComboBox.getValue().toLowerCase();
//
//        String pattern = "https://sprzedajemy.pl/";//motoryzacja/motocykle?q"
//        Data sprzedajemyData = new Data();
//        String searchText = searchTextField.getText();
//        String[] strings = searchText.split(" ");
//        StringBuilder link = new StringBuilder();
//        switch(categoriesComboBox.getValue()){
//            case "Motoryzajca" :
//                if(secondCategoriesComboBox.getValue().equals("Samochody"));
//        }
//        link.append(pattern).append(category).append("/").append(secondCategory);
//        for(int i=0; i<strings.length; i++){
//            if(i<strings.length-1){
//                link.append(strings[i].toLowerCase(new Locale("PL", "pl"))).append("+");
//            }else
//                link.append(strings[i].toLowerCase(new Locale("PL", "pl")));
//        }
//        return null;
//    }


}
