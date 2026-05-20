package com.empresa.pedidos.aplicacion;

import static org.assertj.core.api.Assertions.assertThat;

import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.PedidoId;
import com.empresa.pedidos.dominio.TipoPedido;
import com.empresa.pedidos.dominio.puertos.RepositorioPedidos;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ServicioPedidosTest {

    private final RepositorioEnMemoria repositorio = new RepositorioEnMemoria();
    private final ServicioPedidos servicio = new ServicioPedidos(repositorio);

    @Test
    void guardaYBuscaPedidoPorId() {
        Pedido pedido = new Pedido(TipoPedido.EXPRESS, 80.0, "cliente@correo.com");
        pedido.setId(1L);

        servicio.guardar(pedido);

        assertThat(servicio.buscarPorId(new PedidoId(1L))).containsSame(pedido);
    }

    private static class RepositorioEnMemoria implements RepositorioPedidos {

        private final Map<Long, Pedido> pedidos = new HashMap<>();

        @Override
        public Pedido guardar(Pedido pedido) {
            pedidos.put(pedido.getId(), pedido);
            return pedido;
        }

        @Override
        public Optional<Pedido> buscarPorId(PedidoId id) {
            return Optional.ofNullable(pedidos.get(id.valor()));
        }
    }
}
