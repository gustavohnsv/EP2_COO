package formata;

import main.Produto;
import main.ProdutoAlternativo;

public class formataCor extends formataDecorator{
    public formataCor(formataTemplate wrapped) {
        super(wrapped);
    }
    @Override
    public String formata(Produto produto) {
        ProdutoAlternativo p = (ProdutoAlternativo) produto;
        return "<span style=\"color:" + p.getCor() + "\">" + super.formata(produto) + "</span>";
    }
}
