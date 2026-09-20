package br.UI;

import br.Model.Cliente;
import br.Model.Item;
import br.Model.Produto;
import br.Model.Venda;
import java.util.List;
import java.util.Scanner;

public class UICadVenda {

    private Scanner teclado;

    public UICadVenda() {
        this.teclado = new Scanner(System.in);
    }

    public UICadVenda(Scanner teclado) {
        this.teclado = teclado;
    }

    public Venda iniciarVenda(int idVenda, Cliente cliente) {
        System.out.println("\n--- NOVA VENDA ---");
        System.out.println("Venda Nº: " + idVenda);
        System.out.println("Cliente: " + cliente.getNome());

        System.out.print("Data da venda (ex: 16/08/2024): ");
        String data = teclado.nextLine();

        Venda venda = new Venda(idVenda, data, cliente);
        return venda;
    }

    public Item selecionarItem(List<Produto> produtos) {
        System.out.println("\nProdutos disponíveis:");
        for (Produto p : produtos) {
            System.out.println("ID: " + p.getId() + " - " + p.getNome() +
                    " (R$ " + String.format("%.2f", p.getPreco()) +
                    " | Estoque: " + p.getQuantidade() + ")");
        }

        System.out.print("\nDigite o ID do produto desejado: ");
        int idEscolhido = Integer.parseInt(teclado.nextLine());

        Produto selecionado = null;
        for (Produto p : produtos) {
            if (p.getId() == idEscolhido) {
                selecionado = p;
                break;
            }
        }

        if (selecionado == null) {
            System.out.println("Produto não encontrado!");
            return null;
        }

        if (selecionado.getQuantidade() <= 0) {
            System.out.println("Produto esgotado no estoque!");
            return null;
        }

        System.out.print("Digite a quantidade: ");
        int qtd = Integer.parseInt(teclado.nextLine());

        if (qtd <= 0) {
            System.out.println("Quantidade inválida!");
            return null;
        }

        if (qtd > selecionado.getQuantidade()) {
            System.out.println("Estoque insuficiente! Disponível: " + selecionado.getQuantidade());
            return null;
        }

        return new Item(selecionado, qtd);
    }

    public void mostrarResumoVenda(Venda venda) {
        System.out.println("\n=========================================");
        System.out.println("             RESUMO DA VENDA             ");
        System.out.println("=========================================");
        System.out.println("Venda ID: " + venda.getId());
        System.out.println("Data:     " + venda.getData());
        if (venda.getCliente() != null) {
            System.out.println("Cliente:  " + venda.getCliente().getNome());
        }
        System.out.println("-----------------------------------------");
        System.out.println("ITENS COMPRADOS:");
        for (Item item : venda.getItens()) {
            System.out.println(" - " + item.getProduto().getNome() +
                    " | Qtd: " + item.getQuantidade() +
                    " | Subtotal: R$ " + String.format("%.2f", item.getSubtotal()));
        }
        System.out.println("-----------------------------------------");
        System.out.println("VALOR TOTAL: R$ " + String.format("%.2f", venda.getTotal()));
        System.out.println("=========================================\n");
    }

    public void listarVendas(List<Venda> vendas) {
        System.out.println("\n--- HISTÓRICO DE VENDAS ---");
        if (vendas == null || vendas.isEmpty()) {
            System.out.println("Nenhuma venda realizada até o momento.");
            return;
        }

        for (Venda v : vendas) {
            mostrarResumoVenda(v);
        }
    }
}
