# Jogo do Galo

Jogo do Galo (tic-tac-toe) em Java, com duas interfaces e dois modos de jogo.

## Como abrir no IntelliJ IDEA
1. File > Open... e seleciona a pasta `JogoDoGalo`.
2. Há duas classes com `main()`, cada uma um ponto de entrada:
   - **`JogoConsola`** — versão em modo consola.
   - **`JogoGUI`** — versão com interface gráfica (Swing).
3. Corre a que quiseres (botão direito no ficheiro > Run).

## Como compilar e correr pela linha de comandos
```
javac -d out src/*.java
java -cp out JogoConsola   # versão consola
java -cp out JogoGUI       # versão gráfica
```

## Modos de jogo
- **Dois jogadores** — jogam os dois no mesmo computador, um a seguir ao outro.
- **Contra o computador** — o jogador joga com X, o computador joga com O.

## Estrutura das classes
- `Tabuleiro` — representa o tabuleiro 3x3, sabe jogar numa casa, verificar
  vencedor e verificar empate. Usada pelas duas interfaces.
- `ComputadorIA` — lógica do computador no modo "contra o computador":
  tenta ganhar, senão bloqueia o adversário, senão joga no centro/canto.
- `JogoConsola` — interface em modo consola (menu, leitura de jogadas, impressão do tabuleiro).
- `JogoGUI` — interface gráfica em Swing (tabuleiro de botões, seleção de modo, destaque da jogada vencedora).

## Nota sobre o Nimbus
A versão gráfica usa o tema Nimbus (`UIManager`) em vez do tema do sistema
operativo, porque em Linux o tema do sistema (GTK) tem um problema conhecido
em que o clique do rato nalguns componentes (como listas suspensas) nem
sempre regista corretamente.
