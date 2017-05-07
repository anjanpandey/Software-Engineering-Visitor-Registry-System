package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Form extends Application 
{
	static IdleTimer timer = new IdleTimer();
	
	public void start(Stage Stage) throws Exception {
		
	
		Parent root = FXMLLoader.load(getClass().getResource("Map.fxml"));
		Stage.setTitle("Welcome to Monroe-West Monroe CVB!!!");
		Stage.setScene(new Scene(root));
		Stage.setHeight(1080);
		Stage.setWidth(1920);
		Stage.show();
		
		timer.runTimer(Stage);
		
		root.setOnMouseClicked(mouseHandler);
	    root.setOnMouseDragged(mouseHandler);
	    root.setOnMouseEntered(mouseHandler);
	    root.setOnMouseExited(mouseHandler);
	    root.setOnMouseMoved(mouseHandler);
	    root.setOnMousePressed(mouseHandler);
	    root.setOnMouseReleased(mouseHandler);	

	}

	/***************************************************************************
	 *********************** Mouse Handler *************************************
	 **************************************************************************/
	
	EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() 
	{
		 
        @Override
        public void handle(MouseEvent mouseEvent)
        {
        	timer.restartIdleTimer();
        }
    };
	
	
	public static void main(String[] args) {
		launch(args);
	} 

}