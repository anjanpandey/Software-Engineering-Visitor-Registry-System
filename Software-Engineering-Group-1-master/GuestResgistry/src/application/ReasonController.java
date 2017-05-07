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

@SuppressWarnings("restriction")
public class ReasonController implements Initializable {
	
	@FXML
	private ComboBox<String> Reason;
	@FXML
	private RadioButton rbYes;
	@FXML
	private RadioButton rbNo;
	@FXML
	private TextField Party;
	@FXML
	private TextField Email;
	@FXML
	private Label Party_error;
	@FXML
	private Label Email_error;
	
	ObservableList<String> list = FXCollections.observableArrayList("Business", "Pleasure", "Other");
	
	
	public void SubmitButton(ActionEvent event) throws IOException{
		
		partyValidate(Party, Party_error);
		emailValidate(Email, Email_error);
		
		if ((Party_error.getText().isEmpty()
				&& Email_error.getText().isEmpty()) && (!Party.getText().isEmpty() || !Email.getText().isEmpty()
						|| !Reason.getSelectionModel().isEmpty())){
		Parent newScene = FXMLLoader.load(getClass().getResource("Confirmation.fxml"));
		Stage new_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		new_Stage.setTitle("Your Information");
		new_Stage.setScene(new Scene(newScene,1920, 1080));
		new_Stage.show();
		}
	}
	
	public void radioSelect(ActionEvent eve) {

		if (rbYes.isSelected()) {
			
		}

		else if (rbNo.isSelected()) {
		}
	}
	
	public void partyValidate(TextField Party, Label Party_error) {

		if (Party.getText() != null && !Party.getText().matches("[1-9][0-9]*") && !Party.getText().isEmpty()) {
			Party_error.setText("Please enter a valid number!");
		} else {
			Party_error.setText("");
		}

	}
	

	public void emailValidate(TextField Email, Label Email_error) {
		if (Email.getText() != null
				&& !Email.getText().matches("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+")
				&& !Email.getText().isEmpty()) {
			Email_error.setText("Please enter a valid email address!");
		} else {
			Email_error.setText("");
		}
	}
	
	
	
	public void backScene(ActionEvent e) throws Exception {
		
		Parent newScene = FXMLLoader.load(getClass().getResource("Hotel.fxml"));
		Stage new_Stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		new_Stage.setTitle("Your Information");
		new_Stage.setScene(new Scene(newScene,1920, 1080));
		new_Stage.show();
		
	}


	public void initialize(URL location, ResourceBundle resources) {

		Reason.setItems(list);
	}

}