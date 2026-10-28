/*
 * ======================================================================================
 * NOME DO PROGRAMA: Ficha de Cliente - PortoTech
 * AUTOR: Diana Moreira dos Santos
 * DATA: 07 / Julho / 2026
 * DESCRIÇÃO: Programa de gestão de clientes que apresenta uma ficha formatada 
 * com dados de um cliente da PortoTech.
 * ======================================================================================
 */

#include <stdio.h>  // Necessário para funções de entrada/saída (printf)
#include <stdlib.h> // Necessário para utilitários do sistema

int main() {
    // TAREFA 1: Declaração de variáveis e constantes
    int id_cliente = 3021;
    float desconto = 12.5;
    char tipo_conta = 'P';
    float saldo_credito = 450.00;
    int nivel_fidelidade = 3;
    
    const int MAX_COMPRAS = 100;

    // TAREFA 2: Cabeçalho da ficha
    printf("=======================================\n");
    printf("FICHA DE CLIENTE - PortoTech\n");
    printf("=======================================\n\n");

    // TAREFA 3: Corpo da ficha
    // Utilizamos \t para alinhar e especificadores de formato
    printf("ID de cliente:\t\t%d\n", id_cliente);
    printf("Tipo de conta:\t\t%c (Premium)\n", tipo_conta);
    printf("Nivel de fidelidade:\t%d estrelas\n", nivel_fidelidade);
    printf("Desconto atribuido:\t%.1f%%\n", desconto); // Uso de %% para imprimir o símbolo %
    printf("Saldo de credito:\t%.2f EUR\n", saldo_credito); // 2 casas decimais
    printf("Limite de compras:\t%d operacoes\n\n", MAX_COMPRAS);

    // TAREFA 4: Caminho do ficheiro (usando \\ para representar \)
    printf("Historico:\n");
    printf("C:\\PortoTech\\Clientes\\%d\\historico.txt\n\n", id_cliente);

    // TAREFA 5: Rodapé da ficha
    printf("=======================================\n");
    printf("Documento gerado automaticamente\n");
    printf("=======================================\n");

    return 0; // Ponto de saída do programa
}