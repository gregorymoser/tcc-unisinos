package gui;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Motorista;
import model.services.MotoristaService;

public class MotoristaListController implements Initializable, DataChangeListener {

	private MotoristaService service;

	@FXML
	private TableView<Motorista> tableViewMotorista;

	@FXML
	private TableColumn<Motorista, Integer> tableColumnId;

	@FXML
	private TableColumn<Motorista, String> tableColumnNome;

	@FXML
	private TableColumn<Motorista, Motorista> tableColumnEDIT;

	@FXML
	private TableColumn<Motorista, Motorista> tableColumnREMOVE;

	@FXML
	private Button btNovo;

	private ObservableList<Motorista> obsList;

	@FXML
	public void onBtNovoAction(ActionEvent event) {
		// Captura referencia para o Stage atual
		Stage parentStage = Utils.currentStage(event);
		Motorista obj = new Motorista();
		// Define a ação de clicar no botão como chamada do método que habilita janela
		// do formulário
		createDialogForm(obj, "/gui/MotoristaForm.fxml", parentStage);
	}

	// injeção de dependência manual, sem framework ou cotainer
	// de injeção de dependência automática.
	public void setMotoristaService(MotoristaService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewMotorista.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service estava nulo!");
		}
		List<Motorista> list = service.findAll();
		// instanciar observable list utilizando dados originais da lista
		obsList = FXCollections.observableArrayList(list);
		tableViewMotorista.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}
	
	
	private void createDialogForm(Motorista obj, String absoluteName, Stage parentStage) {
		/*
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			MotoristaFormController controller = loader.getController();
			// injetando subunidade no controlador
			controller.setMotorista(obj);
			//// injetando subunidadeService no controlador
			controller.setMotoristaService(new MotoristaService());
			// Autoinscrição para receber o evento
			controller.subscribeDataChangeListener(this);
			// carregar os dados do objeto no formulário
			controller.updateFormData();

			// Instancia novo Stage
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Nova Motorista");
			dialogStage.setScene(new Scene(pane));
			// Proibir redimensionar a janela
			dialogStage.setResizable(false);
			// Definindo Stage pai desta janela
			dialogStage.initOwner(parentStage);
			// Informa se a janela é modal ou se tem outro comportamento
			// Window Modal mantém a janela travada até ser fechada, para novamente acessar
			// a janela anterior
			dialogStage.initModality(Modality.WINDOW_MODAL);
			// Mostrar e aguardar
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
		}
		*/
	}
	

	@Override
	public void onDataChanged() {
		// Atualiza os dados da tabela
		updateTableView();
	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Motorista, Motorista>() {
			private final Button button = new Button("editar");

			@Override
			protected void updateItem(Motorista obj, boolean empty) {
				super.updateItem(obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;
				}

				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/MotoristaForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Motorista, Motorista>() {
			private final Button button = new Button("remover");

			@Override
			protected void updateItem(Motorista obj, boolean empty) {
				super.updateItem(obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;
				}

				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Motorista obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Você tem certeza que deseja deletar?");
		if(result.get() == ButtonType.OK) {
			if(service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			}
			catch(DbIntegrityException e) {
				Alerts.showAlert("Erro ao remover o objeto", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
}