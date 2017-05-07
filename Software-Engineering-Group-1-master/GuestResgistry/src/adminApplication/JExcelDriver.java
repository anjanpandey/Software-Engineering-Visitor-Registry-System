package adminApplication;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class JExcelDriver {
	public static void generateImportTemplate(File file) throws IOException, WriteException {
		WritableWorkbook myexcel = Workbook.createWorkbook(file);
		WritableSheet mysheet = myexcel.createSheet("mySheet", 0);
		int x = 0;
		mysheet.addCell(new Label(x, 0, "Email"));
		x++;
		mysheet.addCell(new Label(x, 0, "From (City)"));
		x++;
		mysheet.addCell(new Label(x, 0, "From (Metro)"));
		x++;
		mysheet.addCell(new Label(x, 0, "From (State)"));
		x++;
		mysheet.addCell(new Label(x, 0, "From (Country)"));
		x++;
		mysheet.addCell(new Label(x, 0, "From (Zip)"));
		x++;
		mysheet.addCell(new Label(x, 0, "Number in Party"));
		x++;
		mysheet.addCell(new Label(x, 0, "How Referred"));
		x++;
		mysheet.addCell(new Label(x, 0, "Stayed in M/WM Hotel"));
		x++;
		mysheet.addCell(new Label(x, 0, "Destination"));
		x++;
		mysheet.addCell(new Label(x, 0, "Repeat Visitor?"));
		x++;
		mysheet.addCell(new Label(x, 0, "Reason For Travelling"));
		x++;
		mysheet.addCell(new Label(x, 0, "Date of Visit"));

		myexcel.write();
		myexcel.close();
	}

	public static void readXLSFile(File file) {
		Workbook w;
		List<VisitorDetails> newData = new ArrayList<VisitorDetails>();
		try {
			w = Workbook.getWorkbook(file);
			Sheet sheet = w.getSheet(0);

			for (int i = 1; i < sheet.getRows(); i++) {
				int x = 0;
				String email = sheet.getCell(x, i).getContents();
				x++;
				String city = sheet.getCell(x, i).getContents();
				x++;
				String metro = sheet.getCell(x, i).getContents();
				x++;
				String state = sheet.getCell(x, i).getContents();
				x++;
				String country = sheet.getCell(x, i).getContents();
				x++;
				String zipString = sheet.getCell(x, i).getContents();
				Integer zip = (zipString.isEmpty() ? null : Integer.parseInt(zipString));
				x++;
				String partyString = sheet.getCell(x, i).getContents();
				Integer party = (partyString.isEmpty() ? 1 : Integer.parseInt(partyString));
				x++;
				String referred = sheet.getCell(x, i).getContents();
				if (referred.equalsIgnoreCase("Billboard")) {
					referred = "Billboard";
				} else if (referred.equalsIgnoreCase("Interstate Sign")) {
					referred = "Interstate Sign";
				} else if (referred.equalsIgnoreCase("Other")) {
					referred = "Other";
				} else {
					referred = "No Response";
				}
				x++;
				String hotel = sheet.getCell(x, i).getContents();
				if (hotel.equalsIgnoreCase("Yes") || hotel.equalsIgnoreCase("Y")) {
					hotel = "Yes";
				} else if (hotel.equalsIgnoreCase("No") || hotel.equalsIgnoreCase("N")) {
					hotel = "No";
				} else {
					hotel = "No Response";
				}
				x++;
				String destination = sheet.getCell(x, i).getContents();
				x++;
				String repeatString = sheet.getCell(x, i).getContents();
				boolean repeat = false;
				if (repeatString.equalsIgnoreCase("Yes") || repeatString.equalsIgnoreCase("Y")
						|| repeatString.equalsIgnoreCase("True") || repeatString.equalsIgnoreCase("T")) {
					repeat = true;
				}
				x++;
				String travelingFor = sheet.getCell(x, i).getContents();
				if (travelingFor.equalsIgnoreCase("Business")) {
					travelingFor = "Business";
				} else if (travelingFor.equalsIgnoreCase("Pleasure")) {
					travelingFor = "Pleasure";
				} else if (travelingFor.equalsIgnoreCase("Convention")) {
					travelingFor = "Convention";
				} else if (travelingFor.equalsIgnoreCase("Other")) {
					travelingFor = "Other";
				} else {
					travelingFor = "No Response";
				}
				x++;
				String dateString = sheet.getCell(x, i).getContents();
				java.util.Date visitingDay;
				if (!dateString.isEmpty()) {
					DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
					visitingDay = (java.util.Date) formatter.parse(dateString);
				} else {
					visitingDay = null;
				}
				newData.add(new VisitorDetails(email, city, metro, state, country, zip, party, referred, hotel,
						destination, repeat, travelingFor, visitingDay));
			}
			AdminJDBC.addVisitors(newData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveXLSFile(File file, List<VisitorDetails> data) throws IOException, WriteException {
		WritableWorkbook myexcel = Workbook.createWorkbook(file);
		WritableSheet mysheet = myexcel.createSheet("mySheet", 0);

		int x = 0;
		mysheet.addCell(new Label(x, 0, "Email"));
		x++;
		mysheet.addCell(new Label(x, 0, "From (City)"));
		x++;
		mysheet.addCell(new Label(x, 0, "From (Metro)"));
		x++;
		mysheet.addCell(new Label(x, 0, "From (State)"));
		x++;
		mysheet.addCell(new Label(x, 0, "From (Country)"));
		x++;
		mysheet.addCell(new Label(x, 0, "From (Zip)"));
		x++;
		mysheet.addCell(new Label(x, 0, "Number in Party"));
		x++;
		mysheet.addCell(new Label(x, 0, "How Referred"));
		x++;
		mysheet.addCell(new Label(x, 0, "Stayed in M/WM Hotel"));
		x++;
		mysheet.addCell(new Label(x, 0, "Destination"));
		x++;
		mysheet.addCell(new Label(x, 0, "Repeat Visitor?"));
		x++;
		mysheet.addCell(new Label(x, 0, "Reason For Travelling"));
		x++;
		mysheet.addCell(new Label(x, 0, "Date of Visit"));

		for (int i = 0; i < data.size(); i++) {
			int j = 0;
			mysheet.addCell(formatData(i, j, data.get(i).getEmail()));
			j++;
			mysheet.addCell(formatData(i, j, data.get(i).getCity()));
			j++;
			mysheet.addCell(formatData(i, j, data.get(i).getMetro()));
			j++;
			mysheet.addCell(formatData(i, j, data.get(i).getState()));
			j++;
			mysheet.addCell(formatData(i, j, data.get(i).getCountry()));
			j++;
			mysheet.addCell(formatData(i, j, (data.get(i).getZip() != null ? data.get(i).getZip().toString() : "")));
			j++;
			mysheet.addCell(
					formatData(i, j, (data.get(i).getParty() != null ? data.get(i).getParty().toString() : "1")));
			j++;
			mysheet.addCell(formatData(i, j, data.get(i).getHeard()));
			j++;
			mysheet.addCell(formatData(i, j, data.get(i).getHotel()));
			j++;
			mysheet.addCell(formatData(i, j, data.get(i).getDestination()));
			j++;
			mysheet.addCell(formatData(i, j,
					(data.get(i).getRepeatVisit() != null ? data.get(i).getRepeatVisit().toString() : "false")));
			j++;
			mysheet.addCell(formatData(i, j, data.get(i).getTravelingFor()));
			j++;
			mysheet.addCell(formatData(i, j,
					(data.get(i).getVisitingDay() != null ? data.get(i).getVisitingDay().toString() : "")));
			j++;
		}
		try {
			myexcel.write();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			myexcel.close();
		}
	}

	public static void saveEmailList(File file, List<VisitorDetails> data) throws IOException, WriteException {
		WritableWorkbook myexcel = Workbook.createWorkbook(file);
		WritableSheet mysheet = myexcel.createSheet("mySheet", 0);
		try {
			for (int i = 1; i < data.size() - 1; i++) {
				mysheet.addCell(formatData(i - 1, 0, data.get(i).getEmail()));
			}
		} catch (WriteException e) {

		} finally {
			myexcel.write();
			myexcel.close();
		}
	}

	private static Label formatData(int i, int j, String data) {
		return new Label(j, i + 1, data);
	}

}
