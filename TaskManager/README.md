# Sistema de Gestão de Tarefas (Task Manager)

Projeto de Programação — UFCD 10790 — Etapa 1/2

## Como abrir no IntelliJ IDEA
1. File > Open... e seleciona a pasta `TaskManager`.
2. O IntelliJ deve reconhecer a pasta `src` como código-fonte automaticamente
   (se não, clica com o botão direito em `src` > "Mark Directory as" > "Sources Root").
3. A aplicação tem **duas interfaces**, cada uma com o seu próprio `main()`:
   - **`TaskManagerApp`** — versão em modo consola.
   - **`TaskManagerGUI`** — versão com interface gráfica (Swing).
   Corre a que quiseres (botão direito no ficheiro > Run).

## Como compilar e correr pela linha de comandos
```
javac -d out src/*.java
java -cp out TaskManagerApp   # versão consola
java -cp out TaskManagerGUI   # versão gráfica
```

## Estrutura das classes
- `Prioridade`      — enum com os níveis BAIXA, MEDIA, ALTA
- `Tarefa`           — entidade (título, descrição, data limite, prioridade, estado)
- `GestorTarefas`    — lógica de negócio (criar, listar, editar, eliminar, marcar concluída, filtrar, ordenar) — **partilhada pelas duas interfaces**
- `PersistenciaTarefas` — gravação/leitura das tarefas no ficheiro `tarefas.txt`
- `TaskManagerApp`   — interface em modo consola, com tabela colorida (ANSI)
- `TaskManagerGUI`   — interface gráfica em Swing (janela, tabela, formulário, filtros)
- `TarefaTableModel` — adapta a lista de tarefas para ser mostrada na JTable da GUI
- `Cores`            — códigos ANSI usados para colorir o texto da consola

## Duas interfaces, a mesma lógica
`TaskManagerApp` (consola) e `TaskManagerGUI` (Swing) são duas "vistas"
diferentes sobre os *mesmos* dados e a *mesma* lógica de negócio
(`GestorTarefas` e `PersistenciaTarefas`). Nenhuma das duas duplica regras —
ambas chamam os mesmos métodos, o que garante que o comportamento (criar,
editar, filtrar, ordenar, persistir) é sempre idêntico, independentemente da
interface usada.

## Apresentação visual
A listagem de tarefas é apresentada numa tabela com bordas (caracteres Unicode),
cores por prioridade (vermelho/amarelo/verde) e por estado (✔ concluída / ○
pendente), e um resumo no topo com o total de tarefas. Suportado nativamente
pela consola do IntelliJ IDEA. A versão gráfica usa a mesma lógica de cores
(vermelho/amarelo/verde por prioridade) na tabela Swing.

## Persistência
Os dados são guardados automaticamente após cada alteração no ficheiro
`tarefas.txt` (criado na pasta onde o programa corre). Esse ficheiro é
carregado automaticamente ao iniciar a aplicação.
