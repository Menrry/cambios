# 1.- Padrões de criação (como os objetos são instanciados):
  
   **Factory Method:** O próprio Spring Framework usa extensivamente o padrão Método de Fábrica. O contêiner Spring 
atua como uma fábrica de beans. Quando você define um bean em sua configuração 
(seja com @Component, @Service, @Controller, @Bean, etc.), o Spring cria e gerencia a 
instância desse objeto. Neste caso, FixerApiService é um bean gerenciado pelo Spring. A interface 
FixerApiService e sua implementação (que o OpenFeign gera dinamicamente) podem ser vistas como uma 
maneira abstrata de criar um cliente para a API Fixer.
 
   **Singleton:** Por padrão, os beans no Spring são singletons. Isso significa que o contêiner Spring cria 
uma única instância de cada bean e a compartilha no aplicativo. FixerApiService e ExchangeRateController 
são singletons. Isso garante que haja apenas uma instância do serviço para interagir com 
a API do Fixer e um único controlador para lidar com solicitações de alteração de tipo.


 # 2. Padrões Estruturais (Como os objetos são compostos):
  
   **Proxy:** O padrão Proxy é usado implicitamente com Spring AOP (Programação Orientada a Aspectos) e também 
com OpenFeign. Quando você anota uma interface com @FeignClient, o Spring (via OpenFeign) cria um proxy 
dinâmico que implementa essa interface. Este proxy manipula a comunicação HTTP com o serviço externo 
(Fixer API). Você não escreve diretamente o código para fazer solicitações HTTP; O proxy gerado pelo 
Feign faz isso para você.

  **Facade:** O FixerApiService pode ser considerado uma Fachada. Ele encapsula a complexidade da interação 
com a API Fixer (construção de URL, manipulação de parâmetros, desserialização de resposta) e fornece 
uma interface mais simples e coesa para o ExchangeRateController. O controlador não precisa saber os 
detalhes de como ele se comunica com a API externa; simplesmente chama os métodos do FixerApiService.

 # 3. Padrões Comportamentais (como os objetos interagem):
 
   **Estrategy:** Embora não tão evidente neste snippet, a flexibilidade do OpenFeign para configurar diferentes 
codificadores (para serializar solicitações), decodificadores (para desserializar respostas) e ErrorDecoders 
(para lidar com erros) se alinha ao padrão Strategy. Você pode definir diferentes estratégias para lidar com 
essas tarefas e injetá-las no cliente Feign.

  **Template Method:** a maneira como o OpenFeign processa uma chamada para um serviço externo (criando a 
solicitação, enviando, recebendo a resposta, decodificando) segue um fluxo bem definido. Subclasses 
(neste caso, a interface FixerApiService com suas anotações) definem as etapas específicas (a URL, os parâmetros), 
enquanto o esqueleto geral do processo é definido pelo OpenFeign. Isso se assemelha ao padrão do Método de Modelo.

  **Dependency Injection (Injeção de dependência):** este é um padrão fundamental no Spring e é claramente visível aqui. O 
ExchangeRateController recebe uma instância de FixerApiService e a chave da API Fixer por meio de seu construtor. 
Em vez de criar diretamente a instância FixerApiService ou consultar a chave, o Spring se encarrega de 
"injetar" essas dependências. Isso promove a inversão de controle (IoC), a modularidade e facilita os testes. 
A anotação @Autowired (implícita por ter apenas um construtor) é como o Spring executa a injeção de dependência. 
A anotação @Value("${fixer.api.key}") também é uma forma de injeção de dependência, onde o valor é obtido da configuração.

### Autor:
- Engenheiro. Menrry Santana La Cruz <br>
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Menrry/)
[![Linkedin](https://img.shields.io/badge/Linkedin-100000?style=for-the-badge&logo=github&logoColor=white)](https://www.linkedin.com/in/menrrysantana/)
