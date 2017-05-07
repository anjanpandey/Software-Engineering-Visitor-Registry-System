package adminApplication;

import java.util.ArrayList;

public class JavascriptComm {

	public int getSize() {

		ArrayList<String> latLongArray = new ArrayList<String>();
		latLongArray = getLocations();
		System.out.println("size retrieved");
		return latLongArray.size();

	}

	public Double getString(int i) {
		ArrayList<String> latLongArray = getLocations();
		Double number = null;
		if (!latLongArray.get(i).isEmpty()) {
			number = Double.parseDouble(latLongArray.get(i));
			System.out.println("lat or long retrieved: " + number);
		} else {
			ArrayList<Integer> zipData = AnalyticsController.getZipData();
			int j = i / 2;
			if (!(zipData.get(j)==null || zipData.get(j).toString().isEmpty())) {
				String[] latlong = APIClient.geocodingRequest(zipData.get(j).toString());
				if (i % 2 == 0) {
					if(latlong[0] != null){
					number = Double.parseDouble(latlong[0]);
					}
					else{
					number = null;
					}
						
				} else {
					if(latlong[0] != null){
					number = Double.parseDouble(latlong[1]);
					}
					else{
					number = null;
					}
				}
			}
		}
		return number;

	}

	private ArrayList<String> getLocations() {
		ArrayList<String> latlngs = new ArrayList<String>();
		for (String[] latlng : AnalyticsController.getLatLongData()) {
			latlngs.add(latlng[0]);
			latlngs.add(latlng[1]);
		}
		return latlngs;
	}
	
	public void testCall(String str){
		System.out.println("This is test call: " + str);
	}

}
