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
    print("╔" + "═"*58 + "╗")
    print("║" + " "*21 + "O DESPERTAR DO GUERREIRO" + " "*20 + "║")
    print("╚" + "═"*58 + "╝")
    
    typing_effect("\n[SISTEMA] A despertar consciência artificial...")
    nome_guerreiro = input("[VOZ] Qual é o teu nome, viajante? ")
    
    try:
        print("\n[INFO] O sistema requer calibração numérica.")
        confirmacao = int(input("[SISTEMA] Digita o teu nível de prontidão (qualquer número): "))
    except ValueError:
        logger.warning("Entrada inválida detetada! Usando calibração padrão (Nível 7).")
        confirmacao = 7

    forca = random.randint(1, 10)
    agilidade = random.randint(1, 10)
    
    print("\n")
    for i in tqdm(range(100), desc="FORJANDO ALMA E DESTINO", ascii=True, colour='green'):
        time.sleep(0.01)
    
    poder_magico = math.pow(forca, agilidade)
    nivel_defesa = math.sqrt(forca + agilidade)
    
    agora = datetime.now()
    data_criacao = agora.strftime("%d/%m/%Y")
    ano_criacao = agora.year
    
    clear()
    print("        ⚔️  FICHA DE PERSONAGEM: " + nome_guerreiro.upper() + "  ⚔️")
    print("="*60)
    logger.info(f"REGISTO ÉPICO: Criado em {data_criacao} (Era de {ano_criacao})")
    
    print("\n  [ATRIBUTOS PRIMÁRIOS]")
    print(f"  💪 FORÇA:     {forca}/10")
    print(f"  ⚡ AGILIDADE: {agilidade}/10")
    
    print("\n  [PODERES CALCULADOS]")
    print(f"  ✨ PODER MÁGICO:  {poder_magico:.2f}")
    print(f"  🛡️  NÍVEL DEFESA:  {nivel_defesa:.2f}")
    print("="*60)
    
    typing_effect(f"\n[NARRAÇÃO] Que a tua jornada seja longa, {nome_guerreiro}!")
    input("\n[PRESSIONE ENTER PARA ENTRAR NO MUNDO...]")
    clear()

if __name__ == "__main__":
    main()