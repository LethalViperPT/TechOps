# Esta linha não interpretada. É ignorada pelo interpretador Python
# Isto é uma segunda linha que também é um comentário e não é interpretada.
"""
Isto também é um comentário que é ignorado pelo interpretador Python.
Além disso, pode ser de múltiplas linhas, o que facilita a escrita de comentários
mais longos ou explicações detalhadas sobre o código.
"""

# Definição de variáveis. O que são?

# Variáveis são espaços na memória do computador onde podemos armazenar dados.

caixa_sapatos = 5 # O sinal de igual é um sinal de atribuição. Lê-se da direita para
# esquerda. O número 5 é atribuído à variável caixa_sapatos.

caixa_sapatos = 6 # Agora a variável caixa_sapatos tem o valor 6, pois o valor anterior (5)

caixa_sapatos = caixa_sapatos + 2 # Somamos o valor atual de caixa_sapatos (6) com 2,
# Resultado em 8, e atribuímos esse novo valor de volta à variável caixa_sapatos.

# Listas:

lista_de_compras = [5,3,2] # Uma lista com 3 números diferentes.

TX_IVA = 0.23 # Variável (constante) da taxa de iva de 23%

nome = "João" # Variável do tipo string, que armazena o nome "João"
nome = 'Maria' # Variável substituída por "Maria"

print("Olá Mundo") # Instrução para imprimir uma mensagem no ecrã.

# Função é qualquer coisa que tem um nome e um ou vários argumentos

print(nome) # Imprime o valor/conteúdo da variável nome, que é "Maria"
print(caixa_sapatos)

# No Python podemos utilizar uma coisa chamada f-string

print(f"Olá, o meu nome é {nome}. Tenho uma caixa de sapatos que tem o valor de {caixa_sapatos}.")