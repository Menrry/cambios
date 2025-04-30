package bootcamp.dio.Bradesco.cambios.model;



import lombok.Data;

import java.util.Map;

@Data
public class FixerCurrenciesResponse {
    private boolean success;
    private Map<String, String> symbols;
}
