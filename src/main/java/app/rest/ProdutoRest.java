package app.rest;

import app.model.Produto;
import app.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoRest {

    private ProdutoRepository repository;

    public ProdutoRest(ProdutoRepository repository){
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save (@RequestBody Produto produto){
        return repository.save(produto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Integer id ){
        repository.findById(id)
                .map( produto -> {
                    repository.delete(produto);
                    return produto;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Produto não encontrado") );
    }
    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Produto> getAllProducts(){
        return repository.findAll();
    }
}
