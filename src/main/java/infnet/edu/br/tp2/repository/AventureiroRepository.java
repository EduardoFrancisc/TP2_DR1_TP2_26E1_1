package infnet.edu.br.tp2.repository;

import infnet.edu.br.tp2.enums.ClasseAventureiro;
import infnet.edu.br.tp2.model.Aventureiro;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {

    List<Aventureiro> findByOrganizacaoId(Long orgId);

    List<Aventureiro> findByOrganizacaoIdAndAtivoTrue(Long orgId);

    Page<Aventureiro> findByAtivoAndClasseAndNivelGreaterThanEqual(
            Boolean ativo, ClasseAventureiro classe, Integer nivelMin, Pageable pageable);

    Page<Aventureiro> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}