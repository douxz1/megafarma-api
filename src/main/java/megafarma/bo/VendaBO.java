package megafarma.bo;

import megafarma.dao.VendaDAO;
import megafarma.to.VendaTO;
import java.sql.SQLException;
import java.util.List;

public class VendaBO {
    private final VendaDAO dao = new VendaDAO();

    private void validar(VendaTO venda) {
        if (venda == null || venda.getCodigo() <= 0) {
            throw new IllegalArgumentException("O codigo da venda deve ser maior que zero.");
        }
        if (venda.getDataDaVenda() == null) {
            throw new IllegalArgumentException("A data da venda e obrigatoria.");
        }
        if (venda.getCodCliente() <= 0) {
            throw new IllegalArgumentException("Informe o codigo de um cliente existente.");
        }
    }

    public int cadastrar(VendaTO venda) throws SQLException {
        validar(venda);
        return dao.cadastrar(venda);
    }
    public VendaTO consultar(int codigo) throws SQLException {
        return dao.consultar(codigo);
    }
    public List<VendaTO> listar() throws SQLException {
        return dao.listar();
    }
    public int atualizar(VendaTO venda) throws SQLException {
        validar(venda);
        return dao.atualizar(venda);
    }
    public int excluir(int codigo) throws SQLException {
        return dao.excluir(codigo);
    }
}
