package br.BLL;

import br.DAL.DaoProduto;
import br.Model.Produto;
import java.util.List;

public class CadProduto {

    private DaoProduto daoProduto = new DaoProduto();

    public void cadastrarProduto(Produto produto) {
        if (produto != null && produto.getNome() != null && !produto.getNome().trim().isEmpty()) {
            daoProduto.Create(produto);
        }
    }

    public Produto buscarProduto(int id) {
        return daoProduto.Recover(id);
    }

    public void removerProduto(Produto produto) {
        if (produto != null) {
            daoProduto.Delete(produto);
        }
    }

    public void atualizarProduto(Produto produto) {
        if (produto != null) {
            daoProduto.Update(produto);
        }
    }

    public List<Produto> listarProdutos() {
        return daoProduto.listar();
    }
}
