package controllers;

import common.DialogBoxBuilder;
import common.Main;
import dao.UserDAO;
import entity.ConfigEntity;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import tableview.UserTableView;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.File;
import java.util.Optional;

import static common.ConfigurationBuilder.loadConfiguration;
import static common.DialogBoxBuilder.displayErrorDialogBox;
import static common.DialogBoxBuilder.displayInfoDialogBox;

public class SettingsController {

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private TextField searchTextField;

    @FXML
    private TextField returnFolderTextField;

    @FXML
    private TableColumn<UserTableView, String> passwordColumn;

    @FXML
    private TextField hirePhoneNumberTextField;

    @FXML
    private TextField hireAdressTextField;

    @FXML
    private TableView<UserTableView> userTable;

    @FXML
    private TextField rentalFolderTextField;

    @FXML
    private ToggleButton adminTrigger;

    @FXML
    private TextField dailyPenalityTextField;

    @FXML
    private TableColumn<UserTableView, Integer> adminColumn;

    @FXML
    private Button deleteUserButton;

    @FXML
    private TextField hirePostalCodeTextField;

    @FXML
    private TextField maxRentalTimeTextField;

    @FXML
    private TableColumn<UserTableView, String> usernameColumn;

    @FXML
    private Button editUserButton;

    @FXML
    private TextField hireNipTextField;

    @FXML
    private TextField hireNameTextField;

    @FXML
    private Button addUserButton;

    @FXML
    private TextField usernameTextField;

    private int selectedUserID;

    @FXML
    void addUser() {
        if (usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty() || confirmPasswordTextField.getText().isEmpty()) {
            displayErrorDialogBox("Dodawanie użytkownika", "Wypełnienie wszystkich pól jest wymagane.");
        } else {
            if (confirmPasswordTextField.getText().equals(passwordTextField.getText())) {
                if (adminTrigger.isSelected()) {
                    UserDAO.addUser(usernameTextField.getText(), passwordTextField.getText(), 1);
                    clearForm();
                    refreshTable();
                    displayInfoDialogBox("Dodawanie użytkownika", "Dodawanie użytkownika zakończone powodzeniem.");
                } else {
                    UserDAO.addUser(usernameTextField.getText(), passwordTextField.getText(), 0);
                    clearForm();
                    refreshTable();
                    displayInfoDialogBox("Dodawanie użytkownika", "Dodawanie użytkownika zakończone powodzeniem.");
                }
            } else {
                displayErrorDialogBox("Dodawanie użytkownika", "Podane hasła nie są identyczne.");
            }
        }
    }

    @FXML
    void editUser() {
        if (usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty() || confirmPasswordTextField.getText().isEmpty()) {
            displayErrorDialogBox("Edycja użytkownika", "Wypełnienie wszystkich pól jest wymagane.");
        } else {
            if (confirmPasswordTextField.getText().equals(passwordTextField.getText())) {
                if (adminTrigger.isSelected()) {
                    UserDAO.editUser(selectedUserID, usernameTextField.getText(), passwordTextField.getText(), 1);
                    clearForm();
                    refreshTable();
                    displayInfoDialogBox("Edycja użytkownika", "Dodawanie użytkownika zakończone powodzeniem.");
                } else {
                    UserDAO.editUser(selectedUserID, usernameTextField.getText(), passwordTextField.getText(), 0);
                    clearForm();
                    refreshTable();
                    displayInfoDialogBox("Edycja użytkownika", "Dodawanie użytkownika zakończone powodzeniem.");
                }
            } else {
                displayErrorDialogBox("Edycja użytkownika", "Podane hasła nie są identyczne.");
            }
        }
    }

    @FXML
    void deleteUser() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Usuwanie użytkownika");
        alert.setHeaderText("Czy napewno chcesz usunąć wybranego użytkownia?");
        ButtonType buttonTypeOne = new ButtonType("Tak");
        ButtonType buttonTypeCancel = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            UserDAO.removeClient(selectedUserID);
            clearForm();
            refreshTable();
            displayInfoDialogBox("Usuwanie użytkownika", "Usuwanie użytkownika zakończone powodzeniem.");
        } else {
            clearForm();
            refreshTable();
            displayInfoDialogBox("Usuwanie użytkownika", "Usuwanie użytkownika zostało przerwane.");
        }
    }

    @FXML
    void clearForm() {
        addUserButton.setDisable(false);
        editUserButton.setDisable(true);
        deleteUserButton.setDisable(true);
        usernameTextField.clear();
        passwordTextField.clear();
        confirmPasswordTextField.clear();
        adminTrigger.setSelected(false);
    }

    @FXML
    void search() {
        //usuwanie danych z formularzy
        clearForm();

        //wyszukiwanie
        ObservableList<UserTableView> masterData = UserDAO.getUserList();
        FilteredList<UserTableView> filteredData = new FilteredList<>(masterData, p -> true);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(userTableView -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();

            return userTableView.getUsername().toLowerCase().contains(lowerCaseFilter);
        }));
        SortedList<UserTableView> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(userTable.comparatorProperty());
        userTable.setItems(sortedData);
    }

    @FXML
    void clearSearch() {
        searchTextField.clear();
    }

    @FXML
    void refreshTable() {
        ObservableList<UserTableView> observableList = UserDAO.getUserList();
        userTable.setItems(observableList);

    }

    @FXML
    void saveConfig() {
        if (hireNameTextField.getText().isEmpty() ||
                hireAdressTextField.getText().isEmpty() ||
                hirePostalCodeTextField.getText().isEmpty() ||
                hirePhoneNumberTextField.getText().isEmpty() ||
                hireNipTextField.getText().isEmpty() ||
                maxRentalTimeTextField.getText().isEmpty() ||
                dailyPenalityTextField.getText().isEmpty() ||
                rentalFolderTextField.getText().isEmpty() ||
                returnFolderTextField.getText().isEmpty()) {
            DialogBoxBuilder.displayErrorDialogBox("Zapisywanie konfiguracji", "Wypełnienie wszystkich pól jest wymagane.");
        } else {

            if (!maxRentalTimeTextField.getText().matches("^\\d+$")) {
                DialogBoxBuilder.displayErrorDialogBox("Zapisywanie konfiguracji", "Pole \"Maksymalny czas na zwrot przedmiotu\" zawiera nieprawidłową wartość. Dopuszczalne są tylko wartości całkowite");
            } else {
                if (!dailyPenalityTextField.getText().matches("^\\d+$")) {
                    DialogBoxBuilder.displayErrorDialogBox("Zapisywanie konfiguracji", "Pole \"Kara za zwłokę\" zawiera nieprawidłową wartość. Dopuszczalne są tylko wartości całkowite");
                } else {
                    EntityManager manager = Main.emf.createEntityManager();
                    EntityTransaction transaction = null;

                    try {
                        transaction = manager.getTransaction();
                        transaction.begin();

                        ConfigEntity config = manager.find(ConfigEntity.class, 1);
                        config.setHirename(hireNameTextField.getText());
                        config.setHireadress(hireAdressTextField.getText());
                        config.setHirepostalcode(hirePostalCodeTextField.getText());
                        config.setHirephonenumber(hirePhoneNumberTextField.getText());
                        config.setHirenip(hireNipTextField.getText());

                        config.setDailypenality(Integer.parseInt(dailyPenalityTextField.getText()));
                        config.setMaxrentaltime(Integer.parseInt(maxRentalTimeTextField.getText()));

                        config.setOrderconfirmationfolder(rentalFolderTextField.getText());
                        config.setReturnconfirmationfolder(returnFolderTextField.getText());

                        manager.persist(config);
                        transaction.commit();
                        loadConfiguration();
                        displayInfoDialogBox("Zapisywanie konfiguracji", "Zapisywanie konfiguracji zakończone powodzeniem.");
                    } catch (Exception e) {
                        if (transaction != null) {
                            transaction.rollback();
                        }
                        e.printStackTrace();
                    } finally {
                        manager.close();
                    }
                }
            }
        }
    }

    @FXML
    void cancel() {
        fillConfigForm();
        displayInfoDialogBox("Zapisywanie konfiguracji", "Zmiany nie zostały zapisane.");
    }

    @FXML
    void selectRentalFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory == null) {
        } else {
            String path = selectedDirectory.getAbsolutePath();
            path = path.replace("\\", "\\\\");
            rentalFolderTextField.setText(path);
        }
    }

    @FXML
    void selectReturnFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory == null) {
        } else {
            String path = selectedDirectory.getAbsolutePath();
            path = path.replace("\\", "\\\\");
            returnFolderTextField.setText(path);
        }
    }

    private void fillFormWithRowValue(UserTableView clickedRow) {
        selectedUserID = clickedRow.getId();
        usernameTextField.setText(clickedRow.getUsername());
        passwordTextField.setText(clickedRow.getPassword());
        confirmPasswordTextField.setText(clickedRow.getPassword());
        if (clickedRow.getAdmin() == 1) {
            adminTrigger.setSelected(true);
        } else {
            adminTrigger.setSelected(false);
        }
    }

    private void fillConfigForm() {
        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            ConfigEntity config = manager.find(ConfigEntity.class, 1);
            hireNameTextField.setText(config.getHirename());
            hireAdressTextField.setText(config.getHireadress());
            hirePostalCodeTextField.setText(config.getHirepostalcode());
            hirePhoneNumberTextField.setText(config.getHirephonenumber());
            hireNipTextField.setText(config.getHirenip());

            maxRentalTimeTextField.setText(String.valueOf(config.getMaxrentaltime()));
            dailyPenalityTextField.setText(String.valueOf(config.getDailypenality()));

            rentalFolderTextField.setText(config.getOrderconfirmationfolder());
            returnFolderTextField.setText(config.getReturnconfirmationfolder());
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
    void initialize() {
        //inicjalizacja tabeli
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        adminColumn.setCellValueFactory(cellData -> cellData.getValue().adminProperty().asObject());

        //wyświetanie tabeli
        refreshTable();

        //wypelnianie konfiguracji;
        fillConfigForm();

        //wylaczenie edycji sciezki folderów;
        returnFolderTextField.setEditable(false);
        rentalFolderTextField.setEditable(false);

        //select, unselect
        userTable.setRowFactory(tableView2 -> {
            final TableRow<UserTableView> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                final int index = row.getIndex();
                UserTableView clickedRow = row.getItem();
                if (!row.isEmpty()) {
                    fillFormWithRowValue(clickedRow);
                    addUserButton.setDisable(true);
                    editUserButton.setDisable(false);
                    deleteUserButton.setDisable(false);
                }
                if (index >= 0 && index < userTable.getItems().size() && userTable.getSelectionModel().isSelected(index)) {
                    userTable.getSelectionModel().clearSelection();
                    clearForm();
                    event.consume();
                }
            });
            return row;
        });
    }


}
