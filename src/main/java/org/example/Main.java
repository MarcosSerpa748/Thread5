package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
public class Main {
    public static void main(String[] args){
        ExecutorService executores = Executors.newFixedThreadPool(3);

        try {
            String atividadeQueProcessouPrimeiro = executores.invokeAny(new ArrayList<Callable<String>>(List.of(
                    () ->{
                        Thread.sleep(3000);
                        return"Servidor A: A cotação está a R$5.19";
                    },
                    () ->{
                        Thread.sleep(100);
                        throw new RuntimeException("Servidor B está quebrado!");
                    },
                    () ->{
                        Thread.sleep(1500);
                        return"Servidor C: A cotação está a R$5.19";
                    }
            )));

            System.out.println(atividadeQueProcessouPrimeiro);
        }catch (InterruptedException e){
            System.out.println("Thread principal foi interrompida!");
        }catch (ExecutionException e){
            System.out.println("Problema ocorrido em todas as atividades! Problema:"+e.getCause().getMessage());
        }finally {
            executores.shutdown();
        }

    }
}