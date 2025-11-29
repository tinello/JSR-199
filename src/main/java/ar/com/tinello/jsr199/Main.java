package ar.com.tinello.jsr199;

import java.util.Arrays;

import javax.tools.ToolProvider;

public class Main {
    public static void main(String[] args) {
        System.out.println("JSR-199 Example");

        final var className = "Hello";
        final var code = """
            public class Hello {
                public String greet(String name) {
                    return "Hello " + name + " from runtime!!!";
                }
            }
            """;

        try {
            final var startTime = System.currentTimeMillis();
            System.out.println("Start compilation...");
            // Compile and obtain a ClassLoader that contains the compiled class
            final var loader = compile(className, code);
            System.out.println("Compilation successful: " + (System.currentTimeMillis() - startTime) + " ms.");

            
            // Load the class from the ClassLoader
            final var clsHello = loader.loadClass(className);

            // Create an instance and invoke the greet method
            final var objHello = clsHello.getDeclaredConstructor().newInstance();
            final var greet = clsHello.getMethod("greet", String.class).invoke(objHello, "World");
            System.out.println(greet);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static MemoryClassLoader compile(final String className, final String sourceCode) throws Exception {
        final var compiler = ToolProvider.getSystemJavaCompiler();

        final var file = new JavaSourceFromString(className, sourceCode);

        final var fileManager =
                new MemoryFileManager(compiler.getStandardFileManager(null, null, null));

        final var task = compiler.getTask(
                null, fileManager, null, null, null, Arrays.asList(file));

        if (!task.call()) {
            throw new RuntimeException("Compilation failed.");
        }

        return new MemoryClassLoader(fileManager.getClassBytes());
    }
}