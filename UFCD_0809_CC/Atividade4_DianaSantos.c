/*
 * ======================================================================================
 * TAREFA 1: ANÁLISE E QUESTÕES
 * ======================================================================================
 * 1. Por que é que scanf("%s", nome) não utiliza o operador &, ao contrário de scanf("%d", &num)?
 * R: Em C, o nome de um array, ou string, já representa o endereço de memória do seu primeiro elemento. 
 * O operador '&' é necessário apenas para variáveis simples (int/float) para fornecer o endereço onde o scanf deve escrever.
 * * 2. Qual a função de #define TAMANHO 25? O que aconteceria se a linha fosse removida?
 * R: Cria uma constante simbólica. Se removida, o compilador dará erro, pois não reconhecerá o valor associado ao nome TAMANHO.
 * * 3. O que devolve strlen(nome)? Inclui o '\0'? Justifica.
 * R: Devolve o número de caracteres visíveis. Não inclui o '\0', porque a função para de contar exatamente ao encontrar o terminador nulo.
 * * 4. Se o utilizador escrever "Joana Rita", o que fica guardado em 'nome'? Porquê?
 * R: Fica guardado apenas "Joana", porque o especificador "%s" interrompe a leitura no primeiro espaço.
 */

/*
 * ======================================================================================
 * TAREFA 2: CORREÇÃO DE ERROS E QUESTÃO 2
 * ======================================================================================
 * 1. Lista de erros encontrados no código original:
 * - "define CODIGO_MAX 10": Falta o '#' (deve ser #define).
 * - "char codigo Projeto": Nome da variável com espaço (deve ser codigoProjeto).
 * - "scanf("%s", &codigoProjeto)": Uso indevido do '&' para ler uma string.
 * - "scanf("%d", &orcamento)": Orçamento é float, deveria ser "%f" e não "%d".
 * * 2. Reescreve o programa completo e corrigido:
 * (O programa corrigido encontra-se na Tarefa 3 abaixo).
 */

/*
 * ======================================================================================
 * APRESENTAÇÃO DO PROGRAMA
 * NOME DO PROGRAMA: Registo de Projeto
 * AUTOR: Diana Moreira dos Santos
 * DATA: Julho de 2026
 * DESCRIÇÃO: Programa de registo de colaboradores, integrando a correção da Tarefa 2 
 * e o desenvolvimento da Tarefa 3.
 * ======================================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
    /*
     * ======================================================================================
     * TAREFA 3: DESENVOLVIMENTO DO PROGRAMA (CÓDIGO CORRIGIDO E FUNCIONAL)
     * ======================================================================================
     */
    
    char nome[50];
    int numFuncionario;
    float salarioBase;

    // Cabeçalho interativo
    printf("=== REGISTO DE COLABORADOR - DataSoft ===\n");

    // Leitura dos dados (Correções da Tarefa 2 aplicadas aqui)
    printf("Nome do colaborador: ");
    scanf("%s", nome); 

    printf("Numero de funcionário: ");
    scanf("%d", &numFuncionario); 

    printf("Salario base (EUR): ");
    scanf("%f", &salarioBase);    

    // Ficha de registo final
    printf("\n--- FICHA REGISTADA ---\n");
    
    // Validação com strlen
    printf("Nome\t\t: %s (%lu caracteres)\n", nome, strlen(nome));
    printf("Numero\t\t: %d\n", numFuncionario);
    printf("Salario base\t: %.2f EUR\n", salarioBase);

    // Pausa final
    printf("\nPrima qualquer tecla para finalizar o registo...\n");
    getchar(); 
    getchar(); 
    
    return 0;
}