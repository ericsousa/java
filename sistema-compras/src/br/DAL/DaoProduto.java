package br.DAL;

import br.Model.Produto;
import java.util.ArrayList;
import java.util.List;

public class DaoProduto {

    private List<Produto> tabelaProduto = new ArrayList<>();

    // CRUD

    public void Create(Produto produto) {
        tabelaProduto.add(produto);
    }

    public void Delete(Produto produto) {
        tabelaProduto.remove(produto);
    }

    public void Update(Produto produto) {
        Produto produtoBusca = Recover(produto.getId());
        if (produtoBusca != null) {
            produtoBusca.setNome(produto.getNome());
            produtoBusca.setPreco(produto.getPreco());
            produtoBusca.setQuantidade(produto.getQuantidade());
        }
    }

    public Produto Recover(int id) {
        for (Produto p : tabelaProduto) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public List<Produto> listar() {
        return tabelaProduto;
    }
}
