package com.example.demo.service;

import com.example.demo.dto.ProdutoDTO;
import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    // Injeção de dependência por construtor (Exigência da atividade)
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // POST - Cadastrar
    public ProdutoDTO cadastrar(ProdutoDTO dto) {
        Produto produto = deDtoParaEntidade(dto);
        Produto salvo = produtoRepository.save(produto);
        return deEntidadeParaDto(salvo);
    }

    // GET - Listar todos
    public List<ProdutoDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(this::deEntidadeParaDto)
                .collect(Collectors.toList());
    }

    // GET/{id} - Buscar por ID
    public Optional<ProdutoDTO> buscarPorId(Long id) {
        return produtoRepository.findById(id).map(this::deEntidadeParaDto);
    }

    // PUT - Atualizar
    public Optional<ProdutoDTO> atualizar(Long id, ProdutoDTO dto) {
        return produtoRepository.findById(id).map(produtoExistente -> {
            produtoExistente.setNome(dto.getNome());
            produtoExistente.setPreco(dto.getPreco());
            produtoExistente.setCategoria(dto.getCategoria());
            Produto atualizado = produtoRepository.save(produtoExistente);
            return deEntidadeParaDto(atualizado);
        });
    }

    // DELETE - Remover
    public boolean deletar(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Métodos auxiliares de conversão (Mapeamento manual)
    private Produto deDtoParaEntidade(ProdutoDTO dto) {
        return new Produto(dto.getId(), dto.getNome(), dto.getPreco(), dto.getCategoria());
    }

    private ProdutoDTO deEntidadeParaDto(Produto produto) {
        return new ProdutoDTO(produto.getId(), produto.getNome(), produto.getPreco(), produto.getCategoria());
    }
}