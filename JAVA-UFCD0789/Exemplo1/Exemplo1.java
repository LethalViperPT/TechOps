void main() {


    System.out.println("Olá Mundo!"); // desde o inicio

    IO.println("Olá Mundo!"); // desde java 25


    int idade = 10;
    double altura = 1.50; // 12 a 14 cd
    float peso = 40.6f; //7 cd
    boolean aprovado = true;
    char l1 = 'e';
    String nome = "Gonçalo";

    idade = 32;
    // idade = "txt";

    IO.println(idade + 4);
    IO.println(idade + 4.4);
    IO.println(idade + 4.5f);
    IO.println(10/2);
    IO.println(10/6f);
    IO.println(10/6d);
    double d = 12.0f + 12.5f;

    System.out.println("----------------------------------------------------------------------------");

    Scanner sc = new Scanner(System.in);
    System.out.println("Qual é o teu nome?\n");
    String myNome = sc.nextLine();
    System.out.println("O nome é " + myNome);
}