package ar.com.tinello.jsr199;

import java.util.Map;

// ClassLoader que carga bytecode desde memoria
class MemoryClassLoader extends ClassLoader {
    private final Map<String, byte[]> classBytes;

    MemoryClassLoader(Map<String, byte[]> classBytes) {
        this.classBytes = classBytes;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = classBytes.get(name);
        if (bytes == null)
            throw new ClassNotFoundException(name);
        return defineClass(name, bytes, 0, bytes.length);
    }
}
