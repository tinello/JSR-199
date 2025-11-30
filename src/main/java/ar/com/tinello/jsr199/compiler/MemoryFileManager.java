package ar.com.tinello.jsr199.compiler;

import java.util.HashMap;
import java.util.Map;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

// FileManager that stores the .class files in memory
public class MemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {
    private final Map<String, MemoryJavaFileObject> classFiles = new HashMap<>();

    public MemoryFileManager(final JavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(final Location location, final String className,
                                               final JavaFileObject.Kind kind, final FileObject sibling) {
        final var file = new MemoryJavaFileObject(className, kind);
        classFiles.put(className, file);
        return file;
    }

    public Map<String, byte[]> getClassBytes() {
        final var result = new HashMap<String, byte[]>();
        for (Map.Entry<String, MemoryJavaFileObject> e : classFiles.entrySet()) {
            result.put(e.getKey(), e.getValue().getBytes());
        }
        return result;
    }
}
