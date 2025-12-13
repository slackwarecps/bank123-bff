# BFF Bank123

Bem-vindo ao coração da nossa máquina, o BFF (Backend for Frontend) do projeto Bank123.

Pense nele como o meio-campo habilidoso: ele não aparece na foto do gol, mas é ele quem organiza a jogada, pega os dados lá na defesa (banco de dados) e entrega a bola redondinha pro atacante (o aplicativo Flutter) só empurrar pra rede.

Este projeto é feito em **Java 17** com o **Spring Boot**, que é tipo a chuteira nova que a gente usa pra dar show em campo.

## 🚀 Pilha Tecnológica (Nosso Esquema Tático)

-   **Java 17**: O nosso camisa 10, a estrela do time.
-   **Spring Boot**: O nosso técnico, que organiza a casa e deixa tudo pronto pra gente.
-   **Maven**: O roupeiro, que cuida de todas as dependências e garante que ninguém entre em campo com o meião trocado.
-   **PostgreSQL**: O nosso cofre, onde a gente guarda o suado dinheirinho dos nossos clientes.
-   **Firebase Auth**: O segurança da balada. Só entra quem tiver o nome na lista (token JWT válido).
-   **OpenAPI 3.0**: O nosso manual de táticas, documentando todas as jogadas (endpoints).

## 📋 Pré-requisitos

Antes de botar pra rodar, garante que você tem o material de jogo:

1.  **Java 17+ (JDK)**: Se não tiver, a bola nem rola.
2.  **Maven**: Já vem com o nosso wrapper (`mvnw`), então é só usar.
3.  **Docker e Docker Compose**: Para subir o banco de dados PostgreSQL localmente.
4.  **Conta no Firebase**: Você vai precisar de uma conta no Firebase para gerar o arquivo `serviceAccountKey.json`.

## ⚙️ Configuração do Ambiente Local e Credenciais

Para garantir a segurança e facilitar o desenvolvimento, as credenciais e chaves sensíveis são gerenciadas via variáveis de ambiente e arquivos `.gitignore`.

### 1. Banco de Dados PostgreSQL (Docker)

A fonte verdade do esquema do banco de dados está no arquivo `banco-postgres/dump-bank123-v15.sql`. Para subir o banco de dados com a configuração correta:

1.  **Navegue até a pasta `banco-postgres`** no terminal:
    ```bash
    cd banco-postgres
    ```
2.  **Derrube o container existente e apague os volumes de dados (se houver):**
    ```bash
    docker-compose down --volumes
    ```
3.  **Recrie e inicie o container do banco de dados em segundo plano:**
    ```bash
    docker-compose up -d --build
    ```
    Isso criará o container `bank123-postgres` e importará o esquema e os dados iniciais.
4.  **Volte para a raiz do projeto:**
    ```bash
    cd ..
    ```

### 2. Credenciais do Banco de Dados (application.properties)

O arquivo `src/main/resources/application.properties` foi configurado para ler o usuário e a senha do banco de dados de variáveis de ambiente:

```properties
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

*   **Para execução via Maven (`./mvnw spring-boot:run`):**
    Defina as variáveis de ambiente antes de executar o comando:
    ```bash
    export DB_USERNAME=bank123
    export DB_PASSWORD=senhabank123
    ./mvnw spring-boot:run
    ```

*   **Para execução via VS Code (`launch.json`):**
    O arquivo `.vscode/launch.json` já está configurado para injetar essas variáveis de ambiente automaticamente para o perfil de debug.

### 3. Senha do PostgreSQL para Docker Compose

O `docker-compose.yml` agora lê a senha do PostgreSQL de um arquivo `.env`:

*   **Crie um arquivo chamado `.env`** na raiz do projeto com o seguinte conteúdo:
    ```
    POSTGRES_PASSWORD=senhabank123
    ```
*   Este arquivo `.env` foi adicionado ao `.gitignore` e **não deve ser versionado**.

### 4. Chave da Conta de Serviço do Firebase (`serviceAccountKey.json`)

*   O arquivo `serviceAccountKey.json` (baixado do seu projeto Firebase) deve ser colocado na pasta `src/main/resources/`.
*   Este arquivo também foi adicionado ao `.gitignore` e **não deve ser versionado** devido à sua natureza sensível. Obtenha-o de forma segura e não o inclua no controle de versão.

## ▶️ Como Executar (Apito Inicial)

### Via Maven Wrapper:

```bash
# Lembre-se de definir DB_USERNAME e DB_PASSWORD se não estiver usando o VS Code
export DB_USERNAME=bank123
export DB_PASSWORD=senhabank123
./mvnw spring-boot:run
```

### Via VS Code (Debug):

1.  Abra o painel "Run and Debug" (Ctrl+Shift+D ou Cmd+Shift+D).
2.  Selecione a configuração "Spring Boot-BffbankApplication".
3.  Clique no botão de iniciar (seta verde).

Se tudo der certo, o serviço vai subir e ficar esperando a bola chegar na porta `8080`.

## ✅ Como Testar (O Jogo-Treino)

Pra ver se a zaga tá firme e o ataque tá afiado, rode nossa bateria de testes:

```bash
./mvnw test
```

## 📖 API

Quer saber quais são as jogadas ensaiadas? Todos os nossos endpoints estão documentados no arquivo `swagger-bff-bank123.yaml`, seguindo o padrão OpenAPI 3.0. É o nosso VAR, pra não ter dúvida no lance!
