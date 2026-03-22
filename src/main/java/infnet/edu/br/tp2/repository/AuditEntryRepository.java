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

    List<AuditEntry> findByOrganizacaoId(Long orgId);

    List<AuditEntry> findByActorUserId(Long userId);

    List<AuditEntry> findByActorApiKeyId(Long apiKeyId);

    List<AuditEntry> findByOccurredAtBetween(OffsetDateTime start, OffsetDateTime end);

    Page<AuditEntry> findByOrganizacaoId(Long orgId, Pageable pageable);
}