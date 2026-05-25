package com.example.demo.controller;

import com.example.demo.dto.ProdutoDTO;
import com.example.demo.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    // Injeção de dependência por construtor (Exigência da atividade)
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // POST /produto - 201 Created
    @PostMapping
    public ResponseEntity<ProdutoDTO> cadastrarProduto(@Valid @RequestBody ProdutoDTO dto) {
        ProdutoDTO novoDto = produtoService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoDto);
    }

    // GET /produto - 200 OK
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarProdutos() {
        List<ProdutoDTO> lista = produtoService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    // GET /produto/{id} - 200 OK ou 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        Optional<ProdutoDTO> dto = produtoService.buscarPorId(id);
        return dto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT /produto/{id} - 200 OK ou 404 Not Found
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoDTO dto) {
        Optional<ProdutoDTO> atualizado = produtoService.atualizar(id, dto);
        return atualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /produto/{id} - 200 OK ou 404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProduto(@PathVariable Long id) {
        boolean deletado = produtoService.deletar(id);
        if (deletado) {
            return ResponseEntity.ok().build(); // A professora pediu 200 OK para remoção nesta atividade
        }
        return ResponseEntity.notFound().build();
    }
}