package bootcamp.dio.Bradesco.cambios.services;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bootcamp.dio.Bradesco.cambios.model.FixerCurrenciesResponse;
import bootcamp.dio.Bradesco.cambios.model.FixerLatestResponse;



@FeignClient(name = "fixer", url = "${fixer.api.url}")
public interface FixerApiService {

    @GetMapping("/symbols")    
    FixerCurrenciesResponse getAvailableCurrencies(@RequestParam("access_key") String accessKey);

    @GetMapping("/latest")
    FixerLatestResponse getLatestRates(@RequestParam("access_key") String accessKey,
                                   @RequestParam(value = "base", required = false) String base,
                                   @RequestParam(value = "symbols", required = false) String symbols);

 
/* 
*1. Error en la interfaz Feign: Parámetros no opcionales

//La API Fixer permite que base y symbols sean opcionales, pero tu interfaz los marca como requeridos. 
//Esto genera URLs inválidas como /latest?base=null&symbols=null si no se envían valores.

    @GetMapping("/latest")
    FixerLatestResponse getLatestRates(@RequestParam("access_key") String accessKey,
                                       @RequestParam("base") String base,
                                       @RequestParam("symbols") String symbols);
*/
}