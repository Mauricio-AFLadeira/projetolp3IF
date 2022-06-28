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
    private Integer estoqueMax;
    private Integer estoqueMin;
    private Integer estoqueRessuprimento;
    private Integer qtdeEstoque;

    @ManyToOne
    private Categoria categoria;

    @ManyToOne
    private Marca marca;
}