package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "cliente")
public abstract class Cliente {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(dataType = DataType.STRING)
    private String tipo; // Discriminador: "PF" ou "PJ"

    @DatabaseField(dataType = DataType.STRING)
    private String nomeCompleto; // Nome para PF, Razão Social para PJ

    @DatabaseField(dataType = DataType.STRING)
    private String endereco;

    @DatabaseField(dataType = DataType.STRING)
    private String telefone;

    @DatabaseField(dataType = DataType.STRING)
    private String email;

    @DatabaseField(dataType = DataType.STRING)
    private String dataCadastro; // Formato "dd/MM/yyyy"

    // Getters e Setters (copie do tutorial e adapte)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(String dataCadastro) { this.dataCadastro = dataCadastro; }
}