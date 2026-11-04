package megafarma.ClienteResource;

import megafarma.bo.RemedioBO;
import megafarma.to.RemedioTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/remedios")
@CrossOrigin(origins = "*")
public class RemedioResource {
    private final RemedioBO bo = new RemedioBO();

    @GetMapping
    public ResponseEntity<List<RemedioTO>> listar() throws SQLException {
        return ResponseEntity.ok(bo.listar());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<RemedioTO> consultar(@PathVariable int codigo) throws SQLException {
        RemedioTO item = bo.consultar(codigo);
        return item == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody RemedioTO item) throws SQLException {
        int resultado = bo.cadastrar(item);
        return resultado > 0
                ? ResponseEntity.status(HttpStatus.CREATED).body("Remedio cadastrado(a) com sucesso!")
                : ResponseEntity.badRequest().body("Nao foi possivel cadastrar.");
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<String> atualizar(@PathVariable int codigo, @RequestBody RemedioTO item) throws SQLException {
        item.setCodigo(codigo);
        int resultado = bo.atualizar(item);
        return resultado > 0
                ? ResponseEntity.ok("Remedio atualizado(a) com sucesso!")
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> excluir(@PathVariable int codigo) throws SQLException {
        int resultado = bo.excluir(codigo);
        return resultado > 0
                ? ResponseEntity.ok("Remedio excluido(a) com sucesso!")
                : ResponseEntity.notFound().build();
    }
}
