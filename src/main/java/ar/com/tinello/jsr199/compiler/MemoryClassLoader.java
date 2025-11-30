package ar.com.tinello.jsr199.compiler;

import java.util.Map;

// ClassLoader that loads bytecode from memory
public class MemoryClassLoader extends ClassLoader {
    private final Map<String, byte[]> classBytes;

    MemoryClassLoader(Map<String, byte[]> classBytes) {
        this.classBytes = classBytes;
    }

    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        final var bytes = classBytes.get(name);
        if (bytes == null)
            throw new ClassNotFoundException(name);
        return defineClass(name, bytes, 0, bytes.length);
    }
}
