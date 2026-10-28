import os

def clear():
    os.system('clear')

def main():
    clear()
    print("=== SISTEMA DE MÉDIAS ESCOLARES ===")
    
    for i in range(1, 4):
        print(f"\n--- Aluno {i} ---")
        nome = input("Nome: ")
        try:
            n1 = float(input("Nota 1: "))
            n2 = float(input("Nota 2: "))
            n3 = float(input("Nota 3: "))
            n4 = float(input("Nota 4: "))
            
            media = (n1 + n2 + n3 + n4) / 4 
            
            status = "APROVADO" if media >= 10 else "REPROVADO"
            print(f">> {nome} | Média: {media:.2f} | Status: {status}")
        except ValueError:
            print("Erro: Insira apenas números!")

    input("\nPremir ENTER para sair...")

if __name__ == "__main__":
    main()