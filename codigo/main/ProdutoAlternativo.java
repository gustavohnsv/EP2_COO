package main;

public class ProdutoAlternativo extends ProdutoPadrao implements Produto {

    private String negrito;
    private String italico;
    private String cor;

    public ProdutoAlternativo(int id, String descricao, String categoria, int qtdEstoque, double preco, String negrito, String italico, String cor) {
        super(id, descricao, categoria, qtdEstoque, preco);
        setNegrito(negrito);
        setItalico(italico);
        setCor(cor);
    }

    public void setNegrito(String negrito) {
        this.negrito = negrito;
    }

    public void setItalico(String italico) {
        this.italico = italico;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public boolean getNegrito() {
        return Boolean.parseBoolean(this.negrito);
    }

    public boolean getItalico() {
        return Boolean.parseBoolean(this.italico);
    }

    public String getCor() {
        return this.cor;
    }
}
