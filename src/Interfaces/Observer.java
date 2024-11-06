package Interfaces;

import Modelo.Events.GameEvent;

public interface Observer {
    public void update(GameEvent e);
}
