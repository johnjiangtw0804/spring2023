package edu.sjsu.cs158a;

import java.net.Socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Here {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: edu.sjsu.cs158a.Here client hostname port");
            System.exit(1);
        }
        try {
            String hostName = args[1];
            int port = Integer.parseInt(args[2]);
            Socket socket = new Socket(hostName, port);
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();

            // read one byte from the input Stream
            int len = in.read();
            byte[] welcomeMessageInBytes = in.readNBytes(len);
            System.out.printf("%s\n", new String(welcomeMessageInBytes, 0, len));

            int myStudentID = 14725451;
            byte[] myIDBytes = new byte[4];
            // Big Endian (most significant byte first)
            myIDBytes[0] = (byte) ((myStudentID >> 24) & 0xFF);
            myIDBytes[1] = (byte) ((myStudentID >> 16) & 0xFF);
            myIDBytes[2] = (byte) ((myStudentID >> 8) & 0xFF);
            myIDBytes[3] = (byte) (myStudentID & 0xFF);

            String myName = "Jonathan Jiang";
            byte[] nameBytes = myName.getBytes();

            out.write(myIDBytes);
            out.write(nameBytes.length);
            out.write(nameBytes);

            // read 4 bytes from the input Stream, which is the pin
            byte[] pin = in.readNBytes(4);
            out.write(pin);

            len = in.read();
            byte[] finisMessageInBytes = in.readNBytes(len);
            System.out.printf("%s\n", new String(finisMessageInBytes, 0, len));

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}