package ordena;

import main.Produto;

import java.util.Collection;

public interface ordenaStrategy {

    void ordena(int inicio, int fim, Collection<Produto> Produtos);

}
