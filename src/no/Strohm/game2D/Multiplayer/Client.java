package no.Strohm.game2D.Multiplayer;

import no.Strohm.game2D.Game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread{

    public Socket socket;
    public DataInputStream dataInputStream;
    public DataOutputStream dataOutputStream;
    public String gameTag;
    public static boolean run;

    public Client(String ip, int port, String gameTag){
        try {
            socket = new Socket(ip, port);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("CLIENT: Checking for room");
            if(dataInputStream.readUTF().equals("room")){
                System.out.println("CLIENT: Found room on server");
                dataOutputStream.writeUTF(Game.version);
                if(dataInputStream.readUTF().equals("version ok")){
                    System.out.println("CLIENT: Version ok");
                    dataOutputStream.writeUTF(gameTag);
                    if(dataInputStream.readUTF().equals("game tag ok")){
                        System.out.println("CLIENT: Game tag ok");
                        this.gameTag = gameTag;
                        run = true;
                        this.start();
                    }else{
                        System.out.println("CLIENT: Game tag taken");
                    }
                }else{
                    System.out.println("CLIENT: You have to update your game");
                }
            }else{
                System.out.println("CLIENT: Did not find room on server");
            }
        } catch (Exception e) {
            System.out.println("CLIENT: Could not connect to " + ip + ":" + port);
        }
    }

    public void run(){
        while(run){

        }
        System.out.println("CLIENT: Disconnecting");
    }
}
