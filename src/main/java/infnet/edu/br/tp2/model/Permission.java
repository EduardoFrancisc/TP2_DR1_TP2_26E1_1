package infnet.edu.br.tp2.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "permissions", schema = "audit")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    private String descricao;
}