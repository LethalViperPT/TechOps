/* ============================================================
   Atividade 10 - Gestao de dados das instalacoes realizadas (TecnoFibra)
   Autor: Diana Moreira dos Santos
   Data: Julho 2026
   Descricao: Regista o numero de instalacoes de fibra otica
   realizadas em cada um dos 5 dias uteis da semana, calcula
   indicadores basicos e aplica um bonus de 10% aos valores,
   demonstrando o efeito da passagem de matrizes por referencia.

   Nota: usa apenas os conceitos ja estudados ate ao momento
   (variaveis, printf, scanf, ciclos for, if, operadores
   aritmeticos e funcoes) - sem matrizes multidimensionais.
   ============================================================ */

#include <stdio.h>

#define NUM_DIAS 5


/* ============================================================
   Funcao aplicarBonus()
   Recebe a matriz como argumento - como em C um array e passado
   por referencia (o parametro recebe o endereco do primeiro
   elemento), qualquer alteracao feita aqui dentro reflete-se
   diretamente na matriz original usada no main().
   ============================================================ */

void aplicarBonus(int matriz[], int tamanho) {
    for (int i = 0; i < tamanho; i++) {
        /* Aumento de 10%, arredondado por defeito: a divisao
           inteira em C trunca automaticamente as casas decimais,
           o que corresponde exatamente a um arredondamento por
           defeito (ex. 8 * 110 / 100 = 8.8 -> 8). */
        matriz[i] = matriz[i] * 110 / 100;
    }
}


int main(void) {

    int instalacoes[NUM_DIAS];

    printf("=== Registo de instalacoes - TecnoFibra ===\n\n");

    /* Leitura dos valores dos 5 dias uteis */
    for (int dia = 0; dia < NUM_DIAS; dia++) {
        printf("Instalacoes no dia %d: ", dia + 1);
        scanf("%d", &instalacoes[dia]);
    }

    printf("\n--- Valores antes do bonus ---\n");
    for (int dia = 0; dia < NUM_DIAS; dia++) {
        printf("Dia %d: %d instalacoes\n", dia + 1, instalacoes[dia]);
    }

    /* Calculo do total, media e dia com mais instalacoes */
    int total = 0;
    int diaMaximo = 0;

    for (int dia = 0; dia < NUM_DIAS; dia++) {
        total += instalacoes[dia];

        if (instalacoes[dia] > instalacoes[diaMaximo]) {
            diaMaximo = dia;
        }
    }

    /* Cast explicito: total e NUM_DIAS sao ambos int, pelo que a
       divisao seria inteira (truncada) sem a conversao para float. */
    float media = (float) total / NUM_DIAS;

    printf("\n--- Indicadores da semana ---\n");
    printf("Total de instalacoes: %d\n", total);
    printf("Media diaria: %.2f\n", media);
    printf("Dia com mais instalacoes: Dia %d (%d instalacoes)\n",
           diaMaximo + 1, instalacoes[diaMaximo]);

    /* Aplica o bonus de 10% diretamente na matriz original */
    aplicarBonus(instalacoes, NUM_DIAS);

    printf("\n--- Valores depois do bonus (correcao de auditoria) ---\n");
    for (int dia = 0; dia < NUM_DIAS; dia++) {
        printf("Dia %d: %d instalacoes\n", dia + 1, instalacoes[dia]);
    }

    return 0;
}