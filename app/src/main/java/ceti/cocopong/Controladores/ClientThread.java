package ceti.cocopong.Controladores;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import ceti.cocopong.Activities.PongActivity;

/**
 * Created by law on 12/06/16.
 */
public class ClientThread extends Thread {

    private Socket socket;
    private boolean running;
    private PongActivity pongActivity;

    public ClientThread(PongActivity pongActivity){
        this.pongActivity = pongActivity;
    }

    @Override
    public void run() {
        this.pongActivity = pongActivity;
        try {
            socket = new Socket("192.168.137.1",8188);
            pongActivity.getPongView().init();
            while(running){

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(String data)
    {
        try {
            DataOutputStream dOut= new DataOutputStream(socket.getOutputStream());
            dOut.writeUTF(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String receiveData( )
    {
        DataInputStream dIn;
        try {
            dIn = new DataInputStream(socket.getInputStream());
            return dIn.readUTF().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
