package app.service;

import app.dto.PedidoDTO;
import app.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoService {
    Pedido salvar (PedidoDTO pedido);
    List<Pedido> salvarAll(List<PedidoDTO> newPedidos);
    Optional<Pedido> obterPedidoCompleto(Integer id);
}
