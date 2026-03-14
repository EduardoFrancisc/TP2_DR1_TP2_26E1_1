package infnet.edu.br.tp2.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Data
@Table(
        name = "roles",
        schema = "audit",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_role_org_nome", columnNames = {"organizacao_id", "nome"})
        }
)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    // Relacionamento Muitos-para-Muitos com Permissions (através da tabela de junção)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            schema = "audit",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;

    // Relacionamento inverso com Usuario (mapeado pelo campo 'roles' na classe Usuario)
    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> usuarios;
}
