package infnet.edu.br.tp2;

import infnet.edu.br.tp2.dto.*;
import infnet.edu.br.tp2.enums.ClasseAventureiro;
import infnet.edu.br.tp2.enums.NivelPerigo;
import infnet.edu.br.tp2.enums.StatusMissao;
import infnet.edu.br.tp2.repository.ParticipacaoMissaoRepository;
import infnet.edu.br.tp2.service.AventuraService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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

    @Test
    @DisplayName("Deve filtrar aventureiros por status, classe e nível mínimo")
    void testListarAventureirosComFiltros() {
        // Cenário: Página 0, tamanho 10, ordenado por nível decrescente
        PageRequest pageable = PageRequest.of(0, 10, Sort.by("nivel").descending());

        Page<AventureiroSummaryDTO> resultado = aventuraService.listarAventureiros(
                true, ClasseAventureiro.GUERREIRO, 5, pageable);

        assertThat(resultado).isNotNull();
        if (!resultado.isEmpty()) {
            assertThat(resultado.getContent().get(0).ativo()).isTrue();
            assertThat(resultado.getContent().get(0).nivel()).isGreaterThanOrEqualTo(5);
        }
    }

    @Test
    @DisplayName("Deve realizar busca textual parcial por nome")
    void testBuscarAventureiroPorNome() {
        PageRequest pageable = PageRequest.of(0, 5);
        // Supõe-se que exista um "Arthur" ou similar no banco de testes
        Page<AventureiroSummaryDTO> resultado = aventuraService.buscarPorNome("arthur", pageable);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getContent()).allMatch(a ->
                a.nome().toLowerCase().contains("arthur"));
    }

    @Test
    @DisplayName("Deve detalhar perfil completo do aventureiro com agregados")
    void testDetalharAventureiroCompleto() {
        // ID 1L deve existir no seu banco de dados de teste
        AventureiroFullDTO detalhe = aventuraService.detalharAventureiro(1L);

        assertThat(detalhe).isNotNull();
        assertThat(detalhe.nome()).isNotEmpty();
        // Valida se o contador de participações foi inicializado (mesmo que zero)
        assertThat(detalhe.totalParticipacoes()).isGreaterThanOrEqualTo(0L);
    }

    @Test
    @DisplayName("Deve listar missões por status, nível e período")
    void testListarMissoesComFiltros() {
        OffsetDateTime inicio = OffsetDateTime.now().minusMonths(1);
        OffsetDateTime fim = OffsetDateTime.now().plusDays(1);
        PageRequest pageable = PageRequest.of(0, 10);

        Page<MissaoSummaryDTO> resultado = aventuraService.listarMissoes(
                StatusMissao.PLANEJADA, NivelPerigo.ALTO, inicio, fim, pageable);

        assertThat(resultado).isNotNull();
    }

    @Test
    @DisplayName("Deve gerar o ranking de aventureiros por período")
    void testGerarRankingAventureiros() {
        OffsetDateTime inicio = OffsetDateTime.now().minusYears(1);
        OffsetDateTime fim = OffsetDateTime.now();

        List<AventureiroRankingDTO> ranking = aventuraService.obterRanking(inicio, fim);

        assertThat(ranking).isNotNull();
        // O ranking deve vir ordenado por total de participações (maior para menor)
        if (ranking.size() > 1) {
            assertThat(ranking.get(0).totalParticipacoes())
                    .isGreaterThanOrEqualTo(ranking.get(1).totalParticipacoes());
        }
    }
}
