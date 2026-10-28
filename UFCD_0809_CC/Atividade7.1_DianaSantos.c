/* ============================================================
   Atividade 7.1 - Sistema de gestão de inventário (TechNova)
   Autor: Diana Moreira dos Santos
   Data: Julho de 2026
   Descrição: Prova de conceito em C para registar o stock de
   4 tipos de produto em 3 fornecedores, validar os dados
   introduzidos, calcular indicadores de gestão e apresentar
   um relatorio final formatado.
   ============================================================ */

#include <stdio.h>
#include <string.h>

/* ============================================================
   PARTE 1 - Configuração e identificação do fornecedor
   ============================================================ */

#define NUM_FORNECEDORES 3
#define NUM_PRODUTOS 4

int main(void) {

    int stock[NUM_FORNECEDORES][NUM_PRODUTOS];

    /* Nomes dos 4 tipos de produto, na mesma ordem das colunas
       da matriz - facilita a apresentação ao longo do programa. */

    const char *nomesProdutos[NUM_PRODUTOS] = {
        "Resistencias", "Condensadores", "Microcontroladores", "Sensores"
    };

    char nomeResponsavel[30];

    printf("=== Sistema de gestão de inventário - TechNova ===\n\n");
    printf("Nome do responsável (sem espaços): ");
    scanf("%s", nomeResponsavel);

    /* strlen() para validar o comprimento do nome introduzido.
       Aqui apenas assinalamos o aviso, sem obrigar a repetir a leitura. */

    if (strlen(nomeResponsavel) < 3) {
        printf("Aviso: o nome introduzido parece demasiado curto.\n");
    }

    printf("\n");


    /* ============================================================
       PARTE 2 - Preenchimento validado da matriz
       ============================================================ */

    for (int f = 0; f < NUM_FORNECEDORES; f++) {
        for (int p = 0; p < NUM_PRODUTOS; p++) {

            int valor;

            printf("Fornecedor %c, Produto %d (%s): ", 'A' + f, p + 1, nomesProdutos[p]);
            scanf("%d", &valor);

            /* Ciclo while para validar: repete a leitura enquanto o
               valor introduzido for negativo. */

            while (valor < 0) {
                printf("Valor inválido: o stock nao pode ser negativo. Tente novamente: ");
                scanf("%d", &valor);
            }

            stock[f][p] = valor;
        }
    }

    printf("\n");


    /* ============================================================
       PARTE 3 - Cálculo de indicadores de gestão
       ============================================================ */

    /* 1. Total geral de unidades em stock */
    
    int total_geral = 0;
    for (int f = 0; f < NUM_FORNECEDORES; f++) {
        for (int p = 0; p < NUM_PRODUTOS; p++) {
            total_geral += stock[f][p];
        }
    }

    /* 2. Média de unidades por fornecedor.
       Cast explicito: total_geral e NUM_FORNECEDORES são ambos int,
       pelo que a divisão entre eles seria uma divisão inteira
       (truncada) se não fosse convertido um dos operandos para
       float. O cast garante que o resultado mantem casas decimais. */

    float media_por_fornecedor = (float) total_geral / NUM_FORNECEDORES;

    /* 3. Posição (fornecedor, produto) com a maior quantidade em stock */

    int maior_valor = stock[0][0];
    int fornecedor_maior = 0;
    int produto_maior = 0;

    for (int f = 0; f < NUM_FORNECEDORES; f++) {
        for (int p = 0; p < NUM_PRODUTOS; p++) {
            if (stock[f][p] > maior_valor) {
                maior_valor = stock[f][p];
                fornecedor_maior = f;
                produto_maior = p;
            }
        }
    }

    /* 4. Número de produtos com stock abaixo de 10 unidades, por fornecedor */

    int stock_critico[NUM_FORNECEDORES];

    for (int f = 0; f < NUM_FORNECEDORES; f++) {
        int contagem = 0;
        for (int p = 0; p < NUM_PRODUTOS; p++) {
            /* Operador relacional: soma 1 a contagem sempre que a
               comparacao for verdadeira, sem precisar de if/else. */
            contagem += (stock[f][p] < 10);
        }
        stock_critico[f] = contagem;
    }


    /* ============================================================
       PARTE 4 - Relatório final formatado
       ============================================================ */

    printf("================================================\n");
    printf("RELATÓRIO DE INVENTÁRIO - TECHNOVA\n");
    printf("================================================\n");
    printf("Responsável: %s\n", nomeResponsavel);
    printf("------------------------------------------------\n\n");

    printf("Tabela de stock (unidades):\n\n");
    printf("\t\t");
    for (int p = 0; p < NUM_PRODUTOS; p++) {
        printf("%-21s", nomesProdutos[p]);
    }
    printf("\n");

    for (int f = 0; f < NUM_FORNECEDORES; f++) {
        printf("Fornecedor %c\t", 'A' + f);
        for (int p = 0; p < NUM_PRODUTOS; p++) {
            printf("%-21d", stock[f][p]);
        }
        printf("\n");
    }

    printf("\n------------------------------------------------\n");
    printf("Total geral de unidades: %d\n", total_geral);
    printf("Média de unidades por fornecedor: %.2f\n", media_por_fornecedor);
    printf("Produto com maior stock: Fornecedor %c, %s (%d unidades)\n",
           'A' + fornecedor_maior, nomesProdutos[produto_maior], maior_valor);

    printf("\nProdutos com stock critico (< 10 unidades) por fornecedor:\n");
    for (int f = 0; f < NUM_FORNECEDORES; f++) {
        printf("\tFornecedor %c: %d produto(s)\n", 'A' + f, stock_critico[f]);
    }

    printf("================================================\n");

    return 0;
}