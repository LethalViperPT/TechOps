"""
Ler e escrever em ficheiros de texto
"""

import sys
from loguru import logger

logger.remove() # Remove o logger padrão
logger.add(sys.stdout, colorize=True, level="DEBUG", backtrace=True, diagnose=True) # adiciona um logger para a saída padrão

# Abertura do ficheiro usando o método tradicional.

conteudo = open("dados_exemplo.txt",'r', encoding='utf-8')
logger.debug("Conteúdo do ficheiro:")
dados = conteudo.read()
logger.info(dados)
conteudo.close()

# Abertura do ficheiro usando o gerenciador de contexto.

with open("dados_exemplo.txt",'r', encoding='utf-8') as conteudo:
    logger.debug("Conteúdo do ficheiro:")
    dados = conteudo.read()
    logger.info(dados)

# Abertura do ficheiro e leitura linha a linha usando o gerenciador de contexto.

with open("dados_exemplo.txt",'r', encoding='utf-8') as conteudo:
    logger.debug("Conteúdo do ficheiro:")
    for linha in conteudo:
        logger.info(linha.strip())

with open("dados_exemplo.txt",'r', encoding='utf-8') as conteudo:
    logger.debug("Conteúdo do ficheiro:")
    linha = conteudo.readline()
    while linha:
        logger.success(linha.strip())
        linha = conteudo.readline()