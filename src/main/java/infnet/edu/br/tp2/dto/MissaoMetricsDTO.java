package infnet.edu.br.tp2.dto;

import infnet.edu.br.tp2.enums.NivelPerigo;
import infnet.edu.br.tp2.enums.StatusMissao;
import java.math.BigDecimal;

public record MissaoMetricsDTO(
        String titulo,
        StatusMissao status,
        NivelPerigo nivelPerigo,
        Long qtdParticipantes,
        BigDecimal totalRecompensas
) {}