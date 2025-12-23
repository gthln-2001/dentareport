package de.dentareport;

import de.dentareport.exceptions.DentareportSocketException;
import de.dentareport.utils.MiniCache;
import org.apache.hc.client5.http.fluent.Request;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Objects;

public class License {

    public void check() {
//        try {
//            String result = Request.Get("https://dentareport.de/license")
//                    .execute()
//                    .returnContent()
//                    .asString();
//            System.out.println(result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(macAddress());
    }

    public boolean isDemo() {
        return !Objects.equals(
                MiniCache.getOrDefault("no_demo", "false"),
                "true");
    }

    private String macAddress() {
        try {
            StringBuilder result = new StringBuilder();
            for (NetworkInterface ni : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                byte[] hardwareAddress = ni.getHardwareAddress();
                if (hardwareAddress != null) {
                    for (int i = 0; i < hardwareAddress.length; i++) {
                        result.append(String.format((i == 0 ? "" : "-") + "%02X", hardwareAddress[i]));
                    }
                    return result.toString();
                }
            }
            return "";
        } catch (SocketException e) {
            throw new DentareportSocketException(e);
        }
    }
}