package com.lp3.projeto.service;

import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Produto;
import com.lp3.projeto.model.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

    @Transactional
    public Produto salvar(Produto produto){
        validar(produto);
        return repository.save(produto);
    }

    @Transactional
    public void excluir(Produto produto){
        Objects.requireNonNull(produto.getId());
        repository.delete(produto);
    }

    public void validar(Produto produto) {
        if (produto.getNome()==null || produto.getNome().trim().equals("")){
            throw new RegraNegocioException("nome do produto não especificado");
        }
        if (produto.getValorUnitario()==null || produto.getValorUnitario()==0){
            throw new RegraNegocioException("preço não especificado ou sem valor");
        }
        if (produto.getEstoqueMax()==null || produto.getEstoqueMax()==0){
            throw new RegraNegocioException("informe o estoque máximo");
        }
        if (produto.getEstoqueMin()==null || produto.getEstoqueMin()==0){
            throw new RegraNegocioException("informe o estoque mínimo");
        }
        if (produto.getEstoqueRessuprimento()==null || produto.getEstoqueRessuprimento()==0){
            throw new RegraNegocioException("informe o estoque para ressuprimento");
        }
        if (produto.getQtdeEstoque()==null || produto.getQtdeEstoque()==0){
            throw new RegraNegocioException("informe a quantidade no estoque");
        }
        if (produto.getQtdeEstoque() > produto.getEstoqueMax()){
            throw new RegraNegocioException("quantidade no estoque maior que quantidade máxima");
        }
        if (produto.getCategoria().getId()==null){
            throw new RegraNegocioException("categoria não relacionada ao produto");
        }
        if (produto.getMarca().getId()==null){
            throw new RegraNegocioException("marca não relacionada ao produto");
        }

    }
}
