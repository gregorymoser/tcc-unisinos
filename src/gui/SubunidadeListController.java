package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Subunidade;

public class SubunidadeListController implements Initializable{
	
	@FXML
	private TableView<Subunidade> tableViewSubunidade;
	
	@FXML
	private TableColumn<Subunidade, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Subunidade, String> tableColumnNome;
	
	@FXML
	private Button btNovo;
	
	@FXML
	public void onBtNovoAction() {
		System.out.println("onBtNovoAction");
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
}