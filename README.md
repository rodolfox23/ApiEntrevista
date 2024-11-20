# API para entrevista

## Contiene lo siguiente:
  * Login
    * Sign-up para un usuario nuevo pueda ingresar
    * Login para un usuario ya registrado
  * Tareas
    * Obtener tareas
    * Crear Tarea
    * Actualizar tarea
    * borrar tarea
  * Usuario
    * Obtener un usuario por email
    * Update de un usuario
    * Delete de un usuario

## Tecnologias utilizadas
* Spring Boot 3.0.0
* Spring Security
* JWT
* H2
* Gradle
* Bcrypt
* Java 17

## Importante
* Para los endpoint de usuario se debe colocar el token generado en el header con "Authorization" y "Bearer + token"

# Empezando:
* el request para el "sign-up" tiene la siguiente forma:
```json
{
  "nombre":"Rodolfo",
  "email":"rodolfo@example.com",
  "password":"Rodolfo1"
}
```
* El response sera el siguiente si el registro es existoso:
```json
{
  "id": "3",
  "nombre": "Rodolfo",
  "email": "rodolfo@example.com",
  "fechaCreacion": "2024-05-28T15:25:21.260399200",
  "modificado": "2024-05-28T15:25:21.260399200",
  "ultimoLogin": "2024-05-28T15:25:21.259400700",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb2RvbGZvQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE2OTI0MzIxLCJleHAiOjE3MTY5MjQ2MjF9.GbXLJJ33pc_36kqQ9ntXY-XO5RdeHJ1MOAxtp6eaS9w",
  "estaActivo": true
}
```
* Para el "login" el request es el siguiente:
  *Colocar el token generado anteriormente en el header con "Authorization" "Bearer + token" 
```json
{
  "email":"rodolfo@example.com",
  "password":"Rodolfo1"
}

```
* El response de ser un usuario registrado:
```json
{
  "id": "3",
  "created": "2024-05-28T15:25:21.260399",
  "lastLogin": "2024-05-28T15:26:07.975997400",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb2RvbGZvQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE2OTI0MzY3LCJleHAiOjE3MTY5MjQ2Njd9.2Rk10IUXEZg1NZDdMAmYi9Db0Mo7pKYCllJIMXrsIGo",
  "name": "Rodolfo",
  "email": "rodolfo@example.com",
  "active": true
}
```
* Si Se ingresa un email con el formato incorrecto saldra un mensaje como el siguiente

```json
{
    "mensaje": "Formato Email incorrecto"
}
```
* Para crear tareas se debe hacer uso del toguen generado en el login para tener acceso a crear tareas o usuarios
  * Para crear una tarea nueva se usa el siguiente formato.
    * Nota: El estado inicial de una actividad sera "Nueva".
    * http://localhost:8080/api/tareas
```json
{
    "titulo" : "generar documentacion",
    "descripcion" : "generar la documentacion de la api",
    "userId" : "3"
}
```
* Una vez creada se vera el response de esta manera:

```json
{
    "id": 1,
    "titulo": "generar documentacion",
    "descripcion": "generar la documentacion de la api",
    "userId": 3,
    "estado": {
        "id": 1,
        "estado": "Nueva"
    }
}
```
* Los estados de las tareas son generados al partir la aplicacion.
  * Pueden ser consultados simplemente usando
  * Get -> http://localhost:8080/api/estados
  * La respuesta para esta version sera lo siguiente:
  * Igualmente se debe estar logeado para ocupar el bearer token para hacer consultas
````json
[
  {
    "id": 1,
    "estado": "Nueva"
  },
  {
    "id": 2,
    "estado": "Pendiente"
  },
  {
    "id": 3,
    "estado": "En Progreso"
  },
  {
    "id": 4,
    "estado": "Completada"
  }
]
````

* si se desea crear más estados se puede mediante la api solo indicando el nombre del estado que se quiere crear:

````json
{
    "estado": "Retrasada"
}
````
si se vuelve a consultar por los estados existentes saldra lo siguiente:

````json
[
    {
        "id": 1,
        "estado": "Nueva"
    },
    {
        "id": 2,
        "estado": "Pendiente"
    },
    {
        "id": 3,
        "estado": "En Progreso"
    },
    {
        "id": 4,
        "estado": "Completada"
    },
    {
        "id": 5,
        "estado": "Retrasada"
    }
]
````

* Se puede cambiar el regex para validar la password directamente en el aplication properties
  * Ejemplo:
    * app.regex.password=^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,}$
* Para ver la documentacion pueden acceder al siguiente link:
    * http://localhost:8080/swagger-ui/index.html#/
