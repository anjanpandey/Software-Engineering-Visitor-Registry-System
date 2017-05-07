package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HotelController implements Initializable {
	
	@FXML
	private TextField Fname;
	@FXML
	private TextField Lname;
	@FXML
	private RadioButton rbYes;
	@FXML
	private RadioButton rbNo;
	@FXML
	private ComboBox<String> Hear;
	@FXML
	private TextField TravelCity; 
	@FXML
	private Label Destination_error;
	
	
	ObservableList<String> slist = FXCollections.observableArrayList("Billboard", "Interstate Sign", "Other");
	
		
	public void NextButton(ActionEvent event) throws IOException{
		
		destinationValidate(TravelCity, Destination_error);
		
		if (Destination_error.getText().isEmpty() && (!Hear.getSelectionModel().isEmpty() || 
				!TravelCity.getText().isEmpty() || rbYes.isSelected() || rbNo.isSelected())) {
			Parent newScene = FXMLLoader.load(getClass().getResource("Reason.fxml"));
			Stage new_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			new_Stage.setTitle("Your Information");
			new_Stage.setScene(new Scene(newScene, 1680, 1200));
			new_Stage.show();
		}
	
	}
	
	
	public void destinationValidate(TextField TravelCity, Label Destination_error) {

		if (TravelCity.getText() != null && !TravelCity.getText().matches("[a-zA-Z ]+") && !TravelCity.getText().isEmpty()) {
			Destination_error.setText("Please enter a valid City!");
		} else {
			Destination_error.setText("");
		}

	}
	
	
	@SuppressWarnings("restriction")
	public void radioSelect(ActionEvent eve) {

		if (rbYes.isSelected()) {
		}

		else if (rbNo.isSelected()) {
		}
	}

	
	@SuppressWarnings("restriction")
	public void backScene(ActionEvent e) throws Exception {
		
		@SuppressWarnings("restriction")
		Parent newScene = FXMLLoader.load(getClass().getResource("Name.fxml"));
		Stage new_Stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		new_Stage.setTitle("Your Information");
		new_Stage.setScene(new Scene(newScene,1920, 1080));
		new_Stage.show();
		
	}
	
	
	@SuppressWarnings("restriction")
	public void initialize(URL location, ResourceBundle resources) {
		Hear.setItems(slist);
		
	}

}