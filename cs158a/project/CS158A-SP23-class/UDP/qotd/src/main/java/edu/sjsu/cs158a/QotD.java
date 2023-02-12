package edu.sjsu.cs158a;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

public class QotD implements Runnable {
    @Option(names = "--count", description = "Number of quotes to fetch.")
    int count = 3;
    @Parameters(paramLabel = "<hostName>", defaultValue = "djxmmx.net", description = "The host name of the quote server.")
    String hostName = "djxmmx.net";

    public static void main(String[] args) {
        System.exit(new CommandLine(new QotD()).execute(args));
    }

    public void run() {
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            InetAddress dest = InetAddress.getByName(hostName);

            List<String> quoteList = new ArrayList<String>();
            byte[] buffer = new byte[512];
            while (count > 0) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                packet.setAddress(dest);
                packet.setPort(17);
                // send packet
                datagramSocket.send(packet);

                // receive packet
                datagramSocket.receive(packet);
                quoteList.add(new String(packet.getData(), 0, packet.getLength()));
                count--;
            }
            for (int i = 0; i < quoteList.size(); i++) {
                System.out.println("Quote " + (i + 1) + ": \n" + quoteList.get(i));
            }
            datagramSocket.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
