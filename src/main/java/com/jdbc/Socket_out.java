package com.jdbc;

import java.net.*;
import java.io.*;

public class Socket_out {
    //инициализация сокетов и инпут стрима
    private java.net.Socket socket;
    private ServerSocket client ;
    public String data;

    public Socket_out(int port, String send)
    {

        // starts server and waits for a connection
        try{

            client = new ServerSocket(port);
            socket = client.accept();
            BufferedWriter ot = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            ot.write(send);
            ot.close();
            socket.close();

        }
        catch(Exception i){
            System.out.println(i);
        }
    }
}
