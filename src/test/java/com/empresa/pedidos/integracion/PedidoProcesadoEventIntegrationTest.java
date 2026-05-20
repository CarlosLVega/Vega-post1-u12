package com.empresa.pedidos.integracion;

import static org.assertj.core.api.Assertions.assertThat;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.empresa.pedidos.adaptadores.facade.FachadaPedidos;
import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.TipoPedido;
import com.empresa.pedidos.infraestructura.notificaciones.NotificacionEmail;
import com.empresa.pedidos.infraestructura.notificaciones.NotificacionLog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PedidoProcesadoEventIntegrationTest {

    @Autowired
    private FachadaPedidos fachada;

    private final Logger emailLogger = (Logger) LoggerFactory.getLogger(NotificacionEmail.class);
    private final Logger logLogger = (Logger) LoggerFactory.getLogger(NotificacionLog.class);
    private final ListAppender<ILoggingEvent> emailAppender = new ListAppender<>();
    private final ListAppender<ILoggingEvent> logAppender = new ListAppender<>();

    @BeforeEach
    void configurarCapturaDeLogs() {
        emailAppender.start();
        logAppender.start();
        emailLogger.addAppender(emailAppender);
        logLogger.addAppender(logAppender);
    }

    @AfterEach
    void limpiarCapturaDeLogs() {
        emailLogger.detachAppender(emailAppender);
        logLogger.detachAppender(logAppender);
    }

    @Test
    void eventoPublicadoEsRecibidoPorAmbosListeners() {
        Pedido pedido = new Pedido(TipoPedido.ESTANDAR, 100.0, "cliente@correo.com");

        fachada.crearPedido(pedido);

        assertThat(emailAppender.list)
                .anySatisfy(evento -> assertThat(evento.getFormattedMessage()).contains("Email enviado para pedido"));
        assertThat(logAppender.list)
                .anySatisfy(evento -> assertThat(evento.getFormattedMessage()).contains("Pedido procesado"));
    }
}
