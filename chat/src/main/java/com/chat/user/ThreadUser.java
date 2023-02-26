package com.chat.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ThreadUser implements Runnable {

    private BufferedReader bfRd;

    public ThreadUser(Socket socket) throws IOException {
        this.bfRd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = bfRd.readLine();
                System.out.println(message);
            }
        }
        catch (SocketException e) {
            System.out.println("Has salido del chat");
        }
        catch (IOException exception) {
            System.out.println(exception);
        }
        finally {
            try {
                bfRd.close();
            }
            catch (Exception exception) {
                System.out.println(exception);
            }
        }
    }
}
