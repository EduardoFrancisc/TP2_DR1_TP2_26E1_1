package infnet.edu.br.tp2.repository;

import infnet.edu.br.tp2.model.Aventureiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {

    // Regra: "Um aventureiro pertence exclusivamente a uma organização"
    List<Aventureiro> findByOrganizacaoId(Long orgId);

    // Busca apenas aventureiros ativos de uma organização
    List<Aventureiro> findByOrganizacaoIdAndAtivoTrue(Long orgId);
}