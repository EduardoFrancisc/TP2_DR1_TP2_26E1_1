package infnet.edu.br.tp2.dto;

public record AventureiroFullDTO(
        Long id, String nome, String classe, Integer nivel,
        String nomeCompanheiro, String especieCompanheiro,
        Long totalParticipacoes, String tituloUltimaMissao
) {}
