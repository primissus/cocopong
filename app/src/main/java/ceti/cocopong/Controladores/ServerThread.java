package ceti.cocopong.Controladores;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import ceti.cocopong.Activities.PongActivity;

/**
 * Created by law on 12/06/16.
 */
public class ServerThread extends Thread {

    private ServerSocket serverSocket;
    private Socket socket;
    private boolean running;
    private PongActivity pongActivity;

    public ServerThread(PongActivity pongActivity) {
        this.pongActivity = pongActivity;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(8188);
            socket = serverSocket.accept();
            //serverSocket.close();
            pongActivity.dismissProgresDialog();
            pongActivity.getPongView().init();
            while(running){
                String data = receiveData();
                if(data!=null) {
                    try {
                        JSONObject json = new JSONObject(data);
                        pongActivity.dataReceived(json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
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

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void close(){
        try {
            serverSocket.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
