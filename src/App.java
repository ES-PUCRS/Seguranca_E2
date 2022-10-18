import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {

    /* Resource video path */
    private static final String fileName = "./resources/funcoesResumo - SHA1.mp4";

    public static void main(String args[]) throws NoSuchAlgorithmException, IOException {
        /* Java paramether validation */
        String file = "";
        if (args.length > 0)
            file = args[0];
        else
            file = fileName;

        /* READ FILE */
        byte[] rawFile = Files.readAllBytes(Paths.get(file));

        /* Break raw file byte array into chunks -blocks- of 1024 bytes */
        LinkedList<byte[]> blocks = Hash.breakBlock(rawFile);

        /* Get the last block encrypted (bn-1) */
        byte[] bn1, h0;
        bn1 = h0 = Hash.digestMessage(blocks.pollLast());

        /* Iterate over the byte array concatenating the hash function */
        do {
            h0 = Hash.concatenateBlocks(blocks.pollLast(), h0);
            h0 = Hash.digestMessage(h0);
        } while (blocks.size() > 0);

        /* Results */
        System.out.println("Last hash block (bn-1): " + Hash.hexadecimalToString(bn1));
        System.out.println("First hash block (h0): " + Hash.hexadecimalToString(h0));
    }

}