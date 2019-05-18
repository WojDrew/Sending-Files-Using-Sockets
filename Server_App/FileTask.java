package server_app;

import javafx.concurrent.Task;

import java.io.*;
import java.net.Socket;

public class FileTask {


    public static Void call(Socket client)  throws IOException{
        String name; // File name
        long size;   // And its size

        try (DataInputStream dis = new DataInputStream(client.getInputStream())) {
            try {
                name = dis.readUTF();
                size = dis.readLong();
            } catch (IOException e) {
                System.out.println("problem z wczytaniem naglowka");

                client.close();
                return null;
            }

            File file = new File(".", name);
            System.out.println("Pobieranie " + name + " z " + client.getInetAddress().toString() + " ("+ size + " bitow)");

            // Download actually starts here:
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            try {
                int readSum = 0, justRead = 0;
                byte[] buffer = new byte[512];

                while (readSum < size) {
                    justRead = dis.read(buffer);

                    if (justRead != -1) readSum += justRead;
                    else throw new IOException("Problem z pobraniem calego pliku!");

                    fos.write(buffer, 0, justRead);
                }

                System.out.println("Zakonczono pobieranie " + name + "!");
                fos.close();
            } catch (IOException e) {
                System.out.println("Problem podczas pobierania " + name +
                        ", blad: " + e.getMessage());
                file.delete();
            }
        }

        client.close();
        return null;
    }
}
