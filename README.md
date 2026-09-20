# Sistema de Compras em Java

Projeto acadêmico em Java implementando um **Sistema de Compras e Controle de Vendas** com persistência em memória e arquitetura em camadas (**Model-DAL-BLL-UI**).

---

## Estrutura do Projeto

- `sistema-compras/`: Código-fonte do projeto Java.
  - `src/br/Model/`: Classes de domínio (`Cliente`, `Produto`, `Item`, `Venda`).
  - `src/br/DAL/`: Camada de Acesso a Dados com operações CRUD em memória (`DaoCliente`, `DaoProduto`, `DaoVenda`).
  - `src/br/BLL/`: Camada de Regras de Negócio (`CadCliente`, `CadProduto`, `CadVenda`, `Principal`).
  - `src/br/UI/`: Camada de Interface de Usuário via console (`UICadCliente`, `UICadProduto`, `UICadVenda`).
- `docs/`: Documentação detalhada e guia de estudos para a disciplina.
  - [Guia de Estudos Completo](docs/guia-estudos.md)

---

## Como Executar

### Pré-requisitos
- Java Development Kit (JDK) 11 ou superior instalado.

### Execução via Terminal
1. Navegue até a pasta do projeto:
   ```bash
   cd sistema-compras
   ```

2. Compile os arquivos Java:
   ```bash
   javac -d bin src/br/Model/*.java src/br/DAL/*.java src/br/BLL/*.java src/br/UI/*.java
   ```

3. Execute a aplicação:
   ```bash
   java -cp bin br.BLL.Principal
   ```

### Execução via IntelliJ IDEA
1. Abra a pasta `sistema-compras` no IntelliJ IDEA.
2. Aguarde a indexação da pasta `src` como Source Root.
3. Abra a classe `br.BLL.Principal` e clique no botão **Run** (`Shift + F10`).

---

## Funcionalidades
1. **Cadastro e Listagem de Clientes**: Registro com ID, Nome e Endereço.
2. **Cadastro e Listagem de Produtos**: Controle de preço unitário e saldo em estoque.
3. **Realização de Vendas**:
   - Venda associada a um cliente.
   - Adição de múltiplos itens (produto + quantidade).
   - Validação de estoque em tempo real.
   - Cálculo automático de subtotais e valor total.
   - **Movimento de baixa de estoque** automático após confirmação da venda.
4. **Carga de Teste da Lousa**: Opção 6 no menu carrega instantaneamente os dados do exemplo prático dado em aula (João, Tomate e Cenoura) para testes rápidos.
