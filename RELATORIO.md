
---

# Relatório de Mudanças no Código

### Mudanças feitas no código:

1. **Remoção de Método**
    - Removi o método `particiona()` da classe `GeradorDeProdutos`.

2. **Criação de Interfaces e Implementações**
    - Criei a interface `particiona.particionaStrategy`.
    - Criei três implementações da interface `particionaStrategy`.

3. **Estratégia de Ordenação**
    - Criei a interface `ordena.ordenaStrategy`.
    - Criei quatro implementações da interface `ordenaStrategy` (ex: `ordenaInsertion...`).
    - Uma das implementações (`ordena.ordenaQuicksort`) se beneficia da `particiona.particionaStrategy`.

4. **Refatoração de Métodos**
    - Refatorei o método `carregaProdutos()` para ler um arquivo `.csv`, se disponível.

5. **Organização de Pacotes**
    - Criei pacotes para cada funcionalidade do programa e encapsulei suas classes e interfaces.
    - O código principal está agora no pacote `main`.

6. **Dependência em Interface**
    - O vetor de produtos agora depende da interface `Collection`.
    - A implementação escolhida foi `List`, utilizando `ArrayList`.

7. **Modificações no Método `carregaProdutos`**
    - Modifiquei o método `carregaProdutos` para também ler os campos `negrito`, `italico` e `cor`.

8. **Nova Classe `ProdutoAlternativo`**
    - Criei a classe `ProdutoAlternativo` que estende `ProdutoPadrao` e implementa `Produto` para possuir campos necessários de `negrito`, `italico` e `cor`.

9. **Novos Enum**
    - Adicionei dois enums: `ORDEM_CRESC` e `ORDEM_DECRESC` para definir a forma de exibição dos elementos.

10. **Campos Personalizáveis em `GeradorDeRelatorios`**
    - Modifiquei a classe `GeradorDeRelatorios` para possuir novos campos personalizáveis.

11. **Lista Temporária em `geraRelatorio`**
    - Adicionei uma lista temporária chamada `ListOfProdutos` em `geraRelatorio()` que será usada para exibição.

12. **Codificação UTF-8**
    - Adicionei uma tag HTML na saída para indicar que o texto está codificado em UTF-8.
    - Modifiquei a maneira como o arquivo é lido para interpretar a codificação UTF-8.

13. **Comportamento de `geraRelatorio`**
    - Especifiquei o comportamento de `geraRelatorio` para saber trabalhar com `ProdutoPadrao` e `ProdutoAlternativo`, dependendo da implementação no código.

14. **Correção de Strings**
    - Alterei as Strings referentes ao algoritmo de ordenação, pois estavam invertidas.

15. **Remoção de Valores Redundantes**
    - Removi alguns valores redundantes como `algoritmo` e `criterio` por estarem redundantes.

16. **Tratamento de Erros**
    - Adicionei tratativas de erros caso os argumentos fornecidos não estejam de acordo com os esperados.

17. **Melhoria no Método `debug`**
    - Modifiquei o método `debug()` para melhorar a visualização das mensagens.

18. **Função de Ajuda**
    - Criei uma função para fornecer ajuda sobre os argumentos do programa chamada `printHelp()`.
    - Modifiquei `printHelp()` para fornecer ajuda sobre as novas funcionalidades.

19. **Arquivo de Saída para Desenvolvedores**
    - Adicionei um arquivo de saída chamado `logs.txt` para verificar o diretório do programa e se a leitura está correta.

20. **Makefile**
    - Criei um Makefile para ajudar na compilação. Pode usar `make build` para gerar os arquivos `.class` ou `make jar` para gerar um arquivo `.jar`.

---

# Como executar o programa

- Na raiz do projeto, digite o comando `make build` ou `make jar` para compilar o programa
- Caso deseje executar pelo .jar, digite `java -jar GeradorDeRelatorios.jar ...`
- Caso deseje executar pelo .class, vá até o diretório `out/` e digite `java main.GeradorDeRelatorios ...`
- Por fim, caso queira excluir os arquivos compilados, volte para a raiz do projeto e digite `make clean`