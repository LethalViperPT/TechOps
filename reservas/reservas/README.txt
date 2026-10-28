# WorkSpace - Sistema de Gestão de Reservas de Salas

Uma aplicação Web desenvolvida em Java (Spring Boot) para gerir salas e respetivas reservas, cumprindo regras rigorosas
de negócio para evitar sobreposições de horários.

## 🚀 Como Executar a Aplicação

1. Certifique-se de que tem o **Java 17** (ou superior) e o **Maven** instalados no sistema.
2. Abra o projeto na sua IDE (recomendado: IntelliJ IDEA).
3. Aguarde que o Maven descarregue todas as dependências especificadas no ficheiro `pom.xml`.
4. Navegue até à pasta `src/main/java/com/trabalho/reservas` e execute a classe principal `ReservasApplication.java`.
5. Abra o seu navegador web e aceda a: **http://localhost:8081**

*(Nota: A porta padrão foi alterada para 8081 no ficheiro `application.properties` para evitar conflitos de rede locais
com outros serviços).*

A base de dados utilizada é o **H2 (in-memory)**. Não é necessária qualquer configuração ou instalação de servidores
externos. As tabelas são criadas automaticamente no arranque da aplicação através do Spring Data JPA.

## 🏗️ Explicação do Código e Arquitetura

O projeto segue rigorosamente o padrão **MVC (Model-View-Controller)**, garantindo uma clara separação de responsabilidades:

* **Model (`/model`):** Contém as entidades `Sala` e `Reserva`, mapeadas para a base de dados com anotações JPA (`@Entity`).
Incluem validações de dados essenciais (ex: a capacidade da sala tem de ser superior a zero, garantido pela anotação `@Min(1)`).
* **Repository (`/repository`):** Interfaces que estendem `JpaRepository`, fornecendo acesso imediato a operações CRUD
(Criar, Ler, Atualizar, Apagar) sem a necessidade de escrever queries SQL manuais.
* **Service (`/service`):** O núcleo da lógica da aplicação. A classe `ReservaService` isola e aplica as regras de negócio:
valida se a hora de fim é posterior à de início e bloqueia a gravação de reservas caso exista sobreposição de horários para a mesma sala e data.
* **Controller (`/controller`):** As classes `SalaController` e `ReservaController` intercetam os pedidos HTTP (GET/POST),
comunicam com os serviços/repositórios e devolvem as páginas adequadas ao utilizador com os respetivos dados.
* **Views (`/resources/templates`):** Ficheiros HTML enriquecidos com a tecnologia **Thymeleaf** para renderização dinâmica
no lado do servidor. A interface visual personalizada utiliza a biblioteca **Bootstrap 5** e **Bootstrap Icons** injetados
via CSS para garantir um aspeto moderno, responsivo e comercialmente apelativo.