# knative-serving-showcase project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application is packageable using `./mvnw package`.
It produces the executable `knative-serving-showcase-*-runner.jar` file in `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/knative-serving-showcase-*-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

You can then execute your binary: `./target/knative-serving-showcase-*-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image-guide .
