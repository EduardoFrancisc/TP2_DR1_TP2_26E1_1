package infnet.edu.br.tp2.repository;

import infnet.edu.br.tp2.model.Companheiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanheiroRepository extends JpaRepository<Companheiro, Long> {
    // O ID do companheiro é o mesmo ID do Aventureiro
}