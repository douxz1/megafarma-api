package megafarma.ClienteResource;

import megafarma.bo.VendaBO;
import megafarma.to.VendaTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/vendas")
@CrossOrigin(origins = "*")
public class VendaResource {
    private final VendaBO bo = new VendaBO();

    @GetMapping
    public ResponseEntity<List<VendaTO>> listar() throws SQLException {
        return ResponseEntity.ok(bo.listar());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<VendaTO> consultar(@PathVariable int codigo) throws SQLException {
        VendaTO item = bo.consultar(codigo);
        return item == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody VendaTO item) throws SQLException {
        int resultado = bo.cadastrar(item);
        return resultado > 0
                ? ResponseEntity.status(HttpStatus.CREATED).body("Venda cadastrado(a) com sucesso!")
                : ResponseEntity.badRequest().body("Nao foi possivel cadastrar.");
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<String> atualizar(@PathVariable int codigo, @RequestBody VendaTO item) throws SQLException {
        item.setCodigo(codigo);
        int resultado = bo.atualizar(item);
        return resultado > 0
                ? ResponseEntity.ok("Venda atualizado(a) com sucesso!")
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> excluir(@PathVariable int codigo) throws SQLException {
        int resultado = bo.excluir(codigo);
        return resultado > 0
                ? ResponseEntity.ok("Venda excluido(a) com sucesso!")
                : ResponseEntity.notFound().build();
    }
}
