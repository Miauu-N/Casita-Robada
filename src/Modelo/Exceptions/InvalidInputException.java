package Modelo.Exceptions;

public class InvalidInputException extends Exception {
    final TipoInputInvalido tipo;
    public InvalidInputException(TipoInputInvalido tipo) {
        this.tipo = tipo;
    }

    public TipoInputInvalido getTipo() {
        return this.tipo;
    }
}
