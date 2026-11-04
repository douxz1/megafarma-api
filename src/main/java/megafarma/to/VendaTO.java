package megafarma.to;

import java.time.LocalDate;

public class VendaTO {

    private int codigo;
    private LocalDate dataDaVenda;
    private int codCliente;

    public VendaTO() {
    }

    public VendaTO(int codigo, LocalDate dataDaVenda, int codCliente) {
        this.codigo = codigo;
        this.dataDaVenda = dataDaVenda;
        this.codCliente = codCliente;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public LocalDate getDataDaVenda() {
        return dataDaVenda;
    }

    public void setDataDaVenda(LocalDate dataDaVenda) {
        this.dataDaVenda = dataDaVenda;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

}
