package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

public class ThreadServer extends Thread{
    private Socket socket;
    private ArrayList<Socket> usuarios;
    private HashMap<Socket, String> listNomUsu;

    public ThreadServer(Socket socket, ArrayList<Socket> usuarios, HashMap<Socket, String> listNomUsu) {
        this.socket = socket;
        this.usuarios = usuarios;
        this.listNomUsu = listNomUsu;
    }

    @Override
    public void run() {
        try {
            BufferedReader bfRd = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String outputString = bfRd.readLine();
                if (outputString.equals("cerrar sesión")) {
                    throw new SocketException();
                }
                if (!listNomUsu.containsKey(socket)) {
                    String[] messageString = outputString.split(":", 2);
                    listNomUsu.put(socket, messageString[0]);
                    System.out.println(messageString[0] + messageString[1]);
                    mostrarMensaje(socket, messageString[0] + messageString[1]);
                }
                else {
                    System.out.println(outputString);
                    mostrarMensaje(socket, outputString);
                }
            }
        }
        catch (SocketException e) {
            String printMessage = listNomUsu.get(socket) + " dejó el chat";
            System.out.println(printMessage);
            mostrarMensaje(socket, printMessage);
            usuarios.remove(socket);
            listNomUsu.remove(socket);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    private void mostrarMensaje(Socket soc, String outputString) {
        Socket socket;
        PrintWriter printWriter;
        int i = 0;
        while (i < usuarios.size()) {
            socket = usuarios.get(i);
            i++;
            try {
                if (socket != soc) {
                    printWriter = new PrintWriter(socket.getOutputStream(), true);
                    printWriter.println(outputString);
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
}
