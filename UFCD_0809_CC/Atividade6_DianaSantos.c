/* ============================================================
   Atividade 6 - Sistema de registo de produção (DataSoft)
   Autor: [o teu nome]
   Descrição: Programa em C que utiliza as três estruturas
   repetitivas (for, while, do while), cada uma na situação
   mais adequada, para apoiar a linha de produção.
   ============================================================ */

#include <stdio.h>

#define NUM_ESTACOES 6

int main(void) {

    /* ============================================================
       TAREFA 1 - Verificação de estações de trabalho
       Usa-se um FOR porque o número de repetições é conhecido
       à partida (as 6 estações são sempre as mesmas).
       ============================================================ */

    printf("--- Verificacao de estacoes de trabalho ---\n");

    for (int estacao = 1; estacao <= NUM_ESTACOES; estacao++) {
        printf("Estacao %d: Verificada\n", estacao);
    }

    printf("Verificacao concluida: %d estacoes operacionais\n\n", NUM_ESTACOES);


    /* ============================================================
       TAREFA 2 - Registo de peças rejeitadas com valor de paragem
       Usa-se um WHILE porque não se sabe à partida quantos lotes
       vão ser produzidos - o ciclo depende de uma condição (o
       valor sentinela -1) e pode nem sequer processar nenhum
       lote (embora aqui pedimos sempre pelo menos um valor antes).
       ============================================================ */

    int pecas_rejeitadas;
    int total_rejeitadas = 0;

    printf("--- Registo de pecas rejeitadas ---\n");
    printf("Introduza o numero de pecas rejeitadas (-1 para terminar): ");
    scanf("%d", &pecas_rejeitadas);

    while (pecas_rejeitadas != -1) {
        total_rejeitadas += pecas_rejeitadas;
        printf("Introduza o numero de pecas rejeitadas (-1 para terminar): ");
        scanf("%d", &pecas_rejeitadas);
    }

    printf("Total de pecas rejeitadas no turno: %d\n\n", total_rejeitadas);


    /* ============================================================
       TAREFA 3 - Menu de encerramento de turno
       Usa-se um DO WHILE porque o menu tem de ser apresentado
       pelo menos uma vez, mesmo antes de sabermos qual vai ser
       a opção escolhida pelo supervisor. Um while normal só
       testaria a condição no início, o que obrigaria a ler a
       opção antes de entrar no ciclo, ficando o código mais
       complexo e menos natural para este caso.
       ============================================================ */

    int opcao;

    do {
        printf("--- Menu de encerramento de turno ---\n");
        printf("1 - Ver estacoes verificadas\n");
        printf("2 - Ver total de pecas rejeitadas\n");
        printf("0 - Sair\n");
        printf("Escolha uma opcao: ");
        scanf("%d", &opcao);

        if (opcao == 1) {
            printf("Estacoes verificadas: %d\n\n", NUM_ESTACOES);
        } else if (opcao == 2) {
            printf("Total de pecas rejeitadas: %d\n\n", total_rejeitadas);
        } else if (opcao == 0) {
            printf("A encerrar turno\n");
        } else {
            printf("Opcao invalida\n\n");
        }

    } while (opcao != 0);

    return 0;
}

/* ============================================================
   QUESTÃO FINAL DE REFLEXÃO

   Explica qual seria o problema se a Tarefa 3 fosse implementada
   com um while em vez de um do while. Em que situação(ões) o
   comportamento do programa seria diferente?

   RESPOSTA:
   Um ciclo while testa a condição ANTES de executar o corpo do
   ciclo pela primeira vez. No caso do menu, isso significa que
   seria necessário ler o valor de "opcao" antes de entrar no
   ciclo, só para conseguir avaliar a condição "opcao != 0" logo
   à partida. Isto obrigaria a duplicar código, a leitura da opção
   teria de aparecer uma vez antes do while e outra vez dentro
   dele, ou a inventar um valor inicial artificial para "opcao"
   apenas para forçar a primeira entrada no ciclo.

   Além disso, do ponto de vista do comportamento, se a variável
   "opcao" não fosse inicializada corretamente antes do while,
   o programa poderia nunca chegar a mostrar o menu, o que
   viola o requisito de que o menu tem de aparecer pelo menos
   uma vez, independentemente da opção escolhida.

   Com o do while, o corpo do ciclo (mostrar o menu e ler a opção)
   executa sempre primeiro, e só depois a condição é testada para
   decidir se o ciclo se repete. Isto garante, de forma natural e
   sem código duplicado, que o menu aparece sempre pelo menos uma
   vez.
   ============================================================ */