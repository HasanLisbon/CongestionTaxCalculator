# Congestion Tax Calculator

Application to calculate the congestion tax fee in Different cities
## Requirements

For building and running the application you need:

- [OpenJDK 17](https://openjdk.org/projects/jdk/17/)
- [Maven 3](https://maven.apache.org)


## Running the application locally

```shell
mvn spring-boot:run
```

## Swagger api to see the application endpoints

After running the spring boot application swagger api will be available in [localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

# Getting access to database

While running get access to database with http://localhost:8080/h2
The username is "sa" and password is "password"

## Test the application locally using curl

```shell
curl --location 'http://localhost:8080/api/v1/congestion-tax' \
--header 'Content-Type: application/json' \
--data '{
    "vehicle":"car",
    "time":[
        "2013-01-14 21:00:00",
        "2013-01-15 21:00:00",
        "2013-02-08 14:35:00",
        "2013-02-08 16:01:00"
    ],
    "city":"GB"
}'
```
You might use also postman or other tool to make a post request and the endpoint for that is 'http://localhost:8080/api/v1/congestion-tax'
and send the body as shown before.

## Sample response

```shell
{
  "tax": 26,
  "currency": "SEK"
}

```