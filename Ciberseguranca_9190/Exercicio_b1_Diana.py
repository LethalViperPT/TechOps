import os
import time
from loguru import logger
from tqdm import tqdm

def clear_screen():
    os.system('clear')

def main():
    clear_screen()
    print("="*40)
    print("      SISTEMA DE VALIDAÇÃO CYBER      ")
    print("="*40)
    
    password = input("\n[SISTEMA] Insira a password para análise: ")
    
    print("\n")

    for i in tqdm(range(100), desc="Verificando integridade", ascii=True):
        time.sleep(0.02)
    
    print("\n" + "-"*40)
    
    if len(password) > 8:
        logger.success(f"RESULTADO: '{password}' é uma Password segura.")
    else:
        logger.error("RESULTADO: Password demasiado curta!")
    
    print("-"*40)
    input("\nPrime ENTER para sair...")
    clear_screen()

if __name__ == "__main__":
    main()