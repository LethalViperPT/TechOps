"""
Formador, decidi unificar os três exercícios num único script para simular uma Security Suite e aproveitei o seu desafio de ser criativa 
para integrar ferramentas que aprendi na UFCD 9191.
Utilizei a configuração logger.add(sys.stdout, colorize=True) para substituir o print comum em mensagens de sistema.
Implementei barras de progresso entre os exercícios para simular o tempo de processamento de segurança.
Usei o os.system('clear') para o terminal não ficar confuso e cada exercício aparecer de forma isolada, como se fosse uma ferramenta real.
O time.sleep() usei-o para dar tempo para o usuário ler as mensagens antes de limpar o ecrã e passar para o próximo exercício.
Espero não ter exagerado na criatividade e espero que goste. Obrigada.
"""

import sys
import os
import time
from loguru import logger
from tqdm import tqdm

logger.remove()
logger.add(sys.stdout, colorize=True, level="DEBUG", backtrace=True, diagnose=True)

def limpar_ecra():
    os.system('clear')

# Exercício 1 - Calculadora de Idade

limpar_ecra()

nome = input("Qual é o teu nome? ")

ano_nascimento = int(input("Em que ano nasceste? "))
ano_atual = 2026

for i in tqdm(range(100), desc="A calcular idade", colour="blue"):
    time.sleep(0.02)

idade = ano_atual - ano_nascimento

print("\nOlá, " + nome + "!")
print("Tens aproximadamente", idade, "anos.")

if idade < 18:
    logger.info("\nAinda és um 'baby hacker'! 🐣")
elif idade < 50:
    logger.info("\nEstás na idade de ouro da cibersegurança! 💻")
else:
    logger.warning("\nCuidado para não te esqueceres da password, dinossauro! 🦖")

# Exercício 2 - Classificador de Risco

time.sleep(5)
limpar_ecra()

nivel = int(input("Insere o nível de risco (1-10): "))

for i in tqdm(range(100), desc="A analisar pacotes", colour="yellow"):
    time.sleep(0.02)

if nivel <= 3:
    logger.success("\n🟢 Risco Baixo")
elif nivel <= 7:
    logger.warning("\n🟡 Risco Médio")
else:
    logger.critical("\n🔴 Risco Alto")

# Exercício 3 - Confirmação de Conta

time.sleep(5)
limpar_ecra()

user = input("Nome de utilizador: ")
password = input("Palavra-passe: ")

for i in tqdm(range(100), desc="A encriptar com AES-256", colour="green"):
    time.sleep(0.02)

print("\nA sua conta foi criada!")
print("Utilizador:", user)
print("Palavra-passe:", password)

if len(password) < 6:
    logger.error("\nALERTA: A tua password é tão curta que até o meu gato a adivinha! 🐱")
else:
    logger.success("\nSISTEMA: Password aceite. (Vou fingir que não a guardei em texto limpo... 😉)")

print("\n--- FIM DOS TESTES ---")