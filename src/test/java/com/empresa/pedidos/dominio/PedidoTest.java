package com.empresa.pedidos.dominio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class PedidoTest {

    @Test
    void constructorInicializaPedidoRecibido() {
        Pedido pedido = new Pedido(TipoPedido.INTERNACIONAL, 200.0, "cliente@correo.com");

        assertThat(pedido.getTipo()).isEqualTo(TipoPedido.INTERNACIONAL);
        assertThat(pedido.getSubtotal()).isEqualTo(200.0);
        assertThat(pedido.getCorreoCliente()).isEqualTo("cliente@correo.com");
        assertThat(pedido.getEstado()).isEqualTo(EstadoPedido.RECIBIDO);
    }

    @Test
    void settersActualizanCamposDelPedido() {
        Pedido pedido = new Pedido();

        pedido.setId(10L);
        pedido.setTipo(TipoPedido.ESTANDAR);
        pedido.setSubtotal(75.0);
        pedido.setCosto(82.5);
        pedido.setCorreoCliente("nuevo@correo.com");
        pedido.setEstado(EstadoPedido.PROCESADO);

        assertThat(pedido.getId()).isEqualTo(10L);
        assertThat(pedido.getTipo()).isEqualTo(TipoPedido.ESTANDAR);
        assertThat(pedido.getSubtotal()).isEqualTo(75.0);
        assertThat(pedido.getCosto()).isEqualTo(82.5);
        assertThat(pedido.getCorreoCliente()).isEqualTo("nuevo@correo.com");
        assertThat(pedido.getEstado()).isEqualTo(EstadoPedido.PROCESADO);
    }

    @Test
    void pedidoIdRechazaValoresInvalidos() {
        assertThatThrownBy(() -> new PedidoId(null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new PedidoId(0L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
