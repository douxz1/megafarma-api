<div align="center">

# 💊 MegaFarma
### API REST de gerenciamento farmacêutico

**Java 21 · Spring Boot 4 · Oracle Database · Maven · Docker · Render**

Uma API para cadastrar clientes e medicamentos, registrar vendas e administrar itens vendidos, desenvolvida como projeto acadêmico da FIAP.

[Endpoints](#-endpoints) · [Execução local](#-executar-localmente) · [Deploy no Render](#-deploy-no-render) · [Banco de dados](#-banco-de-dados)

</div>

---

## ✨ Sobre o projeto

O **MegaFarma** é um back-end REST com operações CRUD para quatro entidades relacionadas:

| Entidade | Funcionalidade | Rota base |
| :--- | :--- | :--- |
| 👤 Clientes | Cadastro e consulta de clientes | `/api/clientes` |
| 💊 Remédios | Gestão do catálogo de medicamentos | `/api/remedios` |
| 🧾 Vendas | Registro de vendas por cliente | `/api/vendas` |
| 📦 Itens vendidos | Remédios e quantidades de cada venda | `/api/itens-vendidos` |

### Arquitetura

```text
Requisição HTTP (Insomnia / Front-end)
                  │
                  ▼
             Resource
                  │
                  ▼
             BO (regras)
                  │
                  ▼
             DAO (JDBC)
                  │
                  ▼
           Oracle Database
```

- **Resource:** recebe as chamadas HTTP e retorna respostas REST.
- **BO:** aplica validações e regras de negócio.
- **DAO:** executa as consultas SQL por meio de JDBC.
- **TO:** transporta os dados entre as camadas.
- **CorsFilter:** configura CORS para as rotas da API.
- **GlobalExceptionHandler:** padroniza alguns erros HTTP.

## 🧰 Tecnologias

- Java **21**
- Spring Boot **4.1.2** / Spring Web MVC
- Maven
- Oracle Database / Oracle JDBC
- Docker (build multistage)
- Insomnia para testes de endpoints
- Render para hospedagem

## 🔗 Endpoints

| Método | Rota | Ação |
| :---: | :--- | :--- |
| `GET` | `/api/clientes` | Listar clientes |
| `GET` | `/api/clientes/{codigo}` | Buscar cliente |
| `POST` | `/api/clientes` | Cadastrar cliente |
| `PUT` | `/api/clientes/{codigo}` | Atualizar cliente |
| `DELETE` | `/api/clientes/{codigo}` | Excluir cliente |
| `GET` | `/api/remedios` | Listar remédios |
| `GET` | `/api/remedios/{codigo}` | Buscar remédio |
| `POST` | `/api/remedios` | Cadastrar remédio |
| `PUT` | `/api/remedios/{codigo}` | Atualizar remédio |
| `DELETE` | `/api/remedios/{codigo}` | Excluir remédio |
| `GET` | `/api/vendas` | Listar vendas |
| `GET` | `/api/vendas/{codigo}` | Buscar venda |
| `POST` | `/api/vendas` | Registrar venda |
| `PUT` | `/api/vendas/{codigo}` | Atualizar venda |
| `DELETE` | `/api/vendas/{codigo}` | Excluir venda |
| `GET` | `/api/itens-vendidos` | Listar itens |
| `GET` | `/api/itens-vendidos/{codVenda}/{codRemedio}` | Buscar item |
| `POST` | `/api/itens-vendidos` | Cadastrar item |
| `PUT` | `/api/itens-vendidos/{codVenda}/{codRemedio}` | Atualizar quantidade |
| `DELETE` | `/api/itens-vendidos/{codVenda}/{codRemedio}` | Excluir item |
| `GET` | `/health` | Verificar se a aplicação HTTP está no ar |

**Exemplo de POST de cliente:**

```http
POST /api/clientes
Content-Type: application/json
```

```json
{
  "codigo": 1,
  "nome": "Cliente Exemplo",
  "cpf": "12345678900",
  "email": "cliente@exemplo.com",
  "dataDeNascimento": "2005-05-15"
}
```

**Exemplo de POST de remédio:**

```json
{
  "codigo": 1,
  "nome": "Dipirona 500mg",
  "preco": 12.50,
  "dataDeFabricacao": "2026-01-10",
  "dataDeValidade": "2028-01-10"
}
```

**Exemplo de POST de venda:**

```json
{
  "codigo": 1,
  "dataDaVenda": "2026-11-04",
  "codCliente": 1
}
```

**Exemplo de POST de item vendido:**

```json
{
  "codVenda": 1,
  "codRemedio": 1,
  "quantidade": 2
}
```

> Antes de registrar uma venda, crie o cliente. Antes de registrar um item, crie a venda e o remédio. As chaves estrangeiras precisam existir no banco.

## 🗄️ Banco de dados

A aplicação utiliza quatro tabelas Oracle:

```text
DDD_CLIENTES (CODIGO PK)
     └── DDD_VENDAS (CODIGO PK, CODCLIENTE FK)
                 └── DDD_ITENSVENDIDOS (CODVENDA + CODREMEDIO PK composta)
                                     └── DDD_REMEDIOS (CODIGO PK)
```

O script `sql/estrutura_oracle.sql` é uma **referência para um banco novo**. **Não execute** o script sobre suas tabelas já criadas — comandos `CREATE TABLE` repetidos causam erro. A API não cria as tabelas automaticamente.


## 🚀 Deploy no Render

**[https://megafarma-api-kzze.onrender.com](https://megafarma-api-kzze.onrender.com/api/clientes)](https://megafarma-api-kzze.onrender.com/api/clientes)
https://megafarma-api-kzze.onrender.com/health

## 📁 Organização

```text
src/main/java/megafarma/
├── bo/              # Regras de negócio
├── ClienteResource/ # Controllers REST (nome preservado do projeto original)
├── config/          # CORS e health check
├── dao/             # JDBC e ConnectionFactory
├── exception/       # Tratamento de erros
└── to/              # Modelos de transporte
```

---

<div align="center">

**MegaFarma** · Projeto acadêmico de API REST · FIAP

</div>
