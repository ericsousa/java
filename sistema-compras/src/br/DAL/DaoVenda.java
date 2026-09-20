package br.DAL;

import br.Model.Venda;
import java.util.ArrayList;
import java.util.List;

public class DaoVenda {

    private List<Venda> tabelaVenda = new ArrayList<>();

    // CRUD

    public void Create(Venda venda) {
        tabelaVenda.add(venda);
    }

    public void Delete(Venda venda) {
        tabelaVenda.remove(venda);
    }

    public void Update(Venda venda) {
        Venda vendaBusca = Recover(venda.getId());
        if (vendaBusca != null) {
            vendaBusca.setData(venda.getData());
            vendaBusca.setCliente(venda.getCliente());
            vendaBusca.getItens().clear();
            vendaBusca.getItens().addAll(venda.getItens());
        }
    }

    public Venda Recover(int id) {
        for (Venda v : tabelaVenda) {
            if (v.getId() == id) {
                return v;
            }
        }
        return null;
    }

    public List<Venda> listar() {
        return tabelaVenda;
    }
}
