package com.lp3.projeto.service;

import com.lp3.projeto.model.entity.Produto;
import com.lp3.projeto.model.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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


}
