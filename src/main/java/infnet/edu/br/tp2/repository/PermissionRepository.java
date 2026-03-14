package infnet.edu.br.tp2.repository;

import infnet.edu.br.tp2.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    // O 'code' é a UK (Unique Key) desta tabela (ex: 'ROLE_ADMIN' ou 'OP_CREATE')
    // Usado para verificar se a permissão existe antes de vinculá-la a uma Role
    Optional<Permission> findByCode(String code);
}