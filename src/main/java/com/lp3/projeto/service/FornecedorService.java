package com.lp3.projeto.service;

import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Fornecedor;
import com.lp3.projeto.model.repository.FornecedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FornecedorService {

    private FornecedorRepository repository;

    public FornecedorService(FornecedorRepository repository){
        this.repository=repository;
    }

    public List<Fornecedor> getFornecedor(){
        return repository.findAll();
    }

    public Optional<Fornecedor> getFornecdorById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Fornecedor salvar(Fornecedor fornecedor){
        validar(fornecedor);
        return repository.save(fornecedor);
    }

    @Transactional
    public void excluir(Fornecedor fornecedor){
        Objects.requireNonNull(fornecedor.getId());
        repository.delete(fornecedor);
    }

    public void validar(Fornecedor fornecedor) {
        if (fornecedor.getCnpj()==null || fornecedor.getCnpj().trim().equals("")){
            throw new RegraNegocioException("CNPJ não informado");
        }
        if (fornecedor.getInscricaoEstadual()==null || fornecedor.getInscricaoEstadual().trim().equals("")){
            throw new RegraNegocioException("Inscrição Estadual não informada");
        }
        if (fornecedor.getEmail()==null || fornecedor.getEmail().trim().equals("")){
            throw new RegraNegocioException("email não informado");
        }
        if (fornecedor.getEmpresa()==null || fornecedor.getEmpresa().trim().equals("")){
            throw new RegraNegocioException("Empresa não informada");
        }
        if (fornecedor.getLogin()==null || fornecedor.getLogin().trim().equals("")){
            throw new RegraNegocioException("Login não inserido");
        }
        if (fornecedor.getSenha()==null || fornecedor.getSenha().trim().equals("")){
            throw new RegraNegocioException("Senha não inserida");
        }
        if (fornecedor.getTelefone()==null || fornecedor.getTelefone().trim().equals("")){
            throw new RegraNegocioException("Telefone não especificado");
        }
        if (fornecedor.getResponsavel()==null || fornecedor.getResponsavel().trim().equals("")){
            throw new RegraNegocioException("Responsável não informado");
        }
        if (fornecedor.getNome()==null || fornecedor.getNome().trim().equals("")){
            throw new RegraNegocioException("Nome não inserido");
        }
    }
}
