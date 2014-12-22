package no.Strohm.game2D.Multiplayer;

import no.Strohm.game2D.Game;
import no.Strohm.game2D.state.StateGame;
import no.Strohm.game2D.world.World;
import no.Strohm.game2D.world.tiles.Tile;

import static no.Strohm.game2D.state.State.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client extends Thread{

    public Socket socket;
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;
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
                        Game.client = this;
                        this.gameTag = gameTag;
                        states.get(gameId).start();
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

    public void loadMap(){

    }

    public void run(){
        try {
            while (run) {
                String in = dataInputStream.readUTF();
                new input(in).start();
            }
            System.out.println("CLIENT: Exiting loop");
            System.out.println("CLIENT: Disconnecting");
        }catch(Exception e){
            System.out.println("CLIENT: Disconnecting");
        }
    }
}class input extends Thread{

    private String input;

    public input(String input){
        this.input = input;
    }
    public void run(){

        try {
            if(input.startsWith("setMapTile;")){
                    int x, y, tile;
                    input = input.substring("setMapTile;".length());

                    for (int i = 0; true; i++) {
                        if (input.startsWith(i + ";")) {
                            y = i;
                            input = input.substring(String.valueOf(i).length()+1);
                            break;
                        }
                        if (i > Game.mapWidth) {
                            System.out.println("CLIENT: Map width to large: "+input);
                            throw new Exception();
                        }
                    }
                    for (int i = 0; true; i++) {
                        if (input.startsWith(i + ";")) {
                            x = i;
                            input = input.substring(String.valueOf(i).length()+1);
                            break;
                        }
                        if (i > Game.mapHeight){
                            System.out.println("CLIENT: Map height to large: "+input);
                            throw new Exception();}
                    }
                    for (int i = 0; true; i++) {
                        if (input.startsWith(i + ";")) {
                            tile = i;
                            input = input.substring(String.valueOf(i).length()+1);
                            break;
                        }
                        if (i >= Tile.numberOfTiles){
                            System.out.println("CLIENT: Map tile to large: "+input);
                            throw new Exception();
                        }
                    }
                    World.tiles[y][x] = Tile.createTile(tile, x, y, StateGame.getWorld());
                }


        }catch(Exception e){
        }
    }
}
