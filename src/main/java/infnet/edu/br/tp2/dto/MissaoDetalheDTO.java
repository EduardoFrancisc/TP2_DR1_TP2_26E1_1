package infnet.edu.br.tp2.dto;

import java.util.List;

public record MissaoDetalheDTO(MissaoSummaryDTO info, List<ParticipanteDTO> participantes) {}
