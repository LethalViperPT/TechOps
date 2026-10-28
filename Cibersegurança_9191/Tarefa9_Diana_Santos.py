import sys
import requests
from loguru import logger
from tqdm.auto import tqdm

logger.remove()
logger.add(sys.stdout, colorize=True, level="DEBUG")

@logger.catch
def obter_pais(ip):
    url = f"https://ipinfo.io/{ip}/json"
    try:
        resposta = requests.get(url)
        if resposta.status_code == 200:
            return resposta.json().get('country', 'Desconhecido')
    except Exception as e:
        logger.error(f"Erro ao consultar IP {ip}: {e}")
        return 'Erro'
    return 'Desconhecido'

contagem_paises = {}

logger.info("A iniciar a análise geográfica...")

print()

try:
    with open("ips_unicos.csv", "r", encoding="utf-8") as f:
        next(f)
        linhas = f.readlines()

    for linha in tqdm(linhas, desc="Geolocalização", colour="green"):
        ip = linha.strip()
        if ip:
            pais = obter_pais(ip)
            
            if pais in contagem_paises:
                contagem_paises[pais] += 1
            else:
                contagem_paises[pais] = 1

    paises_ordenados = sorted(contagem_paises.items(), key=lambda x: x[1], reverse=True)

    print()
    logger.success("--- RELATÓRIO FINAL: ACESSOS POR PAÍS ---")
    for pais, total in paises_ordenados:
        logger.info(f"País: {pais:5} | Total de Acessos: {total}")

except FileNotFoundError:
    logger.critical("Erro: O ficheiro 'ips_unicos.csv' não foi encontrado.")