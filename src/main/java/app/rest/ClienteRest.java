package app.rest;

import app.model.Cliente;
import app.repository.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteRest {
    private ClienteRepository clienteRepository;

    public ClienteRest(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save (@RequestBody Cliente cliente){
        return clienteRepository.save(cliente);
    }
}
