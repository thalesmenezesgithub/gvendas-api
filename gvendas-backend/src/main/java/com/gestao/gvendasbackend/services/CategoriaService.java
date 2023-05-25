package com.gestao.gvendasbackend.services;

import com.gestao.gvendasbackend.entity.Categoria;
import com.gestao.gvendasbackend.exception.RegraNegocioException;
import com.gestao.gvendasbackend.repositories.CategoriaRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CategoriaService
{
    @Autowired
    private CategoriaRepository repository;

    @Transactional
    public List<Categoria> listarTodas()
    {
        return repository.findAll();
    }

    @Transactional
    public Optional<Categoria> buscarPorId(Long id)
    {
        return repository.findById(id);
    }

    @Transactional
    public Categoria salva(Categoria categoria)
    {
        validaCategoriaDuplicada(categoria);

        return repository.save(categoria);
    }


    @Transactional
    public Categoria atualiza(Long id, Categoria categoria)
    {
        Categoria categoriaSalva = validaCategoria(id);
        validaCategoriaDuplicada(categoria);
        BeanUtils.copyProperties(categoria, categoriaSalva, "id");

        return repository.save(categoriaSalva);
    }

    @Transactional
    public void deleta(Long id)
    {
        repository.deleteById(id);
    }


    private Categoria validaCategoria(Long id)
    {
        Optional<Categoria> categoria = buscarPorId(id);

        if(categoria.isEmpty())
        {
            throw new EmptyResultDataAccessException(1);
        }

        return categoria.get();
    }

    private void validaCategoriaDuplicada(Categoria categoria)
    {
        Categoria  categoriaEncontrada =  repository.findByNome(categoria.getNome());

        if(categoriaEncontrada !=  null && categoriaEncontrada.getId() != categoria.getId())
        {
            throw new RegraNegocioException(String.format("A categoria "+categoria.getNome().toUpperCase()+" está cadastrada."));
        }
    }
}
