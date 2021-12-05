package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread implements Runnable {
    private final Socket clientSocket;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    private String getClientInfo() {
        return clientSocket.getInetAddress().getHostAddress()+":"+
                clientSocket.getPort();
    }

    @Override
    public void run() {
        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Client - " + getClientInfo() + " : " + line);
            }
            System.out.println("Client - " + getClientInfo() + " disconnected");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
                clientSocket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
