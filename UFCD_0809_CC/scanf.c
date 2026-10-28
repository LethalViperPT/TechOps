#include <stdio.h>

int main() {
    int valor;
    int valido = 0;

    while (!valido) {
        printf("Introduza um número inteiro: ");

        if (scanf("%d", &valor) == 1) {
            valido = 1;
        } else {
            printf("Input inválido. Tente novamente.\n");
        }

        // Limpar o que sobrou no buffer, seja qual for o caso
        while (getchar() != '\n');
    }

    printf("Valor lido com sucesso: %d\n", valor);

    return 0;
}