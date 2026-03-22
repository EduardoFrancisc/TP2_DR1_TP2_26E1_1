package infnet.edu.br.tp2.service;

import infnet.edu.br.tp2.dto.*;
import infnet.edu.br.tp2.model.*;
import infnet.edu.br.tp2.enums.*;
import infnet.edu.br.tp2.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AventuraService {

    private final AventureiroRepository aventureiroRepository;
    private final MissaoRepository missaoRepository;
    private final ParticipacaoMissaoRepository participacaoRepository;

    public Page<AventureiroSummaryDTO> listarAventureiros(Boolean ativo, ClasseAventureiro classe, Integer nivel, Pageable pageable) {
        return aventureiroRepository.findByAtivoAndClasseAndNivelGreaterThanEqual(ativo, classe, nivel, pageable)
                .map(a -> new AventureiroSummaryDTO(a.getId(), a.getNome(), a.getClasse().name(), a.getNivel(), a.getAtivo()));
    }

    public AventureiroFullDTO detalharAventureiro(Long id) {
        Aventureiro a = aventureiroRepository.findById(id).orElseThrow();
        Long total = participacaoRepository.countByAventureiroId(id);
        String ultimaMissao = participacaoRepository.findLastMissionTitleByAventureiroId(id);

        return new AventureiroFullDTO(
                a.getId(), a.getNome(), a.getClasse().name(), a.getNivel(),
                a.getCompanheiro() != null ? a.getCompanheiro().getNome() : "Nenhum",
                a.getCompanheiro() != null ? a.getCompanheiro().getEspecie() : "Nenhum",
                total, ultimaMissao
        );
    }

    public Page<AventureiroSummaryDTO> buscarPorNome(String nome, Pageable pageable) {
        return aventureiroRepository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(a -> new AventureiroSummaryDTO(
                        a.getId(),
                        a.getNome(),
                        a.getClasse().name(),
                        a.getNivel(),
                        a.getAtivo()
                ));
    }

    @Transactional
    public Aventureiro criarAventureiro(Aventureiro aventureiro) {
        if (aventureiro.getOrganizacao() == null) {
            throw new IllegalArgumentException("Aventureiro deve pertencer a uma organização.");
        }
        if (aventureiro.getNivel() < 1) {
            aventureiro.setNivel(1);
        }
        return aventureiroRepository.save(aventureiro);
    }

    // Consulta Operacional: Listagem de Missões
    public Page<MissaoSummaryDTO> listarMissoes(StatusMissao status, NivelPerigo nivel, OffsetDateTime inicio, OffsetDateTime fim, Pageable pageable) {
        return missaoRepository.findByStatusAndNivelPerigoAndCreatedAtBetween(status, nivel, inicio, fim, pageable)
                .map(m -> new MissaoSummaryDTO(m.getId(), m.getTitulo(), m.getStatus().name(), m.getNivelPerigo().name(), m.getCreatedAt()));
    }

    // Consulta Operacional: Detalhamento de Missão com Participantes
    public MissaoDetalheDTO detalharMissao(Long id) {
        Missao m = missaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Missão não encontrada"));

        List<ParticipanteDTO> participantes = m.getParticipantes().stream()
                .map(p -> new ParticipanteDTO(p.getAventureiro().getNome(), p.getPapel().name(), p.getRecompensaOuro(), p.getMvp()))
                .toList();

        return new MissaoDetalheDTO(
                new MissaoSummaryDTO(m.getId(), m.getTitulo(), m.getStatus().name(), m.getNivelPerigo().name(), m.getCreatedAt()),
                participantes
        );
    }

    // Relatório Gerencial: Métricas de Missões
    public List<MissaoMetricsDTO> gerarRelatorioMissoes(OffsetDateTime inicio, OffsetDateTime fim) {
        return participacaoRepository.obterMetricasPorPeriodo(inicio, fim);
    }

    @Transactional
    public ParticipacaoMissao registrarParticipacao(Long missaoId, Long aventureiroId, PapelMissao papel, BigDecimal recompensa) {

        Missao missao = missaoRepository.findById(missaoId)
                .orElseThrow(() -> new RuntimeException("Missão não encontrada."));

        Aventureiro aventureiro = aventureiroRepository.findById(aventureiroId)
                .orElseThrow(() -> new RuntimeException("Aventureiro não encontrado."));

        // 1. Validação de Organização (Não permitir relacionamento cruzado)
        if (!missao.getOrganizacao().getId().equals(aventureiro.getOrganizacao().getId())) {
            throw new IllegalStateException("O aventureiro deve pertencer à mesma organização da missão.");
        }

        // 2. Validação de Status do Aventureiro
        if (!aventureiro.getAtivo()) {
            throw new IllegalStateException("Um aventureiro inativo não pode ser associado a novas missões.");
        }

        // 3. Validação de Estado da Missão
        if (missao.getStatus() == StatusMissao.CONCLUIDA || missao.getStatus() == StatusMissao.CANCELADA) {
            throw new IllegalStateException("A missão já foi encerrada e não aceita novos participantes.");
        }

        // 4. Validação de Duplicidade (Garantia de não duplicidade)
        boolean jaParticipa = participacaoRepository.existsByMissaoIdAndAventureiroId(missaoId, aventureiroId);
        if (jaParticipa) {
            throw new IllegalStateException("Este aventureiro já está registrado nesta missão.");
        }

        // Criação do registro de participação
        ParticipacaoMissao participacao = new ParticipacaoMissao();
        participacao.setMissao(missao);
        participacao.setAventureiro(aventureiro);
        participacao.setPapel(papel);
        participacao.setRecompensaOuro(recompensa != null ? recompensa : BigDecimal.ZERO);
        participacao.setMvp(false);

        return participacaoRepository.save(participacao);
    }

    public List<AventureiroRankingDTO> obterRanking(OffsetDateTime inicio, OffsetDateTime fim) {
        return participacaoRepository.obterRanking(inicio, fim);
    }
}