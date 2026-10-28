"""
Escrita de dados num ficheiro de texto
"""

import sys
from loguru import logger

logger.remove() # remove o logger padrão
logger.add(sys.stdout, colorize=True, level="DEBUG", backtrace=True, diagnose=True)

ficheiro = "teste_escrita_ex11.txt"

# w = escreve sempre um novo ficheiro (apagando um existente se o encontrar)
# x = escreve um novo ficheiro. Dá erro se o ficheiro já existe.
# a = adiciona informação no final do ficheiro

with open(ficheiro,'a', encoding='UTF-8') as conteudo:
    conteudo.write("Linha 1\n")
    conteudo.write("Linha 2\n")
    conteudo.write("Linha 3\n")

logger.success(f"Ficheiro {ficheiro} criado com sucesso!")


carros = ["\'Ford\'\n", "BMW\n", "Volvo\n"]


with open(ficheiro,'w', encoding='UTF-8') as conteudo:
    conteudo.writelines(carros)