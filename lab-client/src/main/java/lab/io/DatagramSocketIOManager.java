package lab.io;

import java.util.Random;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import lab.common.commands.CommandResponse;
import lab.common.commands.CommandResult;
import lab.common.io.IOManager;
import lab.common.io.Reader;
import lab.common.io.Writter;
import lab.common.util.CommandWithArguments;

public class DatagramSocketIOManager extends IOManager<CommandResponse, CommandWithArguments> {

    private static final int MAX_PACKAGE_SIZE = 65507;
    private static final int TIMEOUT = 10000;
    private final InetSocketAddress serverAddress;
    private final Random random = new Random();
    private DatagramSocket socket;

    public DatagramSocketIOManager(InetSocketAddress serverAddress) throws SocketException {
        this.serverAddress = serverAddress;
        setupNewSocket();
        setReader(createReader());
        setWritter(createWritter());
    }

    private void setupNewSocket() throws SocketException {
        InetSocketAddress address;
        do {
            address = new InetSocketAddress(random.nextInt(MAX_PACKAGE_SIZE));
        } while (address.isUnresolved());
        socket = new DatagramSocket(address);
        socket.connect(serverAddress);
        socket.setSoTimeout(TIMEOUT);
    }

    private CommandResponse setupNewSocketSuppresed() {
        try {
            setupNewSocket();
            return new CommandResponse(CommandResult.ERROR, "Couldn't get response from server");
        } catch (SocketException e) {
            return new CommandResponse(CommandResult.END, "Can't connect to server");
        }
    }

    private Reader<CommandResponse> createReader() {
        return () -> {
            try {
                DatagramPacket packet = new DatagramPacket(new byte[MAX_PACKAGE_SIZE], MAX_PACKAGE_SIZE);
                socket.receive(packet);
                ByteArrayInputStream byteInputStream = new ByteArrayInputStream(packet.getData());
                ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);
                return (CommandResponse) objectInputStream.readObject();
            } catch (SocketTimeoutException e) {
                return setupNewSocketSuppresed();
            } catch (IOException | ClassNotFoundException e) {
                return new CommandResponse(CommandResult.ERROR, "Couldn't get response from server");
            }
        };
    }

    private Writter<CommandWithArguments> createWritter() {
        return (CommandWithArguments response) -> {
            try {
                ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
                objectOutputStream.writeObject(response);
                DatagramPacket packet = new DatagramPacket(byteOutputStream.toByteArray(),
                        byteOutputStream.toByteArray().length);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + MAX_PACKAGE_SIZE;
        result = prime * result + ((socket == null) ? 0 : socket.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DatagramSocketIOManager other = (DatagramSocketIOManager) obj;
        if (socket == null) {
            return other.socket == null;
        }
        return socket.equals(other.socket);
    }
}
