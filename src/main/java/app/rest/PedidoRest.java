package app.rest;

import app.dto.ListPedidosDTO;
import app.model.Pedido;
import app.repository.PedidoRepository;
import app.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/pedidos")
@RestController
public class PedidoRest {
    private PedidoService service;
    private PedidoRepository repository;

    public PedidoRest(PedidoService service, PedidoRepository repository){
        this.service = service;
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody ListPedidosDTO pedidos){
        service.salvarAll(pedidos.getPedidos());
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Pedido> getById(@PathVariable Integer id ){
//        return service
//                .obterPedidoCompleto(id);
        return null; 
    }

}
