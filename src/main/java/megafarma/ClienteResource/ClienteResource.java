
package megafarma.ClienteResource;

import megafarma.bo.ClienteBO;
import megafarma.to.ClienteTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteResource {

    private final ClienteBO bo = new ClienteBO();

    // LISTAR TODOS OS CLIENTES
    @GetMapping
    public ResponseEntity<List<ClienteTO>> listar()
            throws SQLException {

        return ResponseEntity.ok(bo.listar());
    }

    // CONSULTAR CLIENTE PELO CÓDIGO
    @GetMapping("/{codigo}")
    public ResponseEntity<ClienteTO> consultar(
            @PathVariable int codigo) throws SQLException {

        ClienteTO cliente = bo.consultar(codigo);

        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cliente);
    }

    // CADASTRAR CLIENTE
    @PostMapping
    public ResponseEntity<String> cadastrar(
            @RequestBody ClienteTO cliente) throws SQLException {

        int resultado = bo.cadastrar(cliente);

        if (resultado > 0) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Cliente cadastrado com sucesso!");
        }

        return ResponseEntity.badRequest()
                .body("Não foi possível cadastrar o cliente.");
    }

    // ATUALIZAR CLIENTE
    @PutMapping("/{codigo}")
    public ResponseEntity<String> atualizar(
            @PathVariable int codigo,
            @RequestBody ClienteTO cliente) throws SQLException {

        cliente.setCodigo(codigo);

        int resultado = bo.atualizar(cliente);

        if (resultado > 0) {
            return ResponseEntity.ok(
                    "Cliente atualizado com sucesso!"
            );
        }

        return ResponseEntity.notFound().build();
    }

    // EXCLUIR CLIENTE
    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> excluir(
            @PathVariable int codigo) throws SQLException {

        int resultado = bo.excluir(codigo);

        if (resultado > 0) {
            return ResponseEntity.ok(
                    "Cliente excluído com sucesso!"
            );
        }

        return ResponseEntity.notFound().build();
    }
}
