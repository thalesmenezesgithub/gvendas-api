package com.gestao.gvendasbackend.repositories;

import com.gestao.gvendasbackend.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long>
{
}
