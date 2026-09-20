package br.BLL;

import br.DAL.DaoVenda;
import br.Model.Item;
import br.Model.Produto;
import br.Model.Venda;
import java.util.List;

public class CadVenda {

    private DaoVenda daoVenda = new DaoVenda();

    public void registrarVenda(Venda venda) {
        if (venda != null && !venda.getItens().isEmpty()) {
            daoVenda.Create(venda);
        }
    }

    public Venda buscarVenda(int id) {
        return daoVenda.Recover(id);
    }

    public void removerVenda(Venda venda) {
        if (venda != null) {
            daoVenda.Delete(venda);
        }
    }

    public void atualizarVenda(Venda venda) {
        if (venda != null) {
            daoVenda.Update(venda);
        }
    }

    // Regra da lousa: "MOVIMENTO" (dá baixa no estoque de cada produto vendido)
    public void finalizarVenda(Venda venda) {
        if (venda == null || venda.getItens().isEmpty()) {
            return;
        }

        for (Item item : venda.getItens()) {
            Produto produto = item.getProduto();
            if (produto != null) {
                int novoEstoque = produto.getQuantidade() - item.getQuantidade();
                produto.setQuantidade(novoEstoque);
            }
        }
    }

    public List<Venda> listarVendas() {
        return daoVenda.listar();
    }
}
