package com.empresa.pedidos.adaptadores.procesadores;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import com.empresa.pedidos.dominio.EstadoPedido;
import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.TipoPedido;
import org.junit.jupiter.api.Test;

class ProcesadoresPedidoTest {

    @Test
    void procesadorEstandarCalculaCostoYEstado() {
        Pedido pedido = new Pedido(TipoPedido.ESTANDAR, 100.0, "cliente@correo.com");

        new ProcesadorPedidoEstandar().procesar(pedido);

        assertThat(pedido.getCosto()).isCloseTo(110.0, within(0.001));
        assertThat(pedido.getEstado()).isEqualTo(EstadoPedido.PROCESADO);
    }

    @Test
    void procesadorExpressCalculaCostoYEstado() {
        Pedido pedido = new Pedido(TipoPedido.EXPRESS, 100.0, "cliente@correo.com");

        new ProcesadorPedidoExpress().procesar(pedido);

        assertThat(pedido.getCosto()).isCloseTo(130.0, within(0.001));
        assertThat(pedido.getEstado()).isEqualTo(EstadoPedido.PROCESADO);
    }

    @Test
    void procesadorInternacionalCalculaCostoYEstado() {
        Pedido pedido = new Pedido(TipoPedido.INTERNACIONAL, 100.0, "cliente@correo.com");

        new ProcesadorPedidoInternacional().procesar(pedido);

        assertThat(pedido.getCosto()).isCloseTo(175.0, within(0.001));
        assertThat(pedido.getEstado()).isEqualTo(EstadoPedido.PROCESADO);
    }
}
