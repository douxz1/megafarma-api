package megafarma.dao;

import megafarma.to.ItemVendidoTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemVendidoDAO {

    public int cadastrar(ItemVendidoTO item) throws SQLException {
        String sql = "INSERT INTO DDD_ITENSVENDIDOS (CODVENDA, CODREMEDIO, QUANTIDADE) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getCodVenda());
            stmt.setInt(2, item.getCodRemedio());
            stmt.setInt(3, item.getQuantidade());
            return stmt.executeUpdate();
        }
    }

    public ItemVendidoTO consultar(int codVenda, int codRemedio) throws SQLException {
        String sql = "SELECT * FROM DDD_ITENSVENDIDOS WHERE CODVENDA = ? AND CODREMEDIO = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codVenda);
            stmt.setInt(2, codRemedio);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? converter(rs) : null;
            }
        }
    }

    public List<ItemVendidoTO> listar() throws SQLException {
        List<ItemVendidoTO> itens = new ArrayList<>();
        String sql = "SELECT * FROM DDD_ITENSVENDIDOS ORDER BY CODVENDA, CODREMEDIO";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                itens.add(converter(rs));
            }
        }
        return itens;
    }

    public int atualizar(ItemVendidoTO item) throws SQLException {
        String sql = "UPDATE DDD_ITENSVENDIDOS SET QUANTIDADE = ? WHERE CODVENDA = ? AND CODREMEDIO = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getQuantidade());
            stmt.setInt(2, item.getCodVenda());
            stmt.setInt(3, item.getCodRemedio());
            return stmt.executeUpdate();
        }
    }

    public int excluir(int codVenda, int codRemedio) throws SQLException {
        String sql = "DELETE FROM DDD_ITENSVENDIDOS WHERE CODVENDA = ? AND CODREMEDIO = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codVenda);
            stmt.setInt(2, codRemedio);
            return stmt.executeUpdate();
        }
    }

    private ItemVendidoTO converter(ResultSet rs) throws SQLException {
        ItemVendidoTO item = new ItemVendidoTO();
        item.setCodVenda(rs.getInt("CODVENDA"));
        item.setCodRemedio(rs.getInt("CODREMEDIO"));
        item.setQuantidade(rs.getInt("QUANTIDADE"));
        return item;
    }
}
