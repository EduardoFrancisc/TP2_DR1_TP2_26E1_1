package infnet.edu.br.tp2.dto;

import infnet.edu.br.tp2.enums.NivelPerigo;
import infnet.edu.br.tp2.enums.StatusMissao;
import java.math.BigDecimal;

public record MissaoMetricsDTO(
        String titulo,
        StatusMissao status,       // Alterado de String para o Enum
        NivelPerigo nivelPerigo,   // Alterado de String para o Enum
        Long qtdParticipantes,
        BigDecimal totalRecompensas
) {}