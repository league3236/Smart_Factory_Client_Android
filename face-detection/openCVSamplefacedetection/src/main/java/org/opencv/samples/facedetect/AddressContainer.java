package org.opencv.samples.facedetect;

/**
 * Created by 605pc on 2017-08-30.
 */

public class AddressContainer {

    private String ip;
    public void setIp(String ip){ this.ip = ip; }
    public String getIp(){ return ip; }

    private int port;
    public void setPort(int port){ this.port = port; }
    public int getPort(){ return port; }

    private static AddressContainer instance = new AddressContainer();
    public static AddressContainer getInstance(){ return instance; }

}
