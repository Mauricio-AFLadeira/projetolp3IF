package com.lp3.projeto.service;

import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Endereco;
import com.lp3.projeto.model.repository.EnderecoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {
    private EnderecoRepository repository;

    public EnderecoService(EnderecoRepository repository) {
        this.repository = repository;
    }

    public List<Endereco> getEnderecos() {
        return repository.findAll();
    }

    public Optional<Endereco> getEnderecoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Endereco salvar(Endereco endereco) {
        return repository.save(endereco);
    }

    public void validar(Endereco endereco){

        if (endereco.getLogradouro()==null || endereco.getLogradouro().trim().equals("")){
            throw  new RegraNegocioException("Logradouro não informado");
        }
        if (endereco.getNumero()==null){
            throw  new RegraNegocioException("Número não informado");
        }
        if (endereco.getBairro()==null || endereco.getBairro().trim().equals("")){
            throw  new RegraNegocioException("Bairro não informado");
        }
        if (endereco.getCidade()==null || endereco.getCidade().trim().equals("")){
            throw  new RegraNegocioException("Cidade não informada");
        }
        if (endereco.getUf()==null || endereco.getUf().trim().equals("")){
            throw  new RegraNegocioException("UF não informado");
        }
        if (endereco.getCep()==null || endereco.getCep().trim().equals("")){
            throw  new RegraNegocioException("CEP não informado");
        }
    }
}
