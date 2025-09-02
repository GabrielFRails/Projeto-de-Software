package model;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class ClienteRepositorio {
    private static Database database;
    private static Dao<Cliente, Integer> dao;
    private List<Cliente> loadedClientes;
    private Cliente loadedCliente;

    public ClienteRepositorio(Database database) {
        setDatabase(database);
        loadedClientes = new ArrayList<>();
    }

    public static void setDatabase(Database database) {
        ClienteRepositorio.database = database;
        try {
            dao = DaoManager.createDao(database.getConnection(), Cliente.class);
            TableUtils.createTableIfNotExists(database.getConnection(), Cliente.class);
            // Crie tabelas para subclasses se necessário, mas com single table, é só uma.
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Cliente create(Cliente cliente) {
        try {
            dao.create(cliente);
            this.loadedCliente = cliente;
            loadedClientes.add(cliente);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return cliente;
    }

    public void update(Cliente cliente) {
        try {
            dao.update(cliente);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void delete(Cliente cliente) {
        try {
            dao.delete(cliente);
            loadedClientes.remove(cliente);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Cliente loadFromId(int id) {
        try {
            this.loadedCliente = dao.queryForId(id);
            if (this.loadedCliente != null) {
                loadedClientes.add(this.loadedCliente);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedCliente;
    }

    public List<Cliente> loadAll() {
        try {
            this.loadedClientes = dao.queryForAll();
            if (!this.loadedClientes.isEmpty()) {
                this.loadedCliente = this.loadedClientes.get(0);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedClientes;
    }
}