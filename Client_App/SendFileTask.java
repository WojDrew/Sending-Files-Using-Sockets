package pkg171838lab_2;
import javafx.concurrent.Task;

import java.io.*;
import java.net.Socket;

public class SendFileTask extends Task<Void> {
    File file; //plik do wysłania
    Socket socket;
    public SendFileTask(File file) {
        this.file = file;
    }

    @Override protected Void call() throws Exception {
        updateMessage("Rozpoczecie wysylania");


        socket = new Socket("localhost", 1500);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(file.getName());
        out.writeLong(file.length());

        long totalBytes = file.length(), totalRead = 0;

        updateMessage("Wysylanie pliku");
        updateProgress(0, totalBytes);

        FileInputStream in = new FileInputStream(file);

        byte[] buffer = new byte[8192];
        while (totalRead != totalBytes) {
            int read = in.read(buffer);
            out.write(buffer, 0, read);
            totalRead += read;

            updateProgress(totalRead, totalBytes);
        }

        updateMessage("Wysylanie zakonczone!");
        updateProgress(100, 100);

        return null;
    }
}
