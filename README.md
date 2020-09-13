# Processamento automático csv

Spring Boot Standalone Application responsável por utilizar "serviço da receita" (fake) para processamento automático de arquivo.
Após o processamento o sistema irá gerar um arquivo .csv com o resultado.

### Formato CSV de entrada:

| agencia      | conta | saldo | status |
| --------- | -----| -----| -----|
| 0101  | 1225-6 | 100,00 | A |
| 0101 | 12226-8 | 3200,50 | A |

### Formato CSV de saída:

| agencia      | conta | saldo | status | resultado |
| --------- | -----| -----| -----| -----|
| 0101  | 1225-6 | 100,00 | A | true |
| 0101 | 1226-8 | 3200,50 | A | false |

## Requisitos

Para realizar o build e executar a aplicaçao voce irá precisar de:

- [JDK 14](https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html)
- [Maven](https://maven.apache.org)

## Executando a aplicação
1. Clone o repositório e realize o package
<pre>	
$ git clone https://github.com/ErickHMT/processamento-automatico-csv.git
$ cd processamento-automatico-csv/
$ mvn package
</pre>

2. Execute a aplicação
<pre>	
$ cd target/
$ java -jar "exemplo".jar "nome-arquivo-entrada".csv
</pre>
