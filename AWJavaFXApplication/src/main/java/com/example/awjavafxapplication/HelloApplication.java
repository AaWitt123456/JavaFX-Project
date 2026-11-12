/*package com.example.awjavafxapplication;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.awjavafxapplication.Model.*;

import java.io.IOException;

public class HelloApplication extends Application {


//    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root);
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainMenu.fxml"));
//        Scene scene = new Scene(FXMLLoader.load(), 850, 350);
//        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {


    }

}
*/

package com.example.awjavafxapplication;

import com.example.awjavafxapplication.Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** JAVADOC FOLDER LOCATION : in this project under the src folder.
 * FUTURE ENHANCMENTS : I think it would be better if my auto increment was able to detect the most recent ID and add 1. right now it runs whenever
 * the add button is pressed which causes it to increase even when no new part or product is added.
 *
 * Another potential improvement would be to add more complex error messages that detect the mistake and give a message and guidelines to remedy the error.
 * for example receiving an error after typing "five" into price that tells you that the price can only be a number with no letters.
 *
 * Another improvement would be a button that can be pushed that leads to a full table view for all the parts and products, as right now it only shows 4 pieces of data.
 *
 *ISSUES I HAD : When trying to create the auto increment ID I put the integers to increment within the saveButton method and it gave me errors until I placed them
 * at the top of the AddPartController and AddProductController classes, which then allowed it to work.
 *
 * At some point I accidentally made Product static and didn't notice so when trying to add parts to each product it would add it to all the products, I didn't know
 * what went wrong so I set up an appointment with an instructor who found the problem and helped me fix it.
 *
 * in module-info.java the open and export calls didn't automatically appear for the Model folder, so I emailed my instructor who found the problem and showed me how to fix it.
 * .*/


public class HelloApplication extends Application {

    /**
     *  opens the main menu on startup
     * @param stage
     * @throws IOException
     */
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * populates tables with data
     * @param args
     */

    public static void main(String[] args) {
        Part Wheel = new inHouse(1, "Wheel", 15.00, 40, 20, 100, 1);
        Inventory.addPart(Wheel);

        Part Handle = new inHouse(2, "Handle", 25.00, 20, 10, 35, 2);
        Inventory.addPart(Handle);

        Part spoke = new inHouse(3, "Spoke", 30.00, 10, 0, 120, 3);
        Inventory.addPart(spoke);

        Part Seat = new Outsourced(4, "Seat", 25.00, 10, 5, 120, "Big Bike Seat Co");
        Inventory.addPart(Seat);

        Product BigWheel = new Product(1, "Big Wheel", 249.99, 1, 1, 50);
        Inventory.addProduct(BigWheel);

        Product Skateboard = new Product(2, "Skateboard", 200, 10, 1, 55);
        Inventory.addProduct(Skateboard);

        Product SportsBike = new Product(3, "Sports Bike", 990.95, 7, 1, 500);
        Inventory.addProduct(SportsBike);

        Product RaceBike = new Product( 4, "Racing Bike", 1945, 3, 1,10);
        Inventory.addProduct(RaceBike);

        launch();
    }
}


