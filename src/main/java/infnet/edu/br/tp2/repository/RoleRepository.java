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

    // Busca uma role específica pelo nome dentro de uma organização
    Optional<Role> findByOrganizacaoIdAndNome(Long orgId, String nome);

    // Cumpre o requisito: "Listar roles com suas permissions"
    // O FETCH garante que as permissões venham junto na mesma consulta
    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.permissions WHERE r.organizacao.id = :orgId")
    List<Role> findByOrganizacaoIdWithPermissions(@Param("orgId") Long orgId);

    // Busca todas as roles de uma organização
    List<Role> findByOrganizacaoId(Long orgId);
}