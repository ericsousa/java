package br.UI;

import br.Model.Cliente;
import java.util.List;
import java.util.Scanner;

public class UICadCliente {

    private Scanner teclado;

    public UICadCliente() {
        this.teclado = new Scanner(System.in);
    }

    public UICadCliente(Scanner teclado) {
        this.teclado = teclado;
    }

    public Cliente cadastrarCliente(int idSugerido) {
        System.out.println("\n--- CADASTRO DE CLIENTE ---");
        System.out.println("ID gerado: " + idSugerido);

        System.out.print("Nome: ");
        String nome = teclado.nextLine();

        System.out.print("Endereço: ");
        String endereco = teclado.nextLine();

        Cliente cliente = new Cliente(idSugerido, nome, endereco);
        System.out.println("Cliente cadastrado com sucesso!");
        return cliente;
    }

    public void listarClientes(List<Cliente> clientes) {
        System.out.println("\n--- LISTA DE CLIENTES ---");
        if (clientes == null || clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        for (Cliente c : clientes) {
            System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome() + " | Endereço: " + c.getEndereco());
        }
    }
}
