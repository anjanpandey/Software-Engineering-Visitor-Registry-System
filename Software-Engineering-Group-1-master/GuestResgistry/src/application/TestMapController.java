package application;



import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.json.JSONException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class TestMapController implements Initializable {

	@FXML
	private WebView webView;
	@FXML
	private Button next_btn;
	
	WebEngine webEngine = new WebEngine();
	JSObject window;
	
	/**Used to communicate with the main method that is running*/
	//static Main main;
	
	
	/**
	 * Called from Main.java, this lets the controller know the instance of main to communicate with.
	 * @param newMain The instance of Main that will be communicating with this controller.
	 */

	public void NextButton(ActionEvent event) throws IOException{
		
webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {

			
			@Override
			public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
			
				
				if (newState == State.SUCCEEDED) {
				     window = (JSObject) webEngine.executeScript("window");
					window.setMember("app", new ComTest());
					System.out.println("stateChange");
				}
			}
			
		});

        
         window = (JSObject) webEngine.executeScript("window");
		window.setMember("app", new ComTest());
		webEngine.executeScript("theThing();");
		
	     System.out.println("success");
		
		/*java.net.URL num = getClass().getResource("Map.html");
		webEngine.load(num.toString());
		System.out.println("something");*/
	}
		

	/**
	 * Most of the work for this is done inside of Main.java, this simply initiates the process for opening a new window.
	 * @param f A simple button click (see MapViewer.fxml).
	 * @throws Exception
	 */
	public void changeScene(ActionEvent f) throws Exception{

		
		/*
		// make a WebEngine instance for manipulation of the WebView "map".
		WebEngine webengine = makeEngine(false);
		
		//System.out.println("somewhere is got, we have");
		
		if (webengine != null) {
			//System.out.println("again somewhere is got, we have");
			webengine.executeScript("setLocations(-25.363, 131.044)");
		}
		*/
		
		//main.setTarget("Form.fxml", true);

	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// initialize the map
		makeEngine(true);

	}

	private WebEngine makeEngine(boolean trigger) {

		// make a WebEngine instance for manipulation of the WebView "map".
	    webEngine = webView.getEngine();
		
		// load appropriate file
		java.net.URL num = getClass().getResource("GetCommToWork.html");
		webEngine.load(num.toString());


webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {

			
			@Override
			public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
			
				
				if (newState == State.SUCCEEDED) {
					window = (JSObject) webEngine.executeScript("window");
					window.setMember("app", new ComTest());
					System.out.println("stateChange");
				}
				if(newState != null){
					/*JSObject window = (JSObject) webEngine.executeScript("window");
					window.setMember("app", new ComTest());*/
				}
			}
			
		});

		/*window = (JSObject) webEngine.executeScript("window");
		window.setMember("app", new ComTest());
	     System.out.println("reLoaded");
		
		/*java.net.URL num = getClass().getResource("Map.html");
		webEngine.load(num.toString());
		System.out.println("something");*/

		return webEngine;
	}
	
	

	/**
	 * Required to communicate with the javascript that runs the map, contains methods to populate the map
	 * and use the information sent back from the map (via javascript) to store lat-long pairs and addresses.
	 *
	 */
	/*public class JavaApplication {

		public void callFromJavascript(Object coords)  {
			System.out.println(coords.toString());
			//String[] latLongPair = new String[2];
			//latLongPair = GeoCoding.getLatLong(coords);
			//TestArray.sendLatLongs(latLongPair);
			//System.out.println(SerializeJson.getAddress(GeoCoding.reverseGeoCode(coords)));
			
		}

	}*/
}

/*
 * Takes the latitude and longitude pairs stored in the database and uses them to place pins on the map
 * @param web The WebEngine for the current page in use.
 
private void populateMap(WebEngine web){
	
	ArrayList<String> latLongs = new ArrayList<String>();
	String temp = "";
	int temper = 0;
	
	//pull from "database"
	latLongs = GetFromJDBC.getLatLongs();
	temp = latLongs.get(0);
	
	System.out.println("getting to the communication");
	
	while(temper < latLongs.size()){
		web.executeScript("addToArray(element);");
		temper++;
	}
	
	System.out.println("past the communication");
	
	
	
	//Tell JavaScript to place the pins
	
	System.out.println("getting to the communication");
	
	//web.executeScript("createMarker(test)");
	web.executeScript("callJavaFX();");
	
	System.out.println("past the communication");
		
}*/ //Kept as a reference