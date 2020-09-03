package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
		loadView("/gui/SubunidadeList.fxml", (SubunidadeListController controller) -> {
			//ação de chamar funções agora parametrizadas em relação ao commit anterior
			controller.setSubunidadeService(new SubunidadeService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemViaturaAction() {
		System.out.println("onMenuItemViaturaAction");
	}
	
	@FXML
	public void onMenuItemSobreAction() {
		loadView("/gui/Sobre.fxml", x -> {});
	}
	
	@FXML
	public void onMenuItemManualAction() {
		System.out.println("onMenuItemManualAction");
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
	}
	
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader (getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox)((ScrollPane)mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			//executa função passada como argumento no método
			//utilizada para evitar a codificação de duas funções loadView (para carregar janelas com e sem ação)
			//unica versão da função
			T controller = loader.getController();
			initializingAction.accept(controller);
			
		}catch(IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
		}
	}
}