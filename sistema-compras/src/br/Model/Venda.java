package br.Model;

import java.util.ArrayList;
import java.util.List;

public class Venda {
    private int id;
    private String data;
    private Cliente cliente;
    private List<Item> itens;

    public Venda() {
        this.itens = new ArrayList<>();
    }

    public Venda(int id, String data) {
        this.id = id;
        this.data = data;
        this.itens = new ArrayList<>();
    }

    public Venda(int id, String data, Cliente cliente) {
        this.id = id;
        this.data = data;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public void addItem(Item item) {
        if (item != null) {
            this.itens.add(item);
        }
    }

    public double getTotal() {
        double total = 0.0;
        for (Item item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }
}
