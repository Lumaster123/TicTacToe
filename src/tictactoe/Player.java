package tictactoe;

import engine.connection.ConnectionSystem;
import engine.connection.Packet;
import engine.filesystem.FileSystem;
import java.awt.image.BufferedImage;
import java.util.Random;
import tictactoe.Menus.FieldState;

public class Player {
    
    //Playerinfo ...
    public enum Player_Handle{
        PLAYER_HANDLED_BY_PLAYER,
        PLAYER_HANDLED_BY_COMPUTER,
        PLAYER_HANDLED_BY_ONLINE_PLAYER
    }
    
    public final Player_Handle PLAYER_HANDLE;
    
    public final String playerName;
    
    private BufferedImage img;
    
    private FieldState[][] field;
    
    public Player(Player_Handle player_handle, boolean use_X, String playerName){
        PLAYER_HANDLE = player_handle;
        
        this.playerName = playerName;
        
        if(use_X)
            img = FileSystem.readInternImage("tictactoe/res/std_x.png");
        else
            img = FileSystem.readInternImage("tictactoe/res/std_o.png");
        
    }
    
    public Player(Player_Handle player_handle, boolean use_X, String playerName, String ipaddress, int port){
        PLAYER_HANDLE = player_handle;
        
        this.playerName = playerName;
        
        if(use_X)
            img = FileSystem.readInternImage("tictactoe/res/std_x.png");
        else
            img = FileSystem.readInternImage("tictactoe/res/std_o.png");
        
        initConnectionToServer(ipaddress, port);
    }
    
    public void handleInput(FieldState[][] field){
        this.field = field;
        switch(PLAYER_HANDLE){
            case PLAYER_HANDLED_BY_PLAYER:          handleInputAsPlayer(); break;
            case PLAYER_HANDLED_BY_COMPUTER:        handleInputAsComputer(); break;
            case PLAYER_HANDLED_BY_ONLINE_PLAYER:   handleInputAsOnlinePlayer(); break;
        }
        this.field = null;
    }
    
    public void handleOutput(FieldState[][] field){
        this.field = field;
        switch(PLAYER_HANDLE){
            case PLAYER_HANDLED_BY_PLAYER:          handleOutputAsPlayer(); break;
            case PLAYER_HANDLED_BY_COMPUTER:        handleOutputAsComputer(); break;
            case PLAYER_HANDLED_BY_ONLINE_PLAYER:   handleOutputAsOnlinePlayer(); break;
        }
        this.field = null;
    }
    
    public BufferedImage getImg(){
        return img;
    }
    
    private void handleInputAsPlayer(){
        
    }
    
    private void handleInputAsComputer(){
        Random rd = new Random();
        int usedFields = countUsedFields();
        
        if(usedFields == 0){
            field[rd.nextInt(3)][rd.nextInt(3)] = FieldState.USED_PLAYER2;
        }else{
            while(true){
                int x = rd.nextInt(3), y = rd.nextInt(3);
                if(field[x][y] == FieldState.NOT_USED){
                    field[x][y] = FieldState.USED_PLAYER2;
                    break;
                }
            }          
        }
    }
    
    private void handleInputAsOnlinePlayer(){
        ConnectionSystem connectionSystem = TicTacToe.init.getConnectionSystem();
        Packet packet = connectionSystem.receivePacket();
        
        if(packet.getCommand_short().equals("update")){
            int[][] fData = (int[][])packet.getData().get(0);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++){
                    System.out.println(fData[i][j]);
                    if(fData[i][j] == 0){
                        field[i][j] = FieldState.NOT_USED;
                    }else if(fData[i][j] == 2){
                        field[i][j] = FieldState.USED_PLAYER1;
                    }else if(fData[i][j] == 1){
                        field[i][j] = FieldState.USED_PLAYER2;
                    }

                }
            }
        }
        
    }
    
    
    private void handleOutputAsPlayer(){
        
    }
    
    private void handleOutputAsComputer(){
        
    }
    
    private void handleOutputAsOnlinePlayer(){
        ConnectionSystem connectionSystem = TicTacToe.init.getConnectionSystem();
        Packet packet = new Packet("update");
        int[][] fData = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++){
                if(field[i][j] == FieldState.NOT_USED){
                    fData[i][j] = 0;
                }else if(field[i][j] == FieldState.USED_PLAYER1){
                    fData[i][j] = 1;
                }else if(field[i][j] == FieldState.USED_PLAYER2){
                    fData[i][j] = 2;
                }
            }
        }
        packet.addData((Object)fData);
        connectionSystem.sendPacket(packet);
        System.out.println("added to sent list");
    }
    
    private int countUsedFields(){
        int usedFields = 0;
        
        for (FieldState[] fieldStates : field) {
            for (FieldState fieldState : fieldStates) {
                if(fieldState != FieldState.NOT_USED)
                    usedFields++;
            }
        }
        
        return usedFields;
    }
    
    private void initConnectionToServer(String ipaddress, int port){
        TicTacToe.init.initializeConnectionSystem();
        ConnectionSystem connectionSystem = TicTacToe.init.getConnectionSystem();
        
        connectionSystem.initializeClient(ipaddress, port);
        connectionSystem.connect(5);
        System.out.println("connected!");
        
    }
    
}
