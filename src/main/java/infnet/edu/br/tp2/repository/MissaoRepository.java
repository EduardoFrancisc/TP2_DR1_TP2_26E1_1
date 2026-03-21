package infnet.edu.br.tp2.repository;

import infnet.edu.br.tp2.model.Missao;
import infnet.edu.br.tp2.enums.StatusMissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MissaoRepository extends JpaRepository<Missao, Long> {

    // Regra: "Missões pertencem exclusivamente a uma organização"
    List<Missao> findByOrganizacaoId(Long orgId);

    // Busca missões por status (ex: PLANEJADA) para validar novas entradas
    List<Missao> findByOrganizacaoIdAndStatus(Long orgId, StatusMissao status);
}