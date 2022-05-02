package com.lp3.projeto.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String senha;
    private String telefone;
    private String email;
    private String nome;

    @OneToMany
    private List<Pedido> pedidos;

    @OneToOne (cascade = CascadeType.ALL)
    private Endereco endereco;

}
