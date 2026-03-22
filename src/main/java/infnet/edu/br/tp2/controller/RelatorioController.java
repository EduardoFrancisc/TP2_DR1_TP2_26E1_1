package infnet.edu.br.tp2.controller;

import infnet.edu.br.tp2.dto.AventureiroRankingDTO;
import infnet.edu.br.tp2.dto.MissaoMetricsDTO;
import infnet.edu.br.tp2.service.AventuraService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final AventuraService aventuraService;

    /**
     * REQUISITO: Ranking de Participação
     * Baseado em total de participações, recompensas e MVPs por período.
     */
    @GetMapping("/ranking-aventureiros")
    public ResponseEntity<List<AventureiroRankingDTO>> obterRanking(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fim) {

        return ResponseEntity.ok(aventuraService.obterRanking(inicio, fim));
    }

    /**
     * REQUISITO: Relatório de Missões com Métricas
     * Apresenta quantidade de participantes e total de ouro distribuído por missão.
     */
    @GetMapping("/missoes-metricas")
    public ResponseEntity<List<MissaoMetricsDTO>> gerarRelatorioMissoes(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fim) {

        return ResponseEntity.ok(aventuraService.gerarRelatorioMissoes(inicio, fim));
    }
}