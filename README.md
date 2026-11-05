<div align="center">

# 🌱 EcoAções
### API REST de ações sustentáveis

**Java 21 · Spring Boot 4 · Maven · Docker · Render**

Gerencie ações ecológicas com categorias, descrições e pontos de incentivo.

</div>

---

## ♻️ Sobre

O projeto foi transformado em uma API de sustentabilidade. **Não existem mais recursos de clientes, medicamentos, vendas ou itens vendidos no código desta versão.**

Cada ação possui:
- `id` — identificador numérico;
- `categoria` — por exemplo, CONSUMO, RECICLAGEM, ENERGIA ou MOBILIDADE;
- `titulo` — nome da ação;
- `descricao` — orientação de como realizar a ação;
- `pontos` — recompensa de gamificação.

### 🌿 Ação de exemplo

```json
{
  "id": 4,
  "categoria": "CONSUMO",
  "titulo": "Evitar descartáveis",
  "descricao": "Use garrafa, caneca ou talheres reutilizáveis durante o dia.",
  "pontos": 50
}
```

## 🔗 Endpoints

| Método | Endpoint | Descrição |
|:--|:--|:--|
| GET | `/api/acoes` | Listar todas as ações |
| GET | `/api/acoes/{id}` | Consultar uma ação |
| POST | `/api/acoes` | Cadastrar ação |
| PUT | `/api/acoes/{id}` | Editar ação |
| DELETE | `/api/acoes/{id}` | Excluir ação |
| GET | `/health` | Verificar a aplicação |
| GET | `/` | Informações da API |

### POST — cadastrar ação

```http
POST /api/acoes
Content-Type: application/json
```

```json
{
  "categoria": "ÁGUA",
  "titulo": "Reduzir tempo de banho",
  "descricao": "Diminua o tempo do banho para evitar desperdício de água.",
  "pontos": 35
}
```

**Resposta:** `201 Created`, com o objeto cadastrado e o `id` criado automaticamente.

### PUT — editar ação

```http
PUT /api/acoes/4
Content-Type: application/json
```

```json
{
  "categoria": "CONSUMO",
  "titulo": "Evitar descartáveis",
  "descricao": "Leve uma garrafa e talheres reutilizáveis.",
  "pontos": 50
}
```

### DELETE — remover ação

```http
DELETE /api/acoes/4
```

**Resposta:** `204 No Content`.

## 🛠️ Executar localmente

Requisitos: **JDK 21** e **Maven 3.9+**.

```bash
mvn spring-boot:run
```

Abra `http://localhost:8080/api/acoes`.

## 🐳 Deploy no Render

1. Crie ou atualize um **Web Service** conectado ao repositório.
2. Selecione `main` como branch e `Docker` como runtime.
3. Use `Dockerfile` na raiz.
4. Faça **Deploy latest commit**.
5. Acesse `https://SEU-SERVICO.onrender.com/api/acoes`.

Não é necessário configurar Oracle ou variáveis `DB_*` para esta versão.

## ⚠️ Persistência

**Versão demonstrativa:** as ações são mantidas na memória da aplicação. Ao reiniciar o serviço no Render, os dados retornam às quatro ações iniciais. Não utilizar para dados permanentes. Futuramente, pode ser integrado um banco de dados externo.

## 📁 Estrutura

```text
src/main/java/megafarma/
  MegafarmaApplication.java
  sustentabilidade/
    AcaoResource.java
    ApiResource.java
    ErroResource.java
src/main/resources/application.properties
pom.xml
Dockerfile
README.md
```

> Os arquivos antigos de farmácia não fazem parte desta nova versão do código; foram preservados somente na branch de backup `backup/versao-farmacia-2026-11-04`.
