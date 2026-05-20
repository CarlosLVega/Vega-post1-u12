package com.empresa.pedidos.adaptadores.facade;

import com.empresa.pedidos.adaptadores.procesadores.ProcesadorPedidoFactory;
import com.empresa.pedidos.aplicacion.ServicioPedidos;
import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.PedidoId;
import com.empresa.pedidos.dominio.eventos.PedidoProcesadoEvent;
import java.util.Optional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class FachadaPedidos {

    private final ProcesadorPedidoFactory factory;
    private final ServicioPedidos servicioPedidos;
    private final ApplicationEventPublisher publisher;

    public FachadaPedidos(ProcesadorPedidoFactory factory,
                          ServicioPedidos servicioPedidos,
                          ApplicationEventPublisher publisher) {
        this.factory = factory;
        this.servicioPedidos = servicioPedidos;
        this.publisher = publisher;
    }

    public Pedido crearPedido(Pedido pedido) {
        factory.obtener(pedido.getTipo()).procesar(pedido);
        Pedido guardado = servicioPedidos.guardar(pedido);
        publisher.publishEvent(new PedidoProcesadoEvent(guardado));
        return guardado;
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return servicioPedidos.buscarPorId(new PedidoId(id));
    }
}
