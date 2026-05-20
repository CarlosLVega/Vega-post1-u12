package com.empresa.pedidos.aplicacion;

import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.PedidoId;
import com.empresa.pedidos.dominio.puertos.RepositorioPedidos;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ServicioPedidos {

    private final RepositorioPedidos repositorio;

    public ServicioPedidos(RepositorioPedidos repositorio) {
        this.repositorio = repositorio;
    }

    public Pedido guardar(Pedido pedido) {
        return repositorio.guardar(pedido);
    }

    public Optional<Pedido> buscarPorId(PedidoId id) {
        return repositorio.buscarPorId(id);
    }
}
