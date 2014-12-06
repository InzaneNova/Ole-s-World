package no.Strohm.game2D.Multiplayer;

import no.Strohm.game2D.Game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{

    public ServerSocket serverSocket;
    public static boolean run = false;
    public boolean serverFull = false;
    public static ServerManager serverManager[];

    public Server(int port, int antPlayers){
        try{
            serverSocket = new ServerSocket(port);
            serverManager = new ServerManager[antPlayers];
        }catch(Exception e){
            System.out.println("was not abel to create server on port: " + port);
        }
        System.out.println("Successfully created server");
    }

    public void run(){
        run = true;
        while(loop());
    }

    public boolean loop(){
        int csm = -1;
        for(int c = 0; c < serverManager.length; c++){
            if(serverManager[c].empty){
                csm = c;
                break;
            }
        }
        if(csm == -1){
            serverFull = true;
        }
        try{
            serverManager[csm] = new ServerManager(serverSocket.accept());
            if(serverFull){
                serverManager[csm].dataOutputStream.writeUTF("full");
            }else{
                serverManager[csm].dataOutputStream.writeUTF("room");
            }
            serverManager[csm].ID = csm;
            serverManager[csm].start();
        }catch(Exception e){
            serverManager[csm].empty = true;
        }
        return run;
    }

    public static boolean testGameTag(String gameTag){
        for(int i = 0; i < serverManager.length; i++){
            if(gameTag.equals(serverManager[i].gameTag)){
                return false;
            }
        }
        return true;
    }

}class ServerManager extends Thread{

    public Socket socket;
    public boolean empty = true, running;
    public DataInputStream dataInputStream;
    public DataOutputStream dataOutputStream;
    int ID = 0;
    public String gameTag;

    public ServerManager(Socket getSocket){
        socket = getSocket;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {}
        running = true;
        empty = false;
    }

    public void run(){
        try {
            if(dataInputStream.readUTF().equals(Game.version)){
                gameTag = dataInputStream.readUTF();
                if(Server.testGameTag(gameTag)){
                    System.out.println("Sucsessfully conected to " + gameTag + " on ip: " + socket.getInetAddress().getAddress());
                    while(loop());
                }
            }else{
                dataOutputStream.writeUTF("version outdated");
            }
        } catch (Exception e) {
        }
        empty = true;
    }

    public boolean loop(){
        try {
            String message = dataInputStream.readUTF();
        } catch (IOException e) {
            System.out.println("Dissconected from "+gameTag);
            return false;
        }
        return running;
    }
}
