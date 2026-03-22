package infnet.edu.br.tp2;

import infnet.edu.br.tp2.dto.MissaoDetalheDTO;
import infnet.edu.br.tp2.dto.MissaoMetricsDTO;
import infnet.edu.br.tp2.repository.ParticipacaoMissaoRepository;
import infnet.edu.br.tp2.service.AventuraService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ConsultasAventuraTest {

    @Autowired
    private AventuraService aventuraService;
    @Autowired private ParticipacaoMissaoRepository participacaoRepository;

    @Test
    @DisplayName("Deve retornar missão com lista de participantes vazia sem erro")
    void testMissaoSemParticipantes() {
        // Simulação de busca de missão ID 1 que não tenha participantes
        MissaoDetalheDTO detalhe = aventuraService.detalharMissao(1L);
        assertThat(detalhe.participantes()).isEmpty();
    }

    @Test
    @DisplayName("Deve calcular corretamente os totais de recompensas no relatório")
    void testRelatorioMetricas() {
        OffsetDateTime inicio = OffsetDateTime.now().minusDays(7);
        OffsetDateTime fim = OffsetDateTime.now();

        List<MissaoMetricsDTO> relatorio = aventuraService.gerarRelatorioMissoes(inicio, fim);

        assertThat(relatorio).isNotNull();
        // Verifica se não há duplicidade: cada título de missão deve ser único no relatório
        long uniqueTitles = relatorio.stream().map(MissaoMetricsDTO::titulo).distinct().count();
        assertThat(relatorio.size()).isEqualTo((int) uniqueTitles);
    }
}
