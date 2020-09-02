package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemMotorista;
	
	@FXML
	private MenuItem menuItemSubunidade;
	
	@FXML
	private MenuItem menuItemViatura;
	
	@FXML
	private MenuItem menuItemManual;
	
	@FXML
	public void onMenuItemMotoristaAction() {
		System.out.println("onMenuItemMotoristaAction");
	}
	
	@FXML
	public void onMenuItemSubunidadeAction() {
		System.out.println("onMenuItemSubunidadeAction");
	}
	
	@FXML
	public void onMenuItemViaturaAction() {
		System.out.println("onMenuItemViaturaAction");
	}
	
	@FXML
	public void onMenuItemManualAction() {
		System.out.println("onMenuItemManualAction");
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
	}
}