package com.lp3.projeto.service;

import com.lp3.projeto.exception.RegraNegocioException;
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
        if (pessoaFisica.getEmail()==null || pessoaFisica.getEmail().trim().equals("")){
            throw new RegraNegocioException("email não solicitado");
        }
        if (pessoaFisica.getLogin()==null || pessoaFisica.getLogin().trim().equals("")){
            throw new RegraNegocioException("login não informado");
        }
        if (pessoaFisica.getSenha()==null || pessoaFisica.getSenha().trim().equals("")){
            throw new RegraNegocioException("senha não informada");
        }
        if (pessoaFisica.getNome()==null || pessoaFisica.getNome().trim().equals("")){
            throw new RegraNegocioException("nome não inserido");
        }
        if (pessoaFisica.getTelefone()==null || pessoaFisica.getTelefone().trim().equals("")){
            throw new RegraNegocioException("insira um telefone");
        }
        if (pessoaFisica.getCpf()==null || pessoaFisica.getCpf().trim().equals("")){
            throw new RegraNegocioException("Insira um CPF válido");
        }
        if (pessoaFisica.getDataDeNascimento()==null || pessoaFisica.getDataDeNascimento().trim().equals("")){
            throw new RegraNegocioException("Data não informada");
        }
    }
}
