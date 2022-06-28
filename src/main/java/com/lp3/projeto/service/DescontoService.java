package com.lp3.projeto.service;

import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Desconto;
import com.lp3.projeto.model.repository.DescontoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DescontoService {

    private DescontoRepository repository;

    public DescontoService(DescontoRepository repository){
        this.repository=repository;
    }

    public List<Desconto> getDesconto(){
        return repository.findAll();
    }

    public Optional<Desconto> getDescontoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Desconto salvar(Desconto desconto){
        validar(desconto);
        return repository.save(desconto);
    }

    @Transactional
    public void excluir(Desconto desconto){
        Objects.requireNonNull(desconto.getId());
        repository.delete(desconto);
    }

    public void validar(Desconto desconto) {

        if (desconto.getCategoria()==null){
            throw new RegraNegocioException("Informe para qual categoria o desconto será aplicado");
        }
        if (desconto.getDataInicio()==null){
            throw new RegraNegocioException("Informe a data de inicio na qual o desconto será aplicado");
        }
        if (desconto.getDataVencimento()==null){
            throw new RegraNegocioException("Informe a data de vencimento na qual o desconto será aplicado");
        }

    }
}
