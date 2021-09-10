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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public final class Sender {

    public static void main(String args[]) throws Exception {
        // Get the port number from the command line
        String ipReceiver = args[0];
        int destPort = new Integer(args[1]).intValue();
        int portSender = new Integer(args[2]).intValue();
        String fileName = args[3];
        int maxSize = new Integer(args[4]).intValue();
        int timeout = new Integer(args[5]).intValue(); //fetch the port from the command line
        // Establish the send socket.
        DatagramSocket sendSocket = new DatagramSocket(portSender);
        //convert IP address to INET
        InetAddress destIP = InetAddress.getByName(ipReceiver);
        //////////////////////////////////////////////////
        //Attempting a bufferedRead
        /////////////////////////////////////////////////
        File file = new File("C:\\Users\\Jared\\Documents\\CP 372\\Assignment 2\\test.txt");
        int bytesAmount = 0;
        byte[] msg = new byte[maxSize];
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)){
             bytesAmount = bis.read(msg,0,maxSize); //!!!!!!!!!if the file is not 400 bytes then we will be sending blanks
             int sequenceNum = 0;
             while (bytesAmount>0) {
                 //create byte array that is the size of exactly how much data we read.
                 //byte[] msg2 = new byte[bytesAmount];
                 //fill that array with the data that we read by copying it from the original msg. Recall that the original msg is max size and we may not want all of its info
                 //msg2 = Arrays.copyOfRange(msg,0,bytesAmount);
                 //this will be the msg that includes sequence number and is the right length
                 byte[] msg2 = makepkt(sequenceNum,msg,bytesAmount);

                 //send datagram with msg2, length of msg 2, to IP and port
                 DatagramPacket sndpkt = new DatagramPacket(msg2, msg2.length, destIP, destPort);
                 sendSocket.send(sndpkt);
                 //test
                 String readthis = new String(msg2);
             //    System.out.print(readthis);
                 //read the next max bytes of data
                 bytesAmount = bis.read(msg,0,maxSize);
             }
        }
    }

    public static byte[] makepkt(int sequence, byte[] data, int length){
        // sequence = sequence # attached to packet; data = data to be included in pkt; length = length of actual data
        // packet should look like this : [sequence (1 byte),length (3 bytes),checksum (12 bytes),data (400 bytes max)]
        byte[] sndpkt = new byte[length+15]; //make byte[] of size length

        Checksum chk = new CRC32();
        chk.update(data, 0, data.length);
        System.out.println(chk.getValue());
        String chkValue = String.valueOf(chk.getValue());

        byte[] chkByte = chkValue.getBytes(); //byte array full of checksum
        System.out.println("Byte len of chksum: " + chkByte.length);

        Integer seqInt = new Integer(sequence); //add the sequence number at the beginning of packet
        sndpkt[0] = seqInt.byteValue();
        System.out.println("Length of data in packet: " + length);
        String lengthS = String.valueOf(length); //turn length into byte array
        byte[] lengthB = lengthS.getBytes();

        for(int j=0;j<lengthB.length;j++){ //put length into packet
            sndpkt[j + 1] = lengthB[j];
        }

        for(int k=0;k<chkByte.length;k++){ //put checksum into pkt
            sndpkt[k+4] = chkByte[k];
        }

        for(int i=0;i<length;i++){  //fill the new byte[] with all the data. Only loop as long as the data + 15. No need to loop more
            sndpkt[i+15] = data[i];
        }

        String ree = new String(sndpkt);
        //System.out.print(ree);
        return sndpkt;
    }
}
