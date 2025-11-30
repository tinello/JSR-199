package ar.com.tinello.jsr199.examples;

import ar.com.tinello.jsr199.compiler.MemoryCompile;

public class ThreadExecute implements Execute {

    @Override
    public void run() {
        System.out.println("ThreadExecute running...");

        final var className = "Thread";
        final var code = """
            public class Thread {
                public String greet(String name, String threadName) {
                    return "Hello " + name + " from runtime!, in a Thread: " + threadName;
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

            Runnable task = () -> {
                final var threadId = String.valueOf(Thread.currentThread().threadId());
                final var threadName = Thread.currentThread().getName();
                //System.out.println("Running in thread ID: " + threadId + ", Name: " + threadName);
                final var thread = threadName+" ("+threadId+")";
                try {
                    final var greet = clsHello.getMethod("greet", String.class, String.class).invoke(objHello, "World", thread);
                    System.out.println(greet);    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };

            for (int i = 0; i < 10; i++) {
                Thread.ofVirtual().start(task).setName("Virtual-Thread-" + i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
