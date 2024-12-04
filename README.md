Como Rodar o Projeto
Para rodar o projeto "delivery", siga os seguintes passos:

Pré-requisitos:

Java 17
Maven
Docker (opcional, para rodar o projeto em um contêiner)
Clonar o repositório:

Code
git clone https://github.com/maxtbrsk/delivery.git
cd delivery
Construir o projeto com Maven:

mvn clean package
Rodar o projeto:

java -jar target/springboot-0.0.1-SNAPSHOT.jar
Usar Docker (opcional):

Construir a imagem Docker:
docker build -t delivery-app .
Rodar o contêiner Docker:
docker run -p 8080:8080 delivery-app
Estrutura do Projeto
A estrutura principal do projeto é a seguinte:

pom.xml: Arquivo de configuração do Maven, que inclui as dependências e plugins necessários para construir o projeto.
Dockerfile: Arquivo de configuração para construir a imagem Docker do projeto.
src/: Diretório contendo o código-fonte do projeto.
main/: Contém o código-fonte principal.
java/: Contém os arquivos Java do projeto.
resources/: Contém os arquivos de configuração e recursos do projeto.
test/: Contém os testes do projeto.
Detalhes Adicionais
O projeto usa Spring Boot como framework principal.
As dependências principais incluem Spring Data JPA, Spring Web, e PostgreSQL.
O projeto expõe a aplicação na porta 8080.
Para mais detalhes, consulte os arquivos pom.xml e Dockerfile.