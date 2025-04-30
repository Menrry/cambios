package bootcamp.dio.Bradesco.cambios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
/*
 * 2. Configuración de Feign no escaneada

Si tu clase principal no escanea el paquete bootcamp.dio.Bradesco.cambios.services, 
el cliente Feign no se registra. Verifica esto en tu clase de inicio:
 * 
 * 
 * 
 */
@EnableFeignClients(basePackages = "bootcamp.dio.Bradesco.cambios.services")
@SpringBootApplication
public class CambiosApplication {

	public static void main(String[] args) {
		SpringApplication.run(CambiosApplication.class, args);
	}

}
