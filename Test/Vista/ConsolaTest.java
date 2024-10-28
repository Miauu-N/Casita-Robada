package Vista;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsolaTest {
    @Test
    void consola() {
        Consola consola = new Consola();
        consola.play();
    }
}