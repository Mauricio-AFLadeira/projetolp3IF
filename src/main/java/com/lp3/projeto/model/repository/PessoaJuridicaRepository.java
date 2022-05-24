package com.lp3.projeto.model.repository;

import com.lp3.projeto.model.entity.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaJuridicaRepository extends JpaRepository<PessoaFisica, Long> {
}
