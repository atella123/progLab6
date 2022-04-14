package lab.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import lab.common.commands.CommandResponse;
import lab.common.commands.CommandResult;
import lab.common.io.IOManager;
import lab.common.io.Writter;
import lab.common.io.Reader;
import lab.common.util.CommandWithArguments;

public class DatagramChannelIOManager extends IOManager<CommandWithArguments, CommandResponse> {

    private static final int MAX_PACKAGE_SIZE = 65507;
    private final DatagramChannel datagramChannel;
    private SocketAddress lastRemotAddress;

    public DatagramChannelIOManager(int port) throws IOException {
        datagramChannel = DatagramChannel.open();
        datagramChannel.bind(new InetSocketAddress(port));
        datagramChannel.configureBlocking(false);
        setReader(createReader());
        setWritter(createWritter());
    }

    private Reader<CommandWithArguments> createReader() {
        return () -> {
            ByteBuffer inputPackages = ByteBuffer.wrap(new byte[MAX_PACKAGE_SIZE]);
            try {
                lastRemotAddress = datagramChannel.receive(inputPackages);
                ObjectInputStream objectInputStream = new ObjectInputStream(
                        new ByteArrayInputStream(inputPackages.array()));
                return (CommandWithArguments) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                return null;
            }
        };
    }

    private Writter<CommandResponse> createWritter() {
        return (CommandResponse response) -> {
            if (response.getResult().equals(CommandResult.COMMAND_NOT_FOUND)
                    || response.getResult().equals(CommandResult.NO_INPUT)) {
                return;
            }
            ByteArrayOutputStream dataOutputStream = serealizeCommandResponse(response);
            writeNextDatagram(ByteBuffer.wrap(dataOutputStream.toByteArray()));
        };
    }

    private ByteArrayOutputStream serealizeCommandResponse(CommandResponse response) {
        try {
            ByteArrayOutputStream dataOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(dataOutputStream);
            objectOutputStream.writeObject(response);
            if (dataOutputStream.size() > MAX_PACKAGE_SIZE) {
                objectOutputStream
                        .writeObject(new CommandResponse(CommandResult.ERROR, "Original result couldn't be sent"));
            }
            return dataOutputStream;
        } catch (IOException e) {
            return new ByteArrayOutputStream();
        }
    }

    private void writeNextDatagram(ByteBuffer byteBuffer) {
        while (byteBuffer.hasRemaining()) {
            try {
                datagramChannel.send(byteBuffer, lastRemotAddress);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((datagramChannel == null) ? 0 : datagramChannel.hashCode());
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
        DatagramChannelIOManager other = (DatagramChannelIOManager) obj;
        if (datagramChannel == null) {
            return other.datagramChannel == null;
        }
        return datagramChannel.equals(other.datagramChannel);
    }

}
