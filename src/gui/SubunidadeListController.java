package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Subunidade;
import model.services.SubunidadeService;

public class SubunidadeListController implements Initializable{
	
	private SubunidadeService service;
	
	@FXML
	private TableView<Subunidade> tableViewSubunidade;
	
	@FXML
	private TableColumn<Subunidade, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Subunidade, String> tableColumnNome;
	
	@FXML
	private Button btNovo;
	
	private ObservableList<Subunidade> obsList;
	
	@FXML
	public void onBtNovoAction(ActionEvent event) {
		//Captura referencia para o Stage atual
		Stage parentStage = Utils.currentStage(event);
		//Define a ação de clicar no botão como chamada do método que habilita janela do formulário
		createDialogForm("/gui/SubunidadeForm.fxml", parentStage);
	}
	
	// injeção de dependência manual, sem framework ou cotainer
	//de injeção de dependência automática.
	public void setSubunidadeService(SubunidadeService service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		Stage stage = (Stage)Main.getMainScene().getWindow();
		tableViewSubunidade.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Service estava nulo!");
		}
		List<Subunidade> list = service.findAll();
		//instanciar observable list utilizando dados originais da lista
		obsList = FXCollections.observableArrayList(list);
		tableViewSubunidade.setItems(obsList);
	}
	
	private void createDialogForm(String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader (getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			//Instancia novo Stage
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Nova Subunidade");
			dialogStage.setScene(new Scene(pane));
			//Proibir redimensionar a janela
			dialogStage.setResizable(false);
			//Definindo Stage pai desta janela
			dialogStage.initOwner(parentStage);
			//Informa se a janela é modal ou se tem outro comportamento
			//Window Modal mantém a janela travada até ser fechada, para novamente acessar a janela anterior
			dialogStage.initModality(Modality.WINDOW_MODAL);
			//Mostrar e aguardar
			dialogStage.showAndWait();
		}
		catch(IOException e) {
			Alerts.showAlert("IO Exception", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
		}
	}
}