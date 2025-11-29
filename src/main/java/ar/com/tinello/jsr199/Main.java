package ar.com.tinello.jsr199;

import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

public class Main {
    public static void main(String[] args) {
        System.out.println("JSR-199 Example");

        String className = "Hello";
        String code = 
            "public class Hello {\n" +
            "    public String greet(String name) {\n" +
            "        return \"Hello \" + name + \" from runtime!!!\";\n" +
            "    }\n" +
            "}";

        try {
            final var startTime = System.currentTimeMillis();
            System.out.println("Start compilation...");
            // Compile and obtain a ClassLoader that contains the compiled class
            MemoryClassLoader loader = compile(className, code);
            System.out.println("Compilation successful: " + (System.currentTimeMillis() - startTime) + " ms.");
            

            
            // Load the class from the ClassLoader
            Class<?> cls = loader.loadClass(className);

            Object obj = cls.getDeclaredConstructor().newInstance();
            System.out.println(cls.getMethod("greet", String.class).invoke(obj, "World"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static MemoryClassLoader compile(String className, String sourceCode) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        JavaFileObject file = new JavaSourceFromString(className, sourceCode);

        MemoryFileManager fileManager =
                new MemoryFileManager(compiler.getStandardFileManager(null, null, null));

        JavaCompiler.CompilationTask task = compiler.getTask(
                null, fileManager, null, null, null, Arrays.asList(file));

        if (!task.call()) {
            throw new RuntimeException("Compilation failed.");
        }

        return new MemoryClassLoader(fileManager.getClassBytes());
    }
}