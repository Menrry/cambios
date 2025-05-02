package bootcamp.dio.Bradesco.cambios.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import bootcamp.dio.Bradesco.cambios.model.FixerCurrenciesResponse;
import bootcamp.dio.Bradesco.cambios.model.FixerLatestResponse;

/**
 * Projeto Spring Boot gerado via Spring Initializr.
 * Os seguintes módulos foram selecionados:
 * - Spring Web
 * - Lombok
 * - OpenFeign
 * 
 * Para este projeto usei Java 21
 * 
 * http://localhost:8080/exchange/USD/CLP
 * 
 * http://localhost:8080/exchange/currencies
 * 
 * @author Menrry Santana
 */

@FeignClient(name = "fixer", url = "${fixer.api.url}")
public interface FixerApiService {

    @GetMapping("/symbols")    
    FixerCurrenciesResponse getAvailableCurrencies(@RequestParam("access_key") String accessKey);

    //La API Fixer permite que base y symbols sean opcionales
    @GetMapping("/latest")
    FixerLatestResponse getLatestRates(@RequestParam("access_key") String accessKey,
                                   @RequestParam(value = "base", required = false) String base,
                                   @RequestParam(value = "symbols", required = false) String symbols);

}