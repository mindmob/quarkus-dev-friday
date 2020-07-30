# quarkus-dev-friday project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Setting up the application

Create new Project

``` bash
mvn io.quarkus:quarkus-maven-plugin:1.6.1.Final:create \
    -DprojectGroupId=de.devfriday \
    -DprojectArtifactId=quarkus-dev-friday \
    -Dextensions="reactive-pg-client"
cd quarkus-dev-friday
```

Add jsonb reasteasy and mutiny dependency:

``` bash
./mvnw quarkus:add-extension -Dextensions="io.quarkus:quarkus-resteasy-jsonb,io.quarkus:quarkus-resteasy-mutiny,io.quarkus:quarkus-smallrye-graphql"
```

For testing purposes add these dependencies manually to your `pom.xml`:

``` xml
<dependency>
  <groupId>org.testcontainers</groupId>
  <artifactId>testcontainers</artifactId>
  <version>1.12.5</version>
  <scope>test</scope>
</dependency>
<dependency>
  <groupId>org.testcontainers</groupId>
  <artifactId>postgresql</artifactId>
  <version>1.12.5</version>
  <scope>test</scope>
</dependency>
```

For convenience add Lombok to `pom.xml`. We all love Lombok, right?

``` xml
<dependency>
  <groupId>org.projectlombok</groupId>
  <artifactId>lombok</artifactId>
  <version>1.18.12</version>
  <scope>provided</scope>
</dependency>
```

Create a Docker bridge network (we will need that later when running a native app in Docker): `docker network create quarkus`

Start postgres docker container:

``` bash
docker run --network quarkus --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --name quarkus_test -e POSTGRES_USER=quarkus_dev_friday -e POSTGRES_PASSWORD=quarkus_dev_friday -e POSTGRES_DB=quarkus_dev_friday -p 5432:5432 postgres:latest
```

Inspect the created Docker network to see the actual IP address of the running postgres container: `docker network inspect quarkus`.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

``` bash
./mvnw quarkus:dev
```

## Resources

Get REST resource via: `curl http://localhost:8080/train/`

Get Graphql schema via: `curl http://localhost:8080/graphql/schema.graphql`

Open GraphiQL UI via: `http://localhost:8080/graphql-ui/`

Send a query like:

``` graphql
{
  allTrains {
    name
    id
  }
  getTrain(trainId: 2) {
    name
  }
}
```

## Running the unit tests

You can run the unit tests using

``` bash
mvn test
```

or directly from IntelliJ. If the tests fail in IntelliJ but not in maven you can try this: https://stackoverflow.com/questions/39217830/how-to-use-parameters-javac-option-in-intellij/39232302#39232302

## Debugging the application (IntelliJ)

Install the following plugin from the marketplace: `Quarkus Integration`. Restart IntelliJ.
Create new debug config using the `Quarkus Maven` template.
You can now start the application with a debugger attached by starting the debug config.

When starting the app in dev mode, it will also listen for a debugger on port 5005.
If you want to wait for the debugger to attach before running you can pass `-Dsuspend` on the command line.
If you don’t want the debugger at all you can use `-Ddebug=false`.

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `quarkus-dev-friday-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/quarkus-dev-friday-1.0-SNAPSHOT-runner.jar`.

## Creating a native executable

Install GraalVM via sdkman: `sdk install java 20.1.0.r11-grl`.

Install native image: `gu install native-image`.

You can create a native executable using: `./mvnw package -Pnative`.

You can then execute your native executable with: `./target/quarkus-dev-friday-1.0-SNAPSHOT-runner`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

If you build via Docker, then the executable will only work inside a docker container.

To package your executable into a Docker image use: `docker build -f src/main/docker/Dockerfile.native -t quarkus-dev-friday .`

Then run your image via: `docker run --network quarkus -i --rm -p 8080:8080 quarkus-dev-friday:latest`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.
