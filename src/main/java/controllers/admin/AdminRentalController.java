package controllers.admin;

import common.Main;
import dao.ClientDAO;
import dao.ItemDAO;
import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tableview.ClientTableView;
import tableview.ItemTableView;
import tableview.RentalTableView;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private TableColumn<ItemTableView, Integer> item_idColumn;

    @FXML
    private TableColumn<ItemTableView, Double> bailColumn;

    @FXML
    private TableColumn<ItemTableView, String> nameColumn;

    @FXML
    private TableColumn<ItemTableView, Double> rentalpriceColumn;

    @FXML
    private TableColumn<ItemTableView, Integer> countColumn;

    @FXML
    private TableColumn<ItemTableView, String> itemNameColumn;

    @FXML
    private Button clearSearch;

    @FXML
    private TextArea clientTextArea;

    @FXML
    private ComboBox<ClientTableView> clientComboBox;

    @FXML
    private TableView<ItemTableView> itemTable;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<RentalTableView> rentalTable;

    @FXML
    private TableColumn<RentalTableView, Integer> rental_idColumn;

    @FXML
    private TableColumn<RentalTableView, String> rentalNameColumn;

    @FXML
    private TableColumn<RentalTableView, Integer> rentalCountColumn;

    private ObservableList<ItemTableView> temporaryList = FXCollections.observableArrayList();
    private ObservableList<RentalTableView> rentalList = FXCollections.observableArrayList();

    @FXML
    void search() {
        //wyszukiwanie
        ObservableList<ItemTableView> masterData = ItemDAO.getItemList();
        FilteredList<ItemTableView> filteredData = new FilteredList<>(masterData, p -> true);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(item -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();

            return item.getName().toLowerCase().contains(lowerCaseFilter);
        }));
        SortedList<ItemTableView> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(itemTable.comparatorProperty());
        itemTable.setItems(sortedData);
    }

    @FXML
    void clearSearch(ActionEvent event) {
        searchTextField.clear();
    }

    private void addToOrder(ItemTableView rowData) {
        if (!temporaryList.contains(rowData)) {
            TextInputDialog dialog = new TextInputDialog("1");
            dialog.setTitle("Dodawanie do zamówienia");
            dialog.setHeaderText(null);
            dialog.setContentText("Proszę podać ilość:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String res = result.get().toString();
                if (res.matches("^\\d+$")) {
                    temporaryList.add(rowData);
                    rentalList.add(new RentalTableView(rowData.getItem_id(), rowData.getName(), Integer.parseInt(res)));
                    refreshOrderTable();
                } else {
                    displayErrorDialogBox("Dodawanie do zamówienia", "Podana wartość musi być liczbą całkowitą");
                }
            }
        } else {
            displayErrorDialogBox("Dodawanie do zamówienia", "Wybrany przedmiot znajduje się już na liście zamówienia.");
        }
    }

    private void removeFromOrder() {

        temporaryList.remove(rentalTable.getSelectionModel().getSelectedIndex());
        rentalList.remove(rentalTable.getSelectionModel().getSelectedItem());
        refreshOrderTable();
    }

    @FXML
    void comboBoxSelection() {
        rentalList.clear();
        temporaryList.clear();
        refreshOrderTable();
        clientTextArea.setText("Wybrano klienta: \n" +
                "ID: " + clientComboBox.getValue().getClient_id() + "\n" +
                "Nazwa: " + clientComboBox.getValue().getName() + "\n" +
                "Adres: " + clientComboBox.getValue().getAddress() + "\n" +
                "Kod pocztowy: " + clientComboBox.getValue().getPostalcode() + "\n" +
                "Telefon: " + clientComboBox.getValue().getTelephonenumber() + "\n" +
                "NIP: " + clientComboBox.getValue().getNip());

        ClientEntity clientEntity = null;
        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();
            clientEntity = manager.find(ClientEntity.class, clientComboBox.getValue().getClient_id());

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            manager.close();
        }
        if (!clientEntity.getItemList().isEmpty() || clientEntity.getItemList() == null) {
            itemTable.setDisable(true);
            rentalTable.setDisable(true);
            createOrderButton.setDisable(true);
            displayErrorDialogBox("Wypożyczanie przedmiotu", "Wybrany klient złożył już zamówienie, przyjmij zwrot poprzedniego zamówienia aby wygenerować nowe.");
        } else {
            itemTable.setDisable(false);
            rentalTable.setDisable(false);
            createOrderButton.setDisable(false);
        }
        transaction.commit();
    }

    @FXML
    void refreshItemTable() {
        ObservableList<ItemTableView> observableList = ItemDAO.getItemList();
        itemTable.setItems(observableList);
    }

    @FXML
    void refreshOrderTable() {
        rentalTable.setItems(rentalList);
    }

    private void displayInfoDialogBox(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void displayErrorDialogBox(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Optional<String> displayInputDialogBox(String title, String message, String defaultvalue) {
        TextInputDialog dialog = new TextInputDialog(defaultvalue);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(message);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your name: " + result.get());
        }
        return result;
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
            List<RentalDetailsEntity> rentalDetailsList = new ArrayList<>();
            ClientEntity clientEntity = manager.find(ClientEntity.class, clientComboBox.getValue().getClient_id());

            for (RentalTableView item : rentalList) {
                ItemEntity itemEntity = manager.find(ItemEntity.class, item.getItem_id());
                itemEntity.setClientList(clientList);
                itemList.add(itemEntity);
                manager.persist(itemEntity);

                RentalDetailsEntity rentalDetailsEntity = new RentalDetailsEntity();
                rentalDetailsEntity.setItemId(itemEntity.getItem_id());
                rentalDetailsEntity.setRentalCount(item.getRental_count());
                rentalDetailsEntity.setClientList(clientList);
                rentalDetailsList.add(rentalDetailsEntity);
                manager.persist(rentalDetailsEntity);
            }
            if (itemList.isEmpty()) {
                displayErrorDialogBox("Wypożyczanie przedmiotu", "Tabela \"Zamówienie\" nie może być pusta");
            }
            clientEntity.setItemList(itemList);
            clientEntity.setRentalDetailsList(rentalDetailsList);
            manager.persist(clientEntity);
            transaction.commit();
            displayInfoDialogBox("Wypożyczanie przedmiotu,", "Wypożyczanie przedmiotu(ów) zakończone powodzeniem");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }

    @FXML
    void generateConfirmation(ActionEvent event) {

    }

    @FXML
    void openConfirmationFolder(ActionEvent event) {

    }

    @FXML
    void editOrder(ActionEvent event) {

    }


    public void initialize() {
        //wypelnianie combobox
        ObservableList<ClientTableView> clientTableViewList = ClientDAO.getClientList();
        clientComboBox.setItems(clientTableViewList);

        //blokowanie pól
        rentalTable.setDisable(true);
        itemTable.setDisable(true);
        createOrderButton.setDisable(true);

        //inicjalizacja tabeli Przedmioty
        item_idColumn.setCellValueFactory(cellData -> cellData.getValue().item_idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        bailColumn.setCellValueFactory(cellData -> cellData.getValue().bailProperty().asObject());
        rentalpriceColumn.setCellValueFactory(cellData -> cellData.getValue().rentalpriceProperty().asObject());
        countColumn.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());

        //inicjalizacja tabeli Zamówienie
        rental_idColumn.setCellValueFactory(cellData -> cellData.getValue().item_idProperty().asObject());
        rentalNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        rentalCountColumn.setCellValueFactory(cellData -> cellData.getValue().rental_countProperty().asObject());

        //wyswietlenie tabeli
        refreshItemTable();

        //wyłączenie textarea
        clientTextArea.setEditable(false);

        //doubleclick tabeli Przedmioty
        itemTable.setRowFactory(tv -> {
            TableRow<ItemTableView> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    ItemTableView rowData = row.getItem();
                    addToOrder(rowData);
                }
            });
            return row;
        });

        //doubleclick tabeli Zamówienie
        rentalTable.setRowFactory(tv -> {
            TableRow<RentalTableView> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    RentalTableView rowData = row.getItem();
                    removeFromOrder();
                }
            });
            return row;
        });
    }
}
