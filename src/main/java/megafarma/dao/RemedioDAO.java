package megafarma.dao;

import megafarma.to.RemedioTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class RemedioDAO {

    public int cadastrar(RemedioTO remedio) throws SQLException {
        String sql = "INSERT INTO DDD_REMEDIOS (CODIGO, NOME, PRECO, DATA_DE_FABRICACAO, DATA_DE_VALIDADE) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, remedio.getCodigo());
            stmt.setString(2, remedio.getNome());
            stmt.setBigDecimal(3, remedio.getPreco());
            stmt.setDate(4, remedio.getDataDeFabricacao() == null ? null : Date.valueOf(remedio.getDataDeFabricacao()));
            stmt.setDate(5, remedio.getDataDeValidade() == null ? null : Date.valueOf(remedio.getDataDeValidade()));
            return stmt.executeUpdate();
        }
    }

    public RemedioTO consultar(int codigo) throws SQLException {
        String sql = "SELECT * FROM DDD_REMEDIOS WHERE CODIGO = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? converter(rs) : null;
            }
        }
    }

    public List<RemedioTO> listar() throws SQLException {
        List<RemedioTO> remedios = new ArrayList<>();
        String sql = "SELECT * FROM DDD_REMEDIOS ORDER BY CODIGO";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                remedios.add(converter(rs));
            }
        }
        return remedios;
    }

    public int atualizar(RemedioTO remedio) throws SQLException {
        String sql = "UPDATE DDD_REMEDIOS SET NOME = ?, PRECO = ?, DATA_DE_FABRICACAO = ?, DATA_DE_VALIDADE = ? WHERE CODIGO = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, remedio.getNome());
            stmt.setBigDecimal(2, remedio.getPreco());
            stmt.setDate(3, remedio.getDataDeFabricacao() == null ? null : Date.valueOf(remedio.getDataDeFabricacao()));
            stmt.setDate(4, remedio.getDataDeValidade() == null ? null : Date.valueOf(remedio.getDataDeValidade()));
            stmt.setInt(5, remedio.getCodigo());
            return stmt.executeUpdate();
        }
    }

    public int excluir(int codigo) throws SQLException {
        String sql = "DELETE FROM DDD_REMEDIOS WHERE CODIGO = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            return stmt.executeUpdate();
        }
    }

    private RemedioTO converter(ResultSet rs) throws SQLException {
        RemedioTO remedio = new RemedioTO();
        remedio.setCodigo(rs.getInt("CODIGO"));
        remedio.setNome(rs.getString("NOME"));
        remedio.setPreco(rs.getBigDecimal("PRECO"));
        Date fabricacao = rs.getDate("DATA_DE_FABRICACAO");
        Date validade = rs.getDate("DATA_DE_VALIDADE");
        remedio.setDataDeFabricacao(fabricacao == null ? null : fabricacao.toLocalDate());
        remedio.setDataDeValidade(validade == null ? null : validade.toLocalDate());
        return remedio;
    }
}
