package infnet.edu.br.tp2.controller;

import infnet.edu.br.tp2.dto.AventureiroFullDTO;
import infnet.edu.br.tp2.dto.AventureiroSummaryDTO;
import infnet.edu.br.tp2.enums.ClasseAventureiro;
import infnet.edu.br.tp2.service.AventuraService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aventureiros")
@RequiredArgsConstructor
public class AventureiroController {

    private final AventuraService aventuraService;

    @GetMapping
    public ResponseEntity<Page<AventureiroSummaryDTO>> listar(
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) ClasseAventureiro classe,
            @RequestParam(defaultValue = "1") Integer nivelMin,
            @PageableDefault(sort = "nome") Pageable pageable) {

        return ResponseEntity.ok(aventuraService.listarAventureiros(ativo, classe, nivelMin, pageable));
    }

    @GetMapping("/busca")
    public ResponseEntity<Page<AventureiroSummaryDTO>> buscarPorNome(
            @RequestParam String nome,
            @PageableDefault(sort = "nome") Pageable pageable) {

        return ResponseEntity.ok(aventuraService.buscarPorNome(nome, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AventureiroFullDTO> obterPerfilCompleto(@PathVariable Long id) {
        return ResponseEntity.ok(aventuraService.detalharAventureiro(id));
    }
}