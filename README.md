# cliente

## Prueba Ingreso Banco Internacional, proyecto cliente el Backend se encuentra desarrollado en Spring Boot y el Frontend en Angular

## Proyecto Backend
- 1.- Primero creamos una carpeta en el disco C, puede ser test
- 2.- Abrimos una terminal y navegamos hasta esta carpeta.
- 3.- Ejecutamos el comando, git clone https://github.com/manujcaCepeda/cliente.git
- 4.- Ingresamos a la carpeta cliente-backend, 
- 5.- Desde la terminal ejecutamos el siguiente comando, mvn clean install
- 6.- Ejecutamos, cd target
- 7.- Para levantar la aplicación ejecutamos, java -jar cliente-backend-0.0.1-SNAPSHOT.jar
- 8.- Revisamos que levante correctamente nuestro servidor toncat, por defecto levanta en el puerto 8080
- 9.- Abrimos una navegamos y navegamos a la siguiente URL, http://localhost:8080/usuarios/cedula/001384529
- 10.- Consumimos un endpoint GET que nos trae la información de un cliente

## Proyecto Frontend
- 1.- Navegamos hasta la carpeta donde descargamos las fuentes, ingresamos hasta la carpeta, cliente-frontend
- 2.- Abrimos una terminal y nos ubicamos en la carpeta, cliente-frontend
- 3.- Ejecutamos el comendo, npm install
- 4.- Y posteriormente ejecutamos el comendo, ng serve
- 5.- Revisamos que levante correctamente el servidor 
- 6.- Abrimos un navegador y navegamos a la siguiente URL, http://localhost:4200/
