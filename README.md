# Todo spring boot experiment

* Run database

```sh
docker-compose up -d postgres
```

## Packaging as jar
```sh
./gradlew bootJar
```
## Run all unit testing

```sh
./gradlew test
```
After run test you can see the result with command

__Mac__
```sh
open -a "Google Chrome" build/jacoco/test/html/index.html 

```
__Linux__
```sh
google-chrome build/jacoco/test/html/index.html
```
## Run with spring boot application runner

```sh
./gradlew bootRun
```

## Run and build with docker image

```
./gradlew bootJar
docker-compose up -d --build todo 
```

## Open documentation
```
http://localhost:8000/swagger-ui
```
