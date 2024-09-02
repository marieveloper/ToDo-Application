

## Prerequisites

1. Install a JDK in at least java version 17.0.0
2. Check your `JAVA_HOME` and ensure whether it's set to the correct root directory of your JDK, as well as your JDK `bin` folder
3. Write your email credentials in the `application.properties` file
4. If preferred, you can install [Maven](https://maven.apache.org/), but by following the given steps you can also use the Maven Wrapper scripts (`mvnw` or `mvnw.cmd`)
5. For a valid Database connection please insall a local [MariaDB server](https://mariadb.org/download/), the default port is `3306` and the default credentials are `root`:`root`

## Usage

```bash
./mvnw clean install

# --> http://localhost:8080/api/v1/todos (or http://localhost:8080/api/v1/assignees)
java -jar ./target/rest-api.jar

# --> http://localhost:8080/api/v1/todos (or http://localhost:8080/api/v1/assignees)
./mvnw spring-boot:run
```

```properties
    spring.mail.username=your-email-username
    spring.mail.password=your-email-password
    spring.mail.senderAddress=your-email-address
```

After you run the created JAR file, you should be able to see the implemented resources in your browser (http://localhost:8080/api/v1/todos  or http://localhost:8080/api/v1/assignees).

## Documentation

- General references: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle
- Application properties: https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html
