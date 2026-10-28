# Gestão de Veículos

Aplicação em Java para gerir uma garagem de veículos (carros, motas e
autocarros), com duas interfaces e dados persistentes entre execuções.

## Como abrir no IntelliJ IDEA
1. File > Open... e seleciona a pasta `GestaoVeiculos`.
2. Há duas classes com `main()`:
   - **`GaragemConsola`** — versão em modo consola.
   - **`GaragemGUI`** — versão com interface gráfica (Swing).
3. Corre a que quiseres (botão direito no ficheiro > Run).

## Como compilar e correr pela linha de comandos
```
javac -d out src/*.java
java -cp out GaragemConsola   # versão consola
java -cp out GaragemGUI       # versão gráfica
```

## Estrutura das classes

- `Veiculo` — classe abstrata com os dados comuns a qualquer veículo
  (matrícula, marca, modelo, ano, cor, preço).
- `Carro`, `Mota`, `Autocarro` — subclasses de `Veiculo`, cada uma com os
  seus campos próprios (número de portas/combustível, cilindrada, e
  capacidade de passageiros, respetivamente).
- `GestorVeiculos` — **estende `ArrayList<Veiculo>` (herança)**, em vez de
  ter uma lista como variável lá dentro. Um `GestorVeiculos` É, ele
  próprio, um `ArrayList` de veículos — já vem com `add()`, `size()`,
  `remove()`, etc. herdados, e só acrescenta os métodos que faltam:
  `procurarPorMatricula`, `listarPorTipo`, `removerPorMatricula`,
  `listarTodos` e `pesquisar`.
- `PersistenciaVeiculos` — grava e carrega os veículos num ficheiro de
  texto local (`veiculos.txt`), guardando automaticamente após cada
  alteração (adicionar/remover).
- `GaragemConsola` — interface em modo consola.
- `GaragemGUI` — interface gráfica em Swing, com formulário (os campos
  específicos do tipo mudam consoante o veículo escolhido), tabela,
  filtro por tipo e pesquisa.

## Pesquisa
Tanto a consola (opção 5 do menu) como a interface gráfica (campo
"Pesquisar") permitem procurar um termo livre. A pesquisa cobre **todos
os campos relevantes do modelo** — não só os comuns (matrícula, marca,
modelo, ano, cor, preço, tipo), mas também os específicos de cada
subclasse (número de portas, combustível, cilindrada, capacidade de
passageiros). Isto é feito através do método `getCamposPesquisaveis()`
em `Veiculo`, que já inclui `getInfoEspecifica()` — como esse método é
implementado de forma diferente em cada subclasse (polimorfismo), a
pesquisa cobre automaticamente os campos próprios de cada tipo, sem ser
preciso repetir código.

## Persistência
Os dados são guardados automaticamente após cada alteração (adicionar ou
remover um veículo), no ficheiro `veiculos.txt`, criado na pasta onde o
programa corre. Esse ficheiro é carregado automaticamente ao iniciar a
aplicação — os veículos não se perdem ao fechar o programa.

## Nota sobre o Nimbus
A versão gráfica usa o tema Nimbus em vez do tema do sistema operativo,
porque em Linux o tema do sistema (GTK) tem um problema conhecido em que o
clique do rato nalgumas listas suspensas nem sempre regista corretamente.
Por essa razão, o tema é aplicado logo no `main()`, antes de qualquer
componente da janela ser criado.
