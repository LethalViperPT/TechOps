salario_bruto = int((input("Qual o salário bruto do trabalhador? ")))

TAXA_IRS = 0.20
TAXA_SS = 0.11
SUBSIDIO_ALIMENTACAO = 110

multiplicacao = salario_bruto * TAXA_IRS
multiplicacao2 = salario_bruto * TAXA_SS
salario_liquido = salario_bruto - multiplicacao - multiplicacao2 + SUBSIDIO_ALIMENTACAO

print(f"O salário líquido é {salario_liquido}")

