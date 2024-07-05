package main;

import filtro.*;
import formata.*;
import particiona.*;
import ordena.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class GeradorDeRelatorios {

	public static final String ORDEM_DECRESC = "decresc";
	public static final String ORDEM_CRESC = "cresc";

	public static final String ALG_INSERTIONSORT = "insertion";
	public static final String ALG_QUICKSORT = "quick";

	public static final String CRIT_DESC_CRESC = "descricao_c";
	public static final String CRIT_PRECO_CRESC = "preco_c";
	public static final String CRIT_ESTOQUE_CRESC = "estoque_c";
	
	public static final String FILTRO_TODOS = "todos";
	public static final String FILTRO_ESTOQUE_MENOR_OU_IQUAL_A = "estoque_menor_igual";
	public static final String FILTRO_CATEGORIA_IGUAL_A = "categoria_igual";

	// operador bit a bit "ou" pode ser usado para combinar mais de  
	// um estilo de formatacao simultaneamente (veja como no main)
	public static final int FORMATO_PADRAO  = 0b0000;
	public static final int FORMATO_NEGRITO = 0b0001;
	public static final int FORMATO_ITALICO = 0b0010;

	private Collection<Produto> produtos;

	// Novo!
	private String ordem;
	private String preco_inicial;
	private String preco_final;
	private String keyword;

	private String filtro;
	private String argFiltro;
	private int format_flags;

	// Define quais algoritmos devo usar
	private final ordenaStrategy ordenaStrategy;
	private final filtroStrategy filtroStrategy;

	public GeradorDeRelatorios(Collection<Produto> produtos,
                               String ordem,
                               String preco_inicial,
                               String preco_final,
                               String keyword,
                               String argFiltro,
                               int format_flags,
                               ordenaStrategy ordenaStrategy,
                               filtroStrategy filtroStrategy) {

		this.produtos = produtos;
		this.ordem = ordem;
		this.preco_inicial = preco_inicial;
		this.preco_final = preco_final;
		this.keyword = keyword;
		this.format_flags = format_flags;
		this.argFiltro = argFiltro;
		this.ordenaStrategy = ordenaStrategy;
		this.filtroStrategy = filtroStrategy;
	}
	
	public void debug(){
		System.out.println("Gerando relatório para array contendo " + produtos.size() + " produto(s)");
		System.out.println("parametro filtro = " + (argFiltro.isEmpty() ? "Sem parâmetros para filtrar" : argFiltro));
		System.out.println("substring: " + (keyword.isEmpty() ? "Nenhuma palavra chave" : keyword));
	}

	public void geraRelatorio(String arquivoSaida)  {

		debug();

		ordenaStrategy.ordena(0, (produtos.size() - 1), produtos);

		try (PrintWriter out = new PrintWriter(arquivoSaida)) {
			out.println("<!DOCTYPE html><html>");
			out.println("<meta charset='utf-8'>");
			out.println("<head><title>Relatorio de produtos</title></head>");
			out.println("<body>");
			out.println("Relatorio de Produtos:");
			out.println("<ul>");

			int count = 0;

			List<Produto> ListOfProdutos = new ArrayList<>(produtos);

			if (ordem.equals(ORDEM_DECRESC)) {
				Collections.reverse(ListOfProdutos);
			}

			for (Produto p: ListOfProdutos) {
				// Verifica se o produto está fora do intervalo fornecido
				if (p.getPreco() <= Double.parseDouble(preco_inicial) || p.getPreco() >= Double.parseDouble(preco_final)) {
					continue;
				}
				// Verifica se possui uma palavra chave e depois verifica se a descrição não possui a palavra chave
				if (!keyword.isEmpty() && !(p.getDescricao().toLowerCase().contains(keyword.toLowerCase()))) {
					continue;
				}
				// Verifica se o tipo de produto é 'ProdutoAlternativo'
				if (p instanceof ProdutoAlternativo) {
					if (filtroStrategy.filtra(p)) {
						formataTemplate formatador = Produto::formataParaImpressao;
						if (((ProdutoAlternativo) p).getNegrito() && (format_flags % 2 != 0)) {
							formatador = new formataNegrito(formatador);
						}
						if (((ProdutoAlternativo) p).getItalico() && (format_flags > 1)) {
							formatador = new formataItalico(formatador);
						}
						if (((ProdutoAlternativo) p).getCor() != null && !((ProdutoAlternativo) p).getCor().isEmpty()) {
							formatador = new formataCor(formatador);
						}
						out.print("<li>");
						out.print(formatador.formata(p));
						out.println("</li>");
						count++;
					}
					// Verifica se o tipo de produto é o 'ProdutoPadrao'
				} else if (p instanceof ProdutoPadrao) {
					if (filtroStrategy.filtra(p)) {
						formataTemplate formatador = Produto::formataParaImpressao;
						if (format_flags % 2 != 0) {
							formatador = new formataNegrito(formatador);
						}
						if (format_flags > 1) {
							formatador = new formataItalico(formatador);
						}
						out.print("<li>");
						out.print(formatador.formata(p));
						out.println("</li>");
						count++;
					}
				}
			}

			out.println("</ul>");
			out.println(count + " produtos listados, de um total de " + produtos.size() + ".");
			out.println("</body>");
			out.println("</html>");
		} catch (IOException e) {
			System.err.println("Erro -> Entrada/Saída incorretas em geraRelatorio(): " + e);
		}
	}

	public static ArrayList<Produto> carregaProdutos() {
		ArrayList<Produto> produtos = new ArrayList<>();
		try (BufferedWriter bew = new BufferedWriter(new FileWriter("logs.txt"))) {
			bew.write("Atualmente no caminho: " + System.getProperty("user.dir") + "\n");
			try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("./produtos.csv"), StandardCharsets.UTF_8))) {
				String linha;
				br.readLine(); // Primeira linha não deve ser contabilizada
				while ((linha = br.readLine()) != null) {
					String[] campos = linha.split(", ");
					String id = campos[0];
					String descricao = campos[1];
					String categoria = campos[2];
					String qtdEstoque = campos[3];
					String preco = campos[4];
					String negrito = campos[5];
					String italico = campos[6];
					String cor = campos[7];
					bew.write("Adicionando produtos: " +
							id + " " +
							descricao  + " " +
							categoria + " " +
							qtdEstoque + " " +
							preco + " " +
							negrito + " " +
							italico + " " +
							cor + "\n");
					produtos.add(new ProdutoAlternativo( // informa que o produto que será adicionado é do tipo 'ProdutoAlternativo'
							Integer.parseInt(id),
							descricao,
							categoria,
							Integer.parseInt(qtdEstoque),
							Double.parseDouble(preco),
							negrito,
							italico,
							cor
					));
				}
			} catch (IOException e) {
				System.err.println("Erro -> Entrada/Saída incorretas em carregaProdutos(): " + e);
			}
		} catch (IOException e) {
			System.err.println("Erro -> Entrada/Saída incorretas para gerar logs.txt: " + e);
		}
        return produtos;
	}

	static void printHelp() {
		System.out.println("Uso:");
		// Exemplo: decresc 0 1000 "carregador" quick preco_c todos "" negrito
		System.out.println("\tjava " + GeradorDeRelatorios.class.getName() + " <ordem> <preço inicial> <preço final> '?substring' <algoritmo> <critério de ordenação> <critério de filtragem> '?parâmetro de filtragem' ?negrito ?italico");
		System.out.println("Onde:");
		System.out.println("\tordem: 'cresc' ou 'decresc'");
		System.out.println("\tpreço inicial e preço final: valores com ponto flutuante");
		System.out.println("\t?substring: palavra-chave para descrição");
		System.out.println("\talgoritmo: 'quick' ou 'insertion'");
		System.out.println("\tcriterio de ordenação: 'preco_c' ou 'descricao_c' ou 'estoque_c'");
		System.out.println("\tcriterio de filtragem: 'todos' ou 'estoque_menor_igual' ou 'categoria_igual'");
		System.out.println("\tparâmetro de filtragem: argumentos adicionais necessários para a filtragem, como limite e categoria, respectivamente");
		System.out.println("\tcaso a opção seja 'todos', deixe em branco");
		System.out.println("\topções de formatação: 'negrito' e/ou 'italico'");
		System.out.println("\tnote que possui '?' indica que são campos opcionais. Se possuirem aspas simples, significa que devem obrigatoriamente possuir aspas duplas como argumento");
		System.out.println("\tpor exemplo: decresc 0 1000 \"carregador\" quick preco_c todos \"\" negrito");
		System.out.println();
		System.exit(1);
	}

	public static void main(String [] args) {

		// Verifica se foram passados, pelo menos, 8 argumentos
		if(args.length < 8){
			printHelp();
		}

		// Novo!
		String opcao_exibe = args[0];
		String preco_inicial = args[1];
		String preco_final = args[2];
		String palavra_filtro = args[3];

		// Obtem o valor de cada string
		String opcao_algoritmo = args[4];
		String opcao_criterio_ord = args[5];
		String opcao_criterio_filtro = args[6];
		String opcao_parametro_filtro = args[7];

		String [] opcoes_formatacao = new String[2];

		opcoes_formatacao[0] = args.length > 8 ? args[8] : null;
		opcoes_formatacao[1] = args.length > 9 ? args[9] : null;

		int formato = FORMATO_PADRAO;

        for (String op : opcoes_formatacao) {
            formato |= (op != null ? op.equals("negrito") ? FORMATO_NEGRITO : (op.equals("italico") ? FORMATO_ITALICO : 0) : 0);
        }

		if (!(opcao_exibe.equals(ORDEM_DECRESC)) && !(opcao_exibe.equals(ORDEM_CRESC))) {
			System.err.println("Argumento incorreto! [opcao_exibe]");
			System.exit(-1);
		}

		if (Double.parseDouble(preco_inicial) > Double.parseDouble(preco_final)) {
			System.err.println("Preço inicial > Preço final!");
			System.exit(-1);
		}

		// Escolhe o algoritmo de ordenação
		ordenaStrategy ordenaStrategy = null;
		switch (opcao_algoritmo) {
			case ALG_QUICKSORT:
				// Escolhe o algortimo para particionar pro quick
				particionaStrategy particionaStrategy = null;
				switch(opcao_criterio_ord) {
					case CRIT_DESC_CRESC:
						particionaStrategy = new particionaPorDesc();
						break;
					case CRIT_PRECO_CRESC:
						particionaStrategy = new particionaPorPreco();
						break;
					case CRIT_ESTOQUE_CRESC:
						particionaStrategy = new particionaPorEstoque();
						break;
					default:
						System.err.println("Argumento incorreto! [opcao_criterio_ord]");
						System.exit(-1);
						break;
				}
				ordenaStrategy = new ordenaQuicksort(particionaStrategy);
				break;
			case ALG_INSERTIONSORT:
				switch (opcao_criterio_ord) {
					case CRIT_DESC_CRESC:
						ordenaStrategy = new ordenaInsertionPorDesc();
						break;
					case CRIT_PRECO_CRESC:
						ordenaStrategy = new ordernaInsertiorPorPreco();
						break;
					case CRIT_ESTOQUE_CRESC:
						ordenaStrategy = new ordenaInsertionPorEstoque();
						break;
					default:
						System.err.println("Argumento incorreto! [opcao_criterio_ord]");
						System.exit(-1);
						break;
				}
				break;
			default:
				System.err.println("Argumento incorreto! [opcao_algoritmo]");
				System.exit(-1);
				break;
		}

		// Escolhe o formato de filtragem
		filtroStrategy filtroStrategy = null;
		switch (opcao_criterio_filtro) {
			case FILTRO_TODOS:
				filtroStrategy = new filtroTodos();
				break;
			case FILTRO_ESTOQUE_MENOR_OU_IQUAL_A:
				filtroStrategy = new filtroEstoqueMenorOuIgual(Integer.parseInt(opcao_parametro_filtro)); // tenho que passar o argFiltro como int
				break;
			case FILTRO_CATEGORIA_IGUAL_A:
				filtroStrategy = new filtroCategoriaIgual(opcao_parametro_filtro); // tenho que passar o argFiltro
				break;
			default:
				System.err.println("Argumento incorreto! [opcao_criterio_filtro]");
				System.exit(-1);
				break;
		}
		
		GeradorDeRelatorios gdr = new GeradorDeRelatorios(
				carregaProdutos(),
				opcao_exibe,
				preco_inicial,
				preco_final,
				palavra_filtro,
				opcao_parametro_filtro,
				formato,
				ordenaStrategy,
				filtroStrategy
		);
        gdr.geraRelatorio("saida.html");
    }
}
