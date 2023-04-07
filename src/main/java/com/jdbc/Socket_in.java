package com.jdbc;

import java.net.*;
import java.io.*;

public class Socket_in {
    //инициализация сокетов и инпут стрима
    private java.net.Socket socket;
    private ServerSocket client ;
    public String data;

    public Socket_in(int port)
    {
    // starts server and waits for a connection
        try{
            client = new ServerSocket(port);
            socket = client.accept();
    // takes input from the client socket
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            data = is.readLine();
            socket.close();
            is.close();
        }
        catch(Exception i){
            System.out.println(i);
        }
    }
    public String get(){
        return data;
    }
}
