package filtro;

import main.Produto;

public class filtroEstoqueMenorOuIgual implements filtroStrategy {
    private final int limite;
    public filtroEstoqueMenorOuIgual(int limite) {
        this.limite = limite;
    }
    @Override
    public boolean filtra(Produto produto) {
        return produto.getQtdEstoque() <= limite;
    }
}
