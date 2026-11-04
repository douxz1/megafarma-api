package megafarma.bo;

import megafarma.dao.ItemVendidoDAO;
import megafarma.to.ItemVendidoTO;
import java.sql.SQLException;
import java.util.List;

public class ItemVendidoBO {
    private final ItemVendidoDAO dao = new ItemVendidoDAO();

    private void validar(ItemVendidoTO item) {
        if (item == null || item.getCodVenda() <= 0 || item.getCodRemedio() <= 0) {
            throw new IllegalArgumentException("Informe os codigos da venda e do remedio.");
        }
        if (item.getQuantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
    }

    public int cadastrar(ItemVendidoTO item) throws SQLException {
        validar(item);
        return dao.cadastrar(item);
    }
    public ItemVendidoTO consultar(int codVenda, int codRemedio) throws SQLException {
        return dao.consultar(codVenda, codRemedio);
    }
    public List<ItemVendidoTO> listar() throws SQLException {
        return dao.listar();
    }
    public int atualizar(ItemVendidoTO item) throws SQLException {
        validar(item);
        return dao.atualizar(item);
    }
    public int excluir(int codVenda, int codRemedio) throws SQLException {
        return dao.excluir(codVenda, codRemedio);
    }
}
