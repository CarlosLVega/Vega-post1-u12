package com.empresa.pedidos.dominio.puertos;

import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.PedidoId;
import java.util.Optional;

/**
 * Puerto de persistencia para evitar que la aplicacion dependa de JPA.
 */
public interface RepositorioPedidos {

    Pedido guardar(Pedido pedido);

    Optional<Pedido> buscarPorId(PedidoId id);
}
