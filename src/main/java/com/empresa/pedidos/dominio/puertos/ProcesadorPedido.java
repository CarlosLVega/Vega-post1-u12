package com.empresa.pedidos.dominio.puertos;

import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.TipoPedido;

/**
 * Strategy que define el contrato para calcular y procesar un pedido.
 */
public interface ProcesadorPedido {

    TipoPedido getTipo();

    void procesar(Pedido pedido);
}
