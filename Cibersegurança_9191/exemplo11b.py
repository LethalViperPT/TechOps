"""
Manipulação de strings
"""

import sys
from loguru import logger
import csv

logger.remove() # remove o logger padrão
logger.add(sys.stdout, colorize=True, level="DEBUG", backtrace=True, diagnose=True)


texto = "O Python é uma linguagem de programação muito gira"

dados = texto.split()

logger.debug(f"Texto original: {type(texto)}-> {texto}")
logger.debug(f"Texto dividido: {type(dados)}-> {dados}")

logger.info(f"Parte do texto: {dados[4]}")

coordenadas = "40.123,-8.123"

coordenadas_divididas = coordenadas.split(",")

logger.info(f"Latitude: {coordenadas_divididas[0]}")
logger.info(f"Longitude: {coordenadas_divididas[1]}")

ficheiro = "dados_valores.csv"

def ler_ficheiro_csv(ficheiro):
    with open(ficheiro, 'r', encoding='UTF-8') as conteudo:
        leitor_csv = csv.reader(conteudo, delimiter=',')
        dados = []
        for linha in leitor_csv:
            dados.append(f"Nome: {linha[0]}, género: {linha[2]}, valor:{linha[1]}")
        return dados

dados_lidos = ler_ficheiro_csv(ficheiro)

logger.debug(f"Dados lidos do ficheiro: {type(dados_lidos)} -> {dados_lidos}")