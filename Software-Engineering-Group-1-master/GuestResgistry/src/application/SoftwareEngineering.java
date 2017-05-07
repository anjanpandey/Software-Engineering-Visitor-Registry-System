/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class SoftwareEngineering extends Application {
	
   // public static String screen0ID = "map";
   // public static String screen0File = "Map.fxml";
    public static String screen1ID = "main";
    public static String screen1File = "BeginForm.fxml";
    public static String screen2ID = "screen2";
    public static String screen2File = "MiddleForm.fxml";
    public static String screen3ID = "screen3";
    public static String screen3File = "EndForm.fxml";
    public static String screen4ID = "screen4";
    public static String screen4File = "Gratitude.fxml"; 
    
 
    public void start(Stage primaryStage) throws Exception {
        
        ScreensController mainContainer = new ScreensController();
       // mainContainer.loadScreen(SoftwareEngineering.screen0ID, SoftwareEngineering.screen0File);
        mainContainer.loadScreen(SoftwareEngineering.screen1ID, SoftwareEngineering.screen1File);
        mainContainer.loadScreen(SoftwareEngineering.screen2ID, SoftwareEngineering.screen2File);
        mainContainer.loadScreen(SoftwareEngineering.screen3ID, SoftwareEngineering.screen3File);
        mainContainer.loadScreen(SoftwareEngineering.screen4ID, SoftwareEngineering.screen4File);
        
        mainContainer.setScreen(SoftwareEngineering.screen1ID);  
        
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();  
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
