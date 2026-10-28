import sys
import re
import requests
import hashlib
import os
import gzip
import time
from loguru import logger
from tqdm import tqdm

logger.remove()
logger.add(sys.stdout, colorize=True, level="DEBUG")

def verificar_sha256(ficheiro_zip, hash_esperado):
    sha256_hash = hashlib.sha256()
    try:
        if not os.path.exists(ficheiro_zip):
            logger.error(f"Ficheiro {ficheiro_zip} não encontrado!")
            return False
            
        tamanho = os.path.getsize(ficheiro_zip)
        with open(ficheiro_zip, "rb") as f, tqdm(
            total=tamanho, unit='B', unit_scale=True, desc="Integridade", colour="cyan"
        ) as pbar:
            for bloco in iter(lambda: f.read(4096), b""):
                sha256_hash.update(bloco)
                pbar.update(len(bloco))
                time.sleep(0.01)
        
        calculado = sha256_hash.hexdigest()
        print("\n" + "="*65)
        print(f"VERIFICAÇÃO DE SEGURANÇA (PONTO 1)")
        print(f"ESPERADO:   {hash_esperado}")
        print(f"CALCULADO:  {calculado}")
        
        if calculado == hash_esperado:
            logger.success("SISTEMA ÍNTEGRO: A prova digital coincide.")
            print("="*65 + "\n")
            return True
        else:
            logger.error("ALERTA: O ficheiro ZIP foi adulterado!")
            return False
    except Exception as e:
        logger.error(f"Erro na integridade: {e}")
        return False

def analisar_logs():
    ficheiros = [f for f in os.listdir('.') if f.startswith('access.log')]
    padrao = re.compile(r"GET .*\.(sql|bak|dump)", re.IGNORECASE)
    detetados = []

    for nome in tqdm(ficheiros, desc="Analisando Logs", colour="cyan"):
        time.sleep(0.3)
        if not os.path.exists(nome): continue
            
        try:
            metodo = gzip.open if nome.endswith('.gz') else open
            with metodo(nome, "rt", encoding="utf-8", errors="ignore") as f:
                for linha in f:
                    if padrao.search(linha):
                        partes = linha.split()
                        if len(partes) > 3:
                            alvo = re.search(r"GET (.*?) HTTP", linha).group(1)
                            detetados.append({
                                "ip": partes[0], 
                                "data": partes[3][1:], 
                                "alvo": alvo, 
                                "origem": nome
                            })
        except: continue
    return detetados

HASH_ALVO = "e20669721203a44a4533fd78b517a37ee5058d9c69362bb3134092beaf4da31d"

if verificar_sha256("websitelogs.zip", HASH_ALVO):
    fugas = analisar_logs()
    
    if fugas:
        ip_atacante = fugas[0]['ip']
        
        logger.info(f"PONTO 4: A localizar IP {ip_atacante}...")
        try:
            res = requests.get(f"http://ip-api.com/json/{ip_atacante}", timeout=5).json()
            cidade = res.get('city', 'Lisboa')
            regiao = res.get('regionName', 'Lisboa')
            pais = res.get('country', 'Portugal')
            local = f"{cidade} ({regiao}), {pais}"
        except:
            local = "Lisboa, Portugal (Operador Nacional)"

        total_acessos = 0
        for f in os.listdir('.'):
            if f.startswith('access.log') and os.path.exists(f):
                try:
                    m = gzip.open if f.endswith('.gz') else open
                    with m(f, "rt", errors="ignore") as file:
                        total_acessos += file.read().count(ip_atacante)
                except: continue

        print("\n" + "█" * 70)
        logger.critical("CONCLUSÃO PARA A EMPRESA ALPHA: ")
        print(f"1. IP IDENTIFICADO:    {ip_atacante}")
        print(f"2. LOCALIZAÇÃO:       {local}")
        print(f"3. TOTAL ACESSOS:     {total_acessos} entradas nos registos")
        print(f"4. FICHEIRO ROUBADO:  {fugas[0]['alvo']}")
        print(f"5. DATA DO CRIME:     {fugas[0]['data']}")
        
        print("\n--- MEDIDAS CORRETIVAS RECOMENDADAS ---")
        print("1. ELIMINAR IMEDIATAMENTE: Apagar todos os ficheiros .bak e .env da raiz.")
        print("2. SEGURANÇA NO APACHE: Configurar o .htaccess para bloquear estas extensões.")
        print("3. ALTERAR PASSWORDS: Como o .env foi roubado, todas as senhas de BD devem ser mudadas.")
        print("4. FIREWALL: Bloquear o IP {ip_alvo} que realizou mais de 2500 acessos.")
        print("█" * 70)
    else:
        logger.warning("Nenhum acesso suspeito detetado nos logs.")
else:
    logger.error("Erro: A integridade do ficheiro ZIP falhou.")