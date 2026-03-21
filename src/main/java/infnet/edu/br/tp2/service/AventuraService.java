package infnet.edu.br.tp2.service;

import infnet.edu.br.tp2.model.*;
import infnet.edu.br.tp2.enums.*;
import infnet.edu.br.tp2.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AventuraService {

    private final AventureiroRepository aventureiroRepository;
    private final MissaoRepository missaoRepository;
    private final ParticipacaoMissaoRepository participacaoRepository;

    /**
     * Regra: Um aventureiro não pode existir sem organização.
     * Regra: Nível mínimo permitido: 1.
     */
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

    /**
     * Regra: Um mesmo aventureiro não pode participar mais de uma vez da mesma missão.
     * Regra: Apenas aventureiros da mesma organização da missão podem participar.
     * Regra: Um aventureiro inativo não pode ser associado.
     * Regra: A missão deve estar em estado compatível (PLANEJADA ou EM_ANDAMENTO).
     */
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
}