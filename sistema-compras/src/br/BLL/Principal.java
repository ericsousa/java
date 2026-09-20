package br.BLL;

import br.Model.Cliente;
import br.Model.Item;
import br.Model.Produto;
import br.Model.Venda;
import br.UI.UICadCliente;
import br.UI.UICadProduto;
import br.UI.UICadVenda;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        // Camada de Negócio (BLL)
        CadCliente cadCliente = new CadCliente();
        CadProduto cadProduto = new CadProduto();
        CadVenda cadVenda = new CadVenda();

        // Camada de Interface (UI)
        UICadCliente uiCliente = new UICadCliente(teclado);
        UICadProduto uiProduto = new UICadProduto(teclado);
        UICadVenda uiVenda = new UICadVenda(teclado);

        // Geradores simples de ID
        int proximoIdCliente = 1;
        int proximoIdProduto = 1;
        int proximoIdVenda = 1;

        boolean rodando = true;

        while (rodando) {
            System.out.println("=========================================");
            System.out.println("          SISTEMA DE COMPRAS             ");
            System.out.println("=========================================");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Cadastrar Produto");
            System.out.println("3 - Listar Produtos e Estoque");
            System.out.println("4 - Realizar Venda");
            System.out.println("5 - Listar Histórico de Vendas");
            System.out.println("6 - Carregar Dados de Exemplo da Lousa");
            System.out.println("0 - Sair");
            System.out.println("=========================================");
            System.out.print("Escolha uma opção: ");

            int opcao;
            try {
                opcao = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido!\n");
                continue;
            }

            switch (opcao) {
                case 1:
                    Cliente novoCliente = uiCliente.cadastrarCliente(proximoIdCliente);
                    cadCliente.cadastrarCliente(novoCliente);
                    proximoIdCliente++;
                    break;

                case 2:
                    Produto novoProduto = uiProduto.cadastrarProduto(proximoIdProduto);
                    cadProduto.cadastrarProduto(novoProduto);
                    proximoIdProduto++;
                    break;

                case 3:
                    uiProduto.listarProdutos(cadProduto.listarProdutos());
                    break;

                case 4:
                    if (cadCliente.listarClientes().isEmpty()) {
                        System.out.println("\n[Aviso] É necessário cadastrar ao menos um cliente antes de realizar uma venda.");
                        break;
                    }

                    if (cadProduto.listarProdutos().isEmpty()) {
                        System.out.println("\n[Aviso] É necessário cadastrar ao menos um produto no estoque antes de realizar uma venda.");
                        break;
                    }

                    uiCliente.listarClientes(cadCliente.listarClientes());
                    System.out.print("\nDigite o ID do cliente que está comprando: ");
                    int idCli;
                    try {
                        idCli = Integer.parseInt(teclado.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("ID inválido!");
                        break;
                    }

                    Cliente clienteSelecionado = cadCliente.buscarCliente(idCli);
                    if (clienteSelecionado == null) {
                        System.out.println("Cliente não encontrado!");
                        break;
                    }

                    Venda vendaAtual = uiVenda.iniciarVenda(proximoIdVenda, clienteSelecionado);

                    boolean adicionarMais = true;
                    while (adicionarMais) {
                        Item item = uiVenda.selecionarItem(cadProduto.listarProdutos());
                        if (item != null) {
                            vendaAtual.addItem(item);
                            System.out.println("Item adicionado ao carrinho!");
                        }

                        System.out.print("\nDeseja adicionar outro item? (S/N): ");
                        String resp = teclado.nextLine();
                        adicionarMais = resp.equalsIgnoreCase("S");
                    }

                    if (!vendaAtual.getItens().isEmpty()) {
                        // Aplica o "MOVIMENTO" (baixa no estoque) e registra a venda
                        cadVenda.finalizarVenda(vendaAtual);
                        cadVenda.registrarVenda(vendaAtual);

                        uiVenda.mostrarResumoVenda(vendaAtual);
                        proximoIdVenda++;
                    } else {
                        System.out.println("\nVenda cancelada: nenhum item foi adicionado.");
                    }
                    break;

                case 5:
                    uiVenda.listarVendas(cadVenda.listarVendas());
                    break;

                case 6:
                    // Carrega os dados desenhados pelo professor na lousa
                    Cliente cLousa = new Cliente(proximoIdCliente++, "João", "Rua das Flores, 123");
                    cadCliente.cadastrarCliente(cLousa);

                    Produto p1Lousa = new Produto(proximoIdProduto++, "Tomate", 5.0, 30);
                    Produto p2Lousa = new Produto(proximoIdProduto++, "Cenoura", 3.5, 50);
                    cadProduto.cadastrarProduto(p1Lousa);
                    cadProduto.cadastrarProduto(p2Lousa);

                    System.out.println("\n[Sucesso] Dados da lousa carregados com sucesso!");
                    System.out.println("-> Cliente: João (ID 1)");
                    System.out.println("-> Produto: Tomate (ID 1 | Estoque: 30)");
                    System.out.println("-> Produto: Cenoura (ID 2 | Estoque: 50)\n");
                    break;

                case 0:
                    rodando = false;
                    System.out.println("\nEncerrando o sistema. Até logo!");
                    break;

                default:
                    System.out.println("\nOpção inválida! Escolha uma opção do menu.");
            }
        }
    }
}
