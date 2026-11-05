# 🌱 API de Sustentabilidade — módulo EcoAções

> Módulo sustentável implementado em **Java 21 + Spring Boot 4**, dentro do projeto MegaFarma. As rotas antigas foram mantidas temporariamente para não interromper o sistema.

## ✅ Funcionalidades

- CRUD de **ações ecológicas**, com categoria, título, descrição e pontos.
- Cadastro e consulta de **participantes**.
- Registro de conclusão de ações com crédito automático de pontos.
- Proteção contra o registro da mesma ação pelo mesmo participante no mesmo dia.
- Exclusão de registro com estorno dos pontos.
- **Ranking** ordenado por pontos.

### Exemplo da interface enviada

```json
{
  "id": 4,
  "categoria": "CONSUMO",
  "titulo": "Evitar descartaveis",
  "descricao": "Use garrafa, caneca ou talheres reutilizaveis durante o dia.",
  "pontos": 50
}
```

## 🔗 Endpoints

| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/acoes` | Lista ações sustentáveis |
| GET | `/api/acoes/{id}` | Busca uma ação |
| POST | `/api/acoes` | Cadastra ação |
| PUT | `/api/acoes/{id}` | Atualiza ação |
| DELETE | `/api/acoes/{id}` | Exclui ação sem registros |
| GET | `/api/participantes` | Lista participantes |
| GET | `/api/participantes/{id}` | Busca participante |
| POST | `/api/participantes` | Cadastra participante |
| GET | `/api/registros` | Lista conclusões |
| POST | `/api/registros` | Registra conclusão e credita pontos |
| DELETE | `/api/registros/{id}` | Cancela registro e estorna pontos |
| GET | `/api/ranking` | Lista participantes por pontuação |
| GET | `/health` | Estado HTTP da aplicação |

## 🧪 Teste no Insomnia

**1 — Listar ações**

```http
GET https://megafarma-api-kzze.onrender.com/api/acoes
```

**2 — Criar participante**

```http
POST /api/participantes
Content-Type: application/json
```

```json
{"nome":"Pessoa Exemplo"}
```

**3 — Registrar ação sustentável de ID 4**

```http
POST /api/registros
Content-Type: application/json
```

```json
{"participanteId":1,"acaoId":4}
```

O participante recebe **50 pontos**. Omitir `data` usa a data atual do servidor.

**4 — Conferir o ranking**

```http
GET /api/ranking
```

**5 — Criar nova ação**

```http
POST /api/acoes
Content-Type: application/json
```

```json
{
  "categoria":"AGUA",
  "titulo":"Reduzir tempo de banho",
  "descricao":"Diminua o tempo de banho para economizar agua.",
  "pontos":35
}
```

## ⚠️ Limitações desta versão demonstrativa

- Os dados sustentáveis são armazenados **em memória**, e são perdidos ao reiniciar, escalar ou publicar novamente o serviço.
- Não há login, autenticação nem prova de realização das ações.
- A pontuação é **gamificação demonstrativa**, não medição verificada de carbono.
- O módulo sustentável **não usa o Oracle**. Os módulos farmacêuticos antigos continuam dependendo dele.
- Recomendado para demonstrações e protótipo acadêmico, não para produção com dados reais.
- Para produção: persistência em banco externo, autenticação, testes automatizados e proteção contra abuso.

## 🚀 Deploy

O módulo utiliza o Dockerfile e as configurações de Spring Boot existentes. Faça o redeploy do commit mais recente no Render. Use `/api/acoes` para validar o módulo sustentável, mesmo que o Oracle esteja indisponível.

## 📁 Estrutura

`src/main/java/megafarma/sustentabilidade/SustentabilidadeResource.java`

A API legada do MegaFarma permanece disponível até que a migração do projeto seja aprovada.
