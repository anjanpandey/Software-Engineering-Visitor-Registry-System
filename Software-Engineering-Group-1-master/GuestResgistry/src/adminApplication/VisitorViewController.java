package adminApplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
 
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.IntegerStringConverter;
import jxl.write.WriteException;

@SuppressWarnings("restriction")
public class VisitorViewController implements Initializable {

	@FXML
	private Button addButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button excelButton;
	@FXML
	private Button emailButton;
	@FXML
	private Button importButton;
	@FXML
	private Button refreshButton;
	@FXML
	private DatePicker startDatePicker;
	@FXML
	private DatePicker endDatePicker;
	@FXML
	private TextField filterCity; 
	@FXML
	private TextField filterCountry;
	@FXML
	private TextField filterState;
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
	private TableColumn<VisitorDetails, Integer> zipColumn;
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

	private ObservableList<VisitorDetails> data;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		startDatePicker.setValue(LocalDate.now().minusWeeks(1));
		endDatePicker.setValue(LocalDate.now());
		data = getVisitors(startDatePicker.getValue(), endDatePicker.getValue());
		
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
		
		
		
		refreshTable();
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
		repeatColumn.setCellValueFactory(new PropertyValueFactory<>("repeatVisitString"));
		reasonColumn.setCellValueFactory(new PropertyValueFactory<>("travelingFor"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("visitingDay"));

		visitorTable.setItems(null);
		data = getVisitors(startDatePicker.getValue(), endDatePicker.getValue());
		visitorTable.setItems(data);
		visitorTable.setEditable(true);

		emailColumn.setOnEditCommit(e -> email_OnEditCommit(e));
		emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		metroColumn.setOnEditCommit(e -> metro_OnEditCommit(e));
		metroColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		cityColumn.setOnEditCommit(e -> city_OnEditCommit(e));
		cityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		stateColumn.setOnEditCommit(e -> state_OnEditCommit(e));
		stateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		countryColumn.setOnEditCommit(e -> country_OnEditCommit(e));
		countryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		zipColumn.setOnEditCommit(e -> zip_OnEditCommit(e));
		zipColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		partyColumn.setOnEditCommit(e -> party_OnEditCommit(e));
		partyColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		heardColumn.setOnEditCommit(e -> heard_OnEditCommit(e));
		heardColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		destinationColumn.setOnEditCommit(e -> destination_OnEditCommit(e));
		destinationColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		hotelColumn.setOnEditCommit(e -> hotel_OnEditCommit(e));
		hotelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		repeatColumn.setOnEditCommit(e -> repeat_OnEditCommit(e));
		repeatColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		reasonColumn.setOnEditCommit(e -> reason_OnEditCommit(e));
		reasonColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		visitorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	private void email_OnEditCommit(CellEditEvent<VisitorDetails, String> e) {
		TableColumn.CellEditEvent<VisitorDetails, String> cellEditEvent;
		cellEditEvent = (TableColumn.CellEditEvent<VisitorDetails, String>) e;
		VisitorDetails visitor = cellEditEvent.getRowValue();
		visitor.setEmail(cellEditEvent.getNewValue());
		AdminJDBC.updateVisitorDetails(visitor);
	}

	private void metro_OnEditCommit(CellEditEvent<VisitorDetails, String> e) {
		TableColumn.CellEditEvent<VisitorDetails, String> cellEditEvent;
		cellEditEvent = (TableColumn.CellEditEvent<VisitorDetails, String>) e;
		VisitorDetails visitor = cellEditEvent.getRowValue();
		visitor.setMetro(cellEditEvent.getNewValue());
		AdminJDBC.updateVisitorDetails(visitor);
	}

	private void city_OnEditCommit(CellEditEvent<VisitorDetails, String> e) {
		TableColumn.CellEditEvent<VisitorDetails, String> cellEditEvent;
		cellEditEvent = (TableColumn.CellEditEvent<VisitorDetails, String>) e;
		VisitorDetails visitor = cellEditEvent.getRowValue();
		visitor.setCity(cellEditEvent.getNewValue());
		AdminJDBC.updateVisitorDetails(visitor);
	}

	private void state_OnEditCommit(CellEditEvent<VisitorDetails, String> e) {
		TableColumn.CellEditEvent<VisitorDetails, String> cellEditEvent;
		cellEditEvent = (TableColumn.CellEditEvent<VisitorDetails, String>) e;
		VisitorDetails visitor = cellEditEvent.getRowValue();
		visitor.setState(cellEditEvent.getNewValue());
		AdminJDBC.updateVisitorDetails(visitor);
	}

	private void country_OnEditCommit(CellEditEvent<VisitorDetails, String> e) {
		TableColumn.CellEditEvent<VisitorDetails, String> cellEditEvent;
		cellEditEvent = (TableColumn.CellEditEvent<VisitorDetails, String>) e;
		VisitorDetails visitor = cellEditEvent.getRowValue();
		visitor.setCountry(cellEditEvent.getNewValue());
		AdminJDBC.updateVisitorDetails(visitor);
	}

	private void zip_OnEditCommit(CellEditEvent<VisitorDetails, Integer> e) {
		TableColumn.CellEditEvent<VisitorDetails, Integer> cellEditEvent;
		cellEditEvent = (TableColumn.CellEditEvent<VisitorDetails, Integer>) e;
		VisitorDetails visitor = cellEditEvent.getRowValue();
		visitor.setZip(cellEditEvent.getNewValue());
		AdminJDBC.updateVisitorDetails(visitor);
	}

	private void party_OnEditCommit(CellEditEvent<VisitorDetails, Integer> e) {
		TableColumn.CellEditEvent<VisitorDetails, Integer> cellEditEvent;
		cellEditEvent = (TableColumn.CellEditEvent<VisitorDetails, Integer>) e;
		VisitorDetails visitor = cellEditEvent.getRowValue();
		visitor.setParty(cellEditEvent.getNewValue());
		AdminJDBC.updateVisitorDetails(visitor);
	}

	private void heard_OnEditCommit(CellEditEvent<VisitorDetails, String> e) {
		TableColumn.CellEditEvent<VisitorDetails, String> cellEditEvent;
		cellEditEvent = (TableColumn.CellEditEvent<VisitorDetails, String>) e;
		VisitorDetails visitor = cellEditEvent.getRowValue();
		visitor.setHeard(cellEditEvent.getNewValue());
		AdminJDBC.updateVisitorDetails(visitor);
	}

	private void destination_OnEditCommit(CellEditEvent<VisitorDetails, String> e) {
		TableColumn.CellEditEvent<VisitorDetails, String> cellEditEvent;
		cellEditEvent = (TableColumn.CellEditEvent<VisitorDetails, String>) e;
		VisitorDetails visitor = cellEditEvent.getRowValue();
		visitor.setDestination(cellEditEvent.getNewValue());
		AdminJDBC.updateVisitorDetails(visitor);
	}

	private void hotel_OnEditCommit(CellEditEvent<VisitorDetails, String> e) {
		TableColumn.CellEditEvent<VisitorDetails, String> cellEditEvent;
		cellEditEvent = (TableColumn.CellEditEvent<VisitorDetails, String>) e;
		VisitorDetails visitor = cellEditEvent.getRowValue();
		visitor.setHotel(cellEditEvent.getNewValue());
		AdminJDBC.updateVisitorDetails(visitor);
	}

	private void repeat_OnEditCommit(CellEditEvent<VisitorDetails, String> e) {
		TableColumn.CellEditEvent<VisitorDetails, String> cellEditEvent;
		cellEditEvent = (TableColumn.CellEditEvent<VisitorDetails, String>) e;
		VisitorDetails visitor = cellEditEvent.getRowValue();
		visitor.setRepeatVisitString(cellEditEvent.getNewValue());
		AdminJDBC.updateVisitorDetails(visitor);
	}

	private void reason_OnEditCommit(CellEditEvent<VisitorDetails, String> e) {
		TableColumn.CellEditEvent<VisitorDetails, String> cellEditEvent;
		cellEditEvent = (TableColumn.CellEditEvent<VisitorDetails, String>) e;
		VisitorDetails visitor = cellEditEvent.getRowValue();
		visitor.setTravelingFor(cellEditEvent.getNewValue());
		AdminJDBC.updateVisitorDetails(visitor);
	}

	public void goBack(ActionEvent e) throws IOException {
		Parent newScene = FXMLLoader.load(getClass().getResource("Platform.fxml"));
		Stage new_Stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		new_Stage.setTitle("Visitor Table");
		new_Stage.setScene(new Scene(newScene, 1920, 1080));
		new_Stage.show();
	}

	public void addVisitor(ActionEvent e) throws IOException {
		Parent newScene = FXMLLoader.load(getClass().getResource("AdminViewForm.fxml"));
		Stage new_Stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		new_Stage.setTitle("Form");
		new_Stage.setScene(new Scene(newScene, 1920, 1080));
		new_Stage.show();
	}

	public void deleteVisitor() {
		VisitorDetails visitorToDelete = visitorTable.getSelectionModel().getSelectedItem();
		System.out.println("Delete Button Clicked!");
		Alert deleteAlert = new Alert(Alert.AlertType.WARNING, "Confirm", ButtonType.OK, ButtonType.CANCEL);
		deleteAlert.setContentText("Are you sure you want to delete this?\n\nTHIS CANNOT BE UNDONE.");
		deleteAlert.showAndWait();
		if (deleteAlert.getResult() == ButtonType.OK) {
			AdminJDBC.deleteVisitor(visitorToDelete);
			data.removeAll(visitorTable.getSelectionModel().getSelectedItems());
			visitorTable.getSelectionModel().clearSelection();
		} else {
			deleteAlert.close();
		}
	}

	private ObservableList<VisitorDetails> getVisitors(LocalDate startDate, LocalDate endDate) {
		endDate = endDate.plusDays(1);
		ObservableList<VisitorDetails> visitors = FXCollections.observableArrayList();
		StringBuilder query = new StringBuilder(
				"SELECT * FROM visitors LEFT JOIN visits ON visitors.VisitorID = visits.VisitorID LEFT JOIN visitorlocations on visitors.VisitorID = visitorlocations.VisitorID WHERE visitors.VisitorID IS NOT NULL");
		query.append(" AND Visiting_Day >= '" + startDate.toString() + "' AND Visiting_Day <= '" + endDate.toString()
				+ "' ORDER BY Visiting_Day");
		System.out.println(query.toString());
		for (VisitorDetails v : AdminJDBC.getVisitorDetailsFromQuery(query.toString())) {
			visitors.add(v);
		}
		return visitors;
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
	
	@FXML
	private void ExportAction(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		if (!data.isEmpty()) {
			FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel Files(*.xls)", "*.xls");
			chooser.getExtensionFilters().add(filter);
			File file = chooser.showSaveDialog(excelButton.getScene().getWindow());
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
			File file = chooser.showSaveDialog(emailButton.getScene().getWindow());
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
			File file = chooser.showSaveDialog(excelButton.getScene().getWindow());
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
				File file = chooser.showOpenDialog(importButton.getScene().getWindow());
				JExcelDriver.readXLSFile(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

		}
		refreshTable();
	}
	
	public void deleteAgedData() {
		Alert deleteAlert = new Alert(Alert.AlertType.WARNING, "Delete Aged Data", ButtonType.OK, ButtonType.CANCEL);
		deleteAlert.setContentText("This will delete data which was created before "+ LocalDate.now().minusYears(2)+". Are you sure you want to delete this?\n\nTHIS CANNOT BE UNDONE.");
		deleteAlert.showAndWait();
		if (deleteAlert.getResult() == ButtonType.OK) {
			AdminJDBC.deleteVisitors(Date.valueOf(LocalDate.now().minusYears(2)));
		} else {
			deleteAlert.close();
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

}
