package formata;

import main.Produto;

public class formataDecorator implements formataTemplate {
    protected formataTemplate wrapped;
    public formataDecorator(formataTemplate wrapped) {
        this.wrapped = wrapped;
    }
    @Override
    public String formata(Produto produto) {
        return wrapped.formata(produto);
    }
}
