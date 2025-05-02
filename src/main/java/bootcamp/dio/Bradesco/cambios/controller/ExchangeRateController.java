package bootcamp.dio.Bradesco.cambios.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import bootcamp.dio.Bradesco.cambios.model.FixerCurrenciesResponse;
import bootcamp.dio.Bradesco.cambios.model.FixerLatestResponse;
import bootcamp.dio.Bradesco.cambios.services.FixerApiService;

/**
 * Esse {@link RestController} representa nossa <b>Facade</b>, pois abstrai toda
 * a complexidade de integrações. Em sua essência, o OpenFeign simplifica a criação de 
 * clientes HTTP em Java, especialmente dentro de uma arquitetura de microsserviços 
 * usando o Spring Cloud, abstraindo as complexidades de fazer solicitações HTTP.  Você 
 * define o que precisa e o OpenFeign cuida do resto.
 * @author Menrry
 */

@RestController
@RequestMapping("/exchange")
public class ExchangeRateController {

    private final FixerApiService fixerApiService;
    private final String fixerApiKey;
    private String fixerBaseCurrency; // Ahora sí se usará ya que era final

    public ExchangeRateController(FixerApiService fixerApiService,
                                  @Value("${fixer.api.key}") String fixerApiKey,
                                  @Value("${fixer.api.base-currency:EUR}") String fixerBaseCurrency) {
        this.fixerApiService = fixerApiService;
        this.fixerApiKey = fixerApiKey;
        this.fixerBaseCurrency = fixerBaseCurrency;
    }

    @GetMapping("/{base}/{target}")
    public ResponseEntity<Double> getExchangeRate(@PathVariable String base, @PathVariable String target) {
        base = base.toUpperCase();
        target = target.toUpperCase();
        fixerBaseCurrency = "EUR";

        String symbols = base + "," + target;
        // Usa fixerBaseCurrency en lugar de "EUR"
        FixerLatestResponse response = fixerApiService.getLatestRates(
            fixerApiKey, 
            fixerBaseCurrency, // Variable inyectada
            symbols
        );

        if (response.isSuccess() && response.getRates() != null 
            && response.getRates().containsKey(base) 
            && response.getRates().containsKey(target)) {
            
            double baseRate = response.getRates().get(base);
            double targetRate = response.getRates().get(target);
            double crossRate = targetRate / baseRate;
            
            return ResponseEntity.ok(crossRate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/currencies")
    public ResponseEntity<Map<String, String>> getAvailableCurrencies() {
        FixerCurrenciesResponse response = fixerApiService.getAvailableCurrencies(fixerApiKey);
        if (response.isSuccess() && response.getSymbols() != null) {
            return ResponseEntity.ok(response.getSymbols());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}


