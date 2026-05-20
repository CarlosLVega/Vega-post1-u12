package com.empresa.pedidos.dominio;

public record PedidoId(Long valor) {

    public PedidoId {
        if (valor == null || valor <= 0) {
            throw new IllegalArgumentException("El id del pedido debe ser positivo");
        }
    }
}
