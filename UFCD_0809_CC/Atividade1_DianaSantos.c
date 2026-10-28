/*
 * ======================================================================================
 * TAREFA 1: IDENTIFICAÇÃO DE ELEMENTOS ESTRUTURAIS
 * 1. Diretivas de pré-processador: #include (inclui bibliotecas) e #define (define macros).
 * 2. Constantes: Existem 3 (#define EMPRESA, DEPARTAMENTO, ANO).
 * 3. Ponto de entrada: main() (onde o programa inicia).
 * 4. Função auxiliar: apresentarSistema() (declarada por protótipo antes da main).
 * 5. return 0;: Finaliza a função main indicando sucesso ao sistema.
 * ======================================================================================
 */

/*
 * ======================================================================================
 * TAREFA 2: CORREÇÃO DE ERROS
 * 
 * ERROS IDENTIFICADOS:
 * - Nome de função inválido (continha um espaço).
 * - Ponto de entrada incorreto ("inicio" em vez de "main").
 * - Falta de bibliotecas (#include <stdio.h>).
 * - Organização incorreta (instruções printf fora de função).
 * 
 * *Nome do programa: Sistema de arranque
 * Autor: Diana Moreira dos Santos
 * Data: Julho de 2026
 * Descrição: Programa corrigido para exibir a sede e o estado do sistema.
 * ======================================================================================
 */

#include <stdio.h>
#include <stdlib.h>

#define SEDE "Braga"

void mostrarMensagem(); // Protótipo

int main() {
    mostrarMensagem();
    return 0;
}

void mostrarMensagem() {
    printf("Sede: %s\n", SEDE);
    printf("Sistema operacional.\n");
}

/*
 * ======================================================================================
 * TAREFA 3: DESENVOLVIMENTO DE PROGRAMA
 * 
 * * Nome do programa: Sistema de arranque (DataSoft)
 * Autor: Diana Moreira dos Santos
 * Data: Julho de 2026
 * Descrição: Programa de gestão de pessoal para terminais internos da DataSoft.
 * ======================================================================================
 */

#define NOME_EMPRESA "DataSoft"
#define LOCAL_EMPRESA "Braga"
#define DEP_RH "Departamento de Recursos Humanos"

// Justificativa dos Includes:
// <stdio.h>: Necessário para usar o printf (entrada/saída).
// <stdlib.h>: Necessário para funções utilitárias padrão do sistema.

void exibirInfo(); // Protótipo

int main() {
    exibirInfo();
    return 0;
}

void exibirInfo() {
    printf("%s  %s\n", NOME_EMPRESA, LOCAL_EMPRESA);
    printf("%s\n", DEP_RH);
    printf("Sistema de gestão de pessoal - v2.0\n");
    printf("Terminal iniciado. Bom trabalho!\n");
    printf("========\n");
    printf("=======\n");
}