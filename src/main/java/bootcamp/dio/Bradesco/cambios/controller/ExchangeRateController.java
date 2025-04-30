package bootcamp.dio.Bradesco.cambios.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bootcamp.dio.Bradesco.cambios.model.FixerCurrenciesResponse;
import bootcamp.dio.Bradesco.cambios.model.FixerLatestResponse;
import bootcamp.dio.Bradesco.cambios.services.FixerApiService;

import java.util.Map;

//Menrry Santana
    
@RestController
@RequestMapping("/exchange")
public class ExchangeRateController {

    private final FixerApiService fixerApiService;
    private final String fixerApiKey;
    private final String fixerBaseCurrency;

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
    
        // Obtener tasas de base y target respecto a EUR
        String symbols = base + "," + target;
        FixerLatestResponse response = fixerApiService.getLatestRates(fixerApiKey, "EUR", symbols); // Fuerza base=EUR
    
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

/* 
//El error ocurre porque la API gratuita de Fixer solo permite base=EUR. Para soportar conversiones entre otras monedas, 
//obtén ambas tasas respecto a EUR y calcula la tasa cruzada en tu backend. Así, todas las rutas funcionarán correctamente.
    @GetMapping("/{target}")
    public ResponseEntity<Double> getExchangeRateFromDefaultBase(@PathVariable String target) {
        FixerLatestResponse response = fixerApiService.getLatestRates(fixerApiKey, fixerBaseCurrency.toUpperCase(), target.toUpperCase());
        if (response.isSuccess() && response.getRates() != null && response.getRates().containsKey(target.toUpperCase())) {
            return ResponseEntity.ok(response.getRates().get(target.toUpperCase()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
*/

/* 
    @GetMapping("/rate/{base}/{target}")//@GetMapping("/{base}/{target}")
    public ResponseEntity<Double> getExchangeRate(@PathVariable String base, @PathVariable String target) {
        if (base.equalsIgnoreCase(fixerBaseCurrency)) {
            // Si la base es EUR, llama normalmente
            FixerLatestResponse response = fixerApiService.getLatestRates(fixerApiKey, fixerBaseCurrency, target.toUpperCase());
            if (response.isSuccess() && response.getRates() != null && response.getRates().containsKey(target.toUpperCase())) {
                return ResponseEntity.ok(response.getRates().get(target.toUpperCase()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            // Para cuentas gratuitas, solo EUR como base
            // Obtener tasas de ambas monedas respecto a EUR
            String symbols = base.toUpperCase() + "," + target.toUpperCase();
            FixerLatestResponse response = fixerApiService.getLatestRates(fixerApiKey, fixerBaseCurrency, symbols);
            if (response.isSuccess() && response.getRates() != null
                    && response.getRates().containsKey(base.toUpperCase())
                    && response.getRates().containsKey(target.toUpperCase())) {
                double baseRate = response.getRates().get(base.toUpperCase());
                double targetRate = response.getRates().get(target.toUpperCase());
                double crossRate = targetRate / baseRate;
                return ResponseEntity.ok(crossRate);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }
    */

