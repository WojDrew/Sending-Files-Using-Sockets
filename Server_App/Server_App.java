/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_app;

import javafx.concurrent.Task;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.ExportException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server_App {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(8);
        try (ServerSocket serverSocket = new ServerSocket( 1500)) {
            while (true) {
                final Socket socket = serverSocket.accept();
                executor.submit(() -> {
                    try {
                        FileTask.call(socket);
                    } catch (IOException e) {
                        System.out.println("Problem podczas pobierania: " + e.getMessage());
                    }
                });
            }

        }
        catch (IOException ex){
            System.out.println("\n" + "Wystąpił problem podczas akceptowania poloczenia");
            System.exit(1);
        }
    }
}

