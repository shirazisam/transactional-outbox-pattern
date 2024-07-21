# Transactional Outbox Pattern POC

Bare-bones Spring Boot application illustrating the pattern. CRUD operations are invoked through a web API
using REST endpoints. For each operation, an Outbox entry is created. Periodically the Outbox table is scanned
and each entry is placed on a Rabbit queue. The entry is then removed after successful message 
placement. If the application crashes _before_ sending the messages, they will be sent once the
application restarts.

## Prerequisite conditions
- local MySql database running with existing `mandatedb` schema
- local RabbitMQ broker instance running on port `5672`

**All tables and message queues will automatically be created by the application!**


## Installing and running the application

- clone the repository in a directory:
```
git clone https://github.com/shirazisam/transactional-outbox-pattern.git
cd transactional-outbox-pattern
```
make sure your `JAVA_HOME` environment variable is set correctly

- Run Maven to build and launch the application (or use the wrapper supplied):

```
mvnw spring-boot:run
```

---

## Use the web REST API to execute CRUD commands on the database

Use **Postman** (or other) to send JSON POST & DELETE requests

### Create a Mandate object - use the following POST url:
```
http://localhost:8080/outbox/api/v1/mandates
```
An example Mandate object:
```
{
    "debtType" : "DEBT_A",
    "debtReference" : "debt reference",
    "persIdf" : "1234",
    "mandateReference" : "mandate ref",
    "duration" : 25
}
```
No ID should be specified, as this will automatically be generated by the underlying database.
This will place the following JSON document on the `outbox-messaging-queue` message queue after
removing it from the Outbox table:
```
{"id":24,"mandateId":18,"operation":"CREATED","timestamp":"2024-07-21 16:26:12"}
```
Here, the `"mandateId":18` is the ID of the newly created Mandate and `operation` shows that
the object was `CREATED` at the given timestamp. The `id` in the document is the ID of the Outbox item.

### Update a Mandate object - use the _same_ POST URL as before
This time, ***specify the ID*** of the Mandate you wish to update, along with modified values, e.g.:
```
{
    "id": 18
    "debtType" : "ANOTHER_DEPT_TYPE",
    "debtReference" : "debt reference IS MODIFIED",
    "persIdf" : "1234",
    "mandateReference" : "mandate ref is changed",
    "duration" : 25
}
```
This will place the following JSON document on the `outbox-messaging-queue` after
removing it from the Outbox table:
```
{"id":25,"mandateId":18,"operation":"UPDATED","timestamp":"2024-07-21 17:13:02"}
```
This shows that existing `mandate` with `id` 18 has been updated at the given timestamp.

### Delete a Mandate object 
use the following URL DELETE request format:
```
http://localhost:8080/outbox/api/v1/mandates/{mandateId}
```
for example:
```
http://localhost:8080/outbox/api/v1/mandates/18
```
This will place the following JSON document on the `outbox-messaging-queue`:
```
{"id":26,"mandateId":18,"operation":"DELETED","timestamp":"2024-07-21 17:18:22"}
```
### Querying the Mandate database (GET)
The database can be queried by ID by using the following GET request:
```
http://localhost:8080/outbox/api/v1/mandates/{mandateId}
```
A list of ALL Mandate object can be returned by specifying **no** id, thus:
```
http://localhost:8080/outbox/api/v1/mandates
```

## Running the application as a Docker container

### **- TODO -**