package ar.com.tinello.jsr199.examples;

import ar.com.tinello.jsr199.compiler.MemoryCompile;

public class HelloExecute implements Execute {

    @Override
    public void run() {
        System.out.println("HelloExecute running...");

        final var className = "Hello";
        final var code = """
            public class Hello {
                public String greet(String name) {
                    return "Hello " + name + " from runtime!!!";
                }
            }
            """;

        try {
            final var compiler = new MemoryCompile();
            final var startTime = System.currentTimeMillis();
            System.out.println("Start compilation...");
            // Compile and obtain a ClassLoader that contains the compiled class
            final var loader = compiler.compile(className, code);
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
    
}
