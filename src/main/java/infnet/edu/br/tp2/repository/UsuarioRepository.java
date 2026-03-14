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

    // Essencial para o login e validação de duplicidade
    Optional<Usuario> findByEmail(String email);

    // Cumpre o requisito: "Listar usuários com suas roles"
    // O 'JOIN FETCH' evita o problema de N+1 consultas no banco
    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.roles WHERE u.organizacao.id = :orgId")
    List<Usuario> findByOrganizacaoIdWithRoles(@Param("orgId") Long orgId);

    // Busca usuários por status dentro de uma organização
    List<Usuario> findByOrganizacaoIdAndStatus(Long orgId, String status);
}