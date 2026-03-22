package infnet.edu.br.tp2.repository;

import infnet.edu.br.tp2.dto.AventureiroRankingDTO;
import infnet.edu.br.tp2.dto.MissaoMetricsDTO;
import infnet.edu.br.tp2.model.ParticipacaoMissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface ParticipacaoMissaoRepository extends JpaRepository<ParticipacaoMissao, Long> {

    @Query("SELECT COUNT(p) FROM ParticipacaoMissao p WHERE p.aventureiro.id = :id")
    Long countByAventureiroId(Long id);

    @Query("SELECT p.missao.titulo FROM ParticipacaoMissao p " +
            "WHERE p.aventureiro.id = :id ORDER BY p.dataRegistro DESC LIMIT 1")
    String findLastMissionTitleByAventureiroId(Long id);

    // Ranking de Participação
    @Query("SELECT new infnet.edu.br.tp2.dto.AventureiroRankingDTO(" +
            "p.aventureiro.id, p.aventureiro.nome, COUNT(p), SUM(p.recompensaOuro), " +
            "SUM(CASE WHEN p.mvp = true THEN 1 ELSE 0 END)) " +
            "FROM ParticipacaoMissao p " +
            "WHERE p.dataRegistro BETWEEN :inicio AND :fim " +
            "GROUP BY p.aventureiro.id, p.aventureiro.nome " +
            "ORDER BY COUNT(p) DESC")
    List<AventureiroRankingDTO> obterRanking(OffsetDateTime inicio, OffsetDateTime fim);

    @Query("SELECT new infnet.edu.br.tp2.dto.MissaoMetricsDTO(" +
            "p.missao.titulo, p.missao.status, p.missao.nivelPerigo, " +
            "COUNT(p), SUM(COALESCE(p.recompensaOuro, 0))) " +
            "FROM ParticipacaoMissao p " +
            "WHERE p.missao.createdAt BETWEEN :inicio AND :fim " +
            "GROUP BY p.missao.id, p.missao.titulo, p.missao.status, p.missao.nivelPerigo")
    List<MissaoMetricsDTO> obterMetricasPorPeriodo(OffsetDateTime inicio, OffsetDateTime fim);

    boolean existsByMissaoIdAndAventureiroId(Long missaoId, Long aventureiroId);

    List<ParticipacaoMissao> findByMissaoId(Long missaoId);

    List<ParticipacaoMissao> findByAventureiroId(Long aventureiroId);
}