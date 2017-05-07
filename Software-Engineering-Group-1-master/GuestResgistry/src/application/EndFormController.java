/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author admin
 */
@SuppressWarnings("restriction")
public class EndFormController implements Initializable, ControlledScreen {

	ScreensController myController;

	@FXML
	private ComboBox<String> Reason;
	@FXML
	private RadioButton rbYes;
	@FXML
	private RadioButton rbNo;
	@FXML
	private RadioButton rbRepeatY;
	@FXML
	private RadioButton rbRepeatN;
	@FXML
	private TextField Party;
	@FXML
	private Label email_pop_label;
	@FXML
	private TextField Email;
	@FXML
	private Label Party_error;
	@FXML
	private Label Email_error;
	private Visitor visitor;

	private boolean partyError;
	private boolean emailError;

	ObservableList<String> list = FXCollections.observableArrayList("Business", "Pleasure", "Other");

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		visitor = VisitorContext.getInstance().currentVisitor();
		Reason.setItems(list);

		if (visitor.getRepeatVisit() != null && visitor.getRepeatVisit()) {
			rbRepeatY.setSelected(true);
		}
		if (visitor.getParty() != null) {
			Party.setText(visitor.getParty().toString());
		}
		if (visitor.getTravelingFor() != null && !visitor.getTravelingFor().equals("No Response")) {
			Reason.getSelectionModel().select(visitor.getHeard());
		}
		if (visitor.getEmail() != null) {
			rbYes.setSelected(true);
			email_pop_label.setVisible(true);
			Email.setVisible(true);
			Email.setText(visitor.getEmail());
		}

		System.out.println("End Form Lat: " + visitor.getLatitude());
		System.out.println("End Form Lat: " + visitor.getLongitude());

	}

	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	public void radioSelect(ActionEvent event) {

		if (rbYes.isSelected()) {
			email_pop_label.setVisible(true);
			Email.setVisible(true);
		}

		else if (rbNo.isSelected()) {
			email_pop_label.setVisible(false);
			Email.setVisible(false);
			Email_error.setText("");

		}
	}

	public boolean partyValidate(TextField Party, Label Party_error) {
		if (Party.getText() != null && !Party.getText().matches("[1-9][0-9]*") && !Party.getText().isEmpty()) {
			Party_error.setText("Please enter a valid number!");
			partyError = true;
			return partyError;
		}
		if (Party.getText() != null && !Party.getText().isEmpty() && Party.getText().length() > 3) {
			Party_error.setText("Please give a number between 1 and 999");
			partyError = true;
			return partyError;
		} else {
			Party_error.setText("");
			partyError = false;
		}
		return partyError;
	}

	public boolean emailValidate(TextField Email, Label Email_error) {
		if (Email.getText() != null && !Email.getText().matches("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+")
				&& !Email.getText().isEmpty()) {
			Email_error.setText("Please enter a valid email address!");
			emailError = true;
		} else {
			Email_error.setText("");
			emailError = false;
		}
		return emailError;
	}

	public void goBack(ActionEvent event) throws IOException {

		if (partyError == false) {
			System.out.println(partyError);
			String partyText = Party.getText();
			Integer party = (partyText.isEmpty() ? 1 : Integer.parseInt(partyText));
			visitor.setParty(party.intValue());
		}
		if (Email.getText() != null) {
			visitor.setEmail(Email.getText());
		}
		visitor.setRepeatVisit(rbRepeatY.isSelected());

		String reason = Reason.getValue();
		reason = (reason == null ? "No Response" : reason);
		visitor.setTravelingFor(reason);

		Parent newScene = FXMLLoader.load(getClass().getResource("MiddleForm.fxml"));
		Stage new_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		new_Stage.setTitle("Your Information");
		new_Stage.setScene(new Scene(newScene, 1920, 1080));
		new_Stage.show();

	}

	public void helpButton(ActionEvent event) throws IOException {

		// Load the fxml file and create a new stage for the popup
		FXMLLoader loader = new FXMLLoader(getClass().getResource("VisitorManual.fxml"));
		Parent root = (Parent) loader.load();
		Stage stage = new Stage();
		stage.initStyle(StageStyle.DECORATED);
		stage.setTitle("Help Alert");
		stage.setScene(new Scene(root));
		stage.show();

	}

	/*
	 * @FXML private void goToScreen2(ActionEvent event){
	 * myController.setScreen(SoftwareEngineering.screen2ID); }
	 */

	@FXML
	private void onSubmit(ActionEvent event) throws IOException {

		partyValidate(Party, Party_error);

		if (partyError == false) {
			System.out.println(partyError);
			String partyText = Party.getText();
			Integer party = (partyText.isEmpty() ? 1 : Integer.parseInt(partyText));
			visitor.setParty(party.intValue());
		}

		String reason = Reason.getValue();
		reason = (reason == null ? "No Response" : reason);
		visitor.setTravelingFor(reason);

		emailValidate(Email, Email_error);

		if (emailError == false) {
			if (Email.getText() != null) {
				visitor.setEmail(Email.getText());
			}
		}

		if (rbYes.isSelected() && Email.getText().isEmpty()) {
			Email_error.setText("Please Provide your email address.");
		}

		System.out.println(partyError + " " + emailError);// this is just used
															// for testing.

		if ((Email_error.getText().isEmpty() || !rbYes.isSelected()) && partyError == false && emailError == false) {
			System.out.println(visitor.getZip());
			visitor.setRepeatVisit(rbRepeatY.isSelected());
			JDBC.addVisitor(visitor);
			Parent newScene = FXMLLoader.load(getClass().getResource("Gratitude.fxml"));
			Stage new_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			new_Stage.setTitle("Your Information");
			new_Stage.setScene(new Scene(newScene, 1920, 1080));
			new_Stage.show();

		} // end if
	}// end method

	public void goHome(ActionEvent event) throws IOException {
		if (partyError == false) {
			System.out.println(partyError);
			String partyText = Party.getText();
			Integer party = (partyText.isEmpty() ? 1 : Integer.parseInt(partyText));
			visitor.setParty(party.intValue());
		}
		if (Email.getText() != null) {
			visitor.setEmail(Email.getText());
		}
		visitor.setRepeatVisit(rbRepeatY.isSelected());

		String reason = Reason.getValue();
		reason = (reason == null ? "No Response" : reason);
		visitor.setTravelingFor(reason);

		JDBC.addVisitor(visitor);

		Parent newScene = FXMLLoader.load(getClass().getResource("Map.fxml"));
		Stage new_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		new_Stage.setTitle("Your Information");
		new_Stage.setScene(new Scene(newScene, 1920, 1080));
		new_Stage.show();

		Form.timer.restartIdleTimer();

		newScene.setOnMouseClicked(mouseHandler);
		newScene.setOnMouseDragged(mouseHandler);
		newScene.setOnMouseEntered(mouseHandler);
		newScene.setOnMouseExited(mouseHandler);
		newScene.setOnMouseMoved(mouseHandler);
		newScene.setOnMousePressed(mouseHandler);
		newScene.setOnMouseReleased(mouseHandler);
	}

	/***************************************************************************
	 * + *********************** Mouse Handler
	 * ************************************* +
	 **************************************************************************/

	EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent mouseEvent) {
			Form.timer.restartIdleTimer();
		}
	};

}