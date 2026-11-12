package com.example.awjavafxapplication.Model;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * stores the products and parts in observable lists.
 *
 */

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     *
     * @param newPart
     */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }

    /**
     *
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);
    }

    /**
     * searches for part by finding matching ID otherwise returns error.
     *
     * @param partID
     * @return
     */

    public static Part lookupPart(int partID) {
        for(Part part: Inventory.getAllParts()) {
            while (part.getId() == partID) {
                return part;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Nothing Found");
        alert.show();
        return null;
    }

    /**
     * searches for products using matching ID if not found gives error.
     *
     * @param productID
     * @return
     */

    public static Product lookupProduct(int productID) {

        for(Product product: Inventory.getAllProducts()){
            while (product.getId() == productID) {
                return product;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Nothing Found");
        alert.show();
        return null;
    }

    /**
     * searches for part using part name
     *
     * @param partName
     * @return
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> PartName = FXCollections.observableArrayList();

        for (Part part: allParts) {
            if (part.getName().contains(partName)) {
                PartName.add(part);
            }
        }
        return PartName;
    }

    /**
     * searches for product using product name
     *
     * @param productName
     * @return
     */

    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> ProductName = FXCollections.observableArrayList();

        for (Product product: allProducts) {
            if (product.getName().contains(productName)) {
                ProductName.add(product);
            }
        }
        return ProductName;
    }

    /**
     * modifies part
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * modifies product
     * @param index
     * @param newProduct
     */

    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * deletes part
     * @param selectedPart
     * @return
     */
    public static boolean deletePart (Part selectedPart) {
        if(allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * deletes product
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct (Product selectedProduct) {
        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @return allParts
     */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    /**
     *
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts(){

        return allProducts;
    }



}
