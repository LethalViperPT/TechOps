package com.autocare.gestao.externo;

import com.autocare.gestao.excecoes.NotificacaoExternaException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Integração com um serviço externo de envio de notificações por email
 * (Tarefa 8.2).
 *
 * NOTA SOBRE ESTA DECISÃO (documentação exigida pela Tarefa 8.2, passo 3):
 * O documento de definição do projeto classificou explicitamente o envio
 * automático de SMS/email como "Won't have this time" (RF09), por não ser
 * urgente para a oficina. Ainda assim, para cumprir o objetivo de
 * aprendizagem desta aula — implementar o padrão de integração externa
 * (pedido, resposta, tratamento de erro) — foi adicionada uma notificação
 * pontual (não automática/recorrente) ao cliente quando uma intervenção é
 * concluída, distinta do que tinha sido excluído.
 *
 * Como não existe, dentro do tempo do módulo, uma conta de email/API real
 * configurada (ex. SendGrid, Mailgun) nem autorização para expor
 * credenciais, a integração é SIMULADA: em vez de um pedido HTTP real,
 * lê-se a resposta de um ficheiro JSON local que representa o que uma API
 * real devolveria. O padrão (pedido -> resposta -> tratamento de erro) é
 * exatamente o mesmo que seria usado numa integração real.
 *
 * Para tornar isto real, seria necessário: (1) uma conta numa API de email
 * transacional, (2) a respetiva chave de API, e (3) substituir a leitura
 * do ficheiro local por um pedido HTTP (ex. com java.net.http.HttpClient)
 * para o endpoint dessa API.
 */
public class NotificacaoEmailService {

    private static final String CAMINHO_RESPOSTA_SIMULADA = "dados/simulacao_resposta_email.json";

    public ResultadoNotificacao enviarNotificacao(String destinatario, String assunto, String mensagem)
            throws NotificacaoExternaException {

        // 1. Pedido (real seria um HTTP POST; aqui só o registamos)
        System.out.println("[Serviço externo simulado] A enviar para " + destinatario + " — assunto: \"" + assunto + "\"");

        // 2. Resposta (simulada, lida de um ficheiro local que representa a resposta de uma API real)
        String resposta = lerRespostaSimulada();

        // 3. Tratamento de erro
        if (resposta.contains("\"status\":\"erro\"")) {
            throw new NotificacaoExternaException("O serviço externo respondeu com erro: " + resposta);
        }
        return new ResultadoNotificacao(true, resposta);
    }

    private String lerRespostaSimulada() throws NotificacaoExternaException {
        try {
            return Files.readString(Path.of(CAMINHO_RESPOSTA_SIMULADA));
        } catch (IOException erro) {
            throw new NotificacaoExternaException(
                    "Não foi possível ler a resposta simulada do serviço externo.", erro);
        }
    }
}
