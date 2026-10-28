/*
 * ======================================================================================
 * NOME DO PROGRAMA: Cartão de Boas-Vindas Digital - Atividade 11
 * AUTOR: Diana Moreira dos Santos
 * DATA: Julho de 2026
 * DESCRIÇÃO: Registo e apresentação inicial de um novo colaborador da DataSoft.
 * ======================================================================================
 */

#include <stdio.h> // Parte A.1

int main() {
    
    // ==========================================
    // PARTE A: Constantes e variáveis de string
    // ==========================================
    char empresa[] = "DataSoft"; // Parte A.2
    char nomeColaborador[31];    // Parte A.3 (30 carateres + '\0')
    int numColaborador;

    // ==========================================
    // PARTE C.1: Matriz de strings (Departamentos)
    // ==========================================
    // Inicialização direta dos 4 departamentos
    char departamentos[4][30] = {
        "Contabilidade",
        "Comercial",
        "Informática",
        "Recursos Humanos"
    };

    // Parte A.4: Mensagem de boas-vindas
    printf("Bem-vindo(a) à empresa %s!\n\n", empresa);

    // ==========================================
    // PARTE B: Leitura de dados do colaborador
    // ==========================================
    printf("Introduza o seu primeiro nome (sem espaços): ");
    scanf("%s", nomeColaborador);

    printf("Introduza o seu número de colaborador (1 a 100): ");
    scanf("%d", &numColaborador);

    // Parte B.2: Apresentação combinada de %s e %d
    printf("\n%s, o seu número de colaborador é o %d.\n\n", nomeColaborador, numColaborador);

    // ==========================================
    // PARTE C.2: Ciclo for para percorrer a matriz
    // ==========================================
    printf("Departamentos disponíveis:\n");
    for (int i = 0; i < 4; i++) {
        // Usa (i + 1) para a numeração visual começar em 1 em vez de 0
        printf("%d. %s\n", i + 1, departamentos[i]); 
    }

    // ==========================================
    // PARTE D: Síntese final
    // ==========================================
    printf("\n=== RESUMO DE REGISTO ===\n");
    printf("Nome do colaborador: %s\n", nomeColaborador);
    printf("Número de colaborador: %d\n", numColaborador);
    printf("Empresa: %s\n", empresa);
    printf("--- Lista de departamentos ---\n");
    
    for (int i = 0; i < 4; i++) {
        printf("- %s\n", departamentos[i]);
    }
    printf("=========================\n");

    return 0;
}