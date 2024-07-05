package particiona;

import main.Produto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class particionaPorEstoque implements particionaStrategy{
    @Override
    public int particiona(int inicio, int fim, Collection<Produto> Produtos) {
        List<Produto> produtos = new ArrayList<>(Produtos);
        Produto x = produtos.get(inicio);
        int i = (inicio - 1);
        int j = (fim + 1);
        while (true) {
            do {
                j--;
            } while (produtos.get(j).getQtdEstoque() > x.getQtdEstoque());
            do {
                i++;
            } while (produtos.get(i).getQtdEstoque() < x.getQtdEstoque());
            if (i < j) {
                Produto temp = produtos.get(i);
                produtos.set(i, produtos.get(j));
                produtos.set(j, temp);
            } else {
                Produtos.clear();
                Produtos.addAll(produtos);
                return j;
            }
        }
    }
}
