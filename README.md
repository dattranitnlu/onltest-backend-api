<h3 style="text-align: center">
    BACKEND: TEST MAKER APPLICATION
</h3>

---

APIs Documentation
------------
- Tool: Swagger
- Link document: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Code Conventions By Oracle
------------
- Link document: [https://www.oracle.com/java/technologies/javase/codeconventions-contents.html](https://www.oracle.com/java/technologies/javase/codeconventions-contents.html)

Installation
------------

Pre-conditions:
- JDK: [Oracle JDK version 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
- IDE: IntelliJ IDEA Ultimate(recommended) or eclipse,...
- Database: [PostgreSQL](https://www.postgresql.org/download/) (recommended version 12.x)
- Libraries: [lombok](https://projectlombok.org/)
- Migration: [Flyway](https://flywaydb.org/)

### Clone repo

``` bash
git clone https://gitlab.com/test-maker-team/test-maker-api.git
```

### Go to folder

``` bash
cd test-maker-api/
```

### Pull images and run as containers for project(database, cache, message queue,...)
``` bash
docker-compose up -d
```

### Create environment file with application-<env_name>.yml in <project>/src/main/resources/

### Such as application-dev.yml, application.yml,...

```text
# ===============================
# = DATA SOURCE
# ===============================
# Set here configurations for the database connection
spring:
  datasource:
    url: jdbc:postgresql://<host>:<port_number>/<database_name>
    username: <db_username>
    password: <db_password>
    dbcp2:
      test-while-idle: true
      validation-query: SELECT 1
  sql:
    init:
      continue-on-error: true
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
  flyway:
    locations: classpath:dev/db/migration
    encoding: UTF-8
    url: jdbc:postgresql://<host>:<port_number>/testmaker_dev
    user: <db_username>
    password: <db_password>

jwt:
  signing:
    key:
      secret: mySecret
  http:
    request:
      header: Authorization
  token:
    expiration:
      in:
        seconds: 604800

```

<p align="center">
    <img src="/src/main/resources/static/images/image_env_file.png" alt="Illustration for creating env files">
</p>

### Choose environment to run project

Copy this text into argument vms to run

```text
-Dspring.profiles.active=<env_name>
```

<p style="text-align: center">
    <img src="/src/main/resources/static/images/image_add_args.PNG" alt="Illustration for adding arguments">
</p>

## What's included

Within the download you'll find the following directories and files, logically grouping common assets and providing both
compiled and minified variations. You'll see something like this:

```
<Root project>
├── src/                                                    # project root
│   ├── main/                                               # container java classes and resources
│   │   ├── java                                            # java folder - where storing packages and java classes
│   │   └── resources                                       # resources folder
│   │       ├── static                                      # static - where storing public files
│   │       ├── templates                                   # templates - where storing HTML template
│   │       └── application-<env_name>.yml                  # env files
│   │
│   └── test/                                               # test folder - where storing testing files
│       └── ...
│
└── build.gradle

```