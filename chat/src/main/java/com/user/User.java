package com.user;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class User {
    public static void main(String[] args) {
        String nom;
        String res;
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce tu nombre: ");
        nom = sc.nextLine();
        res = nom;

        try (Socket socket = new Socket("localhost", 5000)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            ThreadUser threadUser = new ThreadUser(socket);
            new Thread(threadUser).start();

            out.println(res + ": se ha conectado al chat");
            do {
                String message = (nom + " : ");
                res = sc.nextLine();
                if (res.equals("cerrar sesión")) {
                    out.println("cerrar sesión");
                    break;
                }
                // Check if the message is a whisper by looking for the syntax "/w recipient message"
                if (res.startsWith("/w ")) {
                    int spaceIndex = res.indexOf(' ', 3);
                    if (spaceIndex != -1) {
                        String recipient = res.substring(3, spaceIndex);
                        String whisperMessage = res.substring(spaceIndex + 1);
                        out.println("(whisper to " + recipient + ") " + message + whisperMessage);
                    } else {
                        System.out.println("Invalid whisper syntax. Usage: /w recipient message");
                    }
                } else {
                    out.println(message + res);
                }
            } while (!res.equals("cerrar sesión"));
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
}
