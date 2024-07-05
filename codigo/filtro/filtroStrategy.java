package filtro;

import main.Produto;

public interface filtroStrategy {
    boolean filtra(Produto produto);
}
