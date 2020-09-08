package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Subunidade;
import model.exceptions.ValidationException;
import model.services.SubunidadeService;

public class SubunidadeFormController implements Initializable{
	
	private Subunidade entity;
	
	private SubunidadeService service;
	
	//Permite que outros objetos se inscrevam na lista e recebam o evento
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
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
	
	public void subscribeDataChangeListener (DataChangeListener listener) {
		//Adicionar na lista
		dataChangeListeners.add(listener);
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
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}
		catch(ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch(DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		//Emitir evento aos listeners
		for(DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	//método responsável por capturar os dados das caixas do formulário, instanciar uma Subunidade e retornar um novo objeto
	private Subunidade getFormData() {
		//cria objeto vaziu
		Subunidade obj = new Subunidade();
		
		//Instancia a exceção, sem lançar
		ValidationException exception = new ValidationException("Validation error");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		//Campo não pode ser vazio
		if(txtNome.getText()==null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "Field can't be empty");
		}
		obj.setNome(txtNome.getText());
		
		if (exception.getErrors().size()>0) {
			throw exception;
		}
		
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
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if(fields.contains("nome")){
			labelErrorNome.setText(errors.get("nome"));
		}
	}
}