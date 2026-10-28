"""
Utilização do Try Except para tratamento de erros
"""


try:
    resposta = int(input("Digite um número: "))

except Exception as erro:
    print("Ocorreu um erro: ", erro)
    exit()

else:
    print("O número digitado foi: ", resposta)

print("Programa finalizado")