package com.gestao.gvendasbackend.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class GestaoVendasExceptionHandler extends ResponseEntityExceptionHandler
{
    private static final String CONSTANT_VALIDATION_NOT_BLANK = "NotBlank";
    private static final String CONSTANT_VALIDATION_LENGTH = "Length";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        gerarListaErros(ex.getBindingResult());
        List<Erro> erros = gerarListaErros(ex.getBindingResult());

        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public  ResponseEntity<Object> handleEmptyDataAccessException(EmptyResultDataAccessException ex, WebRequest request )
    {
        String msgUsuario = "Recurso não encontrado.";
        String msgDesenvolvedor = ex.toString();
        List<Erro> erros = Arrays.asList(new Erro(msgUsuario, msgDesenvolvedor));

        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Object> handleRegradeNegocioException(RegraNegocioException ex, WebRequest request)
    {
        String msgUsuario = ex.getMessage();
        String msgDesenvolvedor = ex.getMessage();
        List<Erro> erros = Arrays.asList(new Erro(msgUsuario, msgDesenvolvedor));

        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private List<Erro> gerarListaErros(BindingResult bindingResult)
    {
        List<Erro> erros = new ArrayList<>();

        bindingResult.getFieldErrors().forEach(fieldError -> {
            String msgUsuario = tratarMensagemErroUsuario(fieldError);
            String msgDesenvolvedor = fieldError.toString();
            erros.add(new Erro(msgUsuario, msgDesenvolvedor));
        });

        return erros;
    }

    public String tratarMensagemErroUsuario(FieldError fieldError)
    {
        if(fieldError.getCode().equals(CONSTANT_VALIDATION_NOT_BLANK))
        {
            return fieldError.getDefaultMessage().concat(" é obrigatório.");
        }
        if(fieldError.getCode().equals(CONSTANT_VALIDATION_LENGTH))
        {
            return fieldError.getDefaultMessage().concat(String.format(" deve ter no entre %s e %s caracteres.",
                    fieldError.getArguments()[2], fieldError.getArguments()[1]));
        }

        return fieldError.toString();
    }
}
