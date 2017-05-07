package adminApplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ManualController implements Initializable {
	
	@FXML
	private WebView webView;
	private WebEngine engine;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		engine = webView.getEngine();
		final String hellohtml = "adminManual.html"; // HTML file to view in web
													// view
		URL urlHello = getClass().getResource(hellohtml);
		engine.load(urlHello.toExternalForm());

	}

}
