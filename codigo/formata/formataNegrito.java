package formata;

import main.Produto;

public class formataNegrito extends formataDecorator {
    public formataNegrito(formataTemplate wrapped) {
        super(wrapped);
    }
    @Override
    public String formata(Produto produto) {
        return "<span style=\"font-weight:bold\">" + super.formata(produto) + "</span>";
    }
}
