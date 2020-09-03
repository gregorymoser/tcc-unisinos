package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	public void onBtNovoAction() {
		System.out.println("onBtNovoAction");
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
}