import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.LinkedList;

public class Hash {

    /*
     * Configuration variable to set the block byte size
     */
    private static final int blockByteSize = 1024;

    /*
     * Returns a byte array list separated by sha blocks of 1024 bytes (1KB)
     * which is respectively the size described as a block on the exercise propose
     * document as the following quote:
     * - "If the file size is not a multiple of 1KB then the very last block will be
     * shorter than 1KB, but all other blocks will be exactly 1KB."
     * 
     * @param source: a file read as raw byte array
     * 
     * @return List<byte[]>: a list of n groups of 1024 bytes chunked by the source
     * 
     * @see System.arraycopy
     */
    public static LinkedList<byte[]> breakBlock(byte[] source) {
        LinkedList<byte[]> list = new LinkedList<byte[]>();

        for (int i = 0, chunck = 0; i < source.length; i += blockByteSize) {
            chunck = Math.min(blockByteSize, source.length - i);
            byte[] block = new byte[chunck];

            System.arraycopy(source, i, block, 0, chunck);
            list.add(block);
        }

        return list;
    }

    /*
     * Returns an encrypted byte array using the SHA-256 java algorithm
     * 
     * @param input: a byte array to be encrypted
     * 
     * @return an encrypted byte array
     * 
     * @see System.arraycopy
     */
    public static byte[] digestMessage(byte[] input)
            throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA-256").digest(input);
    }

    /*
     * Returns a byte array with the head and tail concatenation, it is used to
     * concatenate the encrypted blocks
     * 
     * @param head: This byte array will be the begin of the concatenation
     * 
     * @param tail: Thus this byte array will be at the end of concatenated array
     * 
     * @return a concatenated byte array (head + tail)
     * 
     * @see System.arraycopy
     */
    public static byte[] concatenateBlocks(byte[] head, byte[] tail) {
        byte[] concatenation = new byte[head.length + tail.length];

        System.arraycopy(head, 0, concatenation, 0, head.length);
        System.arraycopy(tail, 0, concatenation, head.length, tail.length);
        return concatenation;
    }

    /*
     * Returns the byte array converted on hexadecimal as String formating as two by
     * two Hex String (37 d8 f1 ...) and so on.
     * 
     * @param bytes: The byte array whence will be use to convert to Hex
     * 
     * @return String formated as byte to hex
     */
    public static String hexadecimalToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x ", b));
        }
        return sb.toString();
    }

}
