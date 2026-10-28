import sys
from loguru import logger

logger.remove()
logger.add(sys.stdout, colorize=True, level="DEBUG")

ficheiro_csv = "coordenadas_originais.csv"
ficheiro_txt = "coordenadas_formatadas.txt"

logger.debug("A iniciar a Tarefa 7...")
print()

lista_formatada = []

with open(ficheiro_csv, 'r', encoding='UTF-8') as conteudo:
    logger.info("A ler as coordenadas e a inverter a ordem (LON, LAT)...")
    for linha in conteudo:
        linha_limpa = linha.strip()
        if linha_limpa:
            partes = linha_limpa.split(",")
            lat = partes[0]
            lon = partes[1]
            
            bloco = "[\n" + lon + ",\n" + lat + "\n]"
            lista_formatada.append(bloco)

print()
logger.info("[------------------------------------------]")
print()

logger.info(f"A criar o ficheiro: {ficheiro_txt}")

with open(ficheiro_txt, 'w', encoding='UTF-8') as saida:
    saida.write('"coordinates": \n')
    
    contador = 0
    total = len(lista_formatada)
    
    for item in lista_formatada:
        saida.write(item)
        contador = contador + 1
        if contador < total:
            saida.write(",\n")
            
    saida.write("\n]")

print()
logger.success("Tarefa concluída com sucesso!")