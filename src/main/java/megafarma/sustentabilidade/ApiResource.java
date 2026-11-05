package megafarma.sustentabilidade;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ApiResource {
    @GetMapping("/")
    public Map<String, Object> inicio() {
        return Map.of(
                "aplicacao", "EcoAções",
                "descricao", "API REST de ações sustentáveis",
                "endpoint", "/api/acoes");
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "application", "ecoacoes");
    }
}
