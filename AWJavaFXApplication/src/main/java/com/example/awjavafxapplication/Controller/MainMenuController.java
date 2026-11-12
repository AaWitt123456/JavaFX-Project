package com.example.awjavafxapplication.Controller;

import com.example.awjavafxapplication.Model.Inventory;
import com.example.awjavafxapplication.Model.Part;
import com.example.awjavafxapplication.Model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Optional;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


/**
 * this is the main menu controller, it allows to click on the buttons leading to the other menus, as well as deleting items from each table, along with searching for items.
 *
 */


public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;


    @FXML private TableColumn<Product, Integer> productIDCol;
    @FXML private TableColumn<Product, String> productNameCol;
    @FXML private TableColumn<Product, Integer> productStockCol;
    @FXML private TableColumn<Product, Double> productPriceCol;
    @FXML private TextField productSearch;
    @FXML private TextField partSearch;
    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partSearchCol;
    @FXML private TableColumn<Part, Double> partPriceCol;
    @FXML private TableView<Product> mainProductsTable;
    @FXML private TableView<Part> mainPartsTable;


    /**
     * this takes you to the menu to add parts.
     *
     * @param event
     * @throws IOException
     */
    public void mainAddPartsButton(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/com/example/awjavafxapplication/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * this takes you to the menu to modify parts but first requires that you click on a part or you will receive an error.
     *
     *
     * @param event
     * @throws IOException
     */

    public void mainModifyPartsButton(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/awjavafxapplication/ModifyPart.fxml"));
            loader.load();

            ModifyPartController MPController = loader.getController();
            MPController.sendPart(mainPartsTable.getSelectionModel().getSelectedIndex(), mainPartsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a part");
            alert.show();
        }

    }

    /**
     * this takes you to the menu to add products
     *
     * @param event
     * @throws IOException
     */

    public void mainAddProductsButton(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/com/example/awjavafxapplication/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * this exists the program
     *
     * @param ExitButton
     */
    public void mainExitButton(ActionEvent ExitButton) {
        System.exit(0);
    }

    /**
     * this takes you to the modify product section but requires first clicking on a product or you'll receive an error.
     *
     * @param event
     * @throws IOException
     */
    public void mainModifyProductButton(ActionEvent event) throws IOException {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/awjavafxapplication/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController MPController = loader.getController();
            MPController.sendProduct(mainProductsTable.getSelectionModel().getSelectedIndex(), mainProductsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();


        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a product");
            alert.show();
        }

    }

    /**
     * this populates the tables on the main menu
     *
     * @param url
     * @param resourceBundle
     */

    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainPartsTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partSearchCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainProductsTable.setItems(Inventory.getAllProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * this deletes a part but first gives a warning to users .
     *
     * @param event
     */

    public void mainDeletePartButton(ActionEvent event) {

        Part selectedPart = mainPartsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setContentText("Do you want to delete the selected part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(selectedPart);
        }


    }

    /**
     *
     * deletes a product but checks if selected or not or if has any parts associated
     * @param event
     */


    public void mainDeleteProductButton(ActionEvent event) {

        Product selectedDeleteProduct = mainProductsTable.getSelectionModel().getSelectedItem();
        if (selectedDeleteProduct.getAllAssociatedParts().size() > 0) {
            Alert cantDelete = new Alert(Alert.AlertType.ERROR);
            cantDelete.setTitle("Error");
            cantDelete.setContentText("Remove associated parts first");
            cantDelete.showAndWait();
            return;

        }

        Product selectedProduct = mainProductsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setContentText("Do you want to delete the selected product?");
        Optional<ButtonType> result = alert.showAndWait();


        if (result.isPresent() && result.get() == ButtonType.OK) {

            Inventory.deleteProduct(selectedProduct);
        }

    }

    /**
     * searches for part and if not found gives an error.
     *
     * @param event
     */
    public void mainPartSearch(ActionEvent event) {

        String searchText = partSearch.getText();
        ObservableList<Part> results = Inventory.lookupPart(searchText);
        try {
            while (results.size() == 0 ) {
                int partID = Integer.parseInt(searchText);
                results.add(Inventory.lookupPart(partID));
            }
            mainPartsTable.setItems(results);
        } catch (NumberFormatException e) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error");
            noParts.setContentText("Part not found: text is case sensitive");
            noParts.showAndWait();
        }

        }

    /**
     * searches for product and if not found gives error.
     *
     * @param event
     */


    public void mainProductSearch(ActionEvent event) {

        String searchText = productSearch.getText();
        ObservableList<Product> results = Inventory.lookupProduct(searchText);
        try {
            while (results.size() == 0 ) {
                int productID = Integer.parseInt(searchText);
                results.add(Inventory.lookupProduct(productID));
            }
            mainProductsTable.setItems(results);
        } catch (NumberFormatException e) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error");
            noParts.setContentText("Product not found: text is case sensitive");
            noParts.showAndWait();
        }

        }

}
