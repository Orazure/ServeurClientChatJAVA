import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class File {
    private FileReader reader;
    private FileWriter writer;
    private final String pathWriteFile;
    private final String pathReadFile;

    public File(String pPathReadFile,String pPathWriteFile)
    {
        this.pathReadFile=pPathReadFile;
        this.pathWriteFile=pPathWriteFile;
    }
    boolean writeInFile(String text)
    {
        //input
        try {
            writer = new FileWriter(pathWriteFile, StandardCharsets.UTF_8,true);
        }catch (IOException ex){
            ex.printStackTrace();
            writer = null;
        }
        try {
            writer.write(text);
            writer.close();
        }catch (IOException ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    char[] readFile()
    {
        char[] array = new char[100];
        //output
        try {
            reader = new FileReader(pathReadFile, StandardCharsets.UTF_8);

        }catch (IOException ex) {
            ex.printStackTrace();
            reader = null;
        }

        try {
            reader.read(array);
            reader.close();
        }catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return array;
    }
}
