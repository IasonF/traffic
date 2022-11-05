# Traffic Events and Violations

## Implementation points
1) Spring web for REST api
2) Spring asynchronous events for creating/processing traffic events
3) Concurrent collections
4) Swagger as a basic ui
5) Some tests

## REST documentation:
The following endpoints:

| Method | Path                       | Description                                          |
|--------|----------------------------|------------------------------------------------------|
| GET    | /events/violations         | Returns UUID of violation, different from event UUID |
| POST   | /events                    | Create a new event                                   |
| GET    | /events/violations/summary | Summary of paid/unpaid fines                         |
| PUT    | /events/violation/{UUID}   | Pay a violation                                      |


## Instructions
Use the following to run: `./gradlew bootRun`
To run the tests, use `./gradlew check`
UI at http://localhost:8080/swagger-ui/index.html

