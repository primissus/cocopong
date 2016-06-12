package ceti.cocopong.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Many on 04/04/2016.
 */
public class HotspotClient implements Parcelable {
    public final static int CONNECTED = 0;
    public final static int RECOGNIZING = 1;
    public final static int RECOGNIZED = 2;

    private String ipAddr;
    private String hWAddr;
    private String Device;
    private boolean isReachable;
    private boolean known;
    private int status;

    public HotspotClient(String ipAddr, String hWAddr, String device, boolean isReachable) {
        this.ipAddr = ipAddr;
        this.hWAddr = hWAddr;
        Device = device;
        known = false;
        status = CONNECTED;
        this.setReachable(isReachable);
    }

    public HotspotClient(String device, boolean known, int status) {
        Device = device;
        this.known = known;
        this.status = status;
    }

    protected HotspotClient(Parcel in) {
        ipAddr = in.readString();
        hWAddr = in.readString();
        Device = in.readString();
        isReachable = in.readByte() != 0;
        known = in.readByte() != 0;
        status = in.readInt();
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getHWAddr() {
        return hWAddr;
    }

    public void setHWAddr(String hWAddr) {
        hWAddr = hWAddr;
    }

    public String getDevice() {
        return Device;
    }

    public void setDevice(String device) {
        Device = device;
    }

    public void setReachable(boolean isReachable) {
        this.isReachable = isReachable;
    }

    public boolean isReachable() {
        return isReachable;
    }

    public void setIsReachable(boolean isReachable) {
        this.isReachable = isReachable;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ipAddr);
        parcel.writeString(hWAddr);
        parcel.writeByte((byte) (isReachable ? 1 : 0));
        parcel.writeByte((byte) (known? 1:0));
        parcel.writeInt(status);
    }

    public static final Creator<HotspotClient> CREATOR = new Creator<HotspotClient>() {
        @Override
        public HotspotClient createFromParcel(Parcel in) {
            return new HotspotClient(in);
        }

        @Override
        public HotspotClient[] newArray(int size) {
            return new HotspotClient[size];
        }
    };

}