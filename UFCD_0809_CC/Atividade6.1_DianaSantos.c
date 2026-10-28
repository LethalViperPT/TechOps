/* ============================================================
   Atividade 6.1 - Simulador de custos energeticos (TechNova)
   Autor: Diana Moreira Santos
   Data: Julho 2026
   Descricao: Simula, hora a hora, o consumo eletrico de 3 linhas
   de producao ao longo de um turno de 8 horas, aplica o
   tarifario por escalao e determina a linha menos eficiente.

   REGRA DESTA ATIVIDADE: ainda nao foram dadas as instrucoes
   if / else / switch. Todas as decisoes sao tomadas apenas com
   operadores relacionais (que em C devolvem 0 ou 1) combinados
   com operadores aritmeticos.
   ============================================================ */

#include <stdio.h>

/* ---------- Parametros do negocio isolados com #define ---------- */
#define NUM_HORAS  8
#define NUM_LINHAS 3

#define PRECO_ESCALAO_1 0.12   /* ate 50 kWh (inclusive)          */
#define PRECO_ESCALAO_2 0.18   /* mais de 50 ate 100 kWh (incl.)  */
#define PRECO_ESCALAO_3 0.25   /* mais de 100 kWh                 */

int main(void) {

    /* ============================================================
       TAREFA 1 - Leitura validada do consumo hora a hora
       ============================================================ */

    float total_linha1 = 0;
    float total_linha2 = 0;
    float total_linha3 = 0;
    float custo_total_turno = 0;

    printf("=== Leitura do consumo por hora ===\n\n");

    for (int hora = 1; hora <= NUM_HORAS; hora++) {

        /* Guarda temporariamente o consumo das 3 linhas nesta hora,
           para depois somar a cada total sem precisar de if/else
           para "escolher" a variavel certa - o indice do array
           trata disso sozinho. */
        float consumo_linha[NUM_LINHAS];

        for (int linha = 0; linha < NUM_LINHAS; linha++) {

            float valor;
            int leitura_ok;

            printf("Hora %d - Linha %d - Consumo (kWh): ", hora, linha + 1);
            leitura_ok = scanf("%f", &valor);

            /* Se scanf nao conseguir ler um numero (ex: foi escrito algo
               que nao e um numero valido), leitura_ok fica a 0 e o valor
               lido fica indeterminado. Limpamos o resto da linha do
               buffer de entrada para nao ficar nenhum caracter "preso",
               o que causaria um loop infinito nas leituras seguintes. */
            while (leitura_ok != 1) {
                while (getchar() != '\n')  /* descarta o resto da linha invalida */
                    ;
                printf("Valor invalido: introduz um numero. Tente novamente: ");
                leitura_ok = scanf("%f", &valor);
            }

            /* Repete a leitura enquanto o valor for negativo.
               Isto e uma repeticao condicional (while), nao uma
               decisao if/else, pelo que respeita a regra da atividade. */
            while (valor < 0) {
                printf("Valor invalido: O consumo nao pode ser negativo. Tente novamente: ");
                leitura_ok = scanf("%f", &valor);

                while (leitura_ok != 1) {
                    while (getchar() != '\n')
                        ;
                    printf("Valor invalido: introduz um numero. Tente novamente: ");
                    leitura_ok = scanf("%f", &valor);
                }
            }

            consumo_linha[linha] = valor;
        }

        /* Soma o valor validado ao total acumulado de cada linha */
        total_linha1 += consumo_linha[0];
        total_linha2 += consumo_linha[1];
        total_linha3 += consumo_linha[2];

        float consumo_hora = consumo_linha[0] + consumo_linha[1] + consumo_linha[2];


        /* ============================================================
           TAREFA 2 - Calculo do custo por hora, com escalao dinamico

           Cada parcela so "conta" quando a comparacao respetiva e
           verdadeira (vale 1); nos outros casos a comparacao vale 0
           e a parcela desaparece da soma - e assim que se "decide"
           o escalao sem if/else.
           ============================================================ */

        int esta_no_escalao_1 = (consumo_hora <= 50);
        int esta_no_escalao_2 = (consumo_hora > 50) * (consumo_hora <= 100);
        int esta_no_escalao_3 = (consumo_hora > 100);

        float preco_kwh_hora =
              PRECO_ESCALAO_1 * esta_no_escalao_1
            + PRECO_ESCALAO_2 * esta_no_escalao_2
            + PRECO_ESCALAO_3 * esta_no_escalao_3;

        float custo_hora = consumo_hora * preco_kwh_hora;

        custo_total_turno += custo_hora;

        printf("Hora %d: Consumo total = %.2f kWh | Escalao = %.2f EUR/kWh | Custo = %.2f EUR\n\n",
               hora, consumo_hora, preco_kwh_hora, custo_hora);
    }


    /* ============================================================
       TAREFA 3 - Identificacao da linha menos eficiente
       ============================================================ */

    /* Cada variavel auxiliar so vale 1 se a linha correspondente for
       simultaneamente maior que as outras duas (combinacao de duas
       comparacoes por multiplicacao, tal como sugerido). */
    int linha1_e_pior = (total_linha1 > total_linha2) * (total_linha1 > total_linha3);
    int linha2_e_pior = (total_linha2 > total_linha1) * (total_linha2 > total_linha3);
    int linha3_e_pior = (total_linha3 > total_linha1) * (total_linha3 > total_linha2);

    /* Apenas uma das tres variaveis acima pode valer 1, pelo que as
       outras duas parcelas desaparecem e sobra o numero da linha certa. */
    int linha_menos_eficiente =
          1 * linha1_e_pior
        + 2 * linha2_e_pior
        + 3 * linha3_e_pior;

    /* Mesma logica de selecao por multiplicacao para isolar o valor
       de consumo da linha "pior" (as outras parcelas anulam-se). */
    float consumo_pior =
          total_linha1 * linha1_e_pior
        + total_linha2 * linha2_e_pior
        + total_linha3 * linha3_e_pior;

    float soma_total   = total_linha1 + total_linha2 + total_linha3;
    float soma_outras_duas = soma_total - consumo_pior;

    /* CAST EXPLICITO (1): soma_outras_duas ja e float, pelo que a
       promocao automatica ja garantiria uma divisao em virgula
       flutuante. O cast e feito na mesma, de forma explicita, para
       deixar claro no codigo que o divisor (NUM_LINHAS - 1) deve
       ser tratado como valor decimal, protegendo o calculo caso
       NUM_LINHAS venha a ser usado noutro contexto inteiro. */
    float media_outras_duas = soma_outras_duas / (float)(NUM_LINHAS - 1);

    float excesso_percentual = (consumo_pior - media_outras_duas) / media_outras_duas * 100.0f;


    /* ============================================================
       TAREFA 4 - Relatorio de decisao para a direcao financeira
       ============================================================ */

    /* CAST EXPLICITO (2): tal como acima, custo_total_turno ja e
       float, pelo que a divisao por NUM_HORAS (int) ja seria
       automaticamente promovida a virgula flutuante. O cast explicito
       documenta essa intencao e evita que uma futura alteracao do
       tipo de custo_total_turno introduza, sem se notar, uma divisao
       inteira truncada. */
    float custo_medio_hora = custo_total_turno / (float) NUM_HORAS;

    printf("==============================================\n");
    printf("RELATORIO DE CUSTOS ENERGETICOS - TURNO\n");
    printf("==============================================\n");
    printf("Linha 1 - Consumo total: %.2f kWh\n", total_linha1);
    printf("Linha 2 - Consumo total: %.2f kWh\n", total_linha2);
    printf("Linha 3 - Consumo total: %.2f kWh\n", total_linha3);
    printf("Linha menos eficiente: Linha %d\n", linha_menos_eficiente);
    printf("Excesso face a media das outras: %.2f%%\n", excesso_percentual);
    printf("----------------------------------------------\n");
    printf("Custo total do turno: %.2f EUR\n", custo_total_turno);
    printf("Custo medio por hora: %.2f EUR\n", custo_medio_hora);
    printf("==============================================\n");

    return 0;
}