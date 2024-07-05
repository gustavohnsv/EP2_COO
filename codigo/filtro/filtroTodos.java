package filtro;

import main.Produto;

public class filtroTodos implements filtroStrategy {
    @Override
    public boolean filtra(Produto produto) {
        return true;
    }
}
