package ordena;

import main.Produto;
import particiona.particionaStrategy;
import java.util.Collection;

public class ordenaQuicksort implements ordenaStrategy {

    private final particionaStrategy particionaStrategy;

    public ordenaQuicksort(particiona.particionaStrategy particionaStrategy) {
        this.particionaStrategy = particionaStrategy;
    }

    @Override
    public void ordena(int inicio, int fim, Collection<Produto> Produtos) {
        if (inicio < fim) {
            int q = particionaStrategy.particiona(inicio, fim, Produtos);
            ordena(inicio, q, Produtos);
            ordena(q + 1, fim, Produtos);
        }
    }
}
