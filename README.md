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
Server: vitalnet
Database: vitaldb
```
Entrar a Adminer postgreSQL URL:
```
http://localhost:9081/
```

Agregar usuario y roles en postgreSQL adminer seccion "comando SQL" (copiar y pegar todo):

```
INSERT INTO paciente (
  apellidos, cedula, correo, fecha_nacimiento, gender, genero, nombres, telefono, id_usuario
) VALUES (
  'Vasquez', '1102345679', 'jostin.vasquez@example.com', '1995-07-30',
  'Masculino', 'M', 'Jostin', '0991234567', 2
);
INSERT INTO permisos (
  id_role, id_permisos, action, resource
) VALUES (
  2, 2, 'WRITE', 'vital_sign'
);
INSERT INTO persona (
  id_role, id_permisos, apelidos, cedula, email, fecha_nacimiento, gender, nombres, telefono
) VALUES (
  2, 2, 'Vasquez', '1102345679', 'jostin.vasquez@example.com', '1995-07-30',
  'Masculino', 'Jostin', '0991234567'
);
INSERT INTO rangos_signos_vitales (
  frecuenciamax, frecuenciamin, pesomax, pesomin, presionnormal, tempmax, tempmin
) VALUES (
  100, 60, 90.0, 45.0, '120/80', 37.5, 36.0
);
INSERT INTO roles (
  name
) VALUES (
  'DOCTOR'
);
INSERT INTO signos_vitales (
  altura, fecharegistro, frecuenciacardiaca, peso, presionarterial, temperatura, patient_id, responsable_id
) VALUES (
  1.74, CURRENT_TIMESTAMP, 70, 70.5, '122/82', 36.7, 2, 2
);

INSERT INTO usuarios (
  name, password, id_role, id_persona
) VALUES (
  'jostin', 'securePassword123', 2, 2
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
