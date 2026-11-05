package megafarma.sustentabilidade;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * API de sustentabilidade demonstrativa.
 * Persistencia em memoria: os dados sao reiniciados em cada redeploy/restart.
 * Rotas antigas do MegaFarma permanecem disponiveis para compatibilidade.
 */
@RestController
@RequestMapping("/api")
public class SustentabilidadeResource {

    private final Map<Long, Acao> acoes = new LinkedHashMap<>();
    private final Map<Long, Participante> participantes = new LinkedHashMap<>();
    private final Map<Long, Registro> registros = new LinkedHashMap<>();
    private long proximaAcao = 5;
    private long proximoParticipante = 1;
    private long proximoRegistro = 1;

    public SustentabilidadeResource() {
        acoes.put(1L, new Acao(1L, "RECICLAGEM", "Separar reciclaveis",
                "Separe papel, plastico, vidro e metal para a coleta seletiva.", 40));
        acoes.put(2L, new Acao(2L, "MOBILIDADE", "Usar transporte coletivo",
                "Priorize onibus, metro ou trem em um deslocamento do dia.", 60));
        acoes.put(3L, new Acao(3L, "ENERGIA", "Economizar energia",
                "Desligue luzes e aparelhos quando nao estiverem em uso.", 30));
        acoes.put(4L, new Acao(4L, "CONSUMO", "Evitar descartaveis",
                "Use garrafa, caneca ou talheres reutilizaveis durante o dia.", 50));
    }

    public record Acao(Long id, String categoria, String titulo, String descricao, int pontos) {}
    public record AcaoEntrada(String categoria, String titulo, String descricao, Integer pontos) {}
    public record Participante(Long id, String nome, int pontos) {}
    public record ParticipanteEntrada(String nome) {}
    public record Registro(Long id, Long participanteId, Long acaoId,
                           LocalDate data, int pontosGanhos) {}
    public record RegistroEntrada(Long participanteId, Long acaoId, LocalDate data) {}
    public record Ranking(Long participanteId, String nome, int pontos) {}

    @GetMapping("/acoes")
    public synchronized List<Acao> listarAcoes() {
        return new ArrayList<>(acoes.values());
    }

    @GetMapping("/acoes/{id}")
    public synchronized Acao buscarAcao(@PathVariable long id) {
        return acaoObrigatoria(id);
    }

    @PostMapping("/acoes")
    public synchronized ResponseEntity<Acao> cadastrarAcao(@RequestBody AcaoEntrada entrada) {
        validarAcao(entrada);
        long id = proximaAcao++;
        Acao acao = new Acao(id, entrada.categoria().trim().toUpperCase(),
                entrada.titulo().trim(), entrada.descricao().trim(), entrada.pontos());
        acoes.put(id, acao);
        return ResponseEntity.created(URI.create("/api/acoes/" + id)).body(acao);
    }

    @PutMapping("/acoes/{id}")
    public synchronized Acao atualizarAcao(@PathVariable long id, @RequestBody AcaoEntrada entrada) {
        acaoObrigatoria(id);
        validarAcao(entrada);
        Acao acao = new Acao(id, entrada.categoria().trim().toUpperCase(),
                entrada.titulo().trim(), entrada.descricao().trim(), entrada.pontos());
        acoes.put(id, acao);
        return acao;
    }

    @DeleteMapping("/acoes/{id}")
    public synchronized ResponseEntity<Void> excluirAcao(@PathVariable long id) {
        acaoObrigatoria(id);
        boolean utilizada = registros.values().stream().anyMatch(r -> r.acaoId().equals(id));
        if (utilizada) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Acao com registros de conclusao nao pode ser excluida");
        }
        acoes.remove(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/participantes")
    public synchronized List<Participante> listarParticipantes() {
        return new ArrayList<>(participantes.values());
    }

    @GetMapping("/participantes/{id}")
    public synchronized Participante buscarParticipante(@PathVariable long id) {
        return participanteObrigatorio(id);
    }

    @PostMapping("/participantes")
    public synchronized ResponseEntity<Participante> cadastrarParticipante(
            @RequestBody ParticipanteEntrada entrada) {
        if (entrada == null || vazio(entrada.nome())) {
            throw new IllegalArgumentException("Nome do participante e obrigatorio.");
        }
        long id = proximoParticipante++;
        Participante participante = new Participante(id, entrada.nome().trim(), 0);
        participantes.put(id, participante);
        return ResponseEntity.created(URI.create("/api/participantes/" + id)).body(participante);
    }

    @GetMapping("/registros")
    public synchronized List<Registro> listarRegistros() {
        return new ArrayList<>(registros.values());
    }

    @PostMapping("/registros")
    public synchronized ResponseEntity<Registro> registrarConclusao(
            @RequestBody RegistroEntrada entrada) {
        if (entrada == null || entrada.participanteId() == null || entrada.acaoId() == null) {
            throw new IllegalArgumentException("Informe participanteId e acaoId.");
        }
        Participante participante = participanteObrigatorio(entrada.participanteId());
        Acao acao = acaoObrigatoria(entrada.acaoId());
        LocalDate data = entrada.data() == null ? LocalDate.now() : entrada.data();
        if (data.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data da atividade nao pode estar no futuro.");
        }
        boolean repetida = registros.values().stream().anyMatch(r ->
                r.participanteId().equals(participante.id())
                && r.acaoId().equals(acao.id())
                && r.data().equals(data));
        if (repetida) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Esta acao ja foi registrada para o participante nesta data");
        }

        long id = proximoRegistro++;
        Registro registro = new Registro(id, participante.id(), acao.id(), data, acao.pontos());
        registros.put(id, registro);
        participantes.put(participante.id(), new Participante(
                participante.id(), participante.nome(), participante.pontos() + acao.pontos()));
        return ResponseEntity.created(URI.create("/api/registros/" + id)).body(registro);
    }

    @DeleteMapping("/registros/{id}")
    public synchronized ResponseEntity<Void> cancelarRegistro(@PathVariable long id) {
        Registro registro = registros.remove(id);
        if (registro == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro nao encontrado");
        }
        Participante participante = participanteObrigatorio(registro.participanteId());
        participantes.put(participante.id(), new Participante(
                participante.id(), participante.nome(), participante.pontos() - registro.pontosGanhos()));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ranking")
    public synchronized List<Ranking> ranking() {
        return participantes.values().stream()
                .map(p -> new Ranking(p.id(), p.nome(), p.pontos()))
                .sorted(Comparator.comparingInt(Ranking::pontos).reversed()
                        .thenComparing(Ranking::nome))
                .toList();
    }

    private Acao acaoObrigatoria(long id) {
        Acao acao = acoes.get(id);
        if (acao == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Acao nao encontrada");
        }
        return acao;
    }

    private Participante participanteObrigatorio(long id) {
        Participante participante = participantes.get(id);
        if (participante == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Participante nao encontrado");
        }
        return participante;
    }

    private static void validarAcao(AcaoEntrada entrada) {
        if (entrada == null || vazio(entrada.categoria()) ||
                vazio(entrada.titulo()) || vazio(entrada.descricao()) ||
                entrada.pontos() == null || entrada.pontos() <= 0) {
            throw new IllegalArgumentException(
                    "Informe categoria, titulo, descricao e pontos positivos.");
        }
    }

    private static boolean vazio(String valor) {
        return valor == null || valor.isBlank();
    }
}
