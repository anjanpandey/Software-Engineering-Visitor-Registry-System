package adminApplication;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;


@SuppressWarnings("restriction")
public class AdminViewFormController implements Initializable {

	@FXML
	private TextField City;
	@FXML
	private ComboBox<String> State;
	@FXML
	private TextField Country;
	@FXML
	private TextField Party;
	@FXML
	private Label FnameError;
	@FXML
	private Label LnameError;
	@FXML
	private Label PartyError;
	@FXML
	private Label DestinationError;
	@FXML
	private Label AddressError;
	@FXML
	private Label EmailError;
	@FXML
	private TextField ZipCode;
	@FXML
	private ComboBox<String> Reason;
	@FXML
	private ComboBox<String> Purpose;
	@FXML
	private RadioButton rbYes;
	@FXML
	private RadioButton rbNo;
	@FXML
	private TextField Destination;
	@FXML
	private RadioButton rbYY;
	@FXML
	private RadioButton rbNN;
	@FXML
	private TextField Email;

	ObservableList<String> state_list = FXCollections.observableArrayList("AL", "AK", "AZ", "AR", "CA", "CO", "CT",
			"DC", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS",
			"MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN",
			"TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");
	ObservableList<String> list = FXCollections.observableArrayList("Business", "Pleasure", "Other");
	ObservableList<String> slist = FXCollections.observableArrayList("Billboard", "Interstate Sign", "Other");

	public void destinationValidate(TextField Destination, Label DestinationError) {

		if (Destination.getText() != null && !Destination.getText().matches("[a-zA-Z ]+")
				&& !Destination.getText().isEmpty()) {
			DestinationError.setText("Please enter a valid City!");
		} else {
			DestinationError.setText("");
		}

	}

	public void emailValidate(TextField Email, Label EmailError) {
		if (Email.getText() != null && !Email.getText().matches("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+")
				&& !Email.getText().isEmpty()) {
			EmailError.setText("Please enter a valid email address!");
		} else {
			EmailError.setText("");
		}
	}

	public void partyValidate(TextField Party, Label PartyError) {

		if (Party.getText() != null && !Party.getText().matches("[1-9][0-9]*") && !Party.getText().isEmpty()) {
			PartyError.setText("Please enter a valid number!");
		} else {
			PartyError.setText("");
		}

	}

	public void exitButtonClicked(ActionEvent event) throws IOException {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Warning!");
		alert.setHeaderText("You are adding visitor.");
		alert.setContentText("Are you sure you want to exit?");
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {

			Parent closeScene = FXMLLoader.load(getClass().getResource("VisitorView.fxml"));
			Stage new_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			new_Stage.setTitle("Visitor");
			new_Stage.setScene(new Scene(closeScene, 1920, 1080));
			new_Stage.show();
		}

		else {

			alert.close();
		}
	}

	public void updateButton(ActionEvent event) throws IOException {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Warning!");
		alert.setHeaderText("You are adding your visitor.");
		alert.setContentText("Are you sure you want to add?");
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {

			emailValidate(Email, EmailError);
			destinationValidate(Destination, DestinationError);
			partyValidate(Party, PartyError);
			String partyString = Party.getText();
			Scanner scan = new Scanner(partyString);
			int party;
			if (scan.hasNextInt()) {
				party = scan.nextInt();
			} else {
				party = 1;
			}
			scan.close();
			

			List<VisitorDetails> single = new ArrayList<VisitorDetails>();
			single.add(new VisitorDetails(Email.getText(), City.getText(), "",
					State.getValue(), Country.getText(), party, Reason.getValue(), (rbYes.isSelected() ? "Yes" : "No"),
					Destination.getText(), false, Purpose.getValue(), new Date()));
			single.get(0).setHeard(Reason.getValue()==null ? "Other" : Reason.getValue());
			single.get(0).setTravelingFor(Purpose.getValue()==null ? "No Response" : Purpose.getValue());
			AdminJDBC.addVisitors(single);

			Parent closeScene = FXMLLoader.load(getClass().getResource("VisitorView.fxml"));
			Stage new_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			new_Stage.setTitle("Map");
			new_Stage.setScene(new Scene(closeScene, 1920, 1800));
			new_Stage.show();
		}

		else {

			alert.close();
		}
	}

	public void initialize(URL location, ResourceBundle resources) {
		State.setItems(state_list);
		Reason.setItems(slist);
		Purpose.setItems(list);
	}

}
