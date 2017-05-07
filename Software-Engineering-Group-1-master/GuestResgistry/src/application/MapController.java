package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import netscape.javascript.JSObject;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class MapController implements Initializable {
	
	//ScreensController myController;
	

	@FXML
	private WebView webView;
	private WebEngine engine;
	private Visitor visitor;
	@FXML
	private Label heading;
	JSObject window;

	@SuppressWarnings("restriction")
	public void displayWeb() {
		engine = webView.getEngine();
		final String hellohtml = "FinalMap.html"; // HTML file to view in web
													// view
		URL urlHello = getClass().getResource(hellohtml);
		engine.load(urlHello.toExternalForm());

		// ----------------------------------------------

		engine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {

				if (newState == State.SUCCEEDED) {
					window = (JSObject) engine.executeScript("window");
					window.setMember("app", new JavascriptComm());
					System.out.println("stateChange");
					populateMap();
				}
			}

		});

		// ----------------------------------------------

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

	@SuppressWarnings("restriction")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		PathTransition transition = new PathTransition();

		Line line = new Line();
		line.setStartX(-50);
		line.setEndX(800);
		line.setStartY(25);
		line.setEndY(25);
		transition.setDuration(Duration.seconds(12));
		transition.setNode(heading);
		transition.setPath(line);
		transition.setAutoReverse(true);
		transition.setCycleCount(TranslateTransition.INDEFINITE);
		transition.play();
		

		visitor = VisitorContext.getInstance().currentVisitor();
		engine = webView.getEngine();
		displayWeb();
	}

	/**
	 * Changes the focus of the map to Asia
	 * 
	 * @param event
	 * @throws IOException
	 */
	@SuppressWarnings("restriction")
	public void AsiaButton(ActionEvent event) throws IOException {

		// ----------------------------------------------

		engine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {

				if (newState == State.SUCCEEDED) {
					window = (JSObject) engine.executeScript("window");
					window.setMember("app", new JavascriptComm());
					System.out.println("stateChange");
				}
			}

		});

		window = (JSObject) engine.executeScript("window");
		window.setMember("app", new JavascriptComm());
		engine.executeScript("getAsianLatandLong();");

		// ----------------------------------------------
		populateMap();
	}

	/**
	 * Changes the focus of the map to Africa
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void AfricaButton(ActionEvent event) throws IOException {

		// ----------------------------------------------

		engine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {

				if (newState == State.SUCCEEDED) {
					window = (JSObject) engine.executeScript("window");
					window.setMember("app", new JavascriptComm());
					System.out.println("stateChange");
				}
			}

		});

		window = (JSObject) engine.executeScript("window");
		window.setMember("app", new JavascriptComm());
		engine.executeScript("getAfricanLatandLong();");

		// ----------------------------------------------
		populateMap();
	}

	/**
	 * Changes the focus of the map to Europe
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void EuropeButton(ActionEvent event) throws IOException {

		// ----------------------------------------------

		engine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {

				if (newState == State.SUCCEEDED) {
					window = (JSObject) engine.executeScript("window");
					window.setMember("app", new JavascriptComm());
					System.out.println("stateChange");
				}
			}

		});

		window = (JSObject) engine.executeScript("window");
		window.setMember("app", new JavascriptComm());
		engine.executeScript("getEuropeanLatandLong();");

		// ----------------------------------------------
		populateMap();
	}

	/**
	 * Changes the focus of the map to the USA
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void USAButton(ActionEvent event) throws IOException {

		// ----------------------------------------------

		engine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {

				if (newState == State.SUCCEEDED) {
					window = (JSObject) engine.executeScript("window");
					window.setMember("app", new JavascriptComm());
					System.out.println("stateChange");
				}
			}

		});

		window = (JSObject) engine.executeScript("window");
		window.setMember("app", new JavascriptComm());
		engine.executeScript("getUSALatandLong();");

		// ----------------------------------------------
		populateMap();
	}

	public void NextButton(ActionEvent event) throws IOException {

		// Get necessary stuff from Javascript
		// ----------------------------------------------

		engine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {

				if (newState == State.SUCCEEDED) {
					window = (JSObject) engine.executeScript("window");
					window.setMember("app", new JavascriptComm());
					System.out.println("stateChange");
				}
			}

		});

		window = (JSObject) engine.executeScript("window");
		window.setMember("app", new JavascriptComm());
		engine.executeScript("giveInfo();");

		
		//myController.setScreen(SoftwareEngineering.screen1ID);
		Parent newScene = FXMLLoader.load(getClass().getResource("BeginForm.fxml"));
		Stage new_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		new_Stage.setTitle("Your Information");
		new_Stage.setScene(new Scene(newScene, 1920, 1080));
		new_Stage.show();

	}

	/**
	 * Initiates the process of populating the javascript run Google map
	 */
	private void populateMap() {
		engine.executeScript("populateJSMap();");
	}

	/*
	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
		
	}
	
	*/

}

