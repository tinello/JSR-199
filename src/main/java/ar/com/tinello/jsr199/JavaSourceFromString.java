package ar.com.tinello.jsr199;

import javax.tools.SimpleJavaFileObject;

class JavaSourceFromString extends SimpleJavaFileObject {
    final String code;

    JavaSourceFromString(final String name, final String code) {
        super(java.net.URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension),
              Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }
}
