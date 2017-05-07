package application;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class Controller implements Initializable {

	@FXML
	private TextField flabel;
	@FXML
	private TextField mlabel;
	@FXML
	private TextField label;
	@FXML
	private TextField dlabel;
	@FXML
	private TextField elabel;
	@FXML
	private TextField plabel;
	@FXML
	private ComboBox<String> purposeLabel;
	@FXML
	private ComboBox<String> reasonLabel;
	@FXML
	private RadioButton rbYes;
	@FXML
	private RadioButton rbNo;
	@FXML
	private Label fnameError;
	@FXML
	private Label mnameError;
	@FXML
	private Label lnameError;
	@FXML
	private Label destinationError;
	@FXML
	private Label partyError;
	@FXML
	private Label emailError;
	@FXML
	private CheckBox checkLabel;

	private String Reason;
	private String Heard;
	private String Fname;
	private String Mname;
	private String Lname;
	private String Email;
	private String Destination;
	private String choice;
	private String Party;

	ObservableList<String> list = FXCollections.observableArrayList("Business", "Pleasure", "Other");
	ObservableList<String> slist = FXCollections.observableArrayList("Billboard", "Interstate Sign", "Other");

	public void radioSelect(ActionEvent eve) {

		if (rbYes.isSelected()) {

			choice = "Yes";
		}

		else if (rbNo.isSelected()) {
			choice = "No";
		}
	}

	public void submitButton(ActionEvent event) throws IOException {

		Fname = flabel.getText();
		Mname = mlabel.getText();
		Lname = label.getText();
		Email = elabel.getText();
		Destination = dlabel.getText();
		Party = plabel.getText();
		Reason = reasonLabel.getValue();
		Heard = purposeLabel.getValue();

		fnameValidate(flabel, fnameError);
		mnameValidate(mlabel, mnameError);
		lnameValidate(label, lnameError);
		destinationValidate(dlabel, destinationError);
		partyValidate(plabel, partyError);
		emailValidate(elabel, emailError);
		chValidate();

		if (fnameError.getText().isEmpty() && mnameError.getText().isEmpty() && lnameError.getText().isEmpty()
				&& destinationError.getText().isEmpty() && partyError.getText().isEmpty()
				&& emailError.getText().isEmpty()) 
		{
			Parent closeScene = FXMLLoader.load(getClass().getResource("Confirmation.fxml"));
			Stage new_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			new_Stage.setTitle("Success!");
			new_Stage.setScene(new Scene(closeScene, 1920, 1080));
			new_Stage.show();
			
			
			/*****************************************************************************
			 	Sends the form data to JDBC to be inserted into the database.
			 	Also ensures that the 'Party' value doesn't blow up the system.
			*****************************************************************************/

			if (Party.isEmpty())
			{
				Party = "0";
			}
			int partyNum = Integer.parseInt(Party);
			
			int visitorID = (int) Math.ceil((Math.random()*10000));//the visitor ID. Will need to make sure that it's unique at some point. 
			//Visitor visit = new Visitor(visitorID,Fname,Mname,Lname,Email,true,Heard,Reason,GeoCoding.getLat(),GeoCoding.getLon(), Destination,choice,partyNum);
			//JDBC.insertIntoDB(visit);
		}
	}

	public void fnameValidate(TextField flabel, Label fnameError) {

		if (flabel.getText() != null && !flabel.getText().matches("[a-zA-Z]+") && !flabel.getText().isEmpty()) {
			fnameError.setText("Please enter a valid name!");
		} else {
			fnameError.setText("");
		}

	}

	public void mnameValidate(TextField mlabel, Label mnameError) {

		if (mlabel.getText() != null && !mlabel.getText().matches("[a-zA-Z]+") && !mlabel.getText().isEmpty()) {
			mnameError.setText("Please enter a valid name!");
		} else {
			mnameError.setText("");
		}

	}

	public void lnameValidate(TextField label, Label lnameError) {

		if (label.getText() != null && !label.getText().matches("[a-zA-Z]+") && !label.getText().isEmpty()) {
			lnameError.setText("Please enter a valid name!");
		} else {
			lnameError.setText("");
		}

	}

	public void destinationValidate(TextField dlabel, Label destinationError) {

		if (dlabel.getText() != null && !dlabel.getText().matches("[a-zA-Z]+") && !dlabel.getText().isEmpty()) {
			destinationError.setText("Please enter a valid name!");
		} else {
			destinationError.setText("");
		}

	}

	public void partyValidate(TextField plabel, Label partyError) {

		if (plabel.getText() != null && !plabel.getText().matches("[0-9]+") && !plabel.getText().isEmpty()) {
			partyError.setText("Please enter a valid number!");
		} else {
			partyError.setText("");
		}

	}

	public void checkValidate(ActionEvent checkEvent) {
		if (checkLabel.isSelected() && elabel.getText().isEmpty()) {
			emailError.setText("Please enter your email if you want to opt-in.");
		} else {
			if (elabel.getText() != null
					&& !elabel.getText().matches("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+")
					&& !elabel.getText().isEmpty()) {
				emailError.setText("Please enter a valid email address!");
			} else {
				emailError.setText("");
			}
		}
	}

	public void chValidate() {
		if (checkLabel.isSelected() && elabel.getText().isEmpty()) {
			emailError.setText("Please enter your email if you want to opt-in.");
		} else {
			if (elabel.getText() != null
					&& !elabel.getText().matches("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+")
					&& !elabel.getText().isEmpty()) {
				emailError.setText("Please enter a valid email address!");
			} else {
				emailError.setText("");
			}
		}
	}

	public void emailValidate(TextField elabel, Label emailError) {
		if (elabel.getText() != null
				&& !elabel.getText().matches("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+")
				&& !elabel.getText().isEmpty()) {
			emailError.setText("Please enter a valid email address!");
		} else {
			emailError.setText("");
		}
	}

	public void resetButton(ActionEvent e) {

		flabel.clear();
		mlabel.clear();
		label.clear();
		plabel.clear();
		dlabel.clear();
		elabel.clear();
		fnameError.setText("");
		mnameError.setText("");
		lnameError.setText("");
		destinationError.setText("");
		partyError.setText("");
		emailError.setText("");
		checkLabel.setSelected(false);
		rbYes.setSelected(false);
		rbNo.setSelected(false);
		reasonLabel.valueProperty().set(null);
		purposeLabel.valueProperty().set(null);

	}

	public void exitButtonClicked() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Warning!");
		alert.setContentText("Are you sure you want to exit?");
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {
			Platform.exit();
		}

		else {

			alert.close();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		purposeLabel.setItems(list);
		reasonLabel.setItems(slist);
	}

}
