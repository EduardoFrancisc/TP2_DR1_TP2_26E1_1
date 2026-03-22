package infnet.edu.br.tp2.dto;

import java.math.BigDecimal;

public record AventureiroRankingDTO(
        Long aventureiroId,
        String nome,
        Long totalParticipacoes,
        BigDecimal somaRecompensas,
        Long totalMVPs // O SUM(CASE...) de booleanos no Postgres/Hibernate costuma retornar Long
) {}