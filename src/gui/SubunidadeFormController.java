package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entities.Subunidade;
import model.services.SubunidadeService;

public class SubunidadeFormController implements Initializable{
	
	private Subunidade entity;
	
	private SubunidadeService service;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private Label labelErrorNome;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar;
	
	public void setSubunidade(Subunidade entity) {
		this.entity = entity;
	}
	
	public void setSubunidadeService(SubunidadeService service) {
		this.service = service;
	}
	
	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		//tratar possíveis erros em virtude da injeção de dependência manual, sem uso de container/framework
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			Utils.currentStage(event).close();
		}
		catch(DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	//método responsável por capturar os dados das caixas do formulário, instanciar uma Subunidade e retornar um novo objeto
	private Subunidade getFormData() {
		//cria objeto vaziu
		Subunidade obj = new Subunidade();
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setNome(txtNome.getText());
		
		return obj;
	}

	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 50);
	}
	
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtNome.setText(entity.getNome());
	}
}