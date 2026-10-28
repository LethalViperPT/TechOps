"""
Uso de bibliotecas em Python
"""

import sys # biblioteca de acesso a funcionalidades do sistema operactivo
import os # sistema operativo

from loguru import logger # biblioteca de logging

logger.remove() # remove o logger padrão
logger.add(sys.stdout, colorize=True, level="DEBUG", backtrace=True, diagnose=True) # adiciona um logger personalizado

os.system("clear") # ou "cls" no Windows
# os.system("cls")


print("Versão do Python:", sys.version)

"""
pip intsall nome-da-biblioteca
python -m pip install nome-da-biblioteca

pip install loguru
python -m pip install loguru
"""

logger.debug("Esta é uma mensagem de debug")
logger.info("Esta é uma mensagem de informação")
logger.warning("Esta é uma mensagem de aviso")
logger.error("Esta é uma mensagem de erro")
logger.critical("Esta é uma mensagem crítica")
logger.success("Esta é uma mensagem de sucesso")
