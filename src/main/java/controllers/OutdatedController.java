package controllers;

import common.Main;
import entity.ClientEntity;
import entity.ItemEntity;
import entity.RentalDetailsEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import tableview.OutdatedTableView;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static common.ConfigurationBuilder.maxrentaltime;

public class OutdatedController {


    @FXML
    private TableColumn<OutdatedTableView, Integer> outdatedDays;

    @FXML
    private TableColumn<OutdatedTableView, String> outdatedRentalDate;

    @FXML
    private TableColumn<OutdatedTableView, String> outdatedNameColumn;

    @FXML
    private TextArea clientTextArea;

    @FXML
    private TableColumn<OutdatedTableView, Integer> outdatedCountColumn;

    @FXML
    private TableView<OutdatedTableView> outdatedTable;

    @FXML
    private TableColumn<OutdatedTableView, Integer> outdatedIdColumn;

    @FXML
    private TextField searchTextField;

    private ObservableList<OutdatedTableView> outdatedList = FXCollections.observableArrayList();

    @FXML
    void search() {
        ObservableList<OutdatedTableView> masterData = outdatedList;
        FilteredList<OutdatedTableView> filteredData = new FilteredList<>(masterData, p -> true);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(item -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();

            return item.getName().toLowerCase().contains(lowerCaseFilter);
        }));
        SortedList<OutdatedTableView> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(outdatedTable.comparatorProperty());
        outdatedTable.setItems(sortedData);
    }

    private void fillOutdatedList() {
        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;
        List<RentalDetailsEntity> list;
        try {
            transaction = manager.getTransaction();
            transaction.begin();
            list = manager.createQuery("select i from RentalDetailsEntity as i", RentalDetailsEntity.class).getResultList();

            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            DateFormat dateFormat1 = new SimpleDateFormat("dd MM yyyy");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
            Date currentDate = new Date();

            for (RentalDetailsEntity item : list) {
                ItemEntity ie = manager.find(ItemEntity.class, item.getItemId());
                Timestamp ts = item.getRentalDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(ts);
                cal.add(Calendar.DAY_OF_WEEK, maxrentaltime);
                Date maxReturnDate = new Date();
                maxReturnDate.setTime(cal.getTime().getTime());
                LocalDate firstDate1 = LocalDate.parse(dateFormat1.format(currentDate), formatter);
                LocalDate secondDate1 = LocalDate.parse(dateFormat1.format(maxReturnDate), formatter);
                long temp = ChronoUnit.DAYS.between(secondDate1, firstDate1);
                int days = (int) temp;
                if (days > 0) {
                    OutdatedTableView otv = new OutdatedTableView(item.getClientList().get(0).getClient_id(), item.getItemId(), ie.getName(), dateFormat.format(item.getRentalDate()), item.getRentalCount(), days);
                    outdatedList.add(otv);
                }
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

    private void refreshOutdatedTable() {
        outdatedTable.setItems(outdatedList);
    }

    @FXML
    void clearSearch() {
        searchTextField.clear();
    }

    private void fillTextArea(OutdatedTableView item) {
        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();

            ClientEntity ce = manager.find(ClientEntity.class, item.getClient_id());

            clientTextArea.setText("Informacje o kliencie: \n" +
                    "ID: " + ce.getClient_id() + "\n" +
                    "Nazwa: " + ce.getName() + "\n" +
                    "Adres: " + ce.getAddress() + "\n" +
                    "Kod pocztowy: " + ce.getPostalcode() + "\n" +
                    "Telefon: " + ce.getPhonenumber() + "\n" +
                    "NIP: " + ce.getNip());

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

    private void clearTextArea() {
        clientTextArea.clear();
    }

    public void initialize() {
        //inicjalizacja tabeli przedawnione
        outdatedIdColumn.setCellValueFactory(cellData -> cellData.getValue().item_idProperty().asObject());
        outdatedNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        outdatedRentalDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        outdatedCountColumn.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());
        outdatedDays.setCellValueFactory(cellData -> cellData.getValue().daysProperty().asObject());
        fillOutdatedList();
        refreshOutdatedTable();

        //wyłączenie textarea
        clientTextArea.setEditable(false);

        //select, unselect
        outdatedTable.setRowFactory(tableView2 -> {
            final TableRow<OutdatedTableView> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                final int index = row.getIndex();
                OutdatedTableView clickedRow = row.getItem();
                if (!row.isEmpty()) {
                    fillTextArea(clickedRow);
                }
                if (index >= 0 && index < outdatedTable.getItems().size() && outdatedTable.getSelectionModel().isSelected(index)) {
                    outdatedTable.getSelectionModel().clearSelection();
                    clearTextArea();
                    event.consume();
                }
            });
            return row;
        });
    }
}

