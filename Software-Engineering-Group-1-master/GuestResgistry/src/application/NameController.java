package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class NameController implements Initializable {

	@FXML
	private TextField Fname;
	@FXML
	private TextField Lname;
	@FXML
	private Label Fname_error;
	@FXML
	private Label Lname_error;
	@FXML
	private ComboBox<String> State;

	private String firstName;
	private String lastName;
	ObservableList<String> state_list = FXCollections.observableArrayList("AL", "AK", "AZ", "AR", "CA", "CO", "CT",
			"DC", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS",
			"MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN",
			"TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");


	public void NextButton(ActionEvent event) throws IOException {

		firstName = Fname.getText();
		lastName = Lname.getText();
		System.out.println("that is done");
		fnameValidate(Fname, Fname_error);
		lnameValidate(Lname, Lname_error);

		if ((Fname_error.getText().isEmpty() && Lname_error.getText().isEmpty())
				&& ((!Fname.getText().isEmpty() || !Lname.getText().isEmpty()))) {
			Parent newScene = FXMLLoader.load(getClass().getResource("Hotel.fxml"));
			Stage new_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			new_Stage.setTitle("Your Information");
			new_Stage.setScene(new Scene(newScene, 1920, 1080));
			new_Stage.show();
		}

		System.out.println(firstName + " " + lastName);

	}
	
	public void backScene(ActionEvent event) throws IOException {
		Parent newScene = FXMLLoader.load(getClass().getResource("Map.fxml"));
		Stage new_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		new_Stage.setTitle("Your Information");
		new_Stage.setScene(new Scene(newScene, 1920, 1080));
		new_Stage.show();	
	}

	public void fnameValidate(TextField Fname, Label Fname_error) {

		if (Fname.getText() != null && !Fname.getText().matches("[a-zA-Z]+") && !Fname.getText().isEmpty()) {
			Fname_error.setText("Please enter a valid First Name!");
		} else {
			Fname_error.setText("");
		}

	}

	public void lnameValidate(TextField Lname, Label Lname_error) {

		if (Lname.getText() != null && !Lname.getText().matches("[a-zA-Z]+") && !Lname.getText().isEmpty()) {
			Lname_error.setText("Please enter a valid Last name!");
		} else {
			Lname_error.setText("");
		}

	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		State.setItems(state_list);
	}

}