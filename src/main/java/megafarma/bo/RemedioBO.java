package megafarma.bo;

import megafarma.dao.RemedioDAO;
import megafarma.to.RemedioTO;
import java.sql.SQLException;
import java.util.List;

public class RemedioBO {
    private final RemedioDAO dao = new RemedioDAO();

    private void validar(RemedioTO r) {
        if (r == null || r.getCodigo() <= 0) {
            throw new IllegalArgumentException("O codigo do remedio deve ser maior que zero.");
        }
        if (r.getNome() == null || r.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do remedio e obrigatorio.");
        }
        if (r.getPreco() == null || r.getPreco().signum() < 0 || r.getPreco().scale() > 2
                || r.getPreco().compareTo(new java.math.BigDecimal("9999.99")) > 0) {
            throw new IllegalArgumentException("Informe um preco entre 0 e 9999.99, com ate 2 casas decimais.");
        }
        if (r.getDataDeFabricacao() == null || r.getDataDeValidade() == null) {
            throw new IllegalArgumentException("Fabricacao e validade sao obrigatorias.");
        }
        if (r.getDataDeValidade().isBefore(r.getDataDeFabricacao())) {
            throw new IllegalArgumentException("A validade nao pode ser anterior a fabricacao.");
        }
    }

    public int cadastrar(RemedioTO remedio) throws SQLException {
        validar(remedio);
        return dao.cadastrar(remedio);
    }
    public RemedioTO consultar(int codigo) throws SQLException {
        return dao.consultar(codigo);
    }
    public List<RemedioTO> listar() throws SQLException {
        return dao.listar();
    }
    public int atualizar(RemedioTO remedio) throws SQLException {
        validar(remedio);
        return dao.atualizar(remedio);
    }
    public int excluir(int codigo) throws SQLException {
        return dao.excluir(codigo);
    }
}
