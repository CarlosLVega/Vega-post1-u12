package com.empresa.pedidos.adaptadores.facade;

import static org.assertj.core.api.Assertions.assertThat;

import com.empresa.pedidos.adaptadores.procesadores.ProcesadorPedidoFactory;
import com.empresa.pedidos.adaptadores.procesadores.ProcesadorPedidoExpress;
import com.empresa.pedidos.aplicacion.ServicioPedidos;
import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.PedidoId;
import com.empresa.pedidos.dominio.TipoPedido;
import com.empresa.pedidos.dominio.eventos.PedidoProcesadoEvent;
import com.empresa.pedidos.dominio.puertos.RepositorioPedidos;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

class FachadaPedidosTest {

    private final ProcesadorPedidoFactory factory = new ProcesadorPedidoFactory(List.of(new ProcesadorPedidoExpress()));
    private final RepositorioEnMemoria repositorio = new RepositorioEnMemoria();
    private final ServicioPedidos servicioPedidos = new ServicioPedidos(repositorio);
    private final PublicadorEnMemoria publisher = new PublicadorEnMemoria();
    private final FachadaPedidos fachada = new FachadaPedidos(factory, servicioPedidos, publisher);

    @Test
    void crearPedidoProcesaGuardaYPublicaEvento() {
        Pedido pedido = new Pedido(TipoPedido.EXPRESS, 100.0, "cliente@correo.com");

        Pedido resultado = fachada.crearPedido(pedido);

        assertThat(resultado.getCosto()).isEqualTo(130.0);
        assertThat(repositorio.guardados).containsExactly(pedido);
        assertThat(publisher.eventos)
                .hasSize(1)
                .first()
                .isInstanceOfSatisfying(PedidoProcesadoEvent.class,
                        evento -> assertThat(evento.pedido()).isSameAs(resultado));
    }

    private static class RepositorioEnMemoria implements RepositorioPedidos {

        private final List<Pedido> guardados = new ArrayList<>();

        @Override
        public Pedido guardar(Pedido pedido) {
            guardados.add(pedido);
            return pedido;
        }

        @Override
        public Optional<Pedido> buscarPorId(PedidoId id) {
            return Optional.empty();
        }
    }

    private static class PublicadorEnMemoria implements ApplicationEventPublisher {

        private final List<Object> eventos = new ArrayList<>();

        @Override
        public void publishEvent(Object event) {
            eventos.add(event);
        }
    }
}
