package com.lp3.projeto.model.repository;

import com.lp3.projeto.model.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
}
