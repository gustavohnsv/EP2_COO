# Makefile para compilar e executar o projeto

# Diretórios com os arquivos fonte
SRC_DIRS := codigo/main codigo/filtro codigo/formata codigo/ordena codigo/particiona

# Todos os arquivos .java nos diretórios listados
SRC_FILES := $(foreach dir, $(SRC_DIRS), $(wildcard $(dir)/*.java))

# Diretório de saída para os arquivos .class
OUT_DIR := out

# Nome do arquivo .jar gerado
JAR_FILE := GeradorDeRelatorios.jar

# Tarefa padrão: compilar o projeto
all: build

# Compilar os arquivos .java
build:
	@mkdir -p $(OUT_DIR)
	javac -d $(OUT_DIR) $(SRC_FILES)

# Executar o programa principal como class (exemplo)
run-example-build:
	java -cp $(OUT_DIR) main.GeradorDeRelatorios decresc 0 1000 "" quick estoque_c todos "" negrito

# Executar o programa principal como jar (exemplo)
run-example-jar:
	java -jar $(JAR_FILE) cresc 0 1000 "A" quick estoque_c estoque_menor_igual "10" italico

# Limpar arquivos compilados
java-clean:
	rm -rf $(OUT_DIR)

# Limpar o arquivo 'logs.txt'
log-clean:
	rm -f logs.txt

# Limpar arquivo.jar
jar-clean:
	rm -f *.jar

# Limpar 'saida.html'
html-clean:
	rm -f *.html

# Limpar todos os arquivos gerados após execução
clean: java-clean log-clean jar-clean html-clean

# Tarefa para gerar o arquivo .jar
jar: build
	jar cfe $(JAR_FILE) main.GeradorDeRelatorios -C $(OUT_DIR) .

# Tarefa para limpar e reconstruir o projeto
rebuild: clean build

# Exibir a mensagem de ajuda no terminal
help: jar
	java -jar $(JAR_FILE)
