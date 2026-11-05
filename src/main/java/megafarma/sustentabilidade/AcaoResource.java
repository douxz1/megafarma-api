package megafarma.sustentabilidade;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * CRUD de acoes sustentaveis, com dados demonstrativos em memoria.
 * Os registros voltam ao estado inicial quando a instancia reinicia.
 */
@RestController
@RequestMapping("/api/acoes")
@CrossOrigin(origins = "*")
public class AcaoResource {

    public record Acao(Long id, String categoria, String titulo, String descricao, int pontos) {}

    public record AcaoEntrada(String categoria, String titulo, String descricao, Integer pontos) {}

    private final Map<Long, Acao> acoes = new LinkedHashMap<>();
    private final AtomicLong proximoId = new AtomicLong(5);

    public AcaoResource() {
        acoes.put(1L, new Acao(1L, "RECICLAGEM", "Separar recicláveis",
                "Separe papel, plástico, vidro e metal para a coleta seletiva.", 40));
        acoes.put(2L, new Acao(2L, "MOBILIDADE", "Usar transporte coletivo",
                "Prefira ônibus, metrô ou trem para um deslocamento do dia.", 60));
        acoes.put(3L, new Acao(3L, "ENERGIA", "Economizar energia",
                "Desligue luzes e aparelhos que não estiverem em uso.", 30));
        acoes.put(4L, new Acao(4L, "CONSUMO", "Evitar descartáveis",
                "Use garrafa, caneca ou talheres reutilizáveis durante o dia.", 50));
    }

    @GetMapping
    public synchronized List<Acao> listar() {
        return new ArrayList<>(acoes.values());
    }

    @GetMapping("/{id}")
    public synchronized Acao consultar(@PathVariable Long id) {
        return obterOu404(id);
    }

    @PostMapping
    public synchronized ResponseEntity<Acao> cadastrar(@RequestBody AcaoEntrada entrada) {
        validar(entrada);
        long id = proximoId.getAndIncrement();
        Acao acao = criar(id, entrada);
        acoes.put(id, acao);
        return ResponseEntity.created(URI.create("/api/acoes/" + id)).body(acao);
    }

    @PutMapping("/{id}")
    public synchronized Acao atualizar(@PathVariable Long id, @RequestBody AcaoEntrada entrada) {
        obterOu404(id);
        validar(entrada);
        Acao atualizada = criar(id, entrada);
        acoes.put(id, atualizada);
        return atualizada;
    }

    @DeleteMapping("/{id}")
    public synchronized ResponseEntity<Void> excluir(@PathVariable Long id) {
        obterOu404(id);
        acoes.remove(id);
        return ResponseEntity.noContent().build();
    }

    private Acao obterOu404(Long id) {
        Acao acao = acoes.get(id);
        if (acao == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ação não encontrada");
        }
        return acao;
    }

    private static Acao criar(Long id, AcaoEntrada dados) {
        return new Acao(id,
                dados.categoria().trim().toUpperCase(),
                dados.titulo().trim(),
                dados.descricao().trim(),
                dados.pontos());
    }

    private static void validar(AcaoEntrada dados) {
        if (dados == null || vazio(dados.categoria()) || vazio(dados.titulo())
                || vazio(dados.descricao()) || dados.pontos() == null || dados.pontos() <= 0) {
            throw new IllegalArgumentException(
                    "Informe categoria, título, descrição e pontos maiores que zero.");
        }
    }

    private static boolean vazio(String texto) {
        return texto == null || texto.isBlank();
    }
}
