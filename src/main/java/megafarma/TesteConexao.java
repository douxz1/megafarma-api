package megafarma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import megafarma.dao.ConnectionFactory;

public class TesteConexao {

    public static void main(String[] args) {

        String sql = "SELECT 1 FROM DUAL";

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement comando = conexao.prepareStatement(sql);
                ResultSet resultado = comando.executeQuery()
        ) {

            if (resultado.next() && resultado.getInt(1) == 1) {
                System.out.println(
                        "SUCESSO: conexão e consulta ao Oracle funcionando!"
                );
            }

        } catch (Exception e) {
            System.err.println("FALHA ao testar a conexão com o Oracle:");
            e.printStackTrace();
        }
    }
}