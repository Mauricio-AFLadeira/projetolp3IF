package com.lp3.projeto.service;

import com.lp3.projeto.model.entity.PessoaFisica;
import com.lp3.projeto.model.repository.PessoaFisicaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PessoaFisicaService {

    private PessoaFisicaRepository repository;

    public PessoaFisicaService(PessoaFisicaRepository repository){
        this.repository=repository;
    }

    public List<PessoaFisica> getPessoaFisica(){
        return repository.findAll();
    }

    public Optional<PessoaFisica> getPessoaFisicaById(Long id){
        return  repository.findById(id);
    }

    @Transactional
    public PessoaFisica salvar(PessoaFisica pessoaFisica){
        validar(pessoaFisica);
        return repository.save(pessoaFisica);
    }

    @Transactional
    public void excluir(PessoaFisica pessoaFisica){
        Objects.requireNonNull(pessoaFisica);
        repository.delete(pessoaFisica);
    }

    public void validar(PessoaFisica pessoaFisica){

    }
}
