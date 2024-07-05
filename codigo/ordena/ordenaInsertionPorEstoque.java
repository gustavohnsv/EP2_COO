package ordena;

import main.Produto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ordenaInsertionPorEstoque implements ordenaStrategy{
    @Override
    public void ordena(int inicio, int fim, Collection<Produto> Produtos) {
        List<Produto> produtos = new ArrayList<>(Produtos);
        for (int i = inicio; i <= fim; i++) {
            Produto x = produtos.get(i);
            int j = (i - 1);
            while (j >= inicio) {
                if (x.getQtdEstoque() < produtos.get(j).getQtdEstoque()) {
                    produtos.set(j+1, produtos.get(j));
                    j--;
                } else break;
            }
            produtos.set(j+1, x);
            Produtos.clear();
            Produtos.addAll(produtos);
        }
    }
}
