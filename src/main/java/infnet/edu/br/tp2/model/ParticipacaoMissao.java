package infnet.edu.br.tp2.model;

import infnet.edu.br.tp2.enums.PapelMissao;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Getter @Setter
@Table(
        name = "participacoes_missao",
        schema = "aventura",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_missao_aventureiro",
                columnNames = {"missao_id", "aventureiro_id"}
        )
)
public class ParticipacaoMissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "missao_id", nullable = false)
    private Missao missao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aventureiro_id", nullable = false)
    private Aventureiro aventureiro;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel_missao", nullable = false)
    private PapelMissao papel;

    @DecimalMin(value = "0.0", message = "A recompensa não pode ser negativa")
    @Column(name = "recompensa_ouro")
    private BigDecimal recompensaOuro;

    @Column(nullable = false)
    private Boolean mvp = false;

    @Column(name = "data_registro", updatable = false)
    private OffsetDateTime dataRegistro;

    @PrePersist
    protected void onCreate() { this.dataRegistro = OffsetDateTime.now(); }
}