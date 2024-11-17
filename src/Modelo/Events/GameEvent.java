package Modelo.Events;

public class GameEvent {
    private EventType tipo;
    private Object contenido;

    public GameEvent(EventType tipo, Object contenido) {
        this.tipo = tipo;
        this.contenido = contenido;
    }

    public GameEvent(EventType tipo){
        this.tipo = tipo;
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
