package com.lp3.projeto.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private Integer valorUnitario;
    private String imagem;
    private String estoqueMax;
    private String estoqueMin;
    private String estoqueRessuprimento;
    private String estoqueEstoque;
    private String desconto;

    @ManyToOne
    private Categoria categoria;

    @ManyToOne
    private Marca marca;

    @OneToOne
    private Cupom cupom;

    @OneToOne
    private formaPagamento formaPagamento;

    @ManyToMany
    @JoinTable(name = "produto_compra",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "compra_id"))
    private List<Compra> compras;
}