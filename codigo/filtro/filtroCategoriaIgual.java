package filtro;

import main.Produto;

public class filtroCategoriaIgual implements filtroStrategy{
    private final String categoria;
    public filtroCategoriaIgual(String categoria) {
        this.categoria = categoria;
    }
    @Override
    public boolean filtra(Produto produto) {
        return produto.getCategoria().equalsIgnoreCase(categoria);
    }
}
