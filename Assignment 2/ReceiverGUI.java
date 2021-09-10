import java.awt.*;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.* ;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import java.io.FileOutputStream;


public class ReceiverGUI extends javax.swing.JFrame {
    final static String CRLF = "\r\n";
    String EOT_CHARS = "8a86f71ecaa6455516a278ee20cca735";

    public ReceiverGUI() {
        initComponents();
    }


    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        receiveButton = new javax.swing.JButton();
        senderIP = new javax.swing.JTextField();
        senderPort = new javax.swing.JTextField();
        receiverPort = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        transferType = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        writeTextArea = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        filenameText = new javax.swing.JTextField();

        receiverPort.setText("5555");
        senderPort.setText("4444");
        filenameText.setText("received.txt");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Receiver");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Sender IP");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 31, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel2.setText("Sender Port");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 21, 0, 0);
        getContentPane().add(jLabel2, gridBagConstraints);

        jLabel3.setText("Receiver Port");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 13, 0, 0);
        getContentPane().add(jLabel3, gridBagConstraints);

        receiveButton.setText("Receive");
        receiveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receiveButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 18, 0, 10);
        getContentPane().add(receiveButton, gridBagConstraints);

        senderIP.setToolTipText("Host address of the sender");
        senderIP.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 247;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 18, 0, 10);
        getContentPane().add(senderIP, gridBagConstraints);

        senderPort.setToolTipText("UDP port number used by the Sender to receive ACKs from the Receiver");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 247;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 18, 0, 10);
        getContentPane().add(senderPort, gridBagConstraints);

        receiverPort.setToolTipText("UDP port number used by the Receiver to receive data from the Sender");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 247;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 18, 0, 10);
        getContentPane().add(receiverPort, gridBagConstraints);

        jLabel4.setText("Transfer Type");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(22, 10, 0, 0);
        getContentPane().add(jLabel4, gridBagConstraints);

        transferType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Reliable", "Unreliable" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 92;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(19, 18, 0, 0);
        getContentPane().add(transferType, gridBagConstraints);

        writeTextArea.setEditable(false);
        writeTextArea.setColumns(5);
        writeTextArea.setRows(5);
        jScrollPane1.setViewportView(writeTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 317;
        gridBagConstraints.ipady = 137;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(18, 10, 11, 10);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        jLabel6.setText("Filename");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 36, 0, 0);
        getContentPane().add(jLabel6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 247;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 18, 0, 10);
        getContentPane().add(filenameText, gridBagConstraints);

        pack();
    }


    private void receiveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int rcvport = Integer.parseInt(receiverPort.getText());
            InetAddress myIP = InetAddress.getLocalHost();
            System.out.println(myIP);
            System.out.println("Ready to receive data on port " + rcvport);
            DatagramSocket receiverSocket = new DatagramSocket(rcvport,myIP);

            // packet should look like this : [sequence (1 byte),length (3 bytes),checksum (12 bytes),data (400 bytes max)]

            DatagramPacket sndpkt;
            boolean EOT = false;
            int length;
            int endOfDataPointer;
            int seqNum;
            byte[] blength;
            byte[] fileData;
            byte[] bchecksum;
            byte[] dataMsg;
            byte[] header;
            FileOutputStream outFile = new FileOutputStream(filenameText.getText());

            Checksum pktchk = new CRC32();
            Checksum computedchk = new CRC32();

            //byte[] chkByte = chkValue.getBytes(); //byte array full of checksum


            //while(!EOT) {
                byte[] rcvpkt = new byte[416]; //zima says this should be 1024
                DatagramPacket datagram = new DatagramPacket(rcvpkt, rcvpkt.length);
                receiverSocket.receive(datagram);

                //Parse the packet data
                rcvpkt = datagram.getData();
                seqNum = rcvpkt[0];
                System.out.println("Sequence number: " + seqNum);

                header = Arrays.copyOfRange(rcvpkt,0,15);
                System.out.println("Header: " + new String(header));

                blength = Arrays.copyOfRange(rcvpkt,1,4);
                length = Integer.valueOf(new String(blength));
                endOfDataPointer = length + 15;
                System.out.println(rcvpkt.length);

                dataMsg = Arrays.copyOfRange(rcvpkt,15,endOfDataPointer);
                System.out.println("Data length: " + length);

                bchecksum = Arrays.copyOfRange(rcvpkt,4,15);
                computedchk.update(dataMsg,0,dataMsg.length-1);
                System.out.println("Computed Checksum: " + computedchk.getValue());
                System.out.println("String value of Computed Checksum: " + computedchk.getValue());
                System.out.println("String value of Packet Checksum: " + new String(bchecksum));

                System.out.println("Length of string message in bytes: " + dataMsg.length);
                System.out.println("String value of message: " + new String(dataMsg));
                System.out.println(CRLF);
                //byte[] chkByte = chkValue.getBytes(); //byte array full of checksum


                fileData = Arrays.copyOfRange(rcvpkt,0,endOfDataPointer);
                String stringMsg = new String(fileData);

                outFile.write(fileData);
                //System.out.print(stringMsg);

                //process if data was corrupted and what seq number was received. Send back ACK
                if (seqNum==0){

                }
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReceiverGUI().setVisible(true);
            }
        });

    }

    // Variables declaration
    private javax.swing.JTextField filenameText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton receiveButton;
    private javax.swing.JTextField receiverPort;
    private javax.swing.JTextField senderIP;
    private javax.swing.JTextField senderPort;
    private javax.swing.JComboBox<String> transferType;
    private javax.swing.JTextArea writeTextArea;
    // End of variables declaration

}
