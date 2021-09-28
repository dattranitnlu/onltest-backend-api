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
- JDK: [Oracle JDK version 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
- IDE: IntelliJ IDEA Ultimate(recommended) or eclipse,...
- Database: [PostgreSQL](https://www.postgresql.org/download/) (recommended version 12.x)
- Libraries: [lombok](https://projectlombok.org/)

### Clone repo

``` bash
git clone https://github.com/hetsotchet12345/onltest-backend-api.git
```

### Create environment file with application-<env_name>.properties in <project>/src/main/resources/

### Such as application-dev.properties, application.properties,...)

```text

# ===============================
# = DATA SOURCE
# ===============================
# Set here configurations for the database connection
spring.datasource.url=jdbc:postgresql://<host>:<port_number>/<database_name>
# Username and secret
spring.datasource.username=<db_username>
spring.datasource.password=<db_password>

# Allows Hibernate to generate SQL optimized for a particular DBMS
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.sql.init.continue-on-error=true

# Keep the connection alive if idle for a long time (needed in production)
spring.datasource.dbcp.test-while-idle=true
spring.datasource.dbcp.validation-query= SELECT 1
# ===============================
# = JPA / HIBERNATE
# ===============================
# Use spring.jpa.properties.* for Hibernate native properties (the prefix is
# stripped before adding them to the entity manager).
# Show or not log for each sql query
spring.jpa.show-sql=true
# Hibernate ddl auto (create, create-drop, update): with "update" the database
# schema will be automatically updated accordingly to java entities found in
# the project
spring.jpa.hibernate.ddl-auto=update

################################################################################
jwt.signing.key.secret=<your_secret_for_jwt>
jwt.http.request.header=Authorization
jwt.token.expiration.in.seconds=604800

```

<p align="center">
    <img src="/src/main/resources/static/images/image_env_file.PNG" alt="Illustration for creating env files">
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