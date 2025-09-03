package view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cliente {
    private SimpleIntegerProperty id;
    private SimpleStringProperty tipo;
    private SimpleStringProperty nomeCompleto;
    private SimpleStringProperty endereco;
    private SimpleStringProperty telefone;
    private SimpleStringProperty email;
    private SimpleStringProperty dataCadastro;
    private SimpleStringProperty cpf; // Para PF
    private SimpleStringProperty cnpj; // Para PJ

    public Cliente(int id, String tipo, String nomeCompleto, String endereco, String telefone, String email, String dataCadastro, String cpf, String cnpj) {
        this.id = new SimpleIntegerProperty(id);
        this.tipo = new SimpleStringProperty(tipo);
        this.nomeCompleto = new SimpleStringProperty(nomeCompleto);
        this.endereco = new SimpleStringProperty(endereco);
        this.telefone = new SimpleStringProperty(telefone);
        this.email = new SimpleStringProperty(email);
        this.dataCadastro = new SimpleStringProperty(dataCadastro);
        this.cpf = new SimpleStringProperty(cpf);
        this.cnpj = new SimpleStringProperty(cnpj);
    }

    public int getId() { return id.get(); }
    public String getTipo() { return tipo.get(); }
    public String getNomeCompleto() { return nomeCompleto.get(); }
    public String getEndereco() { return endereco.get(); }
    public String getTelefone() { return telefone.get(); }
    public String getEmail() { return email.get(); }
    public String getDataCadastro() { return dataCadastro.get(); }
    public String getCpf() { return cpf.get(); }
    public String getCnpj() { return cnpj.get(); }

    public String getCpfOrCnpj() {
        if ("PF".equals(getTipo())) return getCpf();
        else return getCnpj();
    }

    public void setId(int id) { this.id.set(id); }
    public void setTipo(String tipo) { this.tipo.set(tipo); }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto.set(nomeCompleto); }
    public void setEndereco(String endereco) { this.endereco.set(endereco); }
    public void setTelefone(String telefone) { this.telefone.set(telefone); }
    public void setEmail(String email) { this.email.set(email); }
    public void setDataCadastro(String dataCadastro) { this.dataCadastro.set(dataCadastro); }
    public void setCpf(String cpf) { this.cpf.set(cpf); }
    public void setCnpj(String cnpj) { this.cnpj.set(cnpj); }
}