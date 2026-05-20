package com.empresa.pedidos.adaptadores.procesadores;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.empresa.pedidos.dominio.TipoPedido;
import java.util.List;
import org.junit.jupiter.api.Test;

class ProcesadorPedidoFactoryTest {

    private final ProcesadorPedidoEstandar estandar = new ProcesadorPedidoEstandar();
    private final ProcesadorPedidoExpress express = new ProcesadorPedidoExpress();
    private final ProcesadorPedidoInternacional internacional = new ProcesadorPedidoInternacional();
    private final ProcesadorPedidoFactory factory = new ProcesadorPedidoFactory(
            List.of(estandar, express, internacional));

    @Test
    void retornaProcesadorEstandar() {
        assertThat(factory.obtener(TipoPedido.ESTANDAR)).isSameAs(estandar);
    }

    @Test
    void retornaProcesadorExpress() {
        assertThat(factory.obtener(TipoPedido.EXPRESS)).isSameAs(express);
    }

    @Test
    void retornaProcesadorInternacional() {
        assertThat(factory.obtener(TipoPedido.INTERNACIONAL)).isSameAs(internacional);
    }

    @Test
    void rechazaTipoNoSoportado() {
        assertThatThrownBy(() -> factory.obtener(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Tipo de pedido no soportado");
    }
}
