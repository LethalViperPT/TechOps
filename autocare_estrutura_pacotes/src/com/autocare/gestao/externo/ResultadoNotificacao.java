package com.autocare.gestao.externo;

/** Resultado devolvido pelo serviço externo de notificação (Tarefa 8.2). */
public class ResultadoNotificacao {

    private final boolean sucesso;
    private final String respostaCrua;

    public ResultadoNotificacao(boolean sucesso, String respostaCrua) {
        this.sucesso = sucesso;
        this.respostaCrua = respostaCrua;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public String getRespostaCrua() {
        return respostaCrua;
    }
}
