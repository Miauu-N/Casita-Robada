package Interfaces;

import Modelo.Events.GameEvent;

public interface Observable {
    void notificar(GameEvent e);

    void addObserver(Observer o);

    void removeObserver(Observer observer);
}
