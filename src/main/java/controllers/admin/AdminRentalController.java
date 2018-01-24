package controllers.admin;

import common.Main;
import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class AdminRentalController {

    @FXML
    private Button createOrderButton;

    @FXML
    private Button editOrderButton;

    @FXML
    private Button generateConfirmationButton;

    @FXML
    private Button openConfirmationDirectoryButton;

    @FXML
    private TableColumn<Item, Integer> item_idColumn;

    @FXML
    private TableColumn<Item, Double> bailColumn;

    @FXML
    private TableColumn<Item, String> nameColumn;

    @FXML
    private TableColumn<Item, Double> rentalpriceColumn;

    @FXML
    private TableColumn<Item, Integer> countColumn;

    @FXML
    private TableColumn<Item, String> itemNameColumn;

    @FXML
    private TableColumn<Item, Integer> rentalCountColumn;

    @FXML
    private TableColumn<Item, String> rentalNameColumn;

    @FXML
    private Button clearSearch;


    @FXML
    private TableColumn<Item, Integer> rental_idColumn;

    @FXML
    private TextArea clientTextArea;

    @FXML
    private ComboBox<Client> clientComboBox;

    @FXML
    private TableView<Item> itemTable;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<Item> rentalTable;

    private ObservableList<Item> rentalList = FXCollections.observableArrayList();

    @FXML
    void search() {
        //wyszukiwanie
        ObservableList<Item> masterData = ItemDAO.getItemList();
        FilteredList<Item> filteredData = new FilteredList<>(masterData, p -> true);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(item -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();

            return item.getName().toLowerCase().contains(lowerCaseFilter);
        }));
        SortedList<Item> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(itemTable.comparatorProperty());
        itemTable.setItems(sortedData);
    }

    @FXML
    void clearSearch(ActionEvent event) {
        searchTextField.clear();
    }

    private void addToOrder(Item rowData) {
        if (!rentalList.contains(rowData)) {
            rentalList.add(rowData);
            refreshOrderTable();
        } else {
            displayErrorDialogBox("Dodawanie do zamówienia", "Wybrany przedmiot znajduje się już na liście zamówienia.");
        }
    }

    private void removeFromOrder(Item rowData) {
        rentalList.remove(rowData);
        refreshOrderTable();
    }

    @FXML
    void comboBoxSelection() {
        clientTextArea.setText("Wybrano klienta: \n" +
                "ID: " + clientComboBox.getValue().getClient_id() + "\n" +
                "Nazwa: " + clientComboBox.getValue().getName() + "\n" +
                "Adres: " + clientComboBox.getValue().getAddress() + "\n" +
                "Kod pocztowy: " + clientComboBox.getValue().getPostalcode() + "\n" +
                "Telefon: " + clientComboBox.getValue().getTelephonenumber() + "\n" +
                "NIP: " + clientComboBox.getValue().getNip());
    }

    @FXML
    void refreshItemTable() {
        ObservableList<Item> observableList = ItemDAO.getItemList();
        itemTable.setItems(observableList);
    }

    @FXML
    void refreshOrderTable() {
        rentalTable.setItems(rentalList);
    }

    private void displayErrorDialogBox(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void createOrder(ActionEvent event) {
        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            List<ItemEntity> itemList = new ArrayList<>();
            List<ClientEntity> clientList = new ArrayList<>();
            ClientEntity clientEntity = manager.find(ClientEntity.class, clientComboBox.getValue().getClient_id());

            for (Item item: rentalList) {
                // use currInstance

                ItemEntity itemEntity = manager.find(ItemEntity.class, item.getItem_id());
                itemEntity.setClientList(clientList);
                itemList.add(itemEntity);
                manager.persist(itemEntity);
            }
            clientEntity.setItemList(itemList);
            manager.persist(clientEntity);
            transaction.commit();

        } catch (Exception e)
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally
        {
            manager.close();
        }
    }


        @FXML
        void generateConfirmation (ActionEvent event){

        }

        @FXML
        void openConfirmationFolder (ActionEvent event){

        }

        @FXML
        void editOrder (ActionEvent event){

        }


        public void initialize () {
            //wypelnianie combobox
            ObservableList<Client> clientList = ClientDAO.getClientList();
            clientComboBox.setItems(clientList);

            //inicjalizacja tabeli Przedmioty
            item_idColumn.setCellValueFactory(cellData -> cellData.getValue().item_idProperty().asObject());
            nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            bailColumn.setCellValueFactory(cellData -> cellData.getValue().bailProperty().asObject());
            rentalpriceColumn.setCellValueFactory(cellData -> cellData.getValue().rentalpriceProperty().asObject());
            countColumn.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());

            //inicjalizacja tabeli Zamówienie
            rental_idColumn.setCellValueFactory(cellData -> cellData.getValue().item_idProperty().asObject());
            rentalNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            //rentalCountColumn.setCellValueFactory(cellData -> cellData.getValue().);

            //wyswietlenie tabeli
            refreshItemTable();

            //wyłączenie textarea
            clientTextArea.setEditable(false);

            //doubleclick tabeli Przedmioty
            itemTable.setRowFactory(tv -> {
                TableRow<Item> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Item rowData = row.getItem();
                        addToOrder(rowData);
                    }
                });
                return row;
            });

            //doubleclick tabeli Zamówienie
            rentalTable.setRowFactory(tv -> {
                TableRow<Item> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Item rowData = row.getItem();
                        removeFromOrder(rowData);
                    }
                });
                return row;
            });
        }
    }
