package lab.common.io;

import java.io.IOException;
import java.io.InputStreamReader;

public class IOManager {

    private InputStreamReader inputStream = new InputStreamReader(System.in);
    private Reader reader = defaultConsoleReader();
    private Writter writter = System.out::println;

    public IOManager(Writter writter) {
        this.writter = writter;
    }

    public IOManager(Reader reader) {
        this.reader = reader;
    }

    public IOManager(Reader reader, Writter writter) {
        this.reader = reader;
        this.writter = writter;
    }

    public IOManager() {
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Writter getWritter() {
        return writter;
    }

    public void setWritter(Writter writter) {
        this.writter = writter;
    }

    public void setIO(Reader newReader, Writter newWritter) {
        this.setReader(newReader);
        this.setWritter(newWritter);
    }

    public void write(String s) {
        writter.write(s);
    }

    public String readLine() {
        return reader.readLine();
    }

    public Reader defaultConsoleReader() {
        return () -> {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                int nextChar = inputStream.read();
                while (nextChar != -1 && nextChar != Integer.valueOf('\n')) {
                    stringBuilder.append((char) nextChar);
                    nextChar = inputStream.read();
                }
                return stringBuilder.toString();
            } catch (IOException e) {
                return "";
            }
        };
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((reader == null) ? 0 : reader.hashCode());
        result = prime * result + ((writter == null) ? 0 : writter.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        IOManager other = (IOManager) obj;
        if (reader == null) {
            if (other.reader != null) {
                return false;
            }
        } else if (!reader.equals(other.reader)) {
            return false;
        }
        if (writter == null) {
            if (other.writter != null) {
                return false;
            }
        } else if (!writter.equals(other.writter)) {
            return false;
        }
        return true;
    }

}
