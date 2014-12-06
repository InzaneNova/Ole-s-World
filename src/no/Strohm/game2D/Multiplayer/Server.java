package no.Strohm.game2D.Multiplayer;

import no.Strohm.game2D.Game;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server extends Thread{

    public ServerSocket serverSocket;
    public static boolean run = false;
    public boolean serverFull = false;
    public static ServerManager serverManager[];

    public Server(int port, int antPlayers) throws Exception{
        try{
            serverSocket = new ServerSocket(port);
            serverManager = new ServerManager[antPlayers];
            System.out.println("SERVER: Successfully created server");
        }catch(Exception e){
            System.out.println("SERVER: Was not abel to create server on port: " + port);
            throw e;
        }
    }

    public void run(){
        run = true;
        System.out.println("SERVER: Started running server-loop");
        while(loop());
        System.out.println("SERVER: Stopped running server-loop");
    }

    public boolean loop(){
        int csm = -1;
        ServerManager currentConnection = null;
        for(int c = 0; c < serverManager.length; c++){
            if(serverManager[c] == null){
                csm = c;
                break;
            }else if(serverManager[c].empty){
                csm = c;
                break;
            }
        }
        if(csm == -1){
            serverFull = true;
        }
        try{
            currentConnection = new ServerManager(serverSocket.accept());
            System.out.println("SERVER: Connecting to "+currentConnection.socket.getInetAddress()+"...");
            if(serverFull){
                System.out.println("SERVER: Server is full, could not connect");
                currentConnection.dataOutputStream.writeUTF("full");
                throw new Exception();
            }else{
                currentConnection.dataOutputStream.writeUTF("room");
                currentConnection.ID = csm;
                serverManager[csm] = currentConnection;
                serverManager[csm].start();
            }
        }catch(Exception e){
            currentConnection.empty = true;
        }
        return run;
    }

    public static boolean testGameTag(String gameTag){
        for(int i = 0; i < serverManager.length; i++){
            if(serverManager[i] != null) {
                if (gameTag.equals(serverManager[i].gameTag)) {
                    return false;
                }
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
                dataOutputStream.writeUTF("version ok");
                String gameTagBuffer;
                gameTagBuffer = dataInputStream.readUTF();
                if(Server.testGameTag(gameTagBuffer)){
                    dataOutputStream.writeUTF("game tag ok");
                    gameTag = gameTagBuffer;
                    System.out.println("SERVER: Successfully connected to " + gameTag + " on ip: " + socket.getInetAddress());
                    while(loop());
                }else{
                    dataOutputStream.writeUTF("game tag taken");
                    System.out.println("SERVER: Game tag taken");
                }
            }else{
                dataOutputStream.writeUTF("version outdated");
                System.out.println("SERVER: Version outdated");
            }
        } catch (Exception e) {
        }
        empty = true;
    }

    public boolean loop(){
        try {
            String message = dataInputStream.readUTF();
        } catch (IOException e) {
            System.out.println("SERVER: Disconnected from "+gameTag);
            return false;
        }
        return running;
    }
}
