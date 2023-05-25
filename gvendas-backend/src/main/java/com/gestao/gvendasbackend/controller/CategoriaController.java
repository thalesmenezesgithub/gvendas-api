package com.gestao.gvendasbackend.controller;

import com.gestao.gvendasbackend.entity.Categoria;
import com.gestao.gvendasbackend.services.CategoriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api(tags = "Categoria")
@RestController
@RequestMapping("/categoria")
public class CategoriaController
{
    @Autowired
    private CategoriaService service;

    @ApiOperation(value = "listar todas")
    @GetMapping
    public List<Categoria> listarTodas()
    {
        return service.listarTodas();
    }

    @ApiOperation(value = "buscar por id")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Categoria>> buscarPorId(@PathVariable Long id)
    {
       Optional<Categoria> categoria = service.buscarPorId(id);

       return categoria.isPresent() ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "salva nova categoria")
    @PostMapping
    public ResponseEntity<Categoria> salva(@Valid @RequestBody Categoria categoria)
    {
        Categoria categoriaSalva = service.salva(categoria);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @ApiOperation(value = "atualiza categoria")
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualiza(@PathVariable Long id,@Valid @RequestBody Categoria categoria)
    {
        return ResponseEntity.ok(service.atualiza(id, categoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleta(@PathVariable Long id)
    {
        service.deleta(id);

        return ResponseEntity.noContent().build();
    }
}
