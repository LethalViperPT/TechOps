/* ============================================================
   Atividade 7 - Módulo de controlo de vendas (TechNova)
   Autor: Diana Moreira dos Santos
   Data: Julho 2026
   Descrição: Preenche uma matriz 4x5 (lojas x dias) com valores
   de teste, apresenta os dados em formato de tabela e calcula
   o total de vendas da semana para toda a rede de lojas.
   ============================================================ */

#include <stdio.h>

/* ============================================================
   TAREFA 1 - Preparar a estrutura do programa
   ============================================================ */

#define LOJAS 4
#define DIAS  5

int main(void) {

    int vendas[LOJAS][DIAS];


    /* ============================================================
       TAREFA 2 - Preencher a matriz com ciclos aninhados
       O ciclo exterior (l) percorre as lojas, o interior (d)
       percorre os dias.
       ============================================================ */

    for (int l = 0; l < LOJAS; l++) {
        for (int d = 0; d < DIAS; d++) {
            vendas[l][d] = (l + 1) * 100 + d * 20;
        }
    }


    /* ============================================================
       TAREFA 3 - Apresentar a matriz em formato de tabela
       ============================================================ */

    printf("=== Vendas da semana (euros) ===\n\n");

    for (int l = 0; l < LOJAS; l++) {

        /* Cabecalho da loja: dentro do ciclo exterior, fora do
           ciclo interior - imprime-se uma vez por loja. */

        printf("Loja %d:", l + 1);

        for (int d = 0; d < DIAS; d++) {
            printf("%5d", vendas[l][d]);
        }

        /* Mudanca de linha: também dentro do ciclo exterior, fora
           do interior - garante uma linha nova por loja, apenas
           depois de terem sido impressos todos os dias dessa loja. */

        printf("\n");
    }

    printf("\n");


    /* ============================================================
       TAREFA 4 - Calcular o total de vendas da semana
       ============================================================ */

    int total = 0;   /* declarada e inicializada antes dos ciclos */

    for (int l = 0; l < LOJAS; l++) {
        for (int d = 0; d < DIAS; d++) {
            total += vendas[l][d];
        }
    }

    printf("Total de vendas da semana: %d euros\n", total);

    return 0;
}

/* ============================================================
   QUESTÕES DE VERIFICAÇÃO
   ============================================================

   1. Quantas vezes e executado o bloco mais interno dos ciclos
      que preenchem a matriz? Justifica com o cálculo.

      RESPOSTA:
      O ciclo exterior corre LOJAS vezes (4) e, para cada uma
      dessas voltas, o ciclo interior corre DIAS vezes (5).
      O bloco mais interno (vendas[l][d] = ...) executa portanto
      LOJAS x DIAS = 4 x 5 = 20 vezes - uma vez por cada posicao
      da matriz.

   2. O que aconteceria à formatação da tabela se colocasses o
      printf("\n") da Tarefa 3 dentro do ciclo interior, em vez
      de fora?

      RESPOSTA:
      O programa mudaria de linha depois de cada valor impresso,
      em vez de mudar apenas no final de cada loja. Em vez de
      aparecerem os 5 valores de uma loja lado a lado numa única
      linha, cada valor de vendas[l][d] ficaria na sua própria
      linha, e o cabecalho "Loja X:" apareceria seguido de 5
      linhas separadas com um número cada, em vez de uma tabela
      organizada por linhas (lojas) e colunas (dias).

   3. Porque é que a variável total tem de ser declarada e
      inicializada antes dos ciclos que a incrementam, e não
      dentro do ciclo interior?

      RESPOSTA:
      Se "total" fosse declarada e inicializada a 0 dentro do
      ciclo interior, ela seria reposta a 0 em cada iteração
      desse ciclo, perdendo-se o valor acumulado das posições
      anteriores. No final só restaria a soma do último valor
      lido (vendas[LOJAS-1][DIAS-1]), em vez do total de todas
      as posições da matriz. Ao declarar "total" uma unica vez,
      antes dos ciclos, garante-se que o valor e mantido e
      incrementado (+=) ao longo de todas as 20 iterações,
      acumulando corretamente a soma de toda a matriz.
   ============================================================ */