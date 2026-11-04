
package megafarma.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Erros de dados inválidos
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> tratarDadosInvalidos(
            IllegalArgumentException erro) {

        return ResponseEntity
                .badRequest()
                .body(Map.of("erro", erro.getMessage()));
    }


    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Map<String, String>> tratarErroBanco(
            SQLException erro) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "erro",
                        "Não foi possível realizar a operação no banco de dados."
                ));
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> tratarJsonInvalido(
            HttpMessageNotReadableException erro) {

        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "erro",
                        "JSON inválido. Verifique os dados enviados."
                ));
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> tratarCodigoInvalido(
            MethodArgumentTypeMismatchException erro) {

        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "erro",
                        "O código informado na URL é inválido."
                ));
    }
}
