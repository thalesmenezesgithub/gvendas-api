package com.gestao.gvendasbackend.controller;

import com.gestao.gvendasbackend.entity.Produto;
import com.gestao.gvendasbackend.services.ProdutoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(tags = "Produto")
@RestController
@RequestMapping("/produto")
public class ProdutoController
{
    @Autowired
    private ProdutoService service;

    @GetMapping
    public List<Produto> listarTodas()
    {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Produto>> buscarPorId(@PathVariable Long id)
    {
        Optional<Produto> produto = service.buscarPorId(id);

        return produto.isPresent() ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Produto> salva(@RequestBody Produto produto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salva(produto));
    }
}
