package infnet.edu.br.tp2.dto;

import java.time.OffsetDateTime;

public record MissaoSummaryDTO(Long id, String titulo, String status, String nivelPerigo, OffsetDateTime createdAt) {}
