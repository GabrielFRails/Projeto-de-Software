package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Cliente;
import model.ClientePF;
import model.ClientePJ;
import model.Database;
import model.ClienteRepositorio;
import view.AppView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    @FXML
    private TableView<view.Cliente> tabela;
    @FXML
    private TableColumn<view.Cliente, Integer> idCol;
    @FXML
    private TableColumn<view.Cliente, String> tipoCol;
    @FXML
    private TableColumn<view.Cliente, String> nomeCol;
    @FXML
    private TableColumn<view.Cliente, String> enderecoCol;
    @FXML
    private TableColumn<view.Cliente, String> telefoneCol;
    @FXML
    private TableColumn<view.Cliente, String> emailCol;
    @FXML
    private TableColumn<view.Cliente, String> dataCadastroCol;
    @FXML
    private TableColumn<view.Cliente, String> cpfOrCnpjCol;
    @FXML
    private TextField idField;
    @FXML
    private ComboBox<String> tipoCombo;
    @FXML
    private TextField nomeCompletoField;
    @FXML
    private TextField enderecoField;
    @FXML
    private TextField telefoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField dataCadastroField;
    @FXML
    private TextField cpfField;
    @FXML
    private TextField cnpjField;
    @FXML
    private Button adicionarButton;
    @FXML
    private Button atualizarButton;
    @FXML
    private Button deletarButton;
    @FXML
    private Button cancelarButton;
    @FXML
    private Button salvarButton;

    private AppView appView;
    private static Database database = new Database("clientes.sqlite");
    private static ClienteRepositorio clienteRepo = new ClienteRepositorio(database);

    public AppController() {
        this.appView = new AppView();
    }

    public static void main(String[] args) throws Exception {
        AppController appController = new AppController();
        appController.appView.run(args);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configura ComboBox com opções PF e PJ
        tipoCombo.setItems(FXCollections.observableArrayList("PF", "PJ"));
        tipoCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            updateFieldVisibility(newValue);
        });

        // Configura colunas da TableView
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        tipoCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
        enderecoCol.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        telefoneCol.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        dataCadastroCol.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));
        cpfOrCnpjCol.setCellValueFactory(new PropertyValueFactory<>("cpfOrCnpj"));

        // Carrega dados iniciais
        tabela.setItems(loadAllClientes());

        // Listener para seleção na tabela
        tabela.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldSelection, newSelection) -> {
                    handleClienteSelected(newSelection);
                });

        // Estado inicial dos campos e botões
        desabilitarCampos(true);
        desabilitarBotoes(false, true, true, true, true);
        updateFieldVisibility("PF"); // Default PF
    }

    private void desabilitarBotoes(boolean adicionar, boolean atualizar, boolean deletar, boolean cancelar, boolean salvar) {
        adicionarButton.setDisable(adicionar);
        atualizarButton.setDisable(atualizar);
        deletarButton.setDisable(deletar);
        cancelarButton.setDisable(cancelar);
        salvarButton.setDisable(salvar);
    }

    private void desabilitarCampos(boolean desabilitado) {
        nomeCompletoField.setDisable(desabilitado);
        enderecoField.setDisable(desabilitado);
        telefoneField.setDisable(desabilitado);
        emailField.setDisable(desabilitado);
        dataCadastroField.setDisable(desabilitado);
        tipoCombo.setDisable(desabilitado);
        cpfField.setDisable(desabilitado);
        cnpjField.setDisable(desabilitado);
    }

    private void limparCampos() {
        idField.setText("");
        tipoCombo.getSelectionModel().select("PF");
        nomeCompletoField.setText("");
        enderecoField.setText("");
        telefoneField.setText("");
        emailField.setText("");
        dataCadastroField.setText("");
        cpfField.setText("");
        cnpjField.setText("");
    }

    private void updateFieldVisibility(String tipo) {
        if ("PF".equals(tipo)) {
            cpfField.setVisible(true);
            cnpjField.setVisible(false);
            cnpjField.setText("");
        } else {
            cpfField.setVisible(false);
            cnpjField.setVisible(true);
            cpfField.setText("");
        }
    }

    @FXML
    public void onAdicionarButtonAction() {
        tabela.getSelectionModel().select(-1);
        desabilitarCampos(false);
        desabilitarBotoes(true, true, true, false, false);
        limparCampos();
    }

    @FXML
    public void onCancelarButtonAction() {
        desabilitarCampos(true);
        desabilitarBotoes(false, true, true, true, true);
        limparCampos();
        tabela.getSelectionModel().select(-1);
    }

    @FXML
    public void onSalvarButtonAction() {
        try {
            Cliente cliente;
            if ("PF".equals(tipoCombo.getValue())) {
                cliente = new ClientePF();
                ((ClientePF) cliente).setCpf(cpfField.getText());
            } else {
                cliente = new ClientePJ();
                ((ClientePJ) cliente).setCnpj(cnpjField.getText());
            }
            cliente.setNomeCompleto(nomeCompletoField.getText());
            cliente.setEndereco(enderecoField.getText());
            cliente.setTelefone(telefoneField.getText());
            cliente.setEmail(emailField.getText());
            cliente.setDataCadastro(dataCadastroField.getText());

            Cliente clienteSalvo = clienteRepo.create(cliente);
            view.Cliente clienteView = modelToView(clienteSalvo);
            tabela.getItems().add(clienteView);
            tabela.getSelectionModel().select(clienteView);
            desabilitarCampos(true);
            desabilitarBotoes(false, true, true, true, true);
            limparCampos();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao salvar: " + e.getMessage()).show();
        }
    }

    @FXML
    public void onAtualizarButtonAction() {
        try {
            view.Cliente selected = tabela.getSelectionModel().getSelectedItem();
            if (selected == null) {
                new Alert(Alert.AlertType.WARNING, "Selecione um cliente para atualizar.").show();
                return;
            }

            Cliente cliente = clienteRepo.loadFromId(selected.getId());
            if (cliente != null) {
                cliente.setNomeCompleto(nomeCompletoField.getText());
                cliente.setEndereco(enderecoField.getText());
                cliente.setTelefone(telefoneField.getText());
                cliente.setEmail(emailField.getText());
                cliente.setDataCadastro(dataCadastroField.getText());
                if (cliente instanceof ClientePF) {
                    ((ClientePF) cliente).setCpf(cpfField.getText());
                } else if (cliente instanceof ClientePJ) {
                    ((ClientePJ) cliente).setCnpj(cnpjField.getText());
                }

                clienteRepo.update(cliente);
                view.Cliente clienteView = modelToView(cliente);
                tabela.getItems().set(tabela.getSelectionModel().getSelectedIndex(), clienteView);
                desabilitarCampos(true);
                desabilitarBotoes(false, true, true, true, true);
                limparCampos();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao atualizar: " + e.getMessage()).show();
        }
    }

    @FXML
    public void onDeletarButtonAction() {
        try {
            view.Cliente selected = tabela.getSelectionModel().getSelectedItem();
            if (selected == null) {
                new Alert(Alert.AlertType.WARNING, "Selecione um cliente para deletar.").show();
                return;
            }

            Cliente cliente = clienteRepo.loadFromId(selected.getId());
            if (cliente != null) {
                clienteRepo.delete(cliente);
                tabela.getItems().remove(selected);
                desabilitarCampos(true);
                desabilitarBotoes(false, true, true, true, true);
                limparCampos();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao deletar: " + e.getMessage()).show();
        }
    }

    private void handleClienteSelected(view.Cliente newSelection) {
        if (newSelection != null) {
            idField.setText(Integer.toString(newSelection.getId()));
            tipoCombo.getSelectionModel().select(newSelection.getTipo());
            nomeCompletoField.setText(newSelection.getNomeCompleto());
            enderecoField.setText(newSelection.getEndereco());
            telefoneField.setText(newSelection.getTelefone());
            emailField.setText(newSelection.getEmail());
            dataCadastroField.setText(newSelection.getDataCadastro());
            cpfField.setText(newSelection.getCpf());
            cnpjField.setText(newSelection.getCnpj());
            desabilitarBotoes(false, false, false, true, true);
            desabilitarCampos(true);
            tipoCombo.setDisable(true); // Impede mudar tipo ao editar
        } else {
            limparCampos();
            desabilitarBotoes(false, true, true, true, true);
            desabilitarCampos(true);
        }
    }

    private view.Cliente modelToView(Cliente cliente) {
        String cpf = cliente instanceof ClientePF ? ((ClientePF) cliente).getCpf() : "";
        String cnpj = cliente instanceof ClientePJ ? ((ClientePJ) cliente).getCnpj() : "";
        return new view.Cliente(
                cliente.getId(),
                cliente.getTipo(),
                cliente.getNomeCompleto(),
                cliente.getEndereco(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getDataCadastro(),
                cpf,
                cnpj
        );
    }

    private ObservableList<view.Cliente> loadAllClientes() {
        ObservableList<view.Cliente> lista = FXCollections.observableArrayList();
        List<Cliente> listaFromDatabase = clienteRepo.loadAll();
        for (Cliente cliente : listaFromDatabase) {
            lista.add(modelToView(cliente));
        }
        return lista;
    }
}