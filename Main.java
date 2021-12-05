package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final ServerSocket serveurSocket;
        final Scanner sc=new Scanner(System.in);
        final int PORT = 5000;
        final ArrayList<Socket> clients = new ArrayList<>();

        try {
            serveurSocket = new ServerSocket(PORT);
            System.out.println("Server start listening in PORT " + PORT);

            Thread envoi= new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    while(true){
                        msg = sc.nextLine();
                        for(Socket client : clients) {
                            if(client != null) {
                                try {
                                    PrintWriter out = new PrintWriter(client.getOutputStream());
                                    out.println(msg);
                                    out.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            });
            envoi.start();

            while(true) {
                Socket clientSocket = serveurSocket.accept();
                System.out.println("New client connected "
                        + clientSocket.getInetAddress()
                        .getHostAddress()
                        + " with PORT " + clientSocket.getPort());
                ClientThread client
                        = new ClientThread(clientSocket);

                new Thread(client).start();
                clients.add(clientSocket);
            }


        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
