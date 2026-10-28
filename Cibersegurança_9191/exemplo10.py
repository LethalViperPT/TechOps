import sys
from loguru import logger
from tqdm.auto import tqdm
import time


logger.remove() # remove o logger padrão
logger.add(lambda x: tqdm.write(x,end=""), level="DEBUG",colorize=True, backtrace=True, diagnose=True) # adiciona um logger para o tqdm



logger.info("Teste da barra de progresso com tqdm")

variavel = 1000
linha = 0

with tqdm(total=variavel,desc="Linhas processadas", unit="linhas", colour="green") as pbar:
    for i in range(linha,variavel):
        pbar.update(1)
        logger.info(i)
        time.sleep(0.01)