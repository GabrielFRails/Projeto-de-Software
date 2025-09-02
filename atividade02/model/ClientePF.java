package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;

public class ClientePF extends Cliente {

    @DatabaseField(dataType = DataType.STRING)
    private String cpf;

    public ClientePF() {
        setTipo("PF");
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
}