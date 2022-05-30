package com.lp3.projeto.service;

import com.lp3.projeto.model.entity.FormaPagamento;
import com.lp3.projeto.model.repository.FormaPagamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FormaPagamentoService {

    private FormaPagamentoRepository repository;

    public FormaPagamentoService (FormaPagamentoRepository repository){
        this.repository=repository;
    }

    public List<FormaPagamento> getFormaDePagamento(){
        return repository.findAll();
    }

    public Optional<FormaPagamento> getFormaDePagamentoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento){
        validar(formaPagamento);
        return repository.save(formaPagamento);
    }

    @Transactional
    public void excluir(FormaPagamento formaPagamento){
        Objects.requireNonNull(formaPagamento);
        repository.delete(formaPagamento);
    }

    public void validar(FormaPagamento formaPagamento){

    }
}
