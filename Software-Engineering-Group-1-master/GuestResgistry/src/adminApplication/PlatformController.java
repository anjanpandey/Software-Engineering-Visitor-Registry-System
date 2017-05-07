package adminApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@SuppressWarnings("restriction")
public class PlatformController implements Initializable {

	public void Visitor(ActionEvent event) throws IOException {

		Parent newScene = FXMLLoader.load(getClass().getResource("VisitorView.fxml"));
		Stage new_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		new_Stage.setTitle("Visitor Table");
		new_Stage.setScene(new Scene(newScene, 1920, 1080));
		new_Stage.show();
	}
	
	public void helpButton(ActionEvent event) throws IOException {
		
		
	    // Load the fxml file and create a new stage for the popup
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("Manual.fxml"));
	    Parent root = (Parent) loader.load();
	    Stage stage = new Stage();
	    stage.initStyle(StageStyle.DECORATED);
	    stage.setTitle("Help Alert");
	    stage.setScene(new Scene(root));
	    stage.show();

}

	public void Analytics(ActionEvent event) throws IOException {

		Parent newScene = FXMLLoader.load(getClass().getResource("analytics.fxml"));
		Stage new_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		new_Stage.setTitle("Visitor Table");
		new_Stage.setScene(new Scene(newScene, 1920, 1080));
		new_Stage.show();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

}
