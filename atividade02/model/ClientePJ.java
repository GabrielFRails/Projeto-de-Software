package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;

public class ClientePJ extends Cliente {

    @DatabaseField(dataType = DataType.STRING)
    private String cnpj;

    public ClientePJ() {
        setTipo("PJ");
    }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
}