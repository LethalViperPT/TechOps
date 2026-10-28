/* ============================================================
   Atividade 10.1 - Relatorio trimestral de vendas (NovaLuz)
   Autor: Diana Moreira dos Santos
   Data: Julho 2026
   Descricao: Programa que cruza informacao de vendas de 4
   vendedores ao longo de 6 semanas, analisando desempenho,
   deteccao de vendedores em risco, projecao de crescimento
   (recursiva) e ranking, tudo atraves de um menu interativo.

   Regras respeitadas:
   - Sem variaveis globais: tudo circula por argumentos/retorno
   - Todas as matrizes sao passadas como argumentos de funcoes
   - Operadores de atribuicao compostos usados sempre que possivel
   ============================================================ */

#include <stdio.h>

#define NUM_VENDEDORES 4
#define NUM_SEMANAS 6


/* ============================================================
   Preenchimento da matriz de vendas (ciclos for aninhados)
   ============================================================ */

void lerVendas(int matriz[][NUM_SEMANAS], int vendedores, int semanas) {
    for (int v = 0; v < vendedores; v++) {
        for (int s = 0; s < semanas; s++) {
            printf("Vendedor %d, Semana %d: ", v + 1, s + 1);
            scanf("%d", &matriz[v][s]);
        }
    }
}


/* ============================================================
   Analise por vendedor (funcao com matriz 2D como argumento)

   Parametro extra "totalSaida": permite devolver o total de
   vendas do vendedor sem repetir a logica de soma noutro sitio
   (reaproveitado depois na funcao de ranking).
   Parametro extra "mostrarDetalhes": permite reutilizar esta
   funcao tanto para mostrar um relatorio completo (opcao 1 do
   menu) como apenas para obter o total, em silencio, quando
   estamos so a construir o ranking (opcao 3).
   ============================================================ */

void analisarVendedor(int matriz[][NUM_SEMANAS], int vendedor, int semanas,
                       int *totalSaida, int mostrarDetalhes) {

    int total = 0;
    int maiorQuebra = 0;

    /* Um unico ciclo percorre a matriz: soma o total e, ao mesmo
       tempo, compara cada semana com a semana seguinte para
       encontrar a maior quebra entre semanas consecutivas. */
    for (int s = 0; s < semanas; s++) {
        total += matriz[vendedor][s];

        if (s < semanas - 1) {
            int diferenca = matriz[vendedor][s] - matriz[vendedor][s + 1];
            if (diferenca > maiorQuebra) {
                maiorQuebra = diferenca;
            }
        }
    }

    float media = (float) total / semanas;

    /* Categoria calculada apenas com operadores relacionais,
       somando o resultado (0 ou 1) de cada comparacao:
       media >= 250 -> 3 (Excelente)
       150 <= media < 250 -> 2 (Bom)
       80  <= media < 150 -> 1 (Suficiente)
       media < 80 -> 0 (Insuficiente) */
    int categoria = (media >= 250) + (media >= 150) + (media >= 80);

    if (mostrarDetalhes) {
        printf("\n--- Analise do Vendedor %d ---\n", vendedor + 1);
        printf("Total de vendas: %d\n", total);
        printf("Media semanal: %.2f\n", media);
        printf("Maior quebra entre semanas consecutivas: %d\n", maiorQuebra);
        printf("Classificacao de desempenho: ");

        switch (categoria) {
            case 3:
                printf("Excelente\n");
                break;
            case 2:
                printf("Bom\n");
                break;
            case 1:
                printf("Suficiente\n");
                break;
            default:
                printf("Insuficiente\n");
                break;
        }
    }

    *totalSaida = total;
}


/* ============================================================
   Deteccao de vendedores em risco (ciclos aninhados com
   condicoes de controlo)
   ============================================================ */

void listarVendedoresEmRisco(int matriz[][NUM_SEMANAS], int vendedores, int semanas) {

    printf("\n--- Vendedores em risco ---\n");
    int encontrouAlgum = 0;

    for (int v = 0; v < vendedores; v++) {

        /* Variavel de controlo auxiliar: guarda o indice da ultima
           semana fraca "nova" (nao adjacente a uma ja contada).
           Um valor bem afastado (-10) garante que a primeira
           semana fraca encontrada e sempre considerada nao
           adjacente. */
        int semanaFracaAnterior = -10;
        int contadorNaoConsecutivas = 0;

        for (int s = 0; s < semanas; s++) {

            int semanaFraca = (matriz[v][s] < 50);
            int naoAdjacente = (s - semanaFracaAnterior) > 1;

            if (semanaFraca && naoAdjacente) {
                contadorNaoConsecutivas += 1;
                semanaFracaAnterior = s;
            } else if (semanaFraca) {
                /* Semana fraca, mas adjacente a uma ja contada:
                   atualiza a posicao, mas nao conta como nova. */
                semanaFracaAnterior = s;
            }
        }

        if (contadorNaoConsecutivas >= 2) {
            printf("Vendedor %d: %d semanas fracas nao consecutivas\n",
                   v + 1, contadorNaoConsecutivas);
            encontrouAlgum = 1;
        }
    }

    if (!encontrouAlgum) {
        printf("Nenhum vendedor em risco neste trimestre.\n");
    }
}


/* ============================================================
   Ranking de desempenho (matriz auxiliar unidimensional)
   ============================================================ */

void calcularRanking(int matriz[][NUM_SEMANAS], int vendedores, int semanas) {

    int totalPorVendedor[NUM_VENDEDORES];

    /* Reaproveita a logica de soma ja existente em
       analisarVendedor(), sem repetir o calculo - aqui pedimos
       apenas o total, sem imprimir o relatorio detalhado. */
    for (int v = 0; v < vendedores; v++) {
        analisarVendedor(matriz, v, semanas, &totalPorVendedor[v], 0);
    }

    int melhor = 0;
    int pior = 0;

    for (int v = 1; v < vendedores; v++) {
        if (totalPorVendedor[v] > totalPorVendedor[melhor]) {
            melhor = v;
        }
        if (totalPorVendedor[v] < totalPorVendedor[pior]) {
            pior = v;
        }
    }

    printf("\n--- Ranking de desempenho ---\n");
    for (int v = 0; v < vendedores; v++) {
        printf("Vendedor %d: %d unidades\n", v + 1, totalPorVendedor[v]);
    }
    printf("Melhor desempenho: Vendedor %d (%d unidades)\n",
           melhor + 1, totalPorVendedor[melhor]);
    printf("Pior desempenho: Vendedor %d (%d unidades)\n",
           pior + 1, totalPorVendedor[pior]);
}


/* ============================================================
   Funcao recursiva - Projecao de crescimento (sem ciclos)
   ============================================================ */

float projetarCrescimento(float valorAtual, float taxaCrescimento, int semanasRestantes) {

    /* Caso base: nao ha mais semanas a projetar - devolve o
       valor acumulado ate aqui. */
    if (semanasRestantes <= 0) {
        return valorAtual;
    }

    /* Caso recursivo: aplica o crescimento composto desta semana
       e chama-se a si propria para as semanas restantes. */
    float novoValor = valorAtual * (1.0f + taxaCrescimento);
    return projetarCrescimento(novoValor, taxaCrescimento, semanasRestantes - 1);
}


/* ============================================================
   Programa principal - Menu interativo
   ============================================================ */

int main(void) {

    int vendas[NUM_VENDEDORES][NUM_SEMANAS];
    int opcao;

    printf("=== Relatorio trimestral de vendas - NovaLuz ===\n\n");
    printf("Introducao dos dados de vendas:\n");
    lerVendas(vendas, NUM_VENDEDORES, NUM_SEMANAS);

    do {
        printf("\n============================================\n");
        printf("1 - Analisar vendedor especifico\n");
        printf("2 - Listar vendedores em risco\n");
        printf("3 - Ver ranking de desempenho\n");
        printf("4 - Projetar crescimento de um vendedor\n");
        printf("0 - Sair\n");
        printf("Escolha uma opcao: ");
        scanf("%d", &opcao);

        switch (opcao) {

            case 1: {
                int vendedor;
                printf("Numero do vendedor (1 a %d): ", NUM_VENDEDORES);
                scanf("%d", &vendedor);

                if (vendedor >= 1 && vendedor <= NUM_VENDEDORES) {
                    int totalIgnorado;
                    analisarVendedor(vendas, vendedor - 1, NUM_SEMANAS, &totalIgnorado, 1);
                } else {
                    printf("Numero de vendedor invalido.\n");
                }
                break;
            }

            case 2:
                listarVendedoresEmRisco(vendas, NUM_VENDEDORES, NUM_SEMANAS);
                break;

            case 3:
                calcularRanking(vendas, NUM_VENDEDORES, NUM_SEMANAS);
                break;

            case 4: {
                int vendedor;
                int semanasProjecao;
                float taxaPercentual;

                printf("Numero do vendedor (1 a %d): ", NUM_VENDEDORES);
                scanf("%d", &vendedor);

                if (vendedor >= 1 && vendedor <= NUM_VENDEDORES) {
                    printf("Numero de semanas a projetar: ");
                    scanf("%d", &semanasProjecao);
                    printf("Taxa de crescimento semanal (em %%, ex. 5 para 5%%): ");
                    scanf("%f", &taxaPercentual);

                    float valorBase = (float) vendas[vendedor - 1][NUM_SEMANAS - 1];
                    float taxaDecimal = taxaPercentual / 100.0f;

                    float projecao = projetarCrescimento(valorBase, taxaDecimal, semanasProjecao);

                    printf("Valor da ultima semana registada: %.2f\n", valorBase);
                    printf("Projecao apos %d semana(s) a %.1f%% de crescimento: %.2f\n",
                           semanasProjecao, taxaPercentual, projecao);
                } else {
                    printf("Numero de vendedor invalido.\n");
                }
                break;
            }

            case 0:
                printf("A terminar o programa.\n");
                break;

            default:
                printf("Opcao invalida. Tente novamente.\n");
                break;
        }

    } while (opcao != 0);

    return 0;
}