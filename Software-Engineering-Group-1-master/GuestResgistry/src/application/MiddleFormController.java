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
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;


/**
 * FXML Controller class
 *
 * @author admin
 */
@SuppressWarnings("restriction")
public class MiddleFormController implements Initializable {

	ScreensController myController;
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
	@FXML
	private Label empty_field;
	private Visitor visitor;

	ObservableList<String> slist = FXCollections.observableArrayList("Billboard", "Interstate Sign", "Other");

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		visitor = VisitorContext.getInstance().currentVisitor();
		Hear.setItems(slist);
		
		if (visitor.getHeard() != null && !visitor.getHeard().equals("No Response")) {
			Hear.getSelectionModel().select(visitor.getHeard());
		}
		if (visitor.getDestination() != null && !visitor.getDestination().isEmpty()) {
			TravelCity.textProperty().setValue(visitor.getDestination());
		}

		if (visitor.getHotel() != null && !visitor.getHotel().isEmpty()) {
			if (visitor.getHotel().equals("Yes")) {
				rbYes.setSelected(true);
			} else if (visitor.getHotel().equals("No")) {
				rbNo.setSelected(true);
			}
		}

		System.out.println("Middle Form Lat: " + visitor.getLatitude());
		System.out.println("Middle Form Lat: " + visitor.getLongitude());
	}

	/*
	 * public void setScreenParent(ScreensController screenParent){ myController
	 * = screenParent;
	 * 
	 * }
	 * 
	 * public void destinationValidate(TextField TravelCity, Label
	 * Destination_error) {
	 * 
	 * if (TravelCity.getText() != null &&
	 * !TravelCity.getText().matches("[a-zA-Z ]+") &&
	 * !TravelCity.getText().isEmpty()) {
	 * Destination_error.setText("Please enter a valid City!"); } else {
	 * Destination_error.setText(""); }
	 * 
	 * }
	 */

	public void radioSelect(ActionEvent eve) {

		if (rbYes.isSelected()) {
			visitor.setRepeatVisit(true);
		}

		else if (rbNo.isSelected()) {
			visitor.setRepeatVisit(false);
		}
	}

	public void goBack(ActionEvent event) throws IOException {
		Parent newScene = FXMLLoader.load(getClass().getResource("BeginForm.fxml"));
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
	 * 
	 * @FXML private void goToScreen1(ActionEvent event){
	 * 
	 * myController.setScreen(SoftwareEngineering.screen1ID); }
	 */

	@FXML
	private void goToScreen3(ActionEvent event) throws IOException {
		// destinationValidate(TravelCity, Destination_error);
		System.out.println("Form2 visitor: " + visitor.getCity());

		visitor.setDestination(TravelCity.getText());
		if (Hear.getSelectionModel().getSelectedItem() != null) {
			visitor.setHeard(Hear.getSelectionModel().getSelectedItem().toString());
		}
		else 
		{
			visitor.setHeard("No Response");
		}

		if (rbYes.isSelected()) {
			visitor.setHotel("Yes");
		} else {
			visitor.setHotel(null);
		}
		if (rbNo.isSelected()) {
			visitor.setHotel("No");
		} else {
			visitor.setHotel(null);
		}

		/*
		 * if (Destination_error.getText().isEmpty() &&
		 * (!Hear.getSelectionModel().isEmpty() ||
		 * !TravelCity.getText().isEmpty() || rbYes.isSelected() ||
		 * rbNo.isSelected())) {
		 * 
		 * }
		 * 
		 * 
		 * else { if (Destination_error.getText().isEmpty()){ empty_field.
		 * setText("Your information will not be shared. Please enter your destination."
		 * ); }
		 * 
		 */

		// myController.setScreen(SoftwareEngineering.screen3ID);
		Parent newScene = FXMLLoader.load(getClass().getResource("EndForm.fxml"));
		Stage new_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		new_Stage.setTitle("Your Information");
		new_Stage.setScene(new Scene(newScene, 1920, 1080));
		new_Stage.show();
	}

	public void goHome(ActionEvent event) throws IOException{
		
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
	 +	 *********************** Mouse Handler *************************************
	 +	 **************************************************************************/
	 	
	 	EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() 
	 	{
	 		 
	         @Override
	         public void handle(MouseEvent mouseEvent)
	         {
	         	Form.timer.restartIdleTimer();
	         }
	     };
	
	
}