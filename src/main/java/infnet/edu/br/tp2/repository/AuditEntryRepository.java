package infnet.edu.br.tp2.repository;

import infnet.edu.br.tp2.model.AuditEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface AuditEntryRepository extends JpaRepository<AuditEntry, Long> {

    // Busca todas as ações de uma organização específica
    List<AuditEntry> findByOrganizacaoId(Long orgId);

    // Busca o histórico de ações de um usuário específico
    List<AuditEntry> findByActorUserId(Long userId);

    // Busca ações realizadas por uma API Key
    List<AuditEntry> findByActorApiKeyId(Long apiKeyId);

    // Busca por um intervalo de tempo (essencial para relatórios de auditoria)
    List<AuditEntry> findByOccurredAtBetween(OffsetDateTime start, OffsetDateTime end);

    // Busca paginada (boa prática para tabelas de log que ficam gigantes)
    Page<AuditEntry> findByOrganizacaoId(Long orgId, Pageable pageable);
}