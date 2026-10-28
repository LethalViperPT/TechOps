/*
 * ======================================================================================
 * NOME DO PROGRAMA: Ficha de Registo de Colaborador (TechNova)
 * AUTOR: Diana Moreira dos Santos
 * DATA: Julho de 2026
 * DESCRIÇÃO: Programa para registo de dados de colaboradores e cálculo de valores salariais.
 * ======================================================================================
 */

#include <stdio.h>  // Necessário para printf (entrada/saída)
#include <stdlib.h> // Necessário para funções utilitárias do sistema

// TAREFA 1: Definição das constantes

#define MESES_ANO 14
#define SUBSIDIO_DIARIO 6.00
#define DIAS_UTEIS_MES 22

// Protótipo da função auxiliar (declarado antes da main)
void exibirFicha();

int main() {
    // Chamada da função auxiliar
    exibirFicha();
    
    return 0; // Ponto de saída do programa
}

// Definição da função auxiliar (após a main)
void exibirFicha() {
    
    // TAREFA 2: Declaração e inicialização de variáveis com justificações

    char inicialNome = 'S';           // char: único caráter para a inicial
    int numFuncionario = 3021;        // int: número inteiro sem casas decimais
    int codDepartamento = 4;          // int: código numérico inteiro
    float salarioBaseMensal = 1050.00;// float: valores decimais (monetários)
    int anosExperiencia = 3;          // int: contagem de anos inteiros

    // TAREFA 3: Cálculo de valores derivados

    float salarioBrutoAnual = salarioBaseMensal * MESES_ANO;
    float subsidioAlimMensal = SUBSIDIO_DIARIO * DIAS_UTEIS_MES;

    // TAREFA 4: Apresentação dos dados formatados

    printf("\n=== FICHA DE COLABORADOR - TechNova ===\n");
    printf("Inicial do nome: %c\n", inicialNome);
    printf("N. de funcionario: %d\n", numFuncionario);
    printf("Departamento (cod.): %d\n", codDepartamento);
    printf("Anos de experiencia: %d\n", anosExperiencia);
    printf("Salario base mensal: %.2f EUR\n", salarioBaseMensal);
    printf("Subsidio alim. mes.: %.2f EUR\n", subsidioAlimMensal);
    printf("Salario bruto anual: %.2f EUR\n", salarioBrutoAnual);
}

/*
 * ======================================================================================
 * TAREFA 5: VERIFICAÇÃO E ANÁLISE
 * 
 * 1. Por que utilizaste float ou double e não int?
 * R: Porque o salário é um valor monetário que frequentemente inclui cêntimos (casas decimais). 
 * O tipo 'int' apenas armazena números inteiros, o que causaria perda de precisão.
 * 
 * * 2. O que aconteceria se tentasses guardar 1050.00 numa variável do tipo int?
 * R: O compilador truncaria o valor e guardaria apenas a parte inteira (1050), 
 * descartando os decimais.
 * 
 * * 3. Se a taxa do subsídio de alimentação sofresse uma atualização legal, em que parte do código bastaria fazer a alteração? Porquê?
 * R: Bastaria alterar o valor na constante '#define SUBSIDIO_DIARIO'. 
 * Porque as constantes centralizam os valores fixos, facilitando a manutenção.
 * ======================================================================================
 */