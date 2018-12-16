package com.yubin.test;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.Enumeration;

/**
 * ip工具类
 *
 * @author YUBIN
 */
public class IpUtil {
    public static Logger CurrentLogger = LoggerFactory.getLogger(IpUtil.class);

    /**
     * 获取当前IP
     * @return
     * @throws SocketException
     * @throws UnknownHostException
     */
    public static String getLocalIP() {

        try {
            if (!System.getProperty("os.name").contains("Win")) {
                return getLinuxLocalIP();
            } else {
                return getWinLocalIP();
            }
        } catch (Exception e) {
            CurrentLogger.error("获取本机IP异常,Ex:%s", ExceptionUtils.getStackTrace(e));

            return "";
        }

    }
    private static String getLinuxLocalIP() throws SocketException {
        String ip = "";
        Enumeration<NetworkInterface> e1 = NetworkInterface.getNetworkInterfaces();
        while (e1.hasMoreElements()) {
            NetworkInterface ni = e1.nextElement();
            String name = ni.getName();
            if (!name.contains("docker") && !name.contains("lo")){
                Enumeration<InetAddress> e2 = ni.getInetAddresses();
                while (e2.hasMoreElements()) {
                    InetAddress ia = e2.nextElement();
                    if (!(ia instanceof Inet6Address)) {
                        ip = ia.getHostAddress();
                    }
                }
            }
        }
        return ip;
    }
    private static String getWinLocalIP() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
