package com.lp3.projeto.service;

import com.lp3.projeto.exception.RegraNegocioException;
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

        if (pessoaJuridica.getEmail()==null || pessoaJuridica.getEmail().trim().equals("")){
            throw new RegraNegocioException("email não informado");
        }
        if (pessoaJuridica.getEmpresa()==null || pessoaJuridica.getEmpresa().trim().equals("")){
            throw new RegraNegocioException("Empresa não informada");
        }
        if (pessoaJuridica.getLogin()==null || pessoaJuridica.getLogin().trim().equals("")){
            throw new RegraNegocioException("Login não inserido");
        }
        if (pessoaJuridica.getSenha()==null || pessoaJuridica.getSenha().trim().equals("")){
            throw new RegraNegocioException("Senha não inserida");
        }
        if (pessoaJuridica.getTelefone()==null || pessoaJuridica.getTelefone().trim().equals("")){
            throw new RegraNegocioException("Telefone não especificado");
        }
        if (pessoaJuridica.getResponsavel()==null || pessoaJuridica.getResponsavel().trim().equals("")){
            throw new RegraNegocioException("Responsável não informado");
        }
        if (pessoaJuridica.getNome()==null || pessoaJuridica.getNome().trim().equals("")){
            throw new RegraNegocioException("Nome não inserido");
        }
        if (pessoaJuridica.getCnpj()==null || pessoaJuridica.getCnpj().trim().equals("")){
            throw new RegraNegocioException("CNPJ não informado");
        }
    }
}
