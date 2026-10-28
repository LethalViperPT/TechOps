import sys
import os

from loguru import logger

logger.remove()
logger.add(sys.stdout, colorize=True, level="DEBUG", backtrace=True, diagnose=True)

def soma(numero1, numero2):
    return numero1 + numero2

def subtracao(numero1, numero2):
    return numero1 - numero2

def multiplicacao(numero1, numero2):
    return numero1 * numero2

def divisao(numero1, numero2):
    if numero2 == 0:
        return "Erro: Divisão por zero"
    return numero1 / numero2

try:
    os.system("clear")
    
    n1 = int(input("Digite o primeiro número: "))
    n2 = int(input("Digite o segundo número: "))

    print()
    logger.info("Resultados das Operações: ")
    print()

    logger.success(f"Soma: {soma(n1, n2)}")
    logger.debug(f"Subtração: {subtracao(n1, n2)}")
    logger.warning(f"Multiplicação: {multiplicacao(n1, n2)}")
    
    resultado_div = divisao(n1, n2)
    if resultado_div == "Erro: Divisão por zero":
        logger.error(resultado_div)
        logger.critical("Divisão por zero não é permitida!")
        print()
        logger.critical("Programa finalizado com erro!")
    else:
        logger.debug(f"Divisão: {resultado_div}")
        
        print()
        logger.success("Programa finalizado com sucesso!")

except Exception as erro:
    logger.critical(f"Aviso: Não está a utilizar o programa corretamente! Erro: {erro}")
    logger.critical("Programa finalizado com erro!")
