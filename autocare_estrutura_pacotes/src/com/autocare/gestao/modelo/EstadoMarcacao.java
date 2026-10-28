package com.autocare.gestao.modelo;

import java.io.Serializable;

/**
 * Estados possíveis de uma Marcação.
 * AGENDADA é sempre o estado inicial. CONCLUIDA e CANCELADA são estados finais:
 * uma vez lá chegada, a marcação não pode mudar de estado outra vez.
 */
public enum EstadoMarcacao implements Serializable {
    AGENDADA,
    CONCLUIDA,
    CANCELADA
}
