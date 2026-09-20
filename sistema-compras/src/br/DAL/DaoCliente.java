package br.DAL;

import br.Model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class DaoCliente {

    private List<Cliente> tabelaCliente = new ArrayList<>();

    // CRUD

    public void Create(Cliente cliente) {
        tabelaCliente.add(cliente);
    }

    public void Delete(Cliente cliente) {
        tabelaCliente.remove(cliente);
    }

    public void Update(Cliente cliente) {
        Cliente clienteBusca = Recover(cliente.getId());
        if (clienteBusca != null) {
            clienteBusca.setNome(cliente.getNome());
            clienteBusca.setEndereco(cliente.getEndereco());
        }
    }

    public Cliente Recover(int id) {
        for (Cliente c : tabelaCliente) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public List<Cliente> listar() {
        return tabelaCliente;
    }
}
