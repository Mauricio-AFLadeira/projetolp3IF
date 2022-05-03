package com.lp3.projeto.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class FormaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Devemos criar uma classe para cada pagamento? (BoaCompra e Ebanxs)

    @OneToOne
    private Cartao cartao;

    @OneToOne
    private Pix pix;


    private String boleto;

}
