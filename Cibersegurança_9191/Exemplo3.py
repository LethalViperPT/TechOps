"""
Listas e Dicionários em Python
"""


x = 10 # Uma variável simples (inteiro)

#                      0         1       2        3
marca_automovel = ["Mercedes", "BMW", "Audi", "Porsche"] # Uma lista de marcas de automóveis

print(f"O carro selecionado de hoje é: {marca_automovel[0]}")

marca_automovel.append("Ferrari") # Adiciona um novo elemento à lista

print(f"Lista atualizada: {marca_automovel}")

marca_automovel.pop() # Remove o último elemento da lista (LIFO - Last In First Out)

print(f"Lista atualizada: {marca_automovel}")

marca_automovel.insert(0, "Lamborghini") # Insere um novo elemento na posição 0 da lista

print(f"Lista atualizada: {marca_automovel}")

marca_automovel.sort() # Ordena a lista por ordem alfabética
print(f"Lista atualizada: {marca_automovel}")
print("Lista atualizada: ", marca_automovel)

marca_automovel.remove("BMW") # Remove o elemento "BMW" da lista
print(f"Lista atualizada: {marca_automovel}")

print(f"Lista parcial de marcas: {marca_automovel[1:3]}") # Imprime os elementos da posição

print(f"Lista parcial de marcas: {marca_automovel[2:]}") # Imprime os elementos da posição 2

marca_automovel[0] = "Fiat" # Altera o elemento da posição 0 da lista para "Fiat"

print(f"Lista atualizada: {marca_automovel}")


# Dicionários

estudante = {"nome":"Miguel", "nota": 17} # dicionário

print(f"O nome do estudante é {estudante["nome"]} e ele(a) obteve {estudante["nota"]} valores.")
print(f"Comprimento da lista de marca de automóveis: {len(marca_automovel)}")
print(f"Comprimento do dicionário estudante: {len(estudante)}")
print(f"Chaves do dicionário estudante: {estudante.keys()}")
print(f"Valores do dicionário estudante: {estudante.values()}")