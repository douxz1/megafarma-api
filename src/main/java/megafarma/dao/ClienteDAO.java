
package megafarma.dao;

import megafarma.to.ClienteTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // CADASTRAR CLIENTE
    public int cadastrar(ClienteTO cliente) throws SQLException {

        String sql = "INSERT INTO DDD_CLIENTES " +
                "(CODIGO, NOME, CPF, EMAIL, DATA_DE_NASCIMENTO) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, cliente.getCodigo());
            comando.setString(2, cliente.getNome());
            comando.setString(3, cliente.getCpf());
            comando.setString(4, cliente.getEmail());
            comando.setDate(5, Date.valueOf(cliente.getDataDeNascimento()));

            return comando.executeUpdate();
        }
    }

    // CONSULTAR CLIENTE PELO CÓDIGO
    public ClienteTO consultar(int codigo) throws SQLException {

        String sql = "SELECT * FROM DDD_CLIENTES WHERE CODIGO = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, codigo);

            try (ResultSet resultado = comando.executeQuery()) {

                if (resultado.next()) {

                    ClienteTO cliente = new ClienteTO();

                    cliente.setCodigo(resultado.getInt("CODIGO"));
                    cliente.setNome(resultado.getString("NOME"));
                    cliente.setCpf(resultado.getString("CPF"));
                    cliente.setEmail(resultado.getString("EMAIL"));

                    Date data = resultado.getDate("DATA_DE_NASCIMENTO");

                    if (data != null) {
                        cliente.setDataDeNascimento(data.toLocalDate());
                    }

                    return cliente;
                }
            }
        }

        return null;
    }

    // LISTAR TODOS OS CLIENTES
    public List<ClienteTO> listar() throws SQLException {

        String sql = "SELECT * FROM DDD_CLIENTES ORDER BY CODIGO";

        List<ClienteTO> clientes = new ArrayList<>();

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {

                ClienteTO cliente = new ClienteTO();

                cliente.setCodigo(resultado.getInt("CODIGO"));
                cliente.setNome(resultado.getString("NOME"));
                cliente.setCpf(resultado.getString("CPF"));
                cliente.setEmail(resultado.getString("EMAIL"));

                Date data = resultado.getDate("DATA_DE_NASCIMENTO");

                if (data != null) {
                    cliente.setDataDeNascimento(data.toLocalDate());
                }

                clientes.add(cliente);
            }
        }

        return clientes;
    }

    // ATUALIZAR CLIENTE
    public int atualizar(ClienteTO cliente) throws SQLException {

        String sql = "UPDATE DDD_CLIENTES SET " +
                "NOME = ?, CPF = ?, EMAIL = ?, DATA_DE_NASCIMENTO = ? " +
                "WHERE CODIGO = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, cliente.getNome());
            comando.setString(2, cliente.getCpf());
            comando.setString(3, cliente.getEmail());
            comando.setDate(4, Date.valueOf(cliente.getDataDeNascimento()));
            comando.setInt(5, cliente.getCodigo());

            return comando.executeUpdate();
        }
    }

    // EXCLUIR CLIENTE
    public int excluir(int codigo) throws SQLException {

        String sql = "DELETE FROM DDD_CLIENTES WHERE CODIGO = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, codigo);

            return comando.executeUpdate();
        }
    }
}
