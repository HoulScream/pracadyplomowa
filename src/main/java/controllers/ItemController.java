package controllers;

import common.DialogBoxBuilder;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import dao.ItemDAO;
import tableview.ItemTableView;

import java.util.Optional;

public class ItemController {
    private int selectedItemId;

    @FXML
    private TableColumn<ItemTableView, Integer> countColumn;

    @FXML
    private TableView<ItemTableView> itemTable;

    @FXML
    private TextField nameTextField;

    @FXML
    private TableColumn<ItemTableView, Double> rentalpriceColumn;

    @FXML
    private Button addButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private TextField rentalpriceTextField;

    @FXML
    private TableColumn<ItemTableView, Integer> item_idColumn;

    @FXML
    private TableColumn<ItemTableView, Double> bailColumn;

    @FXML
    private TableColumn<ItemTableView, String> nameColumn;

    @FXML
    private Button editButton;

    @FXML
    private TextField countTextField;

    @FXML
    private Button removeButton;

    @FXML
    private TextField bailTextField;

    @FXML
    void addItem() {
        if (nameTextField.getText().isEmpty() || bailTextField.getText().isEmpty() || rentalpriceTextField.getText().isEmpty() || countTextField.getText().isEmpty()) {
            DialogBoxBuilder.displayErrorDialogBox("Dodawanie przedmiotu", "Wypełnienie wszystkich pól jest wymagane.");
        } else {
            if (!bailTextField.getText().matches("^(\\d+\\.)?\\d+$")) {
                DialogBoxBuilder.displayErrorDialogBox("Dodawanie przedmiotu", "Pole \"Kaucja zwrotna\" zawiera nieprawidłową wartość. Użyj \".\" jako separatora wartości dziesiętnych.");
            } else {
                if (!rentalpriceTextField.getText().matches("^(\\d+\\.)?\\d+$")) {
                    DialogBoxBuilder.displayErrorDialogBox("Dodawanie przedmiotu", "Pole \"Cena (24h)\" zawiera nieprawidłową wartość. Użyj \".\" jako separatora wartości dziesiętnych.");
                } else {
                    if (!countTextField.getText().matches("^\\d+$")) {
                        DialogBoxBuilder.displayErrorDialogBox("Dodawanie przedmiotu", "Pole \"Ilość\" zawiera nieprawidłową wartość. Dopuszczalne są tylko wartości całkowite");
                    } else {
                        ItemDAO.addItem(nameTextField.getText(), Double.parseDouble(bailTextField.getText()), Double.parseDouble(rentalpriceTextField.getText()), Integer.parseInt(countTextField.getText()));
                        clearForm();
                        refreshTable();
                        DialogBoxBuilder.displayInfoDialogBox("Dodawanie przedmiotu", "Dodawanie przedmiotu zakończone powodzeniem.");
                    }
                }
            }
        }
    }

    @FXML
    void editItem() {
        if (nameTextField.getText().isEmpty() || bailTextField.getText().isEmpty() || rentalpriceTextField.getText().isEmpty() || countTextField.getText().isEmpty()) {
            DialogBoxBuilder.displayErrorDialogBox("Dodawanie przedmiotu", "Wypełnienie wszystkich pól jest wymagane.");
        } else {
            if (!bailTextField.getText().matches("^(\\d+\\.)?\\d+$")) {
                DialogBoxBuilder.displayErrorDialogBox("Dodawanie przedmiotu", "Pole \"Kaucja zwrotna\" zawiera nieprawidłową wartość. Użyj \".\" jako separatora wartości dziesiętnych.");
            } else {
                if (!rentalpriceTextField.getText().matches("^(\\d+\\.)?\\d+$")) {
                    DialogBoxBuilder.displayErrorDialogBox("Dodawanie przedmiotu", "Pole \"Cena (24h)\" zawiera nieprawidłową wartość. Użyj \".\" jako separatora wartości dziesiętnych.");
                } else {
                    if (!countTextField.getText().matches("^\\d+$")) {
                        DialogBoxBuilder.displayErrorDialogBox("Dodawanie przedmiotu", "Pole \"Ilość\" zawiera nieprawidłową wartość. Dopuszczalne są tylko wartości całkowite");
                    } else {
                        ItemDAO.editItem(selectedItemId, nameTextField.getText(), Double.parseDouble(bailTextField.getText()), Double.parseDouble(rentalpriceTextField.getText()), Integer.parseInt(countTextField.getText()));
                        clearForm();
                        refreshTable();
                        DialogBoxBuilder.displayInfoDialogBox("Edycja przedmiotu", "Edycja przedmiotu zakończona powodzeniem.");
                    }
                }
            }
        }
    }

    @FXML
    void deleteItem() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Usuwanie przedmiotu");
        alert.setHeaderText("Czy napewno chcesz usunąć wybrany przedmiot?");
        ButtonType buttonTypeOne = new ButtonType("Tak");
        ButtonType buttonTypeCancel = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (buttonTypeOne == result.get()) {
            ItemDAO.removeItem(selectedItemId);
            clearForm();
            refreshTable();
            DialogBoxBuilder.displayInfoDialogBox("Usuwanie przedmiotu", "Usuwanie przedmiotu zakończone powodzeniem.");
        } else {
            clearForm();
            refreshTable();
            DialogBoxBuilder.displayInfoDialogBox("Usuwanie przedmiotu", "Usuwanie przedmiotu zostało przerwane.");
        }
    }

    @FXML
    void clearForm() {
        addButton.setDisable(false);
        editButton.setDisable(true);
        removeButton.setDisable(true);
        nameTextField.clear();
        bailTextField.clear();
        rentalpriceTextField.clear();
        countTextField.clear();
    }

    @FXML
    void search() {
        //usuwanie danych z formularzy
        clearForm();

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
    void clearSearch() {
        searchTextField.clear();
    }

    @FXML
    void refreshTable() {
        ObservableList<ItemTableView> observableList = ItemDAO.getItemList();
        itemTable.setItems(observableList);
    }

    private void fillFormWithRowValue(ItemTableView item) {
        selectedItemId = item.getItem_id();
        nameTextField.setText(item.getName());
        bailTextField.setText(Double.toString(item.getBail()));
        rentalpriceTextField.setText(Double.toString(item.getRentalprice()));
        countTextField.setText(Integer.toString(item.getCount()));
    }

    @FXML
    public void initialize() {
        //inicjalizacja tabeli
        item_idColumn.setCellValueFactory(cellData -> cellData.getValue().item_idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        bailColumn.setCellValueFactory(cellData -> cellData.getValue().bailProperty().asObject());
        rentalpriceColumn.setCellValueFactory(cellData -> cellData.getValue().rentalpriceProperty().asObject());
        countColumn.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());

        //wyswietlenie tabeli
        refreshTable();

        //wyłączenie buttonów
        editButton.setDisable(true);
        removeButton.setDisable(true);

        //select, unselect
        itemTable.setRowFactory(tableView2 -> {
            final TableRow<ItemTableView> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                final int index = row.getIndex();
                ItemTableView clickedRow = row.getItem();
                if (!row.isEmpty()) {
                    fillFormWithRowValue(clickedRow);
                    addButton.setDisable(true);
                    editButton.setDisable(false);
                    removeButton.setDisable(false);
                }
                if (index >= 0 && index < itemTable.getItems().size() && itemTable.getSelectionModel().isSelected(index)) {
                    itemTable.getSelectionModel().clearSelection();
                    clearForm();
                    event.consume();
                }
            });
            return row;
        });
    }
}
