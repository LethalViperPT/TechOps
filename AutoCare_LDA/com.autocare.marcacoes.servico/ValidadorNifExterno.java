package com.autocare.marcacoes.servico;

public class ValidadorNifExterno {
    
    /**
     * Integração simulada com serviço externo da Autoridade Tributária.
     * Documentação da simulação (Tarefa 8.2): Devido ao tempo limitado, a chamada 
     * real à API SOAP da AT foi substituída por uma validação local simulada, 
     * devolvendo 'true' para NIFs começados por 1, 2, 5, 6, 8 ou 9 com 9 dígitos.
     */
    public boolean validarNif(String nif) {
        // Padrão de integração simulada documentada, conforme exigido[cite: 5]
        if (nif == null || nif.length() != 9) {
            return false;
        }
        char primeiroDigito = nif.charAt(0);
        return (primeiroDigito == '1' || primeiroDigito == '2' || 
                primeiroDigito == '5' || primeiroDigito == '6' || 
                primeiroDigito == '8' || primeiroDigito == '9');
    }
}