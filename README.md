# Transactional Outbox Pattern POC

Bare-bones Spring Boot application illustrating the pattern. CRUD operations are invoked through a web API
using REST endpoints. For each operation an Outbox entry is created. Periodically the Outbox table is scanned
and each entry is placed on a Rabbit queue. Each entry from the Outbox table is then removed after successful
message placement. If the application crashes _before_ sending the messages, they will be sent once the
application restarts.

## Installing and running the application

- clone the repository in a directory:
```
git clone https://github.com/shirazisam/transactional-outbox-pattern.git
cd 2024-DEV1-005-DevelopmentBooks
```
- Run Maven (or use the wrapper supplied): 

make sure your ```JAVA_HOME``` environment variable is set correctly

- build & launch the application:
```
mvnw spring-boot:run
```
- Use the web API to execute CRUD commands to the database - todo! 
```
http://localhost/bnp-kata/books?nrbooks=12
```
The above command will randomly generate 12 books of Book 1 to Book 5.
The output will be a JSON object with information about:
- Book price
- List of books
- Aggregates by title
- quantity of groupings
- calculation of discount
- calculation of % discount

use the request parameter ```nrbooks``` to randomly change the number of books generated.

## Running the application as a Docker container

If Docker is installed, the application can be launched as a container
by running the Docker build image target.
- in the project root folder, run the following:
```
mvnv package docker:build
```
This will build the application and create the Docker image on the local file system.

- now run the container:
```
docker container run -d -p 80:80 bnp-kata
```
This will launch the application in detached mode, using the default http web port (80).
Now run the application from the browser as before, or Postman using a GET request:
```
http://localhost/bnp-kata/books?nrbooks=10
```
This above example generates 10 random books of Book 1...Book 5. The aggregates,groupings and discounts
are displayed in the output JSON format. Example output:
```
{
  "bookPrice": 50,
  "books": [
    "Book 1",
    "Book 1",
    "Book 2",
    "Book 3",
    "Book 3",
    "Book 3",
    "Book 4",
    "Book 4",
    "Book 4",
    "Book 5"
  ],
  "bookCountByTitle": {
    "Book 1": 2,
    "Book 2": 1,
    "Book 3": 3,
    "Book 4": 3,
    "Book 5": 1
  },
  "groupings": {
    "2": 1,
    "3": 1,
    "5": 1
  },
  "totalPrice": 500,
  "totalDiscountPrice": 417.5,
  "percentageDiscount": 16.5
}
```
