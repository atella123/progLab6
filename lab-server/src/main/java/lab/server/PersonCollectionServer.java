package lab.server;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Scanner;
import java.nio.channels.DatagramChannel;
import java.util.Objects;

import com.google.gson.Gson;

public class PersonCollectionServer {

    private static final Integer BUFFER_SIZE = 1024;

    private Gson gson;
    private int port;
    private DatagramChannel datagramChannel;
    private Scanner conInputChannel;
    private boolean runNext = true;

    public PersonCollectionServer(int port, Gson gson) {
        this.gson = gson;
        this.port = port;
    }

    public void run() {

        try (FileInputStream conInputStream = new FileInputStream(FileDescriptor.in)) {
            datagramChannel = DatagramChannel.open();
            datagramChannel.bind(new InetSocketAddress(port));
            datagramChannel.configureBlocking(false);
            conInputChannel = new Scanner(System.in);
            while (runNext) {
                handleClientRequest();
                readFromConsole();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                datagramChannel.close();
                conInputChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void readFromConsole() {
        try {
            if (System.in.available() != 0) {
                System.out.println(conInputChannel.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClientRequest() {
        ByteBuffer messageBuffer = ByteBuffer.wrap(new byte[BUFFER_SIZE]);
        try {
            SocketAddress remote = datagramChannel.receive(messageBuffer);
            if (Objects.nonNull(remote)) {
                datagramChannel.send(ByteBuffer.wrap("ABoBA".getBytes()), remote);
                System.out.println(new String(messageBuffer.array()).trim() + " from client");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // private void
        // private CommandResponse runCommand(Command cmd) {

        // }
    }
}