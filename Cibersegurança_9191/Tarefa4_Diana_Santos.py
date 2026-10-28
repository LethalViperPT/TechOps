alunos = ["Miguel", "João", "Maria", "Ana", "Pedro", "Sofia", "Ricardo", "Carla", "Rui", "Inês"]

notas = [8, 15, 18, 7, 12, 9, 16, 13, 11, 19]

print("Notas Originais: ")

x = 0

while x < len(alunos):
    print(f"Aluno: {alunos[x]} teve nota: {notas[x]}")
    x += 1

x = 0

while x < len(notas):
    if notas[x] == 8:
        notas[x] = 7
    elif notas[x] == 9:
        notas[x] = 10
    x += 1

print()

print("Notas Após Ajuste (Sem Orais): ")

x = 0

while x < len(alunos):
    print(f"Aluno: {alunos[x]} teve nota: {notas[x]}")
    x += 1