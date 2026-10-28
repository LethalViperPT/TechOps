import static java.lang.IO.*;

void main(){
    desafio1();
}

/* Cria um programa que pede a idade do utilizador
    Se a idade for menor que 18, imprime: "Acesso negado: menor de idade."
    Se a idade for igual ou superior a 18, imprime: "Acesso permitido: bem-vindo!"
 */

void desafio1() {

    String input = readln("Qual é a tua idade? ");

    int idade = Integer.parseInt(input);

    if (idade < 18) {
        println("Acesso negado: menor de idade.");
    } else {
        println("Acesso permitido: bem-vindo!");
    }
}


 /* Pede ao utilizador para inserir uma nota de 0 a 20.
        Se for menor que 10, imprime: "Reprovado."
        Se for entre 10 e 15, imprime: "Suficiente."
        Se for acima de 15, imprime: "Excelente!"
    */

void desafio2() {

    String input = readln("Insira a nota do aluno (0 a 20): ");
    int nota = Integer.parseInt(input);

    if (nota < 10) {
        println("Reprovado.");
    }

    else if (nota >= 10 && nota <= 15) {
        println("Suficiente.");
    }

    else {
        println("Excelente!");
    }
}