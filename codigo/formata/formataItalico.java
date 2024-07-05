package formata;

import main.Produto;

public class formataItalico extends formataDecorator {
    public formataItalico(formataTemplate wrapped) {
        super(wrapped);
    }
    @Override
    public String formata(Produto produto) {
        return "<span style=\"font-style:italic\">" + super.formata(produto) + "</span>";
    }
}
