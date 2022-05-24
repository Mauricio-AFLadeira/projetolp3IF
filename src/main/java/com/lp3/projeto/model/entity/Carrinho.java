package com.lp3.projeto.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Carrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer valorTotal;
    private Integer frete;

    @OneToOne
    private FormaPagamento formaPagamento;

    @ManyToOne
    private Pessoa pessoa;
}
