/* ============================================================
   Atividade 8 - Desenvolvimento de um sistema de apoio a gestao (NorteData)
   Autor: Diana Moreira dos Santos
   Data: Julho 2026
   Descricao: Programa em C que apoia tres decisoes do dia a dia
   da empresa: verificacao do nivel de stock, atribuicao de
   urgencia a encomendas e processamento de uma lista de
   devolucoes, ignorando registos invalidos.

   Nota: por requisito do enunciado, todas as mensagens impressas
   (printf) estao sem caracteres acentuados. Os comentarios do
   codigo, esses sim, mantêm a acentuação normal do português.
   ============================================================ */

#include <stdio.h>

int main(void) {

    /* ============================================================
       Tarefa 1 - Verificação do nível de stock (if, else if, else)
       ============================================================ */

    // Tarefa 1 - Verificacao de stock

    /* Conjunto de teste 1 (ativo): produz o estado "STOCK NORMAL"
       (15 > stockMinimo=10 e 15 < dobro de stockMinimo=20) */
    char nomeProduto[] = "Router Wireless AC1200";
    int quantidadeStock = 15;
    int stockMinimo = 10;

    /* Conjunto de teste 2: para testar, comenta as duas linhas
       acima (quantidadeStock e stockMinimo) e descomenta estas.
       Produz o estado "RUTURA DE STOCK". */
    // int quantidadeStock = 0;
    // int stockMinimo = 10;

    /* Conjunto de teste 3: produz o estado "STOCK CRITICO". */
    // int quantidadeStock = 8;
    // int stockMinimo = 10;

    printf("=== Verificacao de nivel de stock ===\n");
    printf("Produto: %s\n", nomeProduto);
    printf("Stock atual: %d | Stock minimo: %d\n", quantidadeStock, stockMinimo);

    if (quantidadeStock == 0) {
        printf("Estado: RUTURA DE STOCK\n");
    } else if (quantidadeStock <= stockMinimo) {
        printf("Estado: STOCK CRITICO - Reabastecer\n");
    } else if (quantidadeStock < 2 * stockMinimo) {
        printf("Estado: STOCK NORMAL\n");
    } else {
        printf("Estado: STOCK ELEVADO\n");
    }

    printf("\n");


    /* ============================================================
       Tarefa 2 - Estado de urgência de uma encomenda
       (operadores lógicos + switch)
       ============================================================ */

    // Tarefa 2 - Estado de urgencia da encomenda

    int diasParaEntrega = 5;
    int clientePrioritario = 1;   // 1 = sim, 0 = nao
    float valorEncomenda = 950.0;

    printf("=== Estado de urgencia da encomenda ===\n");
    printf("Dias para entrega: %d | Cliente prioritario: %d | Valor: %.2f EUR\n",
           diasParaEntrega, clientePrioritario, valorEncomenda);

    /* Urgente se: faltarem 2 dias ou menos para a entrega,
       OU (cliente prioritario E valor > 800.0 euros) */
    int encomendaUrgente = (diasParaEntrega <= 2) ||
                            (clientePrioritario && valorEncomenda > 800.0);

    if (encomendaUrgente) {
        printf("Encomenda URGENTE\n");
    } else {
        printf("Encomenda normal\n");
    }

    char codigoArmazem = 'B';

    printf("\n");
    printf("=== Armazem responsavel (codigo: %c) ===\n", codigoArmazem);

    switch (codigoArmazem) {
        case 'A':
            printf("Armazem Central - Porto\n");
            break;

        case 'B':
        case 'C':
            /* Case 'B' e 'C' agrupados: partilham o mesmo bloco,
               mas o nome do armazem ainda difere entre eles,
               por isso usamos outro switch pequeno so para o nome. */
            if (codigoArmazem == 'B') {
                printf("Armazem Regional - Braga\n");
            } else {
                printf("Armazem Regional - Coimbra\n");
            }
            printf("Zona Norte/Centro - Portes gratuitos\n");
            break;

        case 'D':
            printf("Armazem Secundario - Aveiro\n");
            break;

        default:
            printf("Codigo de armazem invalido\n");
            break;
    }

    printf("\n");


    /* ============================================================
       Tarefa 3 - Processamento de devoluções (for + continue)
       ============================================================ */

    // Tarefa 3 - Processamento de devolucoes

    float valoresDevolucoes[6] = {45.50, -10.0, 0.0, 120.0, -5.5, 89.90};
    float totalDevolucoes = 0;

    printf("=== Processamento de devolucoes ===\n");

    for (int i = 0; i < 6; i++) {

        if (valoresDevolucoes[i] <= 0) {
            printf("Registo %d ignorado (valor invalido: %.2f)\n", i, valoresDevolucoes[i]);
            continue;
        }

        totalDevolucoes += valoresDevolucoes[i];
        printf("Registo %d processado: %.2f EUR\n", i, valoresDevolucoes[i]);
    }

    printf("\nTotal de devolucoes processadas: %.2f EUR\n", totalDevolucoes);

    return 0;
}

/* ============================================================
   Tarefa 4 - Justificação escrita (síntese e reflexão)
   ============================================================

   1. Na Tarefa 1, porque é que optaste por if / else if e não por
      switch para determinar o estado do stock?

      RESPOSTA:
      O switch em C só compara uma variável com valores discretos
      e exatos (ex. um caráter ou um número específico), não com
      intervalos de valores. O estado do stock depende de
      comparações do tipo "maior que", "menor ou igual a" e
      "entre dois valores" (ex. superior a stockMinimo mas
      inferior ao dobro de stockMinimo), o que só é possível
      exprimir naturalmente com operadores relacionais dentro de
      if / else if. Usar switch obrigaria a testar cada valor
      inteiro possível um a um, o que não é praticável nem faz
      sentido para este problema.

   2. Na Tarefa 2, explica, com base nos valores que atribuíste às
      variáveis, porque é que a tua encomenda foi (ou não)
      considerada urgente, referindo explicitamente qual ou quais
      condições se verificaram?

      RESPOSTA:
      Com diasParaEntrega = 5, clientePrioritario = 1 e
      valorEncomenda = 950.0, a primeira condição
      (diasParaEntrega <= 2) é falsa, porque faltam 5 dias.
      No entanto, a segunda condição (clientePrioritario &&
      valorEncomenda > 800.0) é verdadeira, porque o cliente é
      prioritário (1) e o valor da encomenda (950.0) é superior a
      800.0 euros. Como a condição geral usa o operador ||, basta
      que uma das duas partes seja verdadeira para a encomenda
      ser marcada como urgente - e foi exatamente essa segunda
      condição que se verificou.

   3. Na Tarefa 3, o que aconteceria ao valor de totalDevolucoes
      se, em vez de continue, tivesses usado break na condição que
      deteta valores inválidos? Explica a diferença de
      comportamento.

      RESPOSTA:
      Com continue, o ciclo salta apenas a iteração atual (ignora
      o valor inválido) e continua a percorrer as posições
      seguintes do array, pelo que todos os valores válidos, mesmo
      os que vêm depois de um valor inválido, acabam por ser
      somados a totalDevolucoes. Com break, o ciclo seria
      interrompido por completo logo na primeira posição inválida
      encontrada (índice 1, valor -10.0), pelo que nenhuma das
      posições seguintes (índices 2 a 5) chegaria a ser processada
      - mesmo contendo valores válidos como 120.0 e 89.90. Nesse
      caso, totalDevolucoes ficaria apenas com o valor da posição
      0 (45.50), em vez do total correto de 255.40.
   ============================================================ */