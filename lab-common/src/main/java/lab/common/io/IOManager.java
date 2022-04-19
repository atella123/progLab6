package lab.common.io;

public class IOManager<R, W> {

    private Reader<R> reader;
    private Writter<W> writter;

    public IOManager() {

    }

    public IOManager(Reader<R> reader, Writter<W> writter) {
        this.reader = reader;
        this.writter = writter;
    }

    public Reader<R> getReader() {
        return reader;
    }

    public void setReader(Reader<R> reader) {
        this.reader = reader;
    }

    public Writter<W> getWritter() {
        return writter;
    }

    public void setWritter(Writter<W> writter) {
        this.writter = writter;
    }

    public void setIO(Reader<R> newReader, Writter<W> newWritter) {
        this.setReader(newReader);
        this.setWritter(newWritter);
    }

    public void write(W message) {
        writter.write(message);
    }

    public R read() {
        return reader.readLine();
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
        IOManager<?, ?> other = (IOManager<?, ?>) obj;
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
