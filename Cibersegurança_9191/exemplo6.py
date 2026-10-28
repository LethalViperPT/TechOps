"""
Condicionais em python
if
if~else
if~elif~else

Tabela de verdade:
A   B   A and B   A or B   not A
0   0   0         0          1
0   1   0         1          1
1   0   0         1          0
1   1   1         1          0

"""

a = "Está sol"
b = "Está a chover"
idade = 67

# if simples
print("Hoje está um tempo estranho.")
if a == "Está a chover":
    print("Vou à praia")

# if~else
if a == "Está a chover":
    print("Vou à praia")
else:
    print("Vou ao cinema")
print("Fim do programa")

# if~elif~else
if idade < 18:
    print("És menor")

elif idade < 35:
    print("És jovem")

elif idade < 65:
    print("És adulto")

else:
    print("És sénior")

lista_compras = ["leite", "pão", "ovos", "manteiga", "queijo", "fruta", "legumes"]

if "guarda-napos" in lista_compras:
    print("Os guarda-napos já estão na lista de compras")

else:
    print("Os guarda-napos não estão na lista de compras")

# if simples

nome_utilizador = input("Indique o seu nome de utilizador: ")
password_utilizador = input("Indique a sua password: ")

if nome_utilizador == "admin" and password_utilizador == "1234":
    print("Login bem-sucedido. Bem-vindo, admin!")

else:
    print("Login falhou. Nome de utilizador ou password incorretos.")

# Ifs encadeados

if nome_utilizador == "admin":
    if password_utilizador == "1234":
        print("Login bem-sucedido. Bem-vindo, admin!")
    else:
        print("Login falhou. Utilizador ou Password incorretos.")
else:
    print("Login falhou. Utilizador ou Password incorretos.")

print("Fim do programa")