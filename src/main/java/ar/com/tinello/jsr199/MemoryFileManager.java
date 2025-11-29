package ar.com.tinello.jsr199;

import java.util.HashMap;
import java.util.Map;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

// FileManager que guarda los .class en memoria
class MemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {
    private final Map<String, MemoryJavaFileObject> classFiles = new HashMap<>();

    MemoryFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className,
                                               JavaFileObject.Kind kind, FileObject sibling) {
        MemoryJavaFileObject file = new MemoryJavaFileObject(className, kind);
        classFiles.put(className, file);
        return file;
    }

    public Map<String, byte[]> getClassBytes() {
        Map<String, byte[]> result = new HashMap<>();
        for (Map.Entry<String, MemoryJavaFileObject> e : classFiles.entrySet()) {
            result.put(e.getKey(), e.getValue().getBytes());
        }
        return result;
    }
}
