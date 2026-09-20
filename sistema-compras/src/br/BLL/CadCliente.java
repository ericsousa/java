package br.BLL;

import br.DAL.DaoCliente;
import br.Model.Cliente;
import java.util.List;

public class CadCliente {

    private DaoCliente daoCliente = new DaoCliente();

    public void cadastrarCliente(Cliente cliente) {
        if (cliente != null && cliente.getNome() != null && !cliente.getNome().trim().isEmpty()) {
            daoCliente.Create(cliente);
        }
    }

    public Cliente buscarCliente(int id) {
        return daoCliente.Recover(id);
    }

    public void removerCliente(Cliente cliente) {
        if (cliente != null) {
            daoCliente.Delete(cliente);
        }
    }

    public void atualizarCliente(Cliente cliente) {
        if (cliente != null) {
            daoCliente.Update(cliente);
        }
    }

    public List<Cliente> listarClientes() {
        return daoCliente.listar();
    }
}
