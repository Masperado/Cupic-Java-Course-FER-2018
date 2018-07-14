package hr.fer.zemris.java.hw07.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

/**
 * This class is used for crypting. It gets arguments through command line. Program can be used for file digesting,
 * encrypting and decrypting files. Digests, keys and initialization vectors are given through console.
 */
public class Crypto {


    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        if (!(args.length == 2 || args.length == 3)) {
            throw new IllegalArgumentException("Molim predajte ispravan broj argumenata!");
        }

        Scanner sc = new Scanner(System.in);

        switch (args[0]) {
            case "checksha":
                if (args.length != 2) {
                    System.out.println("Molim predajte ispravan broj argumenata checksha funkciji!");
                    System.exit(1);
                }

                System.out.print("Please provide expected sha-256 digest for hw07test.bin:\n> ");

                String checksum = sc.nextLine();

                checksha(Paths.get(args[1]), checksum);
                break;
            case "encrypt":
            case "decrypt":
                if (args.length != 3) {
                    System.out.println("Molim predajte ispravan broj argumenata encrypt funkciji!");
                    System.exit(1);
                }

                System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
                String password = sc.nextLine();
                System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
                String initializationVector = sc.nextLine();

                crypt(Paths.get(args[1]), Paths.get(args[2]), args[0], password, initializationVector);

                break;
            default:
                System.out.println("Nepoznata akcija!");
                System.exit(1);

        }

        sc.close();

    }

    /**
     * This method is used for encrypting and decrypting files.
     *
     * @param input   Input file
     * @param output  Output file
     * @param arg     encrypt/decrypt
     * @param keyText Key for encrypting
     * @param ivText  Initialization vector for encrypting
     */
    private static void crypt(Path input, Path output, String arg, String keyText, String ivText) {
        try (InputStream is = Files.newInputStream(input, StandardOpenOption.READ); OutputStream os = Files
                .newOutputStream(output, StandardOpenOption.CREATE)) {
            byte[] buff = new byte[4096];
            SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            boolean encrypt = arg.equals("encrypt");

            cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

            while (true) {
                int r = is.read(buff);
                if (r < 1) break;


                os.write(cipher.update(buff, 0, r));
            }

            os.write(cipher.doFinal());

            if (encrypt) {
                System.out.println("Encryption completed. Generated file " + output.toString() + " based on file " +
                        input
                                .toString() + ");");
            } else {
                System.out.println("Decryption completed. Generated file " + output + " based on file " + input + ".");
            }


        } catch (IOException | NoSuchPaddingException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println("Greška prilikom kriptiranja!");
            System.exit(1);
        }


    }

    /**
     * This method is used for checking if checksum of given file equals expected checksum.
     *
     * @param p                File that will be checked
     * @param expectedChecksum Expected checksum
     */
    private static void checksha(Path p, String expectedChecksum) {

        try (InputStream is = Files.newInputStream(p,
                StandardOpenOption.READ)) {
            byte[] buff = new byte[4096];
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            while (true) {
                int r = is.read(buff);
                if (r < 1) break;

                digest.update(buff, 0, r);
            }

            byte[] sum = digest.digest();

            String actualChecksum = Util.bytetohex(sum);

            if (expectedChecksum.equals(actualChecksum)) {
                System.out.println("Digesting completed. Digest of hw07test.bin matches expected digest.");
            } else {
                System.out.println("Digesting completed. Digest of hw07test.bin does not match the expected digest. " +
                        "Digest\n was: " + actualChecksum);
            }
        } catch (IOException | NoSuchAlgorithmException ex) {
            System.out.println("Greška prilikom digestiranja!");
            System.exit(1);
        }

    }

}
