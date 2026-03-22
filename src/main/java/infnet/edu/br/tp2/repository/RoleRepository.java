package infnet.edu.br.tp2.repository;

import infnet.edu.br.tp2.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByOrganizacaoIdAndNome(Long orgId, String nome);

    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.permissions WHERE r.organizacao.id = :orgId")
    List<Role> findByOrganizacaoIdWithPermissions(@Param("orgId") Long orgId);

    List<Role> findByOrganizacaoId(Long orgId);
}