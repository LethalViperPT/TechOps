import sys
from loguru import logger

nome_ficheiro = "poema.txt"

logger.remove()
logger.add(sys.stdout, colorize=True, level="DEBUG")

logger.debug("--- 1. Totalidade do Poema ---")
with open(nome_ficheiro, "r", encoding="utf-8") as conteudo:
    for linha in conteudo:
        logger.info(linha.strip())

print()
print("------------------------------")
print()

logger.success("--- 2. Linhas com a palavra 'mar' ---")
with open(nome_ficheiro, "r", encoding="utf-8") as conteudo:
    for linha in conteudo:
        linha_limpa = linha.strip()
        if "mar" in linha_limpa:
            logger.info(linha_limpa)

print()
print("------------------------------")
print()

with open(nome_ficheiro, "r", encoding="utf-8") as conteudo:
    total_linhas = 0
    for linha in conteudo:
        total_linhas = total_linhas + 1
    
    logger.debug("--- 3. Contagem de Linhas ---")
    logger.info("O ficheiro poema.txt contém o seguinte número de linhas: {total_linhas}", total_linhas=total_linhas)