# Diretrizes para Interação com o Gemini

Este documento fornece diretrizes para o assistente de IA Gemini, garantindo consistência e aderência aos padrões do projeto BFF Bank123.

## 1. Visão Geral do Projeto

O projeto é um **BFF (Backend for Frontend)** para uma aplicação bancária, desenvolvido em **Java com Spring Boot**. Ele serve como uma camada intermediária entre o frontend e o Banco Postgres, expondo APIs REST para funcionalidades como consulta de saldo, extrato e dados de perfil de usuário.

## 2. Pilha Tecnológica Principal

-   **Linguagem:** Java 17+
-   **Framework:** Spring Boot
-   **Build:** Apache Maven (utilize sempre o wrapper `mvnw`)
-   **Banco de Dados:** PostgreSQL (via Spring Data JPA)
-   **Segurança:** Firebase Auth para autenticação de token JWT.
-   **Documentação da API:** OpenAPI 3.0 (no arquivo `swagger-bff-bank123.yaml`)

## 3. Padrões e Convenções de Código

-   **Arquitetura:** Siga estritamente a arquitetura em camadas existente:
    -   `controller`: Apenas para receber requisições, validar entradas e chamar o serviço correspondente.
    -   `service`: Contém toda a lógica de negócio.
    -   `repository`: Interface para acesso a dados via Spring Data JPA.
    -   `model`: Entidades JPA que mapeiam as tabelas do banco de dados.
    -   `dto`: Objetos de Transferência de Dados para as respostas da API.
-   **Estilo de Código:** Mantenha o estilo do código existente. Use as anotações do **Lombok** (`@Data`, `@AllArgsConstructor`, etc.) para evitar código boilerplate.
-   **Imutabilidade:** Quando apropriado, prefira objetos imutáveis, especialmente em DTOs e respostas de serviço.
-   **Tratamento de Erros:** Siga os padrões de tratamento de exceção do Spring Boot.

## 4. Ferramentas e Comandos

-   **Executar a aplicação:**
    ```bash
    ./mvnw spring-boot:run
    ```
-   **Executar os testes:**
    ```bash
    ./mvnw test
    ```
-   **Instalar dependências:**
    ```bash
    ./mvnw install
    ```

## 5. API e Documentação

-   **Fonte da Verdade:** O arquivo `swagger-bff-bank123.yaml` é a fonte da verdade para os endpoints da API.
-   **Modificações:** Ao adicionar ou modificar um endpoint, **sempre** atualize o `swagger-bff-bank123.yaml` primeiro ou na mesma etapa da implementação.

## 6. Commits e Controle de Versão

-   **Mensagens de Commit:** Use o padrão *Conventional Commits*.
    -   Exemplos: `feat: Adiciona endpoint de perfil de usuário`, `fix: Corrige cálculo de saldo`, `docs: Atualiza documentação do endpoint de extrato`.
-   **Branching:** Siga um modelo como o GitFlow (`feature/`, `bugfix/`, `release/`).

## 7. Preferencias
- Sempre me chame de Fabão.
- Uso macbook para codificar e uso o vscode como IDE.

A fonte verdade do banco esta em: banco-postgres/dump-bank123-v15.sql