PASSOS PARA EXECUÇÃO DO PROGRAMA

O processo para executar o programa se divide em duas partes:

1. COMPILAÇÃO
2. EXECUÇÃO DO PROGRAMA


1. COMPILAÇÃO:
    
    No ambiente LINUX abra o terminal e vá para a raiz do projeto.

    Na raíz do projeto rode o seguinte comando:

    javac src/so/engcomputacao/ufal/Tarefa.java src/so/engcomputacao/ufal/SistemaOperacional.java src/so/engcomputacao/ufal/Estado.java src/so/engcomputacao/ufal/Principal.java 

    Note que a classe Principal.java deverá ser a última na linha do comando, as classes anteriores podem ser inseridas na ordem desejada. Você também pode copiar o comando acima, se preferir.

    Se nenhum error ocorrer, os arquivos já deverão estão compilados e prontos para a execução.


2. EXECUÇÃO DO PROGRAMA

    Considerado a partir da raiz do projeto, entre na pasta src/

    Para facilitar a passagem do arquivo de entrada no parâmetro do terminal, salve-o dentro da pasta src, feito isso rode o comando abaixo:

    java so.engcomputacao.ufal.Principal input.txt fcfs (para executar o FCFS)

    java so.engcomputacao.ufal.Principal input.txt rr (para executar o Round-Robin)


    IMPORTANTE: Os comandos acima devem ser executados dentro da pasta src/


    O arquivo de saída (output.txt) será salvo na pasta src/, o mesmo local onde se encontra o arquivo de entrada input.txt
