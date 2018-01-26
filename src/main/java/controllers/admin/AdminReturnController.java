package controllers.admin;

import common.Main;
import dao.ClientDAO;
import entity.ClientEntity;
import entity.ItemEntity;
import entity.RentalDetailsEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tableview.ClientTableView;
import tableview.ReturnTableView;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

public class AdminReturnController {

    @FXML
    private TextArea clientTextArea;

    @FXML
    private ComboBox<ClientTableView> clientComboBox;

    @FXML
    private Button openReturnConfirmationFolderButton;

    @FXML
    private Button returnOrderButton;

    @FXML
    private Button generateReturnConfirmationButton;

    @FXML
    private TableView<ReturnTableView> returnTable;

    @FXML
    private TableColumn<ReturnTableView, String> return_NameColumn;

    @FXML
    private TableColumn<ReturnTableView, Integer> return_idColumn;

    @FXML
    private TableColumn<ReturnTableView, Integer> return_CountColumn;

    @FXML
    private TableColumn<ReturnTableView, String> return_DateColumn;

    ObservableList<ReturnTableView> returnList = FXCollections.observableArrayList();


    @FXML
    void comboBoxSelection(ActionEvent event) {

        refreshReturnTable();
        returnList.clear();
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
        if (clientEntity.getItemList().isEmpty() || clientEntity.getItemList() == null) {
            returnTable.setDisable(true);
            returnOrderButton.setDisable(true);
            displayErrorDialogBox("Zwrot przedmiotu", "Wybrany klient nie złożył żadnego zamówienia.");
        } else {
            returnTable.setDisable(false);
            returnOrderButton.setDisable(false);
            fillReturnTableList();
        }
        transaction.commit();
    }

    @FXML
    void returnOrder(ActionEvent event) {
        ClientEntity clientEntity = null;
        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();
            clientEntity = manager.find(ClientEntity.class, clientComboBox.getValue().getClient_id());
            List<RentalDetailsEntity> temp = clientEntity.getRentalDetailsList();

            for(RentalDetailsEntity item : temp)
            {
                RentalDetailsEntity rde = manager.find(RentalDetailsEntity.class, item.getItemId());
                manager.remove(rde);
            }

            clientEntity.getRentalDetailsList().clear();
            clientEntity.getItemList().clear();
            manager.persist(clientEntity);
            transaction.commit();
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
    void generateReturnConfirmation(ActionEvent event) {

    }

    @FXML
    void openReturnConfirmationFolder(ActionEvent event) {

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

    private void refreshReturnTable() {
        returnTable.setItems(returnList);
    }

    private void fillReturnTableList() {
        ClientEntity clientEntity = null;
        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();
            clientEntity = manager.find(ClientEntity.class, clientComboBox.getValue().getClient_id());
            List<RentalDetailsEntity> rentalDetailsList = clientEntity.getRentalDetailsList();
            List<ItemEntity> itemList = clientEntity.getItemList();
            for (RentalDetailsEntity item : rentalDetailsList) {
                ReturnTableView rtv = new ReturnTableView(item.getItemId(), "temp", item.getRentalDate().toString(), item.getRentalCount());
                returnList.add(rtv);
            }
            int i = 0;
            for (ItemEntity item : itemList) {
                returnList.get(i).setName(item.getName());
                i++;
            }
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }

    public void initialize() {
        //wypelnianie combobox
        ObservableList<ClientTableView> clientTableViewList = ClientDAO.getClientList();
        clientComboBox.setItems(clientTableViewList);

        //blokowanie pól
        returnTable.setDisable(true);
        returnOrderButton.setDisable(true);

        //inicjalizacja tabeli zwroty
        return_idColumn.setCellValueFactory(cellData -> cellData.getValue().item_idProperty().asObject());
        return_NameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        return_DateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        return_CountColumn.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());
    }
}
