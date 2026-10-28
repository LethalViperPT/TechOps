import os
import random
import platform
import time

VERMELHO = "\033[91m"
VERDE = "\033[92m"
AMARELO = "\033[93m"
AZUL = "\033[94m"
RESET = "\033[0m"
NEGRITO = "\033[1m"

def limpar_ecra():
    if platform.system() == "Windows":
        os.system('cls')
    else:
        os.system('clear')

def exibir_help():
    limpar_ecra()
    print(f"{AZUL}{'='*55}{RESET}")
    print(f"{NEGRITO}          CENTRAL DE AJUDA - ADAPTIVE BREACH SYSTEM{RESET}")
    print(f"{AZUL}{'='*55}{RESET}")
    print(f"{NEGRITO}LEGENDA DOS NÓS:{RESET}")
    print(f" [S] -> {VERDE}Sistema Seguro{RESET}: Nó estável e verificado.")
    print(f" [V] -> {AMARELO}Vulnerabilidade{RESET}: Requer ação de 'patch'.")
    print(f" [A] -> {VERMELHO}Ataque Ativo{RESET}: Risco imediato para o sistema.")
    print(f" [C] -> {AZUL}Sistema Crítico{RESET}: Infraestrutura de alta prioridade.")
    print(f" [F] -> {NEGRITO}Flag{RESET}: O teu objetivo principal de captura.")
    print(f"\n{NEGRITO}AÇÕES DISPONÍVEIS:{RESET}")
    print(" scan    : Analisa o nó (Revela o conteúdo e ganha pontos).")
    print(" patch   : Corrige nós 'V' (Reduz risco e ganha muitos pontos).")
    print(" capture : Tenta recolher uma Flag 'F' (Objetivo de vitória).")
    print(" ignore  : Salta o nó (Pode aumentar o risco se for um ataque).")
    print(f"\n{NEGRITO}ATENÇÃO:{RESET} As Flags podem mudar de posição aleatoriamente durante a partida, podendo ser detectado em posições já analisadas. Mantém-te vigilante!")
    print(f"{AZUL}{'='*55}{RESET}")
    input("\nPrime ENTER para retomar a missão...")

def iniciar_partida():
    opcoes_no = ["S", "S", "V", "V", "A", "C"]
    rede = [[random.choice(opcoes_no) for _ in range(4)] for _ in range(4)]
    
    posicoes = [(r, c) for r in range(4) for c in range(4)]
    random.shuffle(posicoes)
    
    f1 = posicoes.pop()
    f2 = posicoes.pop()
    rede[f1[0]][f1[1]] = "F"
    rede[f2[0]][f2[1]] = "F"

    energia = 15
    pontuacao = 0
    risco_sistema = 0
    flags_capturadas = 0
    visitados = []

    while energia > 0 and risco_sistema < 100:
        limpar_ecra()
        print(f"{AZUL}=== TERMINAL DE OPERAÇÕES DE REDE ==={RESET}")
        print(f"\nENERGIA: {AMARELO}{energia}{RESET} | PONTOS: {VERDE}{pontuacao}{RESET} | RISCO: {VERMELHO}{risco_sistema}%{RESET} | FLAGS: {flags_capturadas}/2")
        print("-" * 45)

        for r in range(4):
            linha_visual = "  "
            for c in range(4):
                if [r, c] in visitados:
                    char = rede[r][c]
                    cor = VERDE if char in ["F", "S"] else (VERMELHO if char in ["V", "A"] else RESET)
                    linha_visual += f"[{cor}{char}{RESET}] "
                else:
                    linha_visual += "[?] "
            print(linha_visual)
        
        print("-" * 45)
        
        try:
            print(f"(Comando '{NEGRITO}9{RESET}' para AJUDA)")
            l_in_raw = input("\nLinha (1-4): ").strip()
            if not l_in_raw:
                print(f"{VERMELHO}Aviso: Deves selecionar uma linha!{RESET}")
                time.sleep(2); continue
            
            l_in = int(l_in_raw)
            if l_in == 9:
                exibir_help(); continue
                
            c_in_raw = input("Coluna (1-4): ").strip()
            if not c_in_raw:
                print(f"{VERMELHO}Aviso: Deves selecionar uma coluna!{RESET}")
                time.sleep(2); continue
            
            c_in = int(c_in_raw)
            l, c = l_in - 1, c_in - 1

            if not (0 <= l <= 3 and 0 <= c <= 3):
                print(f"{VERMELHO}Coordenadas fora de alcance!{RESET}")
                time.sleep(2); continue

            acoes_permitidas = ["scan", "patch", "capture", "ignore"]
            acao = input("Ação (scan/patch/capture/ignore): ").lower().strip()
            
            if not acao:
                print(f"{VERMELHO}Aviso: Deves escolher uma ação!{RESET}")
                time.sleep(2); continue
                
            if acao not in acoes_permitidas:
                print(f"{VERMELHO}ERRO: Ação inválida! Escolha entre: scan, patch, capture ou ignore.{RESET}")
                time.sleep(2); continue

            if [l, c] not in visitados:
                visitados.append([l, c])

            tipo = rede[l][c]
            energia -= 1

            if acao == "scan":
                print(f"ANÁLISE: Nó identificado como {NEGRITO}[{tipo}]{RESET}")
                pontuacao += 10
                if tipo == "A": risco_sistema += 5
            
            elif acao == "patch":
                if tipo == "V":
                    print(f"{VERDE}SUCESSO: Vulnerabilidade eliminada.{RESET}")
                    rede[l][c] = "S"; pontuacao += 50; risco_sistema = max(0, risco_sistema - 15)
                elif tipo == "A":
                    print(f"{VERDE}SUCESSO: Ataque neutralizado.{RESET}")
                    rede[l][c] = "S"; pontuacao += 70; risco_sistema = max(0, risco_sistema - 20)
                else:
                    print(f"{VERMELHO}ERRO: Nada para corrigir neste nó.{RESET}")
                    pontuacao -= 20; risco_sistema += 10

            elif acao == "capture":
                if tipo == "F":
                    print(f"{VERDE}🚩 EXCELENTE: Flag capturada e encriptada!{RESET}")
                    flags_capturadas += 1; pontuacao += 150; rede[l][c] = "S"
                else:
                    print(f"{VERMELHO}ERRO: Tentativa de captura falhada.{RESET}")
                    pontuacao -= 50; risco_sistema += 20

            elif acao == "ignore":
                print("Nó ignorado.")
                if tipo in ["V", "A"]: risco_sistema += 15

            if random.random() < 0.2:
                coords_flags = []
                for r_idx in range(4):
                    for c_idx in range(4):
                        if rede[r_idx][c_idx] == "F":
                            coords_flags.append((r_idx, c_idx))
                
                if coords_flags:
                    print(f"\n{AMARELO}⚠️ AVISO: Protocolo de segurança detetado! As flags estão a mudar de posição...{RESET}")
                    time.sleep(1.5)
                    for r_old, c_old in coords_flags:
                        rede[r_old][c_old] = "S"
                        r_new, c_new = random.randint(0,3), random.randint(0,3)
                        while rede[r_new][c_new] == "F":
                            r_new, c_new = random.randint(0,3), random.randint(0,3)
                        rede[r_new][c_new] = "F"

            if flags_capturadas >= 2 and pontuacao >= 250:
                limpar_ecra()
                print(f"\n{VERDE}{'*'*50}")
                print("      VITÓRIA: ACESSO TOTAL GARANTIDO!")
                print(f"      Pontuação: {pontuacao} | Risco: {risco_sistema}%")
                print(f"{'*'*50}{RESET}")
                break

            input("\nPrime ENTER para continuar...")

        except ValueError:
            print(f"{VERMELHO}ERRO: Introduz apenas números para as coordenadas.{RESET}")
            time.sleep(2)

    if risco_sistema >= 100:
        print(f"\n{VERMELHO}🚨 SISTEMA COMPROMETIDO: O risco excedeu o limite!{RESET}")
    elif energia <= 0:
        print(f"\n{VERMELHO}💀 FALHA NA MISSÃO: Energia esgotada.{RESET}")

def main():
    while True:
        iniciar_partida()
        print("\n" + "="*35)
        if input("Desejas reiniciar o sistema? (s/n): ").lower() != 's':
            print(f"{AZUL}Logout efetuado. Até à próxima, Analista.{RESET}")
            break

if __name__ == "__main__":
    main()