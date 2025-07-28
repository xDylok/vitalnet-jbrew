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

INSERT INTO vital_sign_range (
    frecuenciamax,
    frecuenciamin,
    pesomax,
    pesomin,
    presionnormal,
    tempmax,
    tempmin
) VALUES (
    100,              -- frecuencia cardíaca máxima
    60,               -- frecuencia cardíaca mínima
    90.0,             -- peso máximo en kg
    45.0,             -- peso mínimo en kg
    '120/80',         -- presión arterial normal
    37.5,             -- temperatura máxima en °C
    36.0              -- temperatura mínima en °C
);

INSERT INTO vital_sign (
    altura,
    fecharegistro,
    frecuenciacardiaca,
    peso,
    presionarterial,
    temperatura,
    patient_id,
    responsable_id
) VALUES (
    1.75,                          -- altura en metros
    CURRENT_TIMESTAMP,            -- fecha de registro actual
    72,                           -- frecuencia cardíaca
    68.5,                         -- peso en kilogramos
    '120/80',                     -- presión arterial
    36.6,                         -- temperatura en grados Celsius
    1,                          -- ID del paciente
    1                             -- ID del responsable
);

INSERT INTO patient (
    birth_date,
    cedula,
    email,
    first_name,
    gender,
    genero,
    last_name,
    phone,
    tiposangre,
    user_id
) VALUES (
    '1990-05-15',           -- Fecha de nacimiento
    '1102345678',           -- Cédula
    'juan.perez@example.com', -- Email
    'Juan',                 -- Nombre
    'Masculino',            -- Género (opcional)
    'M',                    -- Género alternativo (opcional)
    'Pérez',                -- Apellido
    '0998765432',           -- Teléfono
    'O+',                   -- Tipo de sangre
    1                       -- ID de usuario (clave foránea a users.id)
);
INSERT INTO permission (
    action,
    resource
) VALUES (
    'READ',             -- Acción permitida (ej. READ, WRITE, DELETE)
    'vital_sign'        -- Recurso sobre el que se aplica la acción
);

INSERT INTO person (
    birth_date,
    cedula,
    email,
    first_name,
    gender,
    last_name,
    phone
) VALUES (
    '1985-08-22',           -- Fecha de nacimiento
    '1101122334',           -- Cédula
    'maria.lopez@example.com', -- Email
    'María',                -- Nombre
    'Femenino',             -- Género
    'López',                -- Apellido
    '0987654321'            -- Teléfono
);

INSERT INTO role_permission (
    role_id,
    permission_id
) VALUES (
    2,     -- ID del rol (por ejemplo, Médico)
    5      -- ID del permiso (por ejemplo, WRITE sobre vital_sign)
);



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
