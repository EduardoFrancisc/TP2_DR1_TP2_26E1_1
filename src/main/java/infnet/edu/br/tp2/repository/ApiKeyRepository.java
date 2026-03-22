package infnet.edu.br.tp2.repository;

import infnet.edu.br.tp2.model.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    Optional<ApiKey> findByKeyHash(String keyHash);

    List<ApiKey> findByOrganizacaoId(Long orgId);

    List<ApiKey> findByOrganizacaoIdAndAtivoTrue(Long orgId);

    Optional<ApiKey> findByOrganizacaoIdAndNome(Long orgId, String nome);
}