package io.github.ramon.ReadFlow.infrastructure.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String mensagem) {
        super(mensagem);
    }

    public BadRequestException(String mensagem, Throwable throwable) {
        super(mensagem, throwable);
    }
}
