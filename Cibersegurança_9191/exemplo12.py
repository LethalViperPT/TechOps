"""
Uso de regex para encontrar padrões
"""

import re
import sys
from loguru import logger

logger.remove()
logger.add(sys.stdout, colorize=True, level="DEBUG", backtrace=True, diagnose=True)


string_exemplo = "O IP da minha casa é 194.165.3.21 e do trabalho é 193.136.224.32 e não sei mais."

padrao = re.compile(r'(\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3})')

resultado = padrao.findall(string_exemplo)

if resultado:
    logger.success(resultado)