package infnet.edu.br.tp2.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;

@Entity
@Data
@Table(name = "audit_entries", schema = "audit")
public class AuditEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_user_id")
    private Usuario actorUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_api_key_id")
    private ApiKey actorApiKey;

    @Column(nullable = false)
    private String action;

    @Column(name = "entity_schema")
    private String entitySchema;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "entity_id")
    private String entityId;

    @Column(name = "occurred_at", nullable = false)
    private OffsetDateTime occurredAt;

    @Column(name = "ip", columnDefinition = "inet")
    private String ip;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "diff", columnDefinition = "jsonb")
    private String diff;

    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metadata;

    @Column(nullable = false)
    private Boolean success;
}
