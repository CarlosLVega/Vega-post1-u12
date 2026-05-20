package com.empresa.pedidos.dominio.puertos;

import com.empresa.pedidos.dominio.eventos.PedidoProcesadoEvent;

/**
 * Puerto para listeners que reaccionan cuando un pedido fue procesado.
 */
public interface ServicioNotificacion {

    void notificar(PedidoProcesadoEvent evento);
}
