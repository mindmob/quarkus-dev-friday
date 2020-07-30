# quarkus-dev-friday project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Setting up the application

- Create new Project

```
mvn io.quarkus:quarkus-maven-plugin:1.6.1.Final:create \
    -DprojectGroupId=de.devfriday \
    -DprojectArtifactId=quarkus-dev-friday \
    -Dextensions="reactive-pg-client"
cd quarkus-dev-friday
```

- Add jsonb reasteasy dependency

```
./mvnw quarkus:add-extension -Dextensions="io.quarkus:quarkus-resteasy-jsonb"
```

- Start postgres docker container:

```
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --name quarkus_test -e POSTGRES_USER=quarkus_dev_friday -e POSTGRES_PASSWORD=quarkus_dev_friday -e POSTGRES_DB=quarkus_dev_friday -p 5432:5432 postgres:latest
```






## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Running the unit tests

You can run the unit tests using
```
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

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/quarkus-dev-friday-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.

## Dev Friday