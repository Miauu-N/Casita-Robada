package Modelo.Exceptions;

public class InvalidInputException extends Exception {
    TipoInputInvalido tipo;
    public InvalidInputException(TipoInputInvalido tipo) {
        this.tipo = tipo;
    }

    public TipoInputInvalido getTipo() {
        return this.tipo;
    }
}
