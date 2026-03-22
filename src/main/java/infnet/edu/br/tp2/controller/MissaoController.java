package infnet.edu.br.tp2.controller;

import infnet.edu.br.tp2.dto.MissaoDetalheDTO;
import infnet.edu.br.tp2.dto.MissaoSummaryDTO;
import infnet.edu.br.tp2.enums.NivelPerigo;
import infnet.edu.br.tp2.enums.StatusMissao;
import infnet.edu.br.tp2.service.AventuraService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/missoes")
@RequiredArgsConstructor
public class MissaoController {

    private final AventuraService aventuraService;

    @GetMapping
    public ResponseEntity<Page<MissaoSummaryDTO>> listar(
            @RequestParam(required = false) StatusMissao status,
            @RequestParam(required = false) NivelPerigo nivel,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fim,
            @PageableDefault(sort = "createdAt") Pageable pageable) {

        return ResponseEntity.ok(aventuraService.listarMissoes(status, nivel, inicio, fim, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissaoDetalheDTO> obterDetalhes(@PathVariable Long id) {
        return ResponseEntity.ok(aventuraService.detalharMissao(id));
    }
}