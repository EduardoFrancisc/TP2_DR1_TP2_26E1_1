package infnet.edu.br.tp2.repository;

import infnet.edu.br.tp2.model.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    // Método crucial: usado para validar uma chave que vem em um Header de API
    Optional<ApiKey> findByKeyHash(String keyHash);

    // Busca todas as chaves de uma organização específica
    List<ApiKey> findByOrganizacaoId(Long orgId);

    // Busca apenas as chaves que estão ativas em uma organização
    List<ApiKey> findByOrganizacaoIdAndAtivoTrue(Long orgId);

    // Busca uma chave específica pelo nome (lembrando que nome + org é UK)
    Optional<ApiKey> findByOrganizacaoIdAndNome(Long orgId, String nome);
}