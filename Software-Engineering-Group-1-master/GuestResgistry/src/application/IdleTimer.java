/*
 * @author Hawkins Holden
 * 
 * IdleTimer.java is inititalized once the map is loaded. After ten minutes of 
 * inactivity (ie no mouse movement, clicks, or ANYTHING), the user is taken back
 * to the map. 
 * 
 * Note: will have to make it to where it will give a warning 30 seconds before it
 * times out. Will get to that later. 
 */


package application;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

@SuppressWarnings("restriction")
public class IdleTimer
{
	private Boolean isTimerRunning;
	private Timeline timeline;

	public void runTimer(Stage Stage) throws Exception
	{
		isTimerRunning = true;
		
		timeline = new Timeline(new KeyFrame(Duration.seconds(600), ev -> //the Duration.seconds() determines the time. 5 seconds was
	    {																  //used for testing, but 600 (10 minutes) is what it needs to be
	    	System.out.println("Scene switching will go here");
	    	
	    	try 
	    	{
	    		JDBC.addVisitor(VisitorContext.getInstance().currentVisitor());
	    		Parent root = FXMLLoader.load(getClass().getResource("Map.fxml"));
	    		Stage.setTitle("Welcome to Monroe-West Monroe CVB!!!");
	    		Stage.setScene(new Scene(root));
	    		Stage.setHeight(1080);
	    		Stage.setWidth(1920);
				Stage.show();	
				
				restartIdleTimer();
				
				root.setOnMouseClicked(mouseHandler);
			    root.setOnMouseDragged(mouseHandler);
			    root.setOnMouseEntered(mouseHandler);
			    root.setOnMouseExited(mouseHandler);
			    root.setOnMouseMoved(mouseHandler);
			    root.setOnMousePressed(mouseHandler);
			    root.setOnMouseReleased(mouseHandler);
				
				
	    	}
	    	catch (IOException e)
	    	{
	    		System.out.println("Something's wrong");
	    	}
	    }));
		timeline.play();	
	 }
	
	/***************************************************************************
	 *********************** Mouse Handler *************************************
	 **************************************************************************/
	
	EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() 
	{
		 
        @Override
        public void handle(MouseEvent mouseEvent)
        {
        	restartIdleTimer();
        }
    };
	
	 public boolean isTimerRunning()
	 {
	     return isTimerRunning;
	 }
	 
	 public void restartIdleTimer()
	 {
	     timeline.stop();
	     timeline.play();
	 }
}//end class
