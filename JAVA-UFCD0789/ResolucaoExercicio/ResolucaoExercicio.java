    /*

    faça uma app java que pede um número ao utilizador e verifique se esse número é negativo
    pode assumir o 0 é positivo

     */

import static java.lang.IO.*;

void main() {

    String texto = readln("Introduz um número: ");

    int numero = Integer.parseInt(texto);

    if (numero < 0) {
        println("O número " + numero + " é negativo.");
    } else {
        println("O número " + numero + " é positivo.");
    }
}