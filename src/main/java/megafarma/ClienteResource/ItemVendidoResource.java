package megafarma.ClienteResource;

import megafarma.bo.ItemVendidoBO;
import megafarma.to.ItemVendidoTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/itens-vendidos")
@CrossOrigin(origins = "*")
public class ItemVendidoResource {
    private final ItemVendidoBO bo = new ItemVendidoBO();

    @GetMapping
    public ResponseEntity<List<ItemVendidoTO>> listar() throws SQLException {
        return ResponseEntity.ok(bo.listar());
    }

    // Dois codigos identificam o item: venda + remedio.
    @GetMapping("/{codVenda}/{codRemedio}")
    public ResponseEntity<ItemVendidoTO> consultar(@PathVariable int codVenda,
                                                   @PathVariable int codRemedio) throws SQLException {
        ItemVendidoTO item = bo.consultar(codVenda, codRemedio);
        return item == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody ItemVendidoTO item) throws SQLException {
        int resultado = bo.cadastrar(item);
        return resultado > 0
                ? ResponseEntity.status(HttpStatus.CREATED).body("Item vendido cadastrado com sucesso!")
                : ResponseEntity.badRequest().body("Nao foi possivel cadastrar o item.");
    }

    @PutMapping("/{codVenda}/{codRemedio}")
    public ResponseEntity<String> atualizar(@PathVariable int codVenda,
                                            @PathVariable int codRemedio,
                                            @RequestBody ItemVendidoTO item) throws SQLException {
        item.setCodVenda(codVenda);
        item.setCodRemedio(codRemedio);
        int resultado = bo.atualizar(item);
        return resultado > 0
                ? ResponseEntity.ok("Item vendido atualizado com sucesso!")
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{codVenda}/{codRemedio}")
    public ResponseEntity<String> excluir(@PathVariable int codVenda,
                                          @PathVariable int codRemedio) throws SQLException {
        int resultado = bo.excluir(codVenda, codRemedio);
        return resultado > 0
                ? ResponseEntity.ok("Item vendido excluido com sucesso!")
                : ResponseEntity.notFound().build();
    }
}
