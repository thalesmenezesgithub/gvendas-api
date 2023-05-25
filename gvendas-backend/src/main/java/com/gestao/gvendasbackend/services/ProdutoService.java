package com.gestao.gvendasbackend.services;

import com.gestao.gvendasbackend.entity.Produto;
import com.gestao.gvendasbackend.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService
{
    @Autowired
    private ProdutoRepository repository;

    @Transactional
    public List<Produto> listarTodas()
    {
        return repository.findAll();
    }

    @Transactional
    public Optional<Produto> buscarPorId(Long id)
    {
        return repository.findById(id);
    }

    public Produto salva(Produto produto)
    {
        return repository.save(produto);
    }
}
