package particiona;

import main.Produto;

import java.util.Collection;

public interface particionaStrategy {

    int particiona(int inicio, int fim, Collection<Produto> Produtos);

}
