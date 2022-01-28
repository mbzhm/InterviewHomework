import Readers.FormatReader;
import java.io.File;
import java.util.Map;

public class ThreadReading implements Runnable{

    private FormatReader formatreader = null;
    private File file;
    private Map map;

    public ThreadReading(FormatReader formatreader, File file, Map map) {
        this.formatreader = formatreader;
        this.file = file;
        this.map = map;
    }

    @Override
    public void run() {
        formatreader.read(file, map);
    }
}
