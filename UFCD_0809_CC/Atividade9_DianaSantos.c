/* ============================================================
   Atividade 9 - Calculo de comissoes de vendas (DataSoft)
   Autor: Diana Moreira dos Santos
   Data: Julho 2026
   Descricao: Prototipo em C que calcula comissoes de vendas,
   mantem um registo de auditoria com uma variavel static, soma
   as vendas recursivamente e demonstra o ambito de variaveis
   locais.
   ============================================================ */

#include <stdio.h>


/* ============================================================
   Tarefa 1 e 2 - Funcao calcularComissao() com contador static
   ============================================================ */

float calcularComissao(float valorVenda) {

    /* Variavel static: e inicializada uma unica vez e mantem o
       seu valor entre chamadas sucessivas da funcao, ao contrario
       de uma variavel local normal, que seria recriada do zero
       (e perderia o valor) em cada chamada. */
    static int vendasProcessadas = 0;

    vendasProcessadas++;
    printf("[Auditoria] Venda processada. Total de vendas ate agora: %d\n", vendasProcessadas);

    float comissao = valorVenda * 0.05f;   /* taxa de comissao: 5% */

    if (valorVenda >= 1000.0f) {
        comissao += 20.0f;   /* bonus fixo para vendas >= 1000 EUR */
    }

    return comissao;
}


/* ============================================================
   Tarefa 3 - Funcao recursiva somarVendasRecursivo()
   ============================================================ */

float somarVendasRecursivo(float vendas[], int indice, int totalVendas) {

    /* Caso base: quando o indice ultrapassa o numero de vendas,
       nao ha mais nada a somar - a recursao para aqui. */
    if (indice >= totalVendas) {
        return 0;
    }

    /* Caso recursivo: soma o valor da venda atual com o resultado
       da soma de todas as vendas seguintes (chamada recursiva
       com o indice incrementado). */
    return vendas[indice] + somarVendasRecursivo(vendas, indice + 1, totalVendas);
}


/* ============================================================
   Tarefa 4 - Funcao mostrarResumoVenda()
   ============================================================ */

void mostrarResumoVenda(float valorVenda, float comissao) {
    printf("Venda: %.2f EUR | Comissao: %.2f EUR\n", valorVenda, comissao);
}


/* ============================================================
   Tarefa 6 - Funcao testarAmbito() (variaveis locais)
   ============================================================ */

void testarAmbito(void) {
    int mensagem = 10;   /* variavel local, sem static */
    printf("[testarAmbito] mensagem = %d\n", mensagem);
}


/* ============================================================
   Tarefa 5 - Programa principal
   ============================================================ */

int main(void) {

    float vendas[5] = {450.0, 1200.0, 780.0, 2000.0, 300.0};
    int totalVendas = 5;

    printf("=== Processamento de vendas ===\n\n");

    for (int i = 0; i < totalVendas; i++) {
        float comissao = calcularComissao(vendas[i]);
        mostrarResumoVenda(vendas[i], comissao);
        printf("\n");
    }

    float totalVendido = somarVendasRecursivo(vendas, 0, totalVendas);
    printf("Total de vendas (soma recursiva): %.2f EUR\n\n", totalVendido);


    printf("=== Teste de ambito de variaveis locais ===\n");
    testarAmbito();
    testarAmbito();

    /* RESPOSTA (Tarefa 6):
       A variavel "mensagem" NAO mantem o valor entre as duas
       chamadas - em ambas ela e impressa como 10, porque e uma
       variavel local comum (automatica), sem a palavra-chave
       static. Uma variavel local normal e criada de novo, na
       pilha (stack), sempre que a funcao e chamada, e e destruida
       assim que a funcao termina. Por isso, cada chamada a
       testarAmbito() comeca com uma copia nova de "mensagem",
       sem qualquer memoria da chamada anterior. Se quisessemos
       que o valor persistisse entre chamadas (como aconteceu com
       o contador em calcularComissao()), teriamos de a declarar
       como "static int mensagem = 10;". */

    return 0;
}