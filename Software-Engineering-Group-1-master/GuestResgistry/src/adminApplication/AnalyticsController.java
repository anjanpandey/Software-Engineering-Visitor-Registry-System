package adminApplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jxl.write.WriteException;
import netscape.javascript.JSObject;

@SuppressWarnings("restriction")
public class AnalyticsController implements Initializable {

	@FXML
	private TextField citiesMenuText;
	@FXML
	private TextField filterCity;
	@FXML
	private TextField filterCountry;
	@FXML
	private TextField filterState;
	@FXML
	private Button clearButton;
	@FXML
	private Button refreshButton;
	@FXML
	private Button exportButton;
	@FXML
	private Button exportEmailButton;
	@FXML
	private CheckBox areaCheck;
	@FXML
	private CheckBox dateCheck;
	@FXML
	private CheckBox destinationCheck;
	@FXML
	private CheckBox emailCheck;
	@FXML
	private CheckBox hotelCheck;
	@FXML
	private CheckBox referredCheck;
	@FXML
	private CheckBox repeatCheck;
	@FXML
	private CheckBox travelingCheck;
	@FXML
	private DatePicker endDatePicker;
	@FXML
	private DatePicker startDatePicker;
	@FXML
	private ContextMenu citiesMenu;
	@FXML
	private ContextMenu countriesMenu;
	@FXML
	private ContextMenu destinationMenu;
	@FXML
	private ContextMenu emailMenu;
	@FXML
	private ContextMenu hotelMenu;
	@FXML
	private ContextMenu referenceMenu;
	@FXML
	private ContextMenu repeatMenu;
	@FXML
	private ContextMenu statesMenu;
	@FXML
	private ContextMenu travelingMenu;
	@FXML
	private MenuButton citiesMenuButton;
	@FXML
	private MenuButton countriesMenuButton;
	@FXML
	private MenuButton destinationMenuButton;
	@FXML
	private MenuButton referenceMenuButton;
	@FXML
	private MenuButton statesMenuButton;
	@FXML
	private MenuButton travelingMenuButton;
	@FXML
	private CheckMenuItem providedMenuItem;
	@FXML
	private CheckMenuItem notProvidedMenuItem;
	@FXML
	private TableView<VisitorDetails> visitorTable;
	@FXML
	private TableColumn<VisitorDetails, String> emailColumn;
	@FXML
	private TableColumn<VisitorDetails, String> metroColumn;
	@FXML
	private TableColumn<VisitorDetails, String> cityColumn;
	@FXML
	private TableColumn<VisitorDetails, String> stateColumn;
	@FXML
	private TableColumn<VisitorDetails, String> countryColumn;
	@FXML
	private TableColumn<VisitorDetails, String> zipColumn;
	@FXML
	private TableColumn<VisitorDetails, Integer> partyColumn;
	@FXML
	private TableColumn<VisitorDetails, String> heardColumn;
	@FXML
	private TableColumn<VisitorDetails, String> destinationColumn;
	@FXML
	private TableColumn<VisitorDetails, String> hotelColumn;
	@FXML
	private TableColumn<VisitorDetails, String> repeatColumn;
	@FXML
	private TableColumn<VisitorDetails, String> reasonColumn;
	@FXML
	private TableColumn<VisitorDetails, Date> dateColumn;
	@FXML
	private LineChart<String, Number> lineChart;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private NumberAxis yAxis;

	private static ObservableList<VisitorDetails> data;

	@FXML
	private WebView webView;
	private WebEngine engine;
	JSObject window;

	public void displayWeb() {
		engine = webView.getEngine();
		final String hellohtml = "adminMap.html"; // HTML file to view in web
													// view
		URL urlHello = getClass().getResource(hellohtml);
		engine.load(urlHello.toExternalForm());

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

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		refreshMenus();
		startDatePicker.setValue(LocalDate.now().minusYears(1));
		endDatePicker.setValue(LocalDate.now());
		engine = webView.getEngine();
		refreshData();

		filterCity.textProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				filterVisitorByCity((String) oldValue, (String) newValue);
			}
		});

		filterCountry.textProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				filterVisitorByCountry((String) oldValue, (String) newValue);
			}
		});

		filterState.textProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				filterVisitorByState((String) oldValue, (String) newValue);
			}
		});
	}

	public void filterVisitorByCountry(String oldValue, String newValue) {
		ObservableList<VisitorDetails> filteredList = FXCollections.observableArrayList();
		if ((filterCountry == null) || (newValue.length() < oldValue.length()) || newValue == null) {
			visitorTable.setItems(data);
		} else {
			newValue = newValue.toUpperCase();
			for (VisitorDetails visitors : visitorTable.getItems()) {
				String filterByCountry = visitors.getCountry();
				if (filterByCountry.toUpperCase().contains(newValue)) {
					filteredList.add(visitors);
				}
			}
			visitorTable.setItems(filteredList);
		}

	}

	public void filterVisitorByCity(String oldValue, String newValue) {
		ObservableList<VisitorDetails> filteredList = FXCollections.observableArrayList();
		if ((filterCity == null) || (newValue.length() < oldValue.length()) || newValue == null) {
			visitorTable.setItems(data);
		} else {
			newValue = newValue.toUpperCase();
			for (VisitorDetails visitors : visitorTable.getItems()) {
				String filterByCity = visitors.getCity();
				if (filterByCity.toUpperCase().contains(newValue)) {
					filteredList.add(visitors);
				}
			}
			visitorTable.setItems(filteredList);
		}

	}

	public void filterVisitorByState(String oldValue, String newValue) {
		ObservableList<VisitorDetails> filteredList = FXCollections.observableArrayList();
		if ((filterState == null) || (newValue.length() < oldValue.length()) || newValue == null) {
			visitorTable.setItems(data);
		} else {
			newValue = newValue.toUpperCase();
			for (VisitorDetails visitors : visitorTable.getItems()) {
				String filterByState = visitors.getState();
				if (filterByState.toUpperCase().contains(newValue)) {
					filteredList.add(visitors);
				}
			}
			visitorTable.setItems(filteredList);
		}

	}

	public void refreshMenus() {

		areaCheck.setSelected(false);
		dateCheck.setSelected(false);
		destinationCheck.setSelected(false);
		emailCheck.setSelected(false);
		hotelCheck.setSelected(false);
		referredCheck.setSelected(false);
		repeatCheck.setSelected(false);
		travelingCheck.setSelected(false);

		List<String> cities = AdminJDBC.getCitiesandMetros();
		ObservableList<CheckMenuItem> cityItems = FXCollections.observableArrayList();
		for (String city : cities) {
			cityItems.add(new CheckMenuItem(city));

		}
		citiesMenuButton.getItems().clear();
		citiesMenuButton.getItems().addAll(cityItems);

		List<String> countries = AdminJDBC.getCountries();
		ObservableList<CheckMenuItem> countryItems = FXCollections.observableArrayList();
		for (String country : countries) {
			if (country != null && !country.isEmpty()) {
				countryItems.add(new CheckMenuItem(country));
			}
		}
		countriesMenuButton.getItems().clear();
		countriesMenuButton.getItems().addAll(countryItems);

		List<String> destinations = AdminJDBC.getDestinations();
		Set<String> destinationSet = new HashSet<String>();
		for (String dest : destinations) {
			for (String interest : dest.split(",")) {
				destinationSet.add(interest);
			}
		}
		destinations = new ArrayList<String>();
		for (String destination : destinationSet) {
			destinations.add(destination);
		}
		destinations.sort(Comparator.naturalOrder());
		ObservableList<CheckMenuItem> destinationItems = FXCollections.observableArrayList();
		for (String destination : destinations) {
			destinationItems.add(new CheckMenuItem(destination));
		}
		destinationMenuButton.getItems().clear();
		destinationMenuButton.getItems().addAll(destinationItems);

		List<String> hotels = AdminJDBC.getHotels();
		ObservableList<CheckMenuItem> hotelItems = FXCollections.observableArrayList();
		for (String hotel : hotels) {
			hotelItems.add(new CheckMenuItem(hotel));
		}

		List<String> references = AdminJDBC.getReferences();
		ObservableList<CheckMenuItem> referenceItems = FXCollections.observableArrayList();
		for (String reference : references) {
			referenceItems.add(new CheckMenuItem(reference));
		}
		referenceMenuButton.getItems().clear();
		referenceMenuButton.getItems().addAll(referenceItems);

		List<String> states = AdminJDBC.getStates();
		ObservableList<CheckMenuItem> stateItems = FXCollections.observableArrayList();
		for (String state : states) {
			if (state != null && !state.isEmpty()) {
				stateItems.add(new CheckMenuItem(state));
			}
		}
		statesMenuButton.getItems().clear();
		statesMenuButton.getItems().addAll(stateItems);

		List<String> travelingReasons = AdminJDBC.getReasons();
		ObservableList<CheckMenuItem> reasonItems = FXCollections.observableArrayList();
		for (String reason : travelingReasons) {
			reasonItems.add(new CheckMenuItem(reason));
		}
		travelingMenuButton.getItems().clear();
		travelingMenuButton.getItems().addAll(reasonItems);

		ObservableList<CheckMenuItem> emailItems = FXCollections.observableArrayList();
		emailItems.add(new CheckMenuItem("Provided"));
		emailItems.add(new CheckMenuItem("Not Provided"));
	}

	public void refreshData() {
		data = getFilteredVisitorDetails();
		refreshTable();
		refreshChart();
		displayWeb();

	}

	public void refreshTable() {
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		metroColumn.setCellValueFactory(new PropertyValueFactory<>("metro"));
		cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
		stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
		countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
		zipColumn.setCellValueFactory(new PropertyValueFactory<>("zip"));
		partyColumn.setCellValueFactory(new PropertyValueFactory<>("party"));
		heardColumn.setCellValueFactory(new PropertyValueFactory<>("heard"));
		hotelColumn.setCellValueFactory(new PropertyValueFactory<>("hotel"));
		destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
		repeatColumn.setCellValueFactory(new PropertyValueFactory<>("repeatVisit"));
		reasonColumn.setCellValueFactory(new PropertyValueFactory<>("travelingFor"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("visitingDay"));

		visitorTable.setItems(null);
		visitorTable.setItems(data);
	}

	public ObservableList<VisitorDetails> getFilteredVisitorDetails() {
		Date startDate = Date.valueOf(startDatePicker.getValue());
		Date endDate = Date.valueOf(endDatePicker.getValue().plusDays(1));
		StringBuilder query = new StringBuilder(
				"SELECT * FROM visitors LEFT JOIN visits ON visitors.VisitorID = visits.VisitorID LEFT JOIN visitorlocations on visitors.VisitorID = visitorlocations.VisitorID WHERE visitors.VisitorID IS NOT NULL");
		if (dateCheck.isSelected()) {
			query.append(" AND Visiting_Day >= '" + startDate.toString() + "' AND Visiting_Day <= '"
					+ endDate.toString() + "'");
		}
		if (areaCheck.isSelected()) {
			boolean first = true;
			for (MenuItem cityItem : citiesMenuButton.getItems()) {
				CheckMenuItem city = (CheckMenuItem) cityItem;
				if (city.isSelected()) {
					String cityString = city.getText().split(",")[0];
					if (first) {
						query.append(" AND City = '" + cityString + "' OR Metro = '" + cityString + "'");
						first = false;
					} else {
						query.append(" OR City = '" + cityString + "' OR Metro = '" + cityString + "'");
					}
				}
			}
			for (MenuItem stateItem : statesMenuButton.getItems()) {
				CheckMenuItem state = (CheckMenuItem) stateItem;
				if (state.isSelected()) {
					if (first) {
						query.append(" AND State = '" + state.getText() + "'");
						first = false;
					} else {
						query.append(" OR State = '" + state.getText() + "'");
					}
				}
			}
			for (MenuItem countryItem : countriesMenuButton.getItems()) {
				CheckMenuItem country = (CheckMenuItem) countryItem;
				if (country.isSelected()) {
					if (first) {
						query.append(" AND Country = '" + country.getText() + "'");
						first = false;
					} else {
						query.append(" OR Country = " + country.getText() + "'");
					}
				}
			}
		}
		if (travelingCheck.isSelected()) {
			for (MenuItem reasonItem : travelingMenuButton.getItems()) {
				CheckMenuItem reason = (CheckMenuItem) reasonItem;
				boolean first = true;
				if (reason.isSelected()) {
					if (first) {
						query.append(" AND TravelingFor = '" + reason.getText() + "'");
						first = false;
					} else {
						query.append(" OR TravelingFor = " + reason.getText() + "'");
					}
				}
			}
		}
		if (referredCheck.isSelected()) {
			for (MenuItem referenceItem : referenceMenuButton.getItems()) {
				CheckMenuItem reference = (CheckMenuItem) referenceItem;
				boolean first = true;
				if (reference.isSelected()) {
					if (first) {
						query.append(" AND Heard = '" + reference.getText() + "'");
						first = false;
					} else {
						query.append(" OR Heard = '" + reference.getText() + "'");
					}
				}
			}
		}
		if (hotelCheck.isSelected()) {
			query.append(" AND Hotel = 'Yes'");
		}
		if (destinationCheck.isSelected()) {
			for (MenuItem destinationItem : destinationMenuButton.getItems()) {
				CheckMenuItem destination = (CheckMenuItem) destinationItem;
				boolean first = true;
				if (destination.isSelected()) {
					if (first) {
						query.append(" AND Destination LIKE '%" + destination.getText() + "%'");
						first = false;
					} else {
						query.append(" AND Destination LIKE '%" + destination.getText() + "%'");
					}
				}
			}
		}
		if (repeatCheck.isSelected()) {
			query.append(" AND RepeatVisit = 1");
		}
		if (emailCheck.isSelected()) {
			query.append(" AND Email IS NOT NULL AND Email <> 'null' AND Email <> 'NULL' AND Email <> ''");
		}
		query.append(" ORDER BY Visiting_Day");

		System.out.println(query);
		ObservableList<VisitorDetails> visitors = FXCollections.observableArrayList();
		String queryString = query.toString();
		List<VisitorDetails> stuffReturnedfromQuery = AdminJDBC.getVisitorDetailsFromQuery(queryString);
		for (VisitorDetails v : stuffReturnedfromQuery) {
			visitors.add(v);
		}
		return visitors;
	}

	public ObservableList<VisitorDetails> getFilteredLatLongs() {
		Date startDate = Date.valueOf(startDatePicker.getValue());
		Date endDate = Date.valueOf(endDatePicker.getValue().plusDays(1));
		StringBuilder query = new StringBuilder(
				"SELECT Latitude, Longitude FROM visitors LEFT JOIN visits ON visitors.VisitorID = visits.VisitorID LEFT JOIN visitorlocations on visitors.VisitorID = visitorlocations.VisitorID WHERE visitors.VisitorID IS NOT NULL");
		if (dateCheck.isSelected()) {
			query.append(" AND Visiting_Day >= '" + startDate.toString() + "' AND Visiting_Day <= '"
					+ endDate.toString() + "'");
		}
		if (areaCheck.isSelected()) {
			boolean first = true;
			for (MenuItem cityItem : citiesMenuButton.getItems()) {
				CheckMenuItem city = (CheckMenuItem) cityItem;
				if (city.isSelected()) {
					String cityString = city.getText().split(",")[0];
					if (first) {
						query.append(" AND City = '" + cityString + "' OR Metro = '" + cityString + "'");
						first = false;
					} else {
						query.append(" OR City = '" + cityString + "' OR Metro = '" + cityString + "'");
					}
				}
			}
			for (MenuItem stateItem : statesMenuButton.getItems()) {
				CheckMenuItem state = (CheckMenuItem) stateItem;
				if (state.isSelected()) {
					if (first) {
						query.append(" AND State = '" + state.getText() + "'");
						first = false;
					} else {
						query.append(" OR State = '" + state.getText() + "'");
					}
				}
			}
			for (MenuItem countryItem : countriesMenuButton.getItems()) {
				CheckMenuItem country = (CheckMenuItem) countryItem;
				if (country.isSelected()) {
					if (first) {
						query.append(" AND Country = '" + country.getText() + "'");
						first = false;
					} else {
						query.append(" OR Country = " + country.getText() + "'");
					}
				}
			}
		}
		if (travelingCheck.isSelected()) {
			for (MenuItem reasonItem : travelingMenuButton.getItems()) {
				CheckMenuItem reason = (CheckMenuItem) reasonItem;
				boolean first = true;
				if (reason.isSelected()) {
					if (first) {
						query.append(" AND TravelingFor = '" + reason.getText() + "'");
						first = false;
					} else {
						query.append(" OR TravelingFor = " + reason.getText() + "'");
					}
				}
			}
		}
		if (referredCheck.isSelected()) {
			for (MenuItem referenceItem : referenceMenuButton.getItems()) {
				CheckMenuItem reference = (CheckMenuItem) referenceItem;
				boolean first = true;
				if (reference.isSelected()) {
					if (first) {
						query.append(" AND Heard = '" + reference.getText() + "'");
						first = false;
					} else {
						query.append(" OR Heard = '" + reference.getText() + "'");
					}
				}
			}
		}
		if (hotelCheck.isSelected()) {
			query.append(" AND Hotel = 'Yes'");
		}
		if (destinationCheck.isSelected()) {
			for (MenuItem destinationItem : destinationMenuButton.getItems()) {
				CheckMenuItem destination = (CheckMenuItem) destinationItem;
				boolean first = true;
				if (destination.isSelected()) {
					if (first) {
						query.append(" AND Destination LIKE '%" + destination.getText() + "%'");
						first = false;
					} else {
						query.append(" AND Destination LIKE '%" + destination.getText() + "%'");
					}
				}
			}
		}
		if (repeatCheck.isSelected()) {
			query.append(" AND RepeatVisit = 1");
		}
		if (emailCheck.isSelected()) {
			query.append(" AND Email IS NOT NULL AND Email <> 'null' AND Email <> 'NULL' AND Email <> ''");
		}
		query.append(
				"AND (Latitude IS NOT NULL AND Latitude != '' AND Latitude != 'null' AND Longitude IS NOT NULL AND Longitude != '' AND Longitude != 'null') ORDER BY Visiting_Day");

		System.out.println(query);
		ObservableList<VisitorDetails> visitors = FXCollections.observableArrayList();
		for (VisitorDetails v : AdminJDBC.getVisitorDetailsFromQuery(query.toString())) {
			visitors.add(v);
		}
		return visitors;
	}

	private void populateMap() {
		engine.executeScript("populateJSMap();");
	}

	public void goBack(ActionEvent e) throws IOException {
		Parent newScene = FXMLLoader.load(getClass().getResource("Platform.fxml"));
		Stage new_Stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		new_Stage.setTitle("Visitor Table");
		new_Stage.setScene(new Scene(newScene, 1920, 1800));
		new_Stage.show();
	}

	@FXML
	private void ExportAction(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		if (!data.isEmpty()) {
			FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel Files(*.xls)", "*.xls");
			chooser.getExtensionFilters().add(filter);
			File file = chooser.showSaveDialog(exportButton.getScene().getWindow());
			if (file != null) {
				try {
					JExcelDriver.saveXLSFile(file, data);
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {

		}
	}

	@FXML
	private void ExportEmailAction(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		// Set extension filter
		if (!data.isEmpty()) {
			FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel Files(*.xls)", "*.xls");
			chooser.getExtensionFilters().add(filter);
			// Show save dialog
			File file = chooser.showSaveDialog(exportButton.getScene().getWindow());
			if (file != null) {
				try {
					JExcelDriver.saveEmailList(file, data);
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {

		}
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

	public void ImportAction() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Export mailing list to Excel");
		alert.setHeaderText(
				"An import template file must be used for importing visitor information. Would you like to generate a template file?");
		alert.setContentText("");

		ButtonType buttonTypeOne = new ButtonType("Generate Template");
		ButtonType buttonTypeTwo = new ButtonType("Import from File");
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne) {
			FileChooser chooser = new FileChooser();
			FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel Files(*.xls)", "*.xls");
			chooser.getExtensionFilters().add(filter);
			File file = chooser.showSaveDialog(exportButton.getScene().getWindow());
			try {
				JExcelDriver.generateImportTemplate(file);
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (result.get() == buttonTypeTwo) {
			FileChooser chooser = new FileChooser();
			FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel Files(*.xls)", "*.xls");
			chooser.getExtensionFilters().add(filter);
			try {
				File file = chooser.showOpenDialog(exportButton.getScene().getWindow());
				JExcelDriver.readXLSFile(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

		}
		refreshData();
	}

	@SuppressWarnings("unchecked")
	private void refreshChart() {
		lineChart.getData().clear();
		lineChart.getData().addAll(getChartData());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Series getChartData() {
		XYChart.Series series = new XYChart.Series();
		Calendar cal = Calendar.getInstance();
		java.util.Date minDate = null, maxDate = null;
		if (data.isEmpty() || data.get(0) == null) {
			minDate = cal.getTime();
			maxDate = cal.getTime();
		} else {
			cal.setTime(data.get(0).getVisitingDay());
			minDate = cal.getTime();
			maxDate = cal.getTime();
		}

		for (VisitorDetails datum : data) {
			if (datum.getVisitingDay() != null) {
				Calendar compCal = Calendar.getInstance();
				compCal.setTime(datum.getVisitingDay());
				if (compCal.getTime().compareTo(minDate) < 0) {
					minDate = compCal.getTime();
				} else if (compCal.getTime().compareTo(maxDate) > 0) {
					maxDate = compCal.getTime();
				}
			}
		}

		Calendar initCal = Calendar.getInstance();
		initCal.setTime(minDate);
		java.util.Date date = initCal.getTime();
		while (date.compareTo(maxDate) <= 0) {
			Calendar dateCal = Calendar.getInstance();
			dateCal.setTime(date);
			int count = 0;
			for (VisitorDetails datum : data) {
				if (datum.getVisitingDay() != null) {
					cal.setTime(datum.getVisitingDay());
					if (dateCal.get(Calendar.MONTH) == cal.get(Calendar.MONTH)
							&& dateCal.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
						if (datum.getParty() != null || datum.getParty() > 0) {
							count += datum.getParty();
						} else {
							count++;
						}
					}
				}
			}
			String monthString;
			switch (dateCal.get(Calendar.MONTH)) {
			case Calendar.JANUARY:
				monthString = "January ";
				break;
			case Calendar.FEBRUARY:
				monthString = "February ";
				break;
			case Calendar.MARCH:
				monthString = "March ";
				break;
			case Calendar.APRIL:
				monthString = "April ";
				break;
			case Calendar.MAY:
				monthString = "May ";
				break;
			case Calendar.JUNE:
				monthString = "June ";
				break;
			case Calendar.JULY:
				monthString = "July ";
				break;
			case Calendar.AUGUST:
				monthString = "August ";
				break;
			case Calendar.SEPTEMBER:
				monthString = "September ";
				break;
			case Calendar.OCTOBER:
				monthString = "October ";
				break;
			case Calendar.NOVEMBER:
				monthString = "November ";
				break;
			case Calendar.DECEMBER:
				monthString = "December ";
				break;
			default:
				monthString = "Invalid month";
				break;
			}

			
			monthString += dateCal.get(Calendar.YEAR);
			series.getData().add(new XYChart.Data(monthString, count));
			cal.setTime(date);
			if (cal.get(Calendar.MONTH) == Calendar.DECEMBER) {
				cal.roll(Calendar.MONTH, 1);
				cal.roll(Calendar.YEAR, 1);
			} else {
				cal.roll(Calendar.MONTH, 1);
			}
			date = cal.getTime();
		}
		return series;

	}

	public static ArrayList<String[]> getLatLongData() {
		ArrayList<String[]> locations = new ArrayList<String[]>();

		for (VisitorDetails vd : data) {
			String[] latlng = new String[2];
			if (!(vd.getLatitude() == null || vd.getLatitude().isEmpty() || vd.getLatitude().equals("null"))) {
				latlng[0] = vd.getLatitude();
				latlng[1] = vd.getLongitude();
				locations.add(latlng);
			}
		}
		return locations;
	}

	public static ArrayList<Integer> getZipData() {
		ArrayList<Integer> locations = new ArrayList<Integer>();
		for (VisitorDetails vd : data) {
			locations.add(vd.getZip());
		}
		return locations;
	}
}
