import re
import sys
from loguru import logger
from tqdm.auto import tqdm

logger.remove()
logger.add(lambda x: tqdm.write(x, end=""), level="DEBUG", colorize=True)

ficheiro_log = "server.log" 
ficheiro_saida = "ips_unicos.csv"

bloco = r"(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)"
padrao_ip = re.compile(bloco + r"\." + bloco + r"\." + bloco + r"\." + bloco)

ips_encontrados = []

with open(ficheiro_log, "r", encoding="utf-8") as f:
    total_linhas = sum(1 for linha in f)

logger.info(f"A analisar {total_linhas} linhas do log...")

with open(ficheiro_log, "r", encoding="utf-8") as conteudo:
    with tqdm(total=total_linhas, desc="Extração", unit="lin", colour="blue") as pbar:
        for linha in conteudo:
            resultado = padrao_ip.findall(linha)
            if resultado:
                for ip in resultado:
                    ips_encontrados.append(ip)
            pbar.update(1)

ips_unicos = list(set(ips_encontrados))

print()
logger.debug("--- Lista de IPs Únicos Encontrados ---")

for ip in ips_unicos:
    logger.info(f"IP: {ip}")

with open(ficheiro_saida, 'w', encoding='UTF-8') as saida:
    saida.write("IP_Address\n") 
    for ip in ips_unicos:
        saida.write(ip + "\n")

print()
logger.success(f"Tarefa concluída! {len(ips_unicos)} IPs gravados em '{ficheiro_saida}'")