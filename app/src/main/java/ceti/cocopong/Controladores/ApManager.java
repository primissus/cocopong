package ceti.cocopong.Controladores;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;

import ceti.cocopong.Entidades.HotspotClient;

/**
 * Created by Many on 01/04/2016.
 */
public class ApManager {

    private WifiManager wifiManager;

    public ApManager(WifiManager wifiManager) {
        this.wifiManager = wifiManager;
    }

    //Permite checar si el hotspot esta encendido o no
    public boolean isApOn() {
        //WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        try {
            Method method = wifiManager.getClass().getDeclaredMethod("isWifiApEnabled");
            method.setAccessible(true);
            return (boolean) method.invoke(wifiManager);
        } catch (Throwable ignore) {
        }
        return false;
    }

    //Activa o desactiva el hotspot
    public boolean configApState(String nombrePartida) {
        //WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiConfiguration wc = new WifiConfiguration();
        wc.SSID = "Ptr" + nombrePartida;
        wc.preSharedKey = "pss" + nombrePartida;
        //wc.hiddenSSID = true;
        //wc.status = WifiConfiguration.Status.ENABLED;
        //wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        //wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        wifiManager.setWifiEnabled(false);
        try {
//            if (isApOn()) {
//                wifiManager.setWifiEnabled(false);
//            }
            Method method = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            method.invoke(wifiManager, wc, !isApOn());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean stopHotSpot(){
        try {
//            if (isApOn()) {
//                wifiManager.setWifiEnabled(false);
//            }
            WifiConfiguration wc = new WifiConfiguration();
            Method method = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            method.invoke(wifiManager, wc, false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setConfiguration(String nombrePartida){
        WifiConfiguration wc = new WifiConfiguration();
        wc.SSID = "Ptr" + nombrePartida;
        wc.preSharedKey = "pss" + nombrePartida;
        //wc.hiddenSSID = true;
        //wc.status = WifiConfiguration.Status.ENABLED;
        //wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        //wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        try {
            Method method = wifiManager.getClass().getMethod("setWifiApConfiguration", WifiConfiguration.class);
            method.invoke(wifiManager, wc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<HotspotClient> getHotspotClients(boolean onlyReachables){
        return getHotspotClients(onlyReachables, 600);
    }

    public ArrayList<HotspotClient> getHotspotClients(boolean onlyReachables, int reachableTimeout){
        BufferedReader br = null;
        ArrayList<HotspotClient> result = null;

        try {
            result = new ArrayList<>();
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(" +");

                if ((splitted != null) && (splitted.length >= 4)) {
                    // Basic sanity check
                    String mac = splitted[3];

                    if (mac.matches("..:..:..:..:..:..")) {
                        boolean isReachable = InetAddress.getByName(splitted[0]).isReachable(reachableTimeout);

                        if (!onlyReachables || isReachable) {
                            result.add(new HotspotClient(splitted[0], splitted[3], splitted[5], isReachable));
                        }
                    }
                }
            }
        } catch (Exception e) {
            if(e.getMessage()!=null)
            Log.e(this.getClass().toString(), e.getMessage());
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                Log.e(this.getClass().toString(), e.getMessage());
            }
        }

        return result;
    }
}