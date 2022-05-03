package com.lp3.projeto.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //como tratar as varias formas de chaves pix
    private String chave;

}
