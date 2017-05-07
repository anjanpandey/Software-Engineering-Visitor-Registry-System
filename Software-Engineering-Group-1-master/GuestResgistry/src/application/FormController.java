package application;


import java.beans.EventHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.json.JSONException;

import com.sun.media.jfxmediaimpl.platform.Platform;

//import application2.Main2;
//import application2.Browser.JavaApplication;
//import application.Browser.JavaApplication;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.*;
import netscape.javascript.JSObject;

public class FormController implements Initializable {

	@FXML
	private TextField inputFname;
	@FXML
	private TextField inputMname;
	@FXML
	private TextField inputLname;
	@FXML
	private TextField inputEmail;
	@FXML
	private TextField inputDestination;
	@FXML
	private TextField inputParty;
	@FXML
	private Button closeButton;
	@FXML
	private ComboBox<String> reasonBox;
	@FXML
	private ComboBox<String> purposeBox;
	@FXML
	private RadioButton yesID;
	@FXML
	private RadioButton noID;
	@FXML
	private CheckBox inBox;
	private boolean choice;
	private boolean option;

	public void setData() {

		reasonBox.setStyle("-fx-font-weight:bold;");
		purposeBox.setStyle("-fx-font-weight:bold;");

		if (reasonBox.getValue() == null) {
			reasonBox.getItems().addAll("Billboard", "Interstate Sign", "Other");
		}

		if (reasonBox.getValue() == null && purposeBox.getValue() == null) {
			purposeBox.getItems().addAll("Business", "Pleasure", "Convention", "Other");
		}
	}

	public boolean choiceButton(ActionEvent e) {

		ToggleGroup group = new ToggleGroup();
		yesID.setToggleGroup(group);
		yesID.setSelected(true);
		noID.setToggleGroup(group);

		if (yesID.isSelected()) {
			choice = true;
		} else {
			choice = false;
		}

		return choice;
	}

	public boolean checkButton(ActionEvent m) {

		if (inBox.isSelected()) {
			option = true;
		} else {
			option = false;
		}

		return option;
	}

	public void SubmitButton(ActionEvent event) {
		String Fname = inputFname.getText();
		String Mname = inputMname.getText();
		String Lname = inputLname.getText();
		String Email = inputEmail.getText();
		String Destination = inputDestination.getText();
		String Party = inputParty.getText();
		int numPeople = Integer.parseInt(Party);
		String Reason = reasonBox.getSelectionModel().getSelectedItem().toString();
		String Purpose = purposeBox.getSelectionModel().getSelectedItem().toString();
		boolean Radio = choice;
		boolean Check = option;
	}

	public void openNext() {

		Stage window = (Stage) closeButton.getScene().getWindow();

	}

	public void exitButton() {

		Stage window = (Stage) closeButton.getScene().getWindow();
		window.close();
	}

	public void clearButton(ActionEvent event) {
		inputFname.clear();
		inputMname.clear();
		inputLname.clear();
		inputEmail.clear();
		inputDestination.clear();
		inputParty.clear();
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
