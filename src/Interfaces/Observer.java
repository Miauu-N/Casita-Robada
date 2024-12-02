package Interfaces;

import Modelo.Events.GameEvent;

public interface Observer {
    void update(GameEvent e);
}
