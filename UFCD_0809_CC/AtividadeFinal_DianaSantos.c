/* ============================================================
   Atividade de consolidacao - Sistema de gestao de stocks (LogisFacil)
   Autor: Diana Moreira dos Santos
   Data: Julho de 2026
   Descricao: Aplicacao de consola em C que regista o stock de
   varios produtos em varios armazens, valida e reage a situacoes
   criticas, calcula estatisticas agregadas, aplica uma funcao
   recursiva a um problema de encomendas, e apresenta tudo atraves
   de um menu interativo.
   ============================================================ */

#include <stdio.h>

/* ============================================================
   Parte A - Configuracao inicial
   ============================================================ */

#define NUM_ARMAZENS 3
#define NUM_PRODUTOS 4
#define STOCK_CRITICO 10
#define STOCK_MAXIMO 500


/* ============================================================
   Parte D - Funcao de analise com matriz como argumento

   Recebe a matriz de stock e as matrizes de strings dos armazens
   e produtos, percorre tudo com ciclos aninhados e devolve o
   numero total de alertas criticos encontrados.
   ============================================================ */

int analisarStock(int stock[][NUM_PRODUTOS], char armazens[][20], char produtos[][25],
                   int numArmazens, int numProdutos) {

    int alertas = 0;

    for (int a = 0; a < numArmazens; a++) {
        for (int p = 0; p < numProdutos; p++) {

            int valor = stock[a][p];

            /* Critico: abaixo de STOCK_CRITICO, mas diferente de 0
               (o caso 0 ja foi tratado na Parte C, no preenchimento). */
            if (valor < STOCK_CRITICO && valor != 0) {
                printf("ALERTA: Stock critico de %s no armazem %s (%d unidades).\n",
                       produtos[p], armazens[a], valor);
                alertas += 1;
            }
        }
    }

    return alertas;
}


/* ============================================================
   Parte F - Funcao recursiva aplicada a um problema real

   Caso base: se o stock atual ja atinge o minimo, nao sao
   precisas mais encomendas (retorna 0).
   Caso recursivo: soma 25 unidades (uma encomenda) e chama-se
   a si propria, contando essa encomenda (+1).
   ============================================================ */

int calcularEncomendas(int stockAtual, int minimoDesejado) {

    if (stockAtual >= minimoDesejado) {
        return 0;
    }

    return 1 + calcularEncomendas(stockAtual + 25, minimoDesejado);
}


int main(void) {

    /* ============================================================
       Parte B - Estruturas de dados (matrizes + strings)
       ============================================================ */

    char armazens[NUM_ARMAZENS][20] = {"Porto", "Lisboa", "Faro"};
    char produtos[NUM_PRODUTOS][25] = {"Paletes", "Caixas", "Contentores", "Paletes refrigeradas"};
    int stock[NUM_ARMAZENS][NUM_PRODUTOS];


    /* ============================================================
       Parte C - Preenchimento com validacao (ciclos aninhados +
       condicoes de controlo)
       ============================================================ */

    printf("=== Registo de stock - LogisFacil ===\n\n");

    for (int a = 0; a < NUM_ARMAZENS; a++) {
        for (int p = 0; p < NUM_PRODUTOS; p++) {

            int valor;
            printf("Stock de %s no armazem %s: ", produtos[p], armazens[a]);
            scanf("%d", &valor);

            /* Validacao com while: nao avanca enquanto o valor for negativo */
            while (valor < 0) {
                printf("Valor invalido (nao pode ser negativo). Introduza novamente: ");
                scanf("%d", &valor);
            }

            stock[a][p] = valor;

            if (valor == 0) {
                printf("Produto sem stock neste armazem.\n");
                continue;   /* avanca de imediato, sem verificar stock excessivo */
            }

            if (valor > STOCK_MAXIMO) {
                printf("Stock excessivo - Reveja a encomenda.\n");
            }
        }
    }


    /* ============================================================
       Parte D (continuacao) - Chamada da analise inicial
       ============================================================ */

    printf("\n=== Analise inicial de stock critico ===\n");

    int alertas = analisarStock(stock, armazens, produtos, NUM_ARMAZENS, NUM_PRODUTOS);

    if (alertas == 0) {
        printf("Situacao de stock estavel em todos os armazens.\n");
    } else if (alertas <= 3) {
        printf("Atencao: Alguns produtos precisam de reposicao.\n");
    } else {
        printf("URGENTE: Reunir com a gestao de armazens.\n");
    }


    /* ============================================================
       Parte E - Menu interativo com switch e do while
       ============================================================ */

    int opcao;

    do {
        printf("\n======== MENU LOGISFACIL ========\n");
        printf("1. Consultar o stock de um armazem especifico\n");
        printf("2. Calcular o total de unidades em todos os armazens\n");
        printf("3. Calcular o numero de encomendas necessarias (funcao recursiva)\n");
        printf("4. Sair\n");
        printf("=================================\n");
        printf("Opcao: ");
        scanf("%d", &opcao);

        switch (opcao) {

            case 1: {
                int armazem;
                printf("Numero do armazem (1 a %d): ", NUM_ARMAZENS);
                scanf("%d", &armazem);

                if (armazem < 1 || armazem > NUM_ARMAZENS) {
                    printf("Numero de armazem invalido.\n");
                } else {
                    printf("\n--- Stock do armazem %s ---\n", armazens[armazem - 1]);
                    for (int p = 0; p < NUM_PRODUTOS; p++) {
                        printf("%s: %d unidades\n", produtos[p], stock[armazem - 1][p]);
                    }
                }
                break;
            }

            case 2: {
                int total = 0;
                for (int a = 0; a < NUM_ARMAZENS; a++) {
                    for (int p = 0; p < NUM_PRODUTOS; p++) {
                        total += stock[a][p];
                    }
                }
                printf("Total de unidades em todos os armazens: %d\n", total);
                break;
            }

            case 3: {
                int stockAtual, minimoDesejado;
                printf("Stock atual do produto: ");
                scanf("%d", &stockAtual);
                printf("Quantidade minima pretendida: ");
                scanf("%d", &minimoDesejado);

                int encomendas = calcularEncomendas(stockAtual, minimoDesejado);
                printf("Sao necessarias %d encomendas para atingir o minimo pretendido.\n", encomendas);
                break;
            }

            case 4:
                printf("Ate a proxima! Obrigado por usar o LogisFacil.\n");
                break;   /* sai do switch; a condicao do while termina o ciclo */

            default:
                printf("Opcao invalida. Tente novamente.\n");
                break;
        }

    } while (opcao != 4);

    return 0;
}

/* ============================================================
   Parte G - Encerramento (respostas aos testes)
   ============================================================

   Testes realizados com o seguinte conjunto de dados de entrada
   (por armazem: Porto, Lisboa, Faro; por produto: Paletes,
   Caixas, Contentores, Paletes refrigeradas):

   Porto:  -5 (invalido) -> 7 | 0 | 600 | 50
   Lisboa: 20 | 5 | 15 | 8
   Faro:   100 | 300 | 0 | 9

   1. A validacao de valores negativos (Parte C) funciona
      corretamente com while?

      SIM. Ao introduzir -5 para "Paletes no armazem Porto", o
      programa nao avancou: reapresentou o pedido de valor ("Valor
      invalido (nao pode ser negativo). Introduza novamente:") e
      so aceitou a posicao quando foi introduzido o valor 7,
      valido (>= 0).

   2. O continue (stock = 0) e os avisos de stock excessivo
      funcionam, sem interferir um com o outro?

      SIM. Os dois casos de stock = 0 (Caixas no Porto e
      Contentores em Faro) mostraram a mensagem "Produto sem
      stock neste armazem." e avancaram de imediato para a
      posicao seguinte, sem chegar a verificar o stock excessivo
      (o que nem faria sentido, ja que 0 nunca e > STOCK_MAXIMO).
      Por outro lado, o valor 600 em Contentores no Porto (que
      nao e 0) mostrou corretamente o aviso "Stock excessivo -
      Reveja a encomenda.", sem acionar o continue, confirmando
      que os dois mecanismos atuam de forma independente.

   3. A funcao analisarStock deteta corretamente todos os casos
      criticos?

      SIM. Com os dados de teste, os unicos valores estritamente
      abaixo de STOCK_CRITICO (10) e diferentes de 0 eram:
      Paletes no Porto (7), Caixas em Lisboa (5), Paletes
      refrigeradas em Lisboa (8) e Paletes refrigeradas em Faro
      (9). A funcao gerou exatamente 4 alertas, um para cada um
      destes casos, com o nome correto do armazem e do produto em
      cada mensagem. Como 4 > 3, o main() apresentou corretamente
      a mensagem "URGENTE: Reunir com a gestao de armazens."

   4. O menu nao termina inesperadamente e volta sempre a aparecer
      apos cada opcao (exceto a opcao Sair)?

      SIM. Apos as opcoes 1 (incluindo um numero de armazem
      invalido, fora do intervalo 1-3), 2 e 3, o menu voltou
      sempre a ser apresentado por completo, permitindo continuar
      a usar o programa. So a opcao 4 terminou o ciclo do while
      (com a mensagem de despedida), encerrando o programa como
      esperado.

   5. A funcao recursiva calcularEncomendas produz resultados
      matematicamente corretos para pelo menos 3 casos de teste
      diferentes?

      SIM, testados 3 casos:
      - stock atual = 0, minimo = 100
        0 -> 25 -> 50 -> 75 -> 100 (atinge o minimo)
        Sao precisas 4 encomendas. O programa devolveu 4. Correto.
      - stock atual = 10, minimo = 10
        10 ja e >= 10 (caso base imediato).
        Sao precisas 0 encomendas. O programa devolveu 0. Correto.
      - stock atual = 12, minimo = 50
        12 -> 37 (ainda < 50) -> 62 (atinge o minimo)
        Sao precisas 2 encomendas. O programa devolveu 2. Correto.

   NOTA ADICIONAL (bug encontrado e corrigido durante os testes):
   A matriz de strings dos produtos foi inicialmente declarada
   como char produtos[NUM_PRODUTOS][20]. A string "Paletes
   refrigeradas" tem exatamente 20 caracteres, o que nao deixava
   espaco para o caracter nulo '\0' obrigatorio no fim de toda a
   string em C. Isto causou um buffer overflow (escrita fora dos
   limites do array), que corrompeu memoria adjacente e produziu
   caracteres invalidos na saida do programa. A correcao foi
   aumentar o tamanho do array para [25], garantindo espaco extra
   para o terminador. Esta situacao e um exemplo classico de erro
   em C que nao gera aviso do compilador (mesmo com -Wall
   -Wextra), porque a linguagem permite, tecnicamente, inicializar
   um array de char com uma string do mesmo tamanho do array -
   apenas nao sobra espaco para o '\0'. Fica como boa pratica
   dimensionar sempre os arrays de char com alguma margem acima
   do comprimento exato das strings que vao guardar.
   ============================================================ */