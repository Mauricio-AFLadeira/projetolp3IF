package com.lp3.projeto.model.repository;

import com.lp3.projeto.model.entity.PessoaFisica;
import com.lp3.projeto.model.entity.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {
}
