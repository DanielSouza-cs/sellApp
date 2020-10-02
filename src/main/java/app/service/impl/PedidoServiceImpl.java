package app.service.impl;

import app.dto.ItemPedidoDTO;
import app.dto.PedidoDTO;
import app.exception.RegraNegocioException;
import app.model.Cliente;
import app.model.Item_pedido;
import app.model.Pedido;
import app.model.Produto;
import app.repository.ClienteRepository;
import app.repository.Item_pedidoRepository;
import app.repository.PedidoRepository;
import app.repository.ProdutoRepository;
import app.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final Item_pedidoRepository item_pedidoRepository;

    public List<Pedido> salvarAll(List<PedidoDTO> newPedidos){
        List<Pedido>pedidos = new ArrayList<>();
        for(PedidoDTO pedido: newPedidos){
            Pedido newPedido = salvar(pedido);
            pedidos.add(newPedido);
        }
        return pedidos;
    }
    @Override
    @Transactional
    public Pedido salvar( PedidoDTO newPedido ){
        Integer idCliente = newPedido.getCliente();
        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado"));
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setNumero_controle(newPedido.getNumero_controle());
        pedido.setTotal(newPedido.getTotal());
        pedido.setData_pedido(LocalDate.now());
        List<Item_pedido> itemsPedido = converteItens(pedido, newPedido.getItens());
        pedidoRepository.save(pedido);
        item_pedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    private List<Item_pedido> converteItens(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items
                .stream()
                .map( item -> {
                    Integer idProduto = item.getProduto();
                    Produto produto = produtoRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException(
                                            "Código de produto inválido: "+ idProduto
                                    ));

                    Item_pedido itemPedido = new Item_pedido();
                    if(item.getQuantidade() == 0) itemPedido.setQuantidade(1);
                    else itemPedido.setQuantidade(item.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidoRepository.findByIdFetchItens(id);
    }
}
