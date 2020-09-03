package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.SubunidadeService;

public class MainViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemMotorista;
	
	@FXML
	private MenuItem menuItemSubunidade;
	
	@FXML
	private MenuItem menuItemViatura;
	
	@FXML
	private MenuItem menuItemSobre;
	
	@FXML
	private MenuItem menuItemManual;
	
	@FXML
	public void onMenuItemMotoristaAction() {
		System.out.println("onMenuItemMotoristaAction");
	}
	
	@FXML
	public void onMenuItemSubunidadeAction() {
		//provisoriamente loadView2
		loadView2("/gui/SubunidadeList.fxml");
	}
	
	@FXML
	public void onMenuItemViaturaAction() {
		System.out.println("onMenuItemViaturaAction");
	}
	
	@FXML
	public void onMenuItemSobreAction() {
		loadView("/gui/Sobre.fxml");
	}
	
	@FXML
	public void onMenuItemManualAction() {
		System.out.println("onMenuItemManualAction");
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
	}
	
	private synchronized void loadView(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader (getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox)((ScrollPane)mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
		}catch(IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	private synchronized void loadView2(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader (getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox)((ScrollPane)mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			//processo manual de inje��o de depend�ncia no controller
			//atualiza��o de dados na tela do tableView
			SubunidadeListController controller = loader.getController();
			controller.setSubunidadeService(new SubunidadeService());
			controller.updateTableView();
			
		}catch(IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
		}
	}
}