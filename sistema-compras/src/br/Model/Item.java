package br.Model;

public class Item {
    private int id;
    private Produto produto;
    private int quantidade;

    public Item() {
    }

    public Item(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Item(int id, Produto produto, int quantidade) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    // Método compatível com o nome usado na lousa
    public int getQtde() {
        return this.quantidade;
    }

    public void setQtde(int qtde) {
        this.quantidade = qtde;
    }

    public double getSubtotal() {
        if (produto != null) {
            return produto.getPreco() * quantidade;
        }
        return 0.0;
    }

    @Override
    public String toString() {
        String nomeProd = (produto != null) ? produto.getNome() : "Desconhecido";
        return nomeProd + " x " + quantidade + " = R$ " + String.format("%.2f", getSubtotal());
    }
}
