"""
Uso de funções em Python
"""

def imprimir_ecra():
    print("Olá")
    return

def mostrar():
    global x
    x = 10
    print("Variável dentro da função: ", x)

def soma(numero1,numero2):
    resultado = numero1 + numero2
    return resultado

def imprimir(x):
    print("-----")
    print(x)
    print("-----")

def subtracao(x,y=0):
    resultado = x - y
    return resultado

imprimir_ecra()
x = 20
mostrar()
print("Variável fora da função: ", x)
mostrar()
print("Vamos somar 5 + 2")
conta = soma(5,2)
imprimir(conta)
print("5-2=",subtracao(5,2))


