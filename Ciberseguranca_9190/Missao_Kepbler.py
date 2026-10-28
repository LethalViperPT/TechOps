import os
import time
import random
import math
from datetime import datetime
from loguru import logger
from tqdm import tqdm

def clear():
    os.system('clear')

def typing_effect(text, speed=0.03):
    for char in text:
        print(char, end='', flush=True)
        time.sleep(speed)
    print()

def main():
    clear()
    print("🚀 " + "="*54 + " 🚀")
    print("      DEEP SPACE NAVIGATION: KEPLER-22b SIMULATOR      ")
    print("🚀 " + "="*54 + " 🚀")
    
    typing_effect("\n[COMUNICADOR] A estabelecer ligação com a Estação Espacial...")
    astronauta = input("[SISTEMA] Identifique-se, Capitão: ")
    
    try:
        sorte_num = int(input(f"[SISTEMA] Capitão {astronauta}, escolha o número de Sorte (1-10): "))
    except ValueError:
        logger.warning("Entrada inválida! O sistema de navegação assumiu o valor padrão: 7.")
        sorte_num = 7
    
    combustivel = random.randint(1, 10)
    
    energia_salto = math.pow(combustivel, 2)
    
    ano_atual = datetime.now().year
    
    typing_effect("\n[ALERTA] Motores a aquecer. Verificando integridade da nave...")
    for i in tqdm(range(100), desc="IGNIÇÃO EM CURSO", ascii=True, colour='blue'):
        time.sleep(0.02)
    
    clear()
    print(f"🛰️  RELATÓRIO DE BORDO: CAPITÃO {astronauta.upper()}  🛰️")
    print("="*60)
    
    if ano_atual > 2025:
        logger.info(f"REGISTO: Nave detetada no ano {ano_atual} (Modelo Novo).")
    else:
        logger.warning(f"REGISTO: Nave detetada no ano {ano_atual} (Modelo Antigo).")
        
    print(f"\n[DADOS DE TELEMETRIA]")
    print(f"  > RESERVA DE COMBUSTÍVEL: {combustivel}L")
    print(f"  > ENERGIA DE SALTO GERADA: {energia_salto:.0f}u")
    print("-" * 40)
    
    if energia_salto > 20:
        logger.success(f"ORDEM FINAL: O Capitão {astronauta} conseguiu aterrar em Kepler-22b!")
    else:
        logger.critical(f"ORDEM FINAL: Missão falhou. {astronauta}, ficámos sem energia para o pouso.")
    print("="*60)
    
    typing_effect("\n[COMUNICADOR] Transmissão terminada. Desligando...")
    input("\n[PRESSIONE ENTER PARA SAIR DO COCKPIT]")
    clear()

if __name__ == "__main__":
    main()