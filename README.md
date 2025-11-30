# JSR-199
This project is focused on testing the generation and compilation of Java classes at runtime from text.

## Require

 - Java 23
 - Maven 3.9
 - VScode
    - Extension Pack for Java -> https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack


Generate package

```bash
./mvnw clean package
```

Run

```bash
java -jar ./target/jsr-199-1.0.0.jar
```