package Interfaces;

import Modelo.Events.GameEvent;

public interface Observable {
    public void notificar(GameEvent e);

    public void addObserver(Observer o);

    void removeObserver(Observer observer);
}
