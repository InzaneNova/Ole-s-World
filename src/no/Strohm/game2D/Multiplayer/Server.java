package no.Strohm.game2D.Multiplayer;

import java.net.ServerSocket;

public class Server extends Thread{

    public ServerSocket serverSocket;
    public boolean run = true;
    public ServerManager serverManager[];

    public Server(int port, int antPlayers){
        try{
            serverSocket = new ServerSocket(port);
            serverManager = new ServerManager[antPlayers];
        }catch(Exception e){

        }
    }
    public void run(){
        while(loop());
    }
    public boolean loop(){
        try{

        }cat
        return run;
    }
}class ServerManager{

}
