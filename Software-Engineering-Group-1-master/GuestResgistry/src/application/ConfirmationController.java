package application;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ConfirmationController implements Initializable {

	
	
	public void switchScene(ActionEvent event) throws IOException
	{
		
		Parent setScene = FXMLLoader.load(getClass().getResource("Map.fxml"));
		Stage new_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		new_Stage.setTitle("Welcome to Monroe-West Monroe CVB!!!");
		new_Stage.setScene(new Scene(setScene,1920, 1080));
		new_Stage.show();
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
}