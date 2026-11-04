
package megafarma.bo;

import megafarma.dao.ClienteDAO;
import megafarma.to.ClienteTO;

import java.sql.SQLException;
import java.util.List;

public class ClienteBO {

    private ClienteDAO dao = new ClienteDAO();

    // CADASTRAR CLIENTE
    public int cadastrar(ClienteTO cliente) throws SQLException {

        validarCliente(cliente);

        if (cliente.getCodigo() <= 0) {
            throw new IllegalArgumentException(
                    "O código do cliente deve ser maior que zero."
            );
        }

        return dao.cadastrar(cliente);
    }

    // CONSULTAR CLIENTE
    public ClienteTO consultar(int codigo) throws SQLException {

        if (codigo <= 0) {
            throw new IllegalArgumentException(
                    "Código inválido."
            );
        }

        return dao.consultar(codigo);
    }

    // LISTAR CLIENTES
    public List<ClienteTO> listar() throws SQLException {

        return dao.listar();
    }

    // ATUALIZAR CLIENTE
    public int atualizar(ClienteTO cliente) throws SQLException {

        validarCliente(cliente);

        if (cliente.getCodigo() <= 0) {
            throw new IllegalArgumentException(
                    "Código inválido."
            );
        }

        return dao.atualizar(cliente);
    }

    // EXCLUIR CLIENTE
    public int excluir(int codigo) throws SQLException {

        if (codigo <= 0) {
            throw new IllegalArgumentException(
                    "Código inválido."
            );
        }

        return dao.excluir(codigo);
    }

    // VALIDAR DADOS DO CLIENTE
    private void validarCliente(ClienteTO cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "Cliente não informado."
            );
        }

        if (cliente.getNome() == null ||
                cliente.getNome().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Nome do cliente é obrigatório."
            );
        }

        if (cliente.getCpf() == null ||
                cliente.getCpf().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "CPF do cliente é obrigatório."
            );
        }

        if (cliente.getEmail() == null ||
                cliente.getEmail().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "E-mail do cliente é obrigatório."
            );
        }

        if (cliente.getDataDeNascimento() == null) {
            throw new IllegalArgumentException(
                    "Data de nascimento é obrigatória."
            );
        }
    }
}
