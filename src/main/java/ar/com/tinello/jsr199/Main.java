package ar.com.tinello.jsr199;

import java.util.HashMap;

import ar.com.tinello.jsr199.compiler.MemoryCompile;
import ar.com.tinello.jsr199.examples.Execute;
import ar.com.tinello.jsr199.examples.HelloExecute;

public class Main {
    public static void main(String[] args) {

        final var compilers = new HashMap<String, Execute>();
        compilers.put("Hello", new HelloExecute());

        if (args.length != 1 || !compilers.containsKey(args[0])) {
            System.out.println("Available examples:");
            for (var key : compilers.keySet()) {
                System.out.println(" - " + key);
            }
            return;
        }

        compilers.get(args[0]).run();

    }



}