package com.lp3.projeto.service;

import com.lp3.projeto.model.entity.PessoaJuridica;
import com.lp3.projeto.model.repository.PessoaJuridicaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PessoaJuridicaService {

    private PessoaJuridicaRepository repository;

    public PessoaJuridicaService(PessoaJuridicaRepository repository){
        this.repository=repository;
    }

    public List<PessoaJuridica> getPessoaJuridica(){
        return repository.findAll();
    }

    public Optional<PessoaJuridica> getPessoaJuridicaById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public PessoaJuridica salvar(PessoaJuridica pessoaJuridica){
        validar(pessoaJuridica);
        return repository.save(pessoaJuridica);
    }

    @Transactional
    public void excluir(PessoaJuridica pessoaJuridica){
        Objects.requireNonNull(pessoaJuridica);
        repository.delete(pessoaJuridica);
    }

    public void validar(PessoaJuridica pessoaJuridica){

    }
}
