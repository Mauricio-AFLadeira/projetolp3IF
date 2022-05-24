package com.lp3.projeto.service;

import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Produto;
import com.lp3.projeto.model.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProdutoService {

    private ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository){
        this.repository=repository;
    }

    public List<Produto> getProdutos(){
        return repository.findAll();
    }

    public Optional<Produto> getProdutoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Produto salvar(Produto produto){
        validar(produto);
        return repository.save(produto);
    }

    @Transactional
    public void excluir(Produto produto){
        Objects.requireNonNull(produto.getId());
        repository.delete(produto);
    }

    public void validar(Produto produto) {

    }
}
