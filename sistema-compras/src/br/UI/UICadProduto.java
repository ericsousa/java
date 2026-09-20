package br.UI;

import br.Model.Produto;
import java.util.List;
import java.util.Scanner;

public class UICadProduto {

    private Scanner teclado;

    public UICadProduto() {
        this.teclado = new Scanner(System.in);
    }

    public UICadProduto(Scanner teclado) {
        this.teclado = teclado;
    }

    public Produto cadastrarProduto(int idSugerido) {
        System.out.println("\n--- CADASTRO DE PRODUTO ---");
        System.out.println("ID gerado: " + idSugerido);

        System.out.print("Nome do produto: ");
        String nome = teclado.nextLine();

        System.out.print("Preço unitário (R$): ");
        double preco = Double.parseDouble(teclado.nextLine().replace(",", "."));

        System.out.print("Quantidade inicial em estoque: ");
        int quantidade = Integer.parseInt(teclado.nextLine());

        Produto produto = new Produto(idSugerido, nome, preco, quantidade);
        System.out.println("Produto cadastrado com sucesso!");
        return produto;
    }

    public void listarProdutos(List<Produto> produtos) {
        System.out.println("\n--- LISTA DE PRODUTOS ---");
        if (produtos == null || produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (Produto p : produtos) {
            System.out.println("ID: " + p.getId() + " | Nome: " + p.getNome() +
                    " | Preço: R$ " + String.format("%.2f", p.getPreco()) +
                    " | Estoque: " + p.getQuantidade());
        }
    }
}
