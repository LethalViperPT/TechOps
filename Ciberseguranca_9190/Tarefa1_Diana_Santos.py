"""
Parte 1 - Caracteres de Escape
"""

# 1. Mostra: === SISTEMA SEGURO === Utilizador: admin Estado: ativo
print("=== SISTEMA SEGURO ===\nUtilizador: admin\nEstado: ativo")

print()
# 2. Mostra: IP Acessos 192.168.0.1 10 10.0.0.2 5
print("IP\t\tAcessos")
print("192.168.0.1\t10")
print("10.0.0.2\t5")

print()
# 3. Mostra: Alerta: "Acesso não autorizado"
print("Alerta: \"Acesso não autorizado\"")

print()
# 4. Mostra: C:\Logs\seguranca\log1.txt
print("C:\\Logs\\seguranca\\log1.txt")

print()
"""
Parte 2 - Operadores Aritméticos
"""

# 5. Pede número de tentativas usadas (máximo 5) e mostra quantas faltam.
usadas = int(input("Tentativas usadas: "))
faltam = 5 - usadas
print("Tentativas restantes:", faltam)

print()
# 6. Pede acessos de 3 servidores e calcula o total.
s1 = int(input("Acessos Servidor 1: "))
s2 = int(input("Acessos Servidor 2: "))
s3 = int(input("Acessos Servidor 3: "))
total = s1 + s2 + s3
print("Total de acessos:", total)

print()
# 7. Pede acessos de 3 dias e calcula a média.
dia1 = int(input("Acessos Dia 1: "))
dia2 = int(input("Acessos Dia 2: "))
dia3 = int(input("Acessos Dia 3: "))
media = (dia1 + dia2 + dia3) / 3
print("Média de acessos:", media)

print()
# 8. Pede número total de pacotes e mostra: - pacotes por 2 (//); - resto (%)
total_pacotes = int(input("Número total de pacotes: "))
por_dois = total_pacotes // 2
resto = total_pacotes % 2
print("Pacotes por 2:", por_dois)
print("Resto:", resto)

print()
"""
Parte 3 – Integração
"""

# 9. Pede IP e número de acessos e mostra: --- LOG --- IP: XAcessos: X Dobro de acessos: X
ip = input("Introduza o IP: ")
acessos = int(input("Número de acessos: "))
print("\n--- LOG ---")
print("IP:", ip)
print("Acessos:", acessos)
print("Dobro de acessos:", acessos * 2)

print()
# 10. Pede número de pacotes e calcula: - dobro; - triplo; - metade
pacotes = int(input("Número de pacotes: "))
print("Dobro:", pacotes * 2)
print("Triplo:", pacotes * 3)
print("Metade:", pacotes / 2)

print()
# 11. Pede nome do utilizador e tamanho da password. Calcula:; Força = tamanho * 2; Mostra:; Utilizador: X; Tamanho: X; Força: X
utilizador = input("Nome do utilizador: ")
tamanho_pass = int(input("Tamanho da password: "))
forca = tamanho_pass * 2
print("\nUtilizador:", utilizador)
print("Tamanho:", tamanho_pass)
print("Força:", forca)