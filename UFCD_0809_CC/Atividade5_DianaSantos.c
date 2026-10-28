/* ============================================================
   Atividade 5 - Relatório de desempenho (DataSoft)
   Autor: Diana Moreira dos Santos
   Data: Julho de 2026
   Descrição: Programa em C que aplica operadores aritméticos,
   de atribuição, cast e relacionais sobre dados de desempenho
   de um servidor.
   ============================================================ */

#include <stdio.h>

/* ---------- Constantes globais ---------- */
#define NUM_DIAS 4
#define OBJETIVO_DIARIO 150.0
#define NUM_SERVIDORES 3

int main(void) {

    printf("=== Relatório de desempenho - DataSoft ===\n\n");

    /* ============================================================
       TAREFA 1 - Declaração e cálculo do total de tarefas
       ============================================================ */

    /* 1. Uma variável int para cada dia, com o respetivo valor */
    int dia1 = 145;
    int dia2 = 210;
    int dia3 = 98;
    int dia4 = 175;

    /* 2. Variável para o total, inicializada a 0 */
    int totalTarefas = 0;

    /* 3. Operador de atribuição composto += para acumular o total */
    totalTarefas += dia1;
    totalTarefas += dia2;
    totalTarefas += dia3;
    totalTarefas += dia4;

    /* 4. Apresentação do resultado */
    printf("Total de tarefas processadas (%d dias): %d\n", NUM_DIAS, totalTarefas);


    /* ============================================================
       TAREFA 2 - Cálculo da média diária com cast
       ============================================================ */

    /* Cálculo da média com cast explícito.
       É necessário fazer o cast de (int) para (double) porque,
       em C, a divisão entre dois inteiros (totalTarefas / NUM_DIAS)
       produz sempre um resultado inteiro (divisão truncada),
       perdendo as casas decimais. Ao converter um dos operandos
       para double antes da divisão, obrigamos o compilador a
       efetuar uma divisão em vírgula flutuante, preservando a
       precisão decimal do resultado. */
    double mediaDiaria = (double) totalTarefas / NUM_DIAS;

    printf("Média diária de tarefas: %.2f\n\n", mediaDiaria);


    /* ============================================================
       TAREFA 3 - Verificação do objetivo de desempenho
       ============================================================ */

    /* Operador relacional >= para verificar se a média atinge o objetivo.
       Em C, o resultado de uma comparação é sempre 0 (falso) ou 1 (verdadeiro),
       pelo que pode ser guardado diretamente numa variável int. */
    int objetivoAtingido = (mediaDiaria >= OBJETIVO_DIARIO);

    if (objetivoAtingido) {
        double diferenca = mediaDiaria - OBJETIVO_DIARIO;
        printf("Objetivo diário ATINGIDO!\n");
        printf("Diferença acima do objetivo: %.2f tarefas/dia\n\n", diferenca);
    } else {
        double diferenca = OBJETIVO_DIARIO - mediaDiaria;
        printf("Objetivo diário NÃO atingido.\n");
        printf("Diferença abaixo do objetivo: %.2f tarefas/dia\n\n", diferenca);
    }


    /* ============================================================
       TAREFA 4 - Distribuição de tarefas por servidores
       ============================================================ */

    /* 1. Variável com o identificador da tarefa */
    int idTarefa = 847;

    /* 3. Operador módulo para calcular o servidor de destino (distribuição cíclica) */
    int servidorDestino = idTarefa % NUM_SERVIDORES;

    /* 4. Apresentação do resultado */
    printf("Tarefa %d atribuída ao servidor: %d\n", idTarefa, servidorDestino);

    /* 5. Verificação de paridade com o operador módulo (%) por 2 */
    if (idTarefa % 2 == 0) {
        printf("Número da tarefa é PAR.\n");
    } else {
        printf("Número da tarefa é IMPAR.\n");
    }

    return 0;
}