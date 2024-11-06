package Modelo.Events;

public class GameEvent {
    private EventType tipo;
    private String contenido;

    public GameEvent(EventType tipo, String contenido) {
        this.tipo = tipo;
        this.contenido = contenido;
    }

    public GameEvent(EventType tipo){
        new GameEvent(tipo,null);
    }

    public EventType getTipo() {
        return this.tipo;
    }

    public String getContenido() {
        return this.contenido;
    }
}
