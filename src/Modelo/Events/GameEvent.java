package Modelo.Events;

import java.io.Serializable;

public class GameEvent implements Serializable {
    private final EventType tipo;
    private final Object contenido;

    public GameEvent(EventType tipo, Object contenido) {
        this.tipo = tipo;
        this.contenido = contenido;
    }

    public EventType getTipo() {
        return this.tipo;
    }

    public Object getContenido() {
        return this.contenido;
    }

    @Override
    public String toString() {
        return "GameEvent{" +
                "tipo=" + tipo +
                '}';
    }
}
