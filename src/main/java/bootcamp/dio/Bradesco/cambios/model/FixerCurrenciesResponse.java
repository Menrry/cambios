package bootcamp.dio.Bradesco.cambios.model;
/**
 * Representa as estruturas de respostas JSON da API Fixer.
 * A anotação @Data do Lombok gera automaticamente getters, setters,
 * equals, hashCode y toString.
 * 
 * @author Menrry Santana
 */
import lombok.Data;
import java.util.Map;

@Data
public class FixerCurrenciesResponse {
    private boolean success;
    private Map<String, String> symbols;
}
