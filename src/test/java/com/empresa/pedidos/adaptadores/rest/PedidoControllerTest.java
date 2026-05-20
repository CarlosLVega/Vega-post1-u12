package com.empresa.pedidos.adaptadores.rest;

import static org.assertj.core.api.Assertions.assertThat;

import com.empresa.pedidos.adaptadores.facade.FachadaPedidos;
import com.empresa.pedidos.adaptadores.procesadores.ProcesadorPedidoEstandar;
import com.empresa.pedidos.adaptadores.procesadores.ProcesadorPedidoFactory;
import com.empresa.pedidos.aplicacion.ServicioPedidos;
import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.PedidoId;
import com.empresa.pedidos.dominio.TipoPedido;
import com.empresa.pedidos.dominio.puertos.RepositorioPedidos;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class PedidoControllerTest {

    private final RepositorioEnMemoria repositorio = new RepositorioEnMemoria();
    private final FachadaPedidos fachada = new FachadaPedidos(
            new ProcesadorPedidoFactory(List.of(new ProcesadorPedidoEstandar())),
            new ServicioPedidos(repositorio),
            new PublicadorSinOperacion());
    private final PedidoController controller = new PedidoController(fachada);

    @Test
    void crearRetornaPedidoProcesado() {
        Pedido pedido = new Pedido(TipoPedido.ESTANDAR, 100.0, "cliente@correo.com");

        ResponseEntity<Pedido> respuesta = controller.crear(pedido);

        assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(respuesta.getBody()).isNotNull();
        assertThat(respuesta.getBody().getCosto()).isCloseTo(110.0, org.assertj.core.api.Assertions.within(0.001));
    }

    @Test
    void buscarPorIdRetornaPedidoExistente() {
        Pedido pedido = new Pedido(TipoPedido.ESTANDAR, 50.0, "cliente@correo.com");
        pedido.setId(1L);
        repositorio.guardar(pedido);

        ResponseEntity<Pedido> respuesta = controller.buscarPorId(1L);

        assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(respuesta.getBody()).isSameAs(pedido);
    }

    @Test
    void buscarPorIdRetornaNotFoundCuandoNoExiste() {
        ResponseEntity<Pedido> respuesta = controller.buscarPorId(99L);

        assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private static class RepositorioEnMemoria implements RepositorioPedidos {

        private long secuencia = 1L;
        private final Map<Long, Pedido> pedidos = new HashMap<>();

        @Override
        public Pedido guardar(Pedido pedido) {
            if (pedido.getId() == null) {
                pedido.setId(secuencia++);
            }
            pedidos.put(pedido.getId(), pedido);
            return pedido;
        }

        @Override
        public Optional<Pedido> buscarPorId(PedidoId id) {
            return Optional.ofNullable(pedidos.get(id.valor()));
        }
    }

    private static class PublicadorSinOperacion implements ApplicationEventPublisher {

        @Override
        public void publishEvent(Object event) {
        }
    }
}
