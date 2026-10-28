"""
Uso de APIs para resolver IPs para localizações geográficas e outras informações relacionadas.
exemplo: 194.163.165.234
"""

import sys
from loguru import logger
import requests

logger.remove() # remove o logger padrão
logger.add(sys.stdout, colorize=True, level="DEBUG", backtrace=True, diagnose=True)

ip_exemplo = "194.163.165.234"

@logger.catch
def obter_info(ip):
    url = f"https://ipinfo.io/{ip}/json"
    try:
        resposta = requests.get(url)
        resposta.raise_for_status()
        return resposta.json()

    except Exception as e:
        logger.error(f"Ocorreu um erro. Erro foi: {e}")

info_ip = obter_info(ip_exemplo)
logger.debug(info_ip)