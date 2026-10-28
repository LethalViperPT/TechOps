"""
Ciclos em Python
"""

marcas = ["Audi", "BMW", "Mercedes", "Porsche", "Ferrari", "Lamborghini", "Fiat"]
x = 0

while x < len(marcas):
    print(f"O meu carro é um {marcas[x]}")
    x += 1

print("Fim do ciclo while")


for carro in marcas:
    print(f"O meu carro é um {carro}")
print("Fim do ciclo for")

erro = True

while erro == True:
    try:
        resposta = int(input("Digite um número: "))
        erro = False
    except ValueError:
        print("Número inválido. Tente Novamente.")
        erro = True

print("O número digitado foi: ", resposta)


# < menor
# > maior
# <= menor ou igual
# >= maior ou igual
# == igual
# != diferente

