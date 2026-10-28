lista = []

try:
    numero1 = int(input("Indique o primeiro número: "))
    lista.append(numero1)
    
    numero2 = int(input("Indique o segundo número: "))
    lista.append(numero2)
    
    print(f"Conteúdo da lista: {lista}")
    
    soma = lista[0] + lista[1]
    
    print(f"O resultado da soma de ambos os números é {soma}")

except:
    print("Erro: Não está a utilizar o programa corretamente.")