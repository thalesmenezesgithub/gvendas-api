package com.gestao.gvendasbackend.repositories;

import com.gestao.gvendasbackend.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>
{
    Categoria findByNome(String nome);
}
