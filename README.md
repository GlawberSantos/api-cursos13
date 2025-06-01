API Cursos de Programação
API RESTful para gerenciamento de cursos de programação, com funcionalidades básicas de CRUD e ativação/desativação dos cursos.

Tecnologias utilizadas
Java 17+

Spring Boot

Spring Data JPA

Hibernate

Banco de dados H2 (configuração padrão, pode ser alterado)

Maven

Jakarta Validation

Funcionalidades
Criação de curso (POST /courses)

Listagem de cursos com filtro opcional por nome e categoria (GET /courses)

Atualização de curso pelo ID (PUT /courses/{id})

Remoção de curso pelo ID (DELETE /courses/{id})

Ativação/Desativação do curso via toggle (PATCH /courses/{id}/active)

Estrutura do Curso
Cada curso possui as seguintes propriedades:

Propriedade	Descrição
id	Identificador único (gerado pelo BD)
name	Nome do curso
category	Categoria (FRONTEND, BACKEND, DATABASE)
active	Status ativo/inativo (boolean)
createdAt	Data de criação
updatedAt	Data da última atualização

Endpoints principais
Criar curso
http
Copiar código
POST /courses
Content-Type: application/json

{
  "name": "Java Básico",
  "category": "BACKEND"
}
Listar cursos
http
Copiar código
GET /courses
Com filtros opcionais por name e category:

pgsql
Copiar código
GET /courses?name=Java&category=BACKEND
Atualizar curso pelo ID
http
Copiar código
PUT /courses/{id}
Content-Type: application/json

{
  "name": "Java Avançado",
  "category": "BACKEND"
}
Remover curso pelo ID
http
Copiar código
DELETE /courses/{id}
Ativar/Desativar curso (toggle)
http
Copiar código
PATCH /courses/{id}/active
Como rodar o projeto
Clone o repositório:

bash
Copiar código
git clone https://github.com/seu-usuario/seu-repositorio.git
Entre na pasta do projeto:

bash
Copiar código
cd seu-repositorio
Execute com Maven:

bash
Copiar código
./mvnw spring-boot:run
A API estará disponível em: http://localhost:8080

Considerações
A validação dos campos name e category está presente nas rotas de criação e atualização.

Categoria deve ser uma das opções: FRONTEND, BACKEND ou DATABASE.

Erros são tratados e retornam mensagens claras com o status HTTP adequado (ex: 404 quando o curso não é encontrado).

Contato
Desenvolvido por [GlauberSantos]

