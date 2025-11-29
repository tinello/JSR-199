package ar.com.tinello.jsr199;

import java.util.Map;

// ClassLoader that loads bytecode from memory
class MemoryClassLoader extends ClassLoader {
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
