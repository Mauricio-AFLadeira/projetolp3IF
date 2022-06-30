package com.lp3.projeto.service;

import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Pessoa;
import com.lp3.projeto.model.repository.PessoaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PessoaService {

    private PessoaRepository repository;

    public PessoaService(PessoaRepository repository){
        this.repository=repository;
    }

    public List<Pessoa> getPessoa(){
        return repository.findAll();
    }

    public Optional<Pessoa> getPessoaById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Pessoa salvar(Pessoa Pessoa){
        validar(Pessoa);
        return repository.save(Pessoa);
    }

    @Transactional
    public void excluir(Pessoa Pessoa){
        Objects.requireNonNull(Pessoa.getId());
        repository.delete(Pessoa);
    }

    public void validar(Pessoa Pessoa){

    }
}
