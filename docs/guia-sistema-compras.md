# Sistema de Compras - Documentação e Guia de Estudos

Este projeto é uma implementação em Java de um **Sistema de Compras e Controle de Vendas**, desenvolvido com foco em Orientação a Objetos e estruturado na arquitetura em camadas (**Model**, **DAL**, **BLL**, **UI**), utilizando persistência em memória.

---

## 1. Visão Geral e Objetivo

O objetivo do sistema é gerenciar clientes, produtos em estoque e realizar transações de venda com múltiplos itens, garantindo o cálculo de totais, validação de disponibilidade e atualização automática do estoque após a conclusão da venda (o conceito de **Movimento**).

---

## 2. Modelagem das Classes (`br.Model`)

O domínio da aplicação é composto por 4 classes principais:

```
+---------------+           1           +---------------+
|    Cliente    |<----------------------|     Venda     |
+---------------+                       +---------------+
                                                |
                                                | 1..* (ArrayList)
                                                v
+---------------+           1           +---------------+
|    Produto    |<----------------------|     Item      |
+---------------+                       +---------------+
```

### Detalhamento das Classes

1. **`Cliente`**:
   - **Responsabilidade**: Representa o comprador cadastrado no sistema.
   - **Atributos**: `id` (int), `nome` (String), `endereco` (String).
   - **Construtores**: Permite criar instâncias com `(id, nome)` ou `(id, nome, endereco)`.

2. **`Produto`**:
   - **Responsabilidade**: Representa os itens cadastrados no catálogo/estoque da loja.
   - **Atributos**: `id` (int), `nome` (String), `preco` (double), `quantidade` (int - estoque disponível).
   - **Destaque**: Possui os métodos `getQuantidade()` / `getQtde()` e `setQuantidade()` / `setQtde()` para refletir as anotações feitas em aula.

3. **`Item`**:
   - **Responsabilidade**: Representa um item específico dentro do carrinho de uma venda.
   - **Atributos**: `produto` (Produto), `quantidade` (int).
   - **Método `getSubtotal()`**: Calcula `produto.getPreco() * quantidade`.

4. **`Venda`**:
   - **Responsabilidade**: Representa uma transação de compra efetuada por um cliente.
   - **Atributos**: `id` (int), `data` (String), `cliente` (Cliente), `itens` (List<Item>).
   - **Método `addItem(Item item)`**: Adiciona um item ao carrinho da venda.
   - **Método `getTotal()`**: Percorre a lista de itens somando o subtotal de cada um.

---

## 3. Ponto-Chave: Por que a classe `Item` é necessária?

Uma dúvida comum em modelagem orientada a objetos é: *por que não colocar a classe `Produto` diretamente dentro da lista da `Venda`?*

- O **`Produto`** representa o registro geral no depósito (ex: "Tomate", com preço unitário R$ 5,00 e 30 unidades em estoque na loja).
- O **`Item`** representa a linha do pedido (o cliente "João" está comprando **10** unidades de "Tomate" nesta compra específica).

Se associássemos `Produto` diretamente à `Venda`, não teríamos onde registrar a **quantidade comprada** sem adulterar o estoque geral do produto no catálogo. A classe `Item` atua como a entidade associativa entre a `Venda` e o `Produto`.

---

## 4. O Conceito de "Movimento" (Baixa de Estoque)

Na lousa da aula, foi especificada a regra de movimentação de estoque:

$$\text{Estoque Atual} = \text{Estoque Anterior} - \text{Quantidade Vendida}$$

Em código, isso se reflete no método `finalizarVenda(Venda venda)` dentro da camada **BLL** (`CadVenda`):

```java
public void finalizarVenda(Venda venda) {
    for (Item item : venda.getItens()) {
        Produto produto = item.getProduto();
        if (produto != null) {
            int novoEstoque = produto.getQuantidade() - item.getQuantidade();
            produto.setQuantidade(novoEstoque);
        }
    }
}
```

Dessa forma, assim que a venda é concluída, os produtos envolvidos têm seus estoques atualizados automaticamente.

---

## 5. Arquitetura em Camadas

O projeto foi dividido em pacotes bem definidos para manter a responsabilidade única e a separação de conceitos:

```
sistema-compras/src/
└── br/
    ├── Model/       -> Entidades de negócio (Cliente, Produto, Item, Venda)
    ├── DAL/         -> Data Access Layer (Persistência e CRUD em memória)
    ├── BLL/         -> Business Logic Layer (Regras de negócio e coordenação)
    └── UI/          -> User Interface (Entrada e saída via Console com Scanner)
```

### 1. `br.Model`
Contém apenas as estruturas de dados (POJOs - Plain Old Java Objects) com atributos privados, construtores e métodos getters/setters. Não realiza cálculos externos nem acessa banco de dados.

### 2. `br.DAL` (Data Access Layer)
Simula as tabelas do banco de dados na memória através de coleções `ArrayList`.
Cada classe DAO (`DaoCliente`, `DaoProduto`, `DaoVenda`) implementa as operações fundamentais de **CRUD**:
- **C**reate: adiciona um novo registro à lista.
- **R**ecover (Read): busca um registro pelo seu identificador (`id`).
- **U**pdate: atualiza os dados de um registro existente.
- **D**elete: remove um registro da lista.
- **listar**: retorna a lista completa de registros.

### 3. `br.BLL` (Business Logic Layer)
Camada responsável por validar os dados e aplicar as regras de negócio antes de repassar as operações para a DAL:
- `CadCliente`: validações de cadastro de clientes.
- `CadProduto`: validações e buscas de produtos.
- `CadVenda`: orquestra a conclusão da venda, garantindo que o movimento de baixa no estoque seja executado e a venda registrada.

### 4. `br.UI` (User Interface)
Responsável exclusivamente pela interação com o usuário através do terminal:
- `UICadCliente`: lê dados para novo cliente e imprime listagens.
- `UICadProduto`: lê dados para novo produto e imprime catálogo com estoque.
- `UICadVenda`: guia o fluxo de venda, seleção de itens, conferência de estoque disponível e exibição do cupom fiscal / resumo da venda.

### 5. `Principal` (`br.BLL.Principal`)
Classe executável que contém o método `main`, inicializando os controladores e provendo um menu interativo em loop (`while` e `switch-case`).

---

## 6. Exemplo de Execução da Aula

No menu principal, a **Opção 6** carrega automaticamente o cenário desenhado pelo professor na lousa:

1. Cadastro do Cliente: `Cliente(1, "João")`
2. Cadastro dos Produtos:
   - `Produto(1, "Tomate", preco=5.00, estoque=30)`
   - `Produto(2, "Cenoura", preco=3.50, estoque=50)`
3. Abertura da Venda ID 1 na data `16/08/2024` para o cliente João.
4. Adição dos itens:
   - 10 Tomates (Subtotal: R$ 50,00)
   - 5 Cenouras (Subtotal: R$ 17,50)
5. Total da Venda: **R$ 67,50**
6. Novo estoque após a venda:
   - Tomates: $30 - 10 = 20$
   - Cenouras: $50 - 5 = 45$

---

## 7. Perguntas Frequentes para Prova / Defesa Oral

Aqui estão as perguntas que o professor costuma fazer sobre essa arquitetura e como responder:

1. **Por que utilizamos a camada DAL e não salvamos os dados direto na UI ou no `main`?**
   - *Resposta*: Para manter o princípio de separação de responsabilidades. A UI só deve cuidar de interagir com o usuário, enquanto a DAL cuida do armazenamento e recuperação dos dados. Se amanhã trocarmos o `ArrayList` por um banco de dados real (MySQL, PostgreSQL), só precisamos alterar a DAL, sem mexer no restante do sistema.

2. **Qual é a função da BLL no fluxo de venda?**
   - *Resposta*: A BLL é onde residem as regras de negócio. No nosso caso, é na classe `CadVenda` que é executado o método `finalizarVenda()`, responsável por iterar os itens da compra e dar baixa na quantidade em estoque dos produtos correspondentes.

3. **Como funciona a relação entre `Venda`, `Item` e `Produto`?**
   - *Resposta*: É uma relação de agregação e composição. Uma `Venda` é composta por uma lista de `Item` (`ArrayList<Item>`). Cada `Item` contém uma referência para o `Produto` selecionado e guarda a quantidade comprada para aquela venda específica.

4. **Por que o método `Recover` na DAL é importante?**
   - *Resposta*: Porque tanto para atualizar (`Update`) quanto para validar compras ou buscar informações de um cliente/produto, precisamos localizar o objeto exato na memória a partir do seu `id`.
