# Registo do Teste de Desempenho — Tarefa 9.3

**Como fazer:** corre a classe `TesteDesempenho.java` (tem o seu próprio
`main`) diretamente no IntelliJ. Ela mede o tempo da pesquisa combinada
(Tarefa 7.1) com ~10 marcações e depois com ~500, usando a base de dados
real (JDBC).

## Resultados

| Operação | Volume de dados | Tempo medido |
|---|---|---|
| `MarcacaoServico.pesquisar(AGENDADA, null, null)` | ~10 marcações | *(preencher com o valor impresso pela consola)* |
| `MarcacaoServico.pesquisar(AGENDADA, null, null)` | ~500 marcações | *(preencher com o valor impresso pela consola)* |

## Limitação conhecida (identificada por análise do código)

O método `MarcacaoServico.pesquisar(...)` chama `marcacaoRepositorio.listarTodas()`
e só depois filtra em memória, com Streams. Isto significa que a **tabela
inteira de marcações é sempre trazida da base de dados**, independentemente
de quantos resultados a pesquisa vá realmente devolver — os filtros de
estado, mecânico e data não são traduzidos em cláusulas `WHERE` do SQL.

Para um projeto deste porte (dezenas de marcações por semana), isto não é
problemático. Mas se o volume crescesse significativamente, seria uma fonte
de lentidão perceptível, porque o tempo de resposta cresceria
proporcionalmente ao número total de marcações na tabela, não ao número de
resultados da pesquisa.

**Correção não aplicada nesta atividade, por ser um refactor maior** (passaria
por construir a query SQL dinamicamente, com `WHERE` condicionais, dentro de
`MarcacaoRepositorio`) — fica documentada aqui como limitação conhecida, tal
como o passo 3 da tarefa permite.
