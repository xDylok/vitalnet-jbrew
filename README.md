# Eclipse Starter for Jakarta EE
Aviso:
```
En caso de que no se levante con los puertos indicados, revisar el archivo docker-compose.yml
y server.xml
```

Construir el proyecto "target":
```
./mvnw clean package 
```
Limpiar base de datos y volumenes:
```
docker-compose down -v
```
Levantar ase de datos Docker:

```
docker-compose up
```
Acceder a la base de datos PostgreSQL con Adminer
```
Usuario: admin
Contraseña: admin1234
Server: db
Database: mydb
```
Entrar a Adminer postgreSQL URL:
```
http://localhost:9081/
```

Agregar usuario y roles en postgreSQL adminer seccion "comando SQL" (copiar y pegar todo):

```
INSERT INTO Role (id, name) VALUES (1, 'ADMIN');
INSERT INTO Role (id, name) VALUES (2, 'DOCTOR');
INSERT INTO Role (id, name) VALUES (3, 'ENFERMERO');
INSERT INTO Role (id, name) VALUES (4, 'PACIENTE');
INSERT INTO Role (id, name) VALUES (5, 'USUARIO');

INSERT INTO Users (id, name, password, role_id)
VALUES (1, 'admin', 'qwMrWvaYim58QzJxxLJvbg==', 1);
SELECT setval('public.users_id_seq', (SELECT MAX(id) FROM users));

```
Para acceder a la aplicacion vitalnet:
```
http://localhost:9000/start.xhtml
```
Iniciar sesión con las siguientes credenciales:
```
User: admin
Password: admin1234
```
