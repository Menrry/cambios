package bootcamp.dio.Bradesco.cambios.model;
/**
 * Representa las estructuras de las respuestas JSON de la API de Fixer. 
 * La anotación @Data de Lombok genera automáticamente getters, setters, 
 * equals, hashCode y toString.
 * 
 * @author Menrry Santana
 */
import lombok.Data;
import java.util.Map;

@Data
public class FixerLatestResponse {
    private boolean success;
    private Long timestamp;
    private String base;
    private String date;
    private Map<String, Double> rates;
}