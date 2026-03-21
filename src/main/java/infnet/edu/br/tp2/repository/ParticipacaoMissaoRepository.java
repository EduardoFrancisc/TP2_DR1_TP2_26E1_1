package infnet.edu.br.tp2.repository;

import infnet.edu.br.tp2.model.ParticipacaoMissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ParticipacaoMissaoRepository extends JpaRepository<ParticipacaoMissao, Long> {

    // Essencial para a regra: "A participação é única para o par (Missão, Aventureiro)"
    boolean existsByMissaoIdAndAventureiroId(Long missaoId, Long aventureiroId);

    // Para listar todos os aventureiros de uma missão específica
    List<ParticipacaoMissao> findByMissaoId(Long missaoId);

    // Para listar todas as missões de um aventureiro específico
    List<ParticipacaoMissao> findByAventureiroId(Long aventureiroId);
}