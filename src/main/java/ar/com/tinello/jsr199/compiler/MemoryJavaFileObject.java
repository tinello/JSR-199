package ar.com.tinello.jsr199.compiler;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

// Representation of the .class compiled in memory
public class MemoryJavaFileObject extends SimpleJavaFileObject {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    public MemoryJavaFileObject(String className, Kind kind) {
        super(URI.create("mem:///" + className.replace('.', '/') + kind.extension), kind);
    }

    @Override
    public OutputStream openOutputStream() {
        return outputStream;
    }

    public byte[] getBytes() {
        return outputStream.toByteArray();
    }
}