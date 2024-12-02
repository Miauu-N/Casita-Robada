package Vista;

import Vista.Consola.Texto;
import org.junit.jupiter.api.Test;

class TextoTest {
    @Test
    void consola() {
        Texto texto = new Texto();
        texto.play();
    }
}