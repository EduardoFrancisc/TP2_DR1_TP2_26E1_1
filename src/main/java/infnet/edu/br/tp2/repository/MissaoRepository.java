package infnet.edu.br.tp2.repository;

import infnet.edu.br.tp2.enums.NivelPerigo;
import infnet.edu.br.tp2.model.Missao;
import infnet.edu.br.tp2.enums.StatusMissao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface MissaoRepository extends JpaRepository<Missao, Long> {

    List<Missao> findByOrganizacaoId(Long orgId);

    List<Missao> findByOrganizacaoIdAndStatus(Long orgId, StatusMissao status);

    Page<Missao> findByStatusAndNivelPerigoAndCreatedAtBetween(
            StatusMissao status, NivelPerigo nivel,
            OffsetDateTime inicio, OffsetDateTime fim, Pageable pageable);
}