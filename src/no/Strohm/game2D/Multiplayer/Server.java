package no.Strohm.game2D.Multiplayer;

import java.net.ServerSocket;

public class Server extends Thread{

    public ServerSocket serverSocket;

    public Server(int port){
        try{
            serverSocket = new ServerSocket(port);
        }catch(Exception e){

        }
    }
}
