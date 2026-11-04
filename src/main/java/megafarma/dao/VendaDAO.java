package megafarma.dao;

import megafarma.to.VendaTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    public int cadastrar(VendaTO venda) throws SQLException {
        String sql = "INSERT INTO DDD_VENDAS (CODIGO, DATA_DA_VENDA, CODCLIENTE) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, venda.getCodigo());
            stmt.setDate(2, Date.valueOf(venda.getDataDaVenda()));
            stmt.setInt(3, venda.getCodCliente());
            return stmt.executeUpdate();
        }
    }

    public VendaTO consultar(int codigo) throws SQLException {
        String sql = "SELECT * FROM DDD_VENDAS WHERE CODIGO = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? converter(rs) : null;
            }
        }
    }

    public List<VendaTO> listar() throws SQLException {
        List<VendaTO> vendas = new ArrayList<>();
        String sql = "SELECT * FROM DDD_VENDAS ORDER BY CODIGO";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                vendas.add(converter(rs));
            }
        }
        return vendas;
    }

    public int atualizar(VendaTO venda) throws SQLException {
        String sql = "UPDATE DDD_VENDAS SET DATA_DA_VENDA = ?, CODCLIENTE = ? WHERE CODIGO = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(venda.getDataDaVenda()));
            stmt.setInt(2, venda.getCodCliente());
            stmt.setInt(3, venda.getCodigo());
            return stmt.executeUpdate();
        }
    }

    public int excluir(int codigo) throws SQLException {
        String sql = "DELETE FROM DDD_VENDAS WHERE CODIGO = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            return stmt.executeUpdate();
        }
    }

    private VendaTO converter(ResultSet rs) throws SQLException {
        VendaTO venda = new VendaTO();
        venda.setCodigo(rs.getInt("CODIGO"));
        Date data = rs.getDate("DATA_DA_VENDA");
        venda.setDataDaVenda(data == null ? null : data.toLocalDate());
        venda.setCodCliente(rs.getInt("CODCLIENTE"));
        return venda;
    }
}
