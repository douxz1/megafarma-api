package megafarma.to;


public class ItemVendidoTO {

    private int codVenda;
    private int codRemedio;
    private int quantidade;

    public ItemVendidoTO() {
    }

    public ItemVendidoTO(int codVenda, int codRemedio, int quantidade) {
        this.codVenda = codVenda;
        this.codRemedio = codRemedio;
        this.quantidade = quantidade;
    }

    public int getCodVenda() {
        return codVenda;
    }

    public void setCodVenda(int codVenda) {
        this.codVenda = codVenda;
    }

    public int getCodRemedio() {
        return codRemedio;
    }

    public void setCodRemedio(int codRemedio) {
        this.codRemedio = codRemedio;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

}
