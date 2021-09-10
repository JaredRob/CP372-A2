import java.awt.*;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.text.MaskFormatter;
import java.io.* ;
import java.net.* ;
import java.util.* ;
import java.net.InetAddress;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Object;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import java.io.FileOutputStream;

public final class Receiver {
    String EOT_CHARS = "8a86f71ecaa6455516a278ee20cca735";

    public static void main(String args[]) throws Exception {
        int port = 5555;
        InetAddress myIP = InetAddress.getLocalHost();
        System.out.println(myIP);
        DatagramSocket receiverSocket = new DatagramSocket(port,myIP);
        System.out.println("Ready to receive data on port" + port);
        //System.out.print(receiverSocket.getLocalAddress());

        DatagramPacket rcvpkt;
        DatagramPacket sndpkt;
        boolean EOT = false;
        //int seqNo;
        //FileOutputStream outFile = new FileOutputStream(filenameText);


        while(!EOT) {
            byte[] seqNo = new byte[1];
            byte[] msg = new byte[401]; //zima says this should be 1024
            DatagramPacket datagram = new DatagramPacket(msg, msg.length);
            receiverSocket.receive(datagram);
            msg = datagram.getData();
            int seqNum = msg[0];
            byte[] fileData = new byte[msg.length-1];
            fileData = Arrays.copyOfRange(msg,1,msg.length-1);
            String stringMsg = new String(fileData);
            System.out.print(stringMsg);

            //process if data was corrupted and what seq number was received. Send back ACK
            if (seqNum==0){

            }
        }

    }

//    public static byte[] (){
//        byte[] sndpkt;
//        return sndpkt
//    }
}
