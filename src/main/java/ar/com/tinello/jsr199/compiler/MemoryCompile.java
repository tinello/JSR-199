package ar.com.tinello.jsr199.compiler;

import java.util.Arrays;

import javax.tools.ToolProvider;

public class MemoryCompile {

    public MemoryClassLoader compile(final String className, final String sourceCode) throws Exception {
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
