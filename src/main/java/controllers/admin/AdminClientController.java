package controllers.admin;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import tableview.ClientTableView;
import dao.ClientDAO;

import java.util.Optional;

public class AdminClientController {
    private int selectedClientId;

    @FXML
    private TextField nipTextField;

    @FXML
    private TableColumn<ClientTableView, String> addressColumn;

    @FXML
    private TableColumn<ClientTableView, Integer> client_idColumn;

    @FXML
    private TableView<ClientTableView> clientTable;

    @FXML
    private TextField addressTextField;

    @FXML
    private TableColumn<ClientTableView, String> phonenumberColumn;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField postalcodeTextField;

    @FXML
    private Button cleanButton;

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<ClientTableView, String> nipColumn;

    @FXML
    private TextField searchTextField;

    @FXML
    private TextField phonenumberTextField;

    @FXML
    private TableColumn<ClientTableView, String> postalcodeColumn;

    @FXML
    private TableColumn<ClientTableView, String> nameColumn;

    @FXML
    private Button refreshButton;

    @FXML
    private Button editButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button clearSearch;


    @FXML
    void addClient(ActionEvent event) {
        if (nameTextField.getText().isEmpty() || addressTextField.getText().isEmpty() || postalcodeTextField.getText().isEmpty() || phonenumberTextField.getText().isEmpty()) {
            displayErrorDialogBox("Dodawanie klienta", "Wypełnienie wszystkich pól poza polem \"NIP\" jest wymagane.");
        } else {
            ClientDAO.addClient(nameTextField.getText(), addressTextField.getText(), postalcodeTextField.getText(), phonenumberTextField.getText(), nipTextField.getText());
            clearForm();
            refreshTable();
            displayInfoDialogBox("Dodawanie klienta", "Dodawanie klienta zakończone powodzeniem.");
        }
    }

    @FXML
    void editClient(ActionEvent event) {
        if (nameTextField.getText().isEmpty() || addressTextField.getText().isEmpty() || postalcodeTextField.getText().isEmpty() || phonenumberTextField.getText().isEmpty()) {
            displayErrorDialogBox("Dodawanie klienta", "Wypełnienie wszystkich pól poza polem \"NIP\" jest wymagane.");
        } else {
            ClientDAO.editClient(selectedClientId, nameTextField.getText(), addressTextField.getText(), postalcodeTextField.getText(), phonenumberTextField.getText(), nipTextField.getText());
            clearForm();
            refreshTable();
            displayInfoDialogBox("Edycja klienta", "Edycja klienta zakończona powodzeniem.");
        }
    }

    @FXML
    void deleteClient(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Usuwanie klienta");
        alert.setHeaderText("Czy napewno chcesz usunąć wybranego klienta?");
        ButtonType buttonTypeOne = new ButtonType("Tak");
        ButtonType buttonTypeCancel = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            ClientDAO.removeClient(selectedClientId);
            clearForm();
            refreshTable();
            displayInfoDialogBox("Usuwanie klienta", "Usuwanie klienta zakończone powodzeniem.");
        } else {
            clearForm();
            refreshTable();
            displayInfoDialogBox("Usuwanie klienta", "Usuwanie klienta zostało przerwane.");
        }
    }

    @FXML
    void search() {
        //usuwanie danych z formularzy
        clearForm();

        //wyszukiwanie
        ObservableList<ClientTableView> masterData = ClientDAO.getClientList();
        FilteredList<ClientTableView> filteredData = new FilteredList<>(masterData, p -> true);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(clientTableView -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();

            return clientTableView.getName().toLowerCase().contains(lowerCaseFilter);
        }));
        SortedList<ClientTableView> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(clientTable.comparatorProperty());
        clientTable.setItems(sortedData);
    }

    @FXML
    void clearForm() {
        addButton.setDisable(false);
        editButton.setDisable(true);
        removeButton.setDisable(true);
        nameTextField.clear();
        addressTextField.clear();
        postalcodeTextField.clear();
        phonenumberTextField.clear();
        nipTextField.clear();
    }

    @FXML
    void refreshTable() {
        ObservableList<ClientTableView> observableList = ClientDAO.getClientList();
        clientTable.setItems(observableList);
    }

    @FXML
    void clearSearch() {
        searchTextField.clear();
    }

    @FXML
    public void initialize() {
        //inicjalizacja tabeli
        client_idColumn.setCellValueFactory(cellData -> cellData.getValue().client_idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        postalcodeColumn.setCellValueFactory(cellData -> cellData.getValue().postalcodeProperty());
        phonenumberColumn.setCellValueFactory(cellData -> cellData.getValue().telephonenumberProperty());
        nipColumn.setCellValueFactory(cellData -> cellData.getValue().nipProperty());

        //wyswietlenie tabeli
        refreshTable();

        //wyłączenie guzików
        editButton.setDisable(true);
        removeButton.setDisable(true);

        //select, unselect
        clientTable.setRowFactory(tableView2 -> {
            final TableRow<ClientTableView> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                final int index = row.getIndex();
                ClientTableView clickedRow = row.getItem();
                if (!row.isEmpty()) {
                    fillFormWithRowValue(clickedRow);
                    addButton.setDisable(true);
                    editButton.setDisable(false);
                    removeButton.setDisable(false);
                }
                if (index >= 0 && index < clientTable.getItems().size() && clientTable.getSelectionModel().isSelected(index)) {
                    clientTable.getSelectionModel().clearSelection();
                    clearForm();
                    event.consume();
                }
            });
            return row;
        });
    }

    private void fillFormWithRowValue(ClientTableView item) {
        selectedClientId = item.getClient_id();
        nameTextField.setText(item.getName());
        addressTextField.setText(item.getAddress());
        postalcodeTextField.setText(item.getPostalcode());
        phonenumberTextField.setText(item.getTelephonenumber());
        nipTextField.setText(item.getNip());
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
}
