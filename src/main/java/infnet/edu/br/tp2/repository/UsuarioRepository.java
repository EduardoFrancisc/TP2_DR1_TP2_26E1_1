package infnet.edu.br.tp2.repository;

import infnet.edu.br.tp2.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.roles WHERE u.organizacao.id = :orgId")
    List<Usuario> findByOrganizacaoIdWithRoles(@Param("orgId") Long orgId);

    List<Usuario> findByOrganizacaoIdAndStatus(Long orgId, String status);
}