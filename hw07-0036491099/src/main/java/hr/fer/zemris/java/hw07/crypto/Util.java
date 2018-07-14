package hr.fer.zemris.java.hw07.crypto;

/**
 * This class is used as a utility class for {@link Crypto} class. It defines two static method, hextobyte and
 * bytetohex that are used for transforming byte array to String and vice-versa.
 */
public class Util {

    /**
     * This method is used for transforming String to byte array.
     *
     * @param keyText String
     * @return Byte array
     */
    public static byte[] hextobyte(String keyText) {
        if (keyText.length() == 0) {
            return new byte[0];
        }

        if (keyText.length() % 2 != 0) {
            throw new IllegalArgumentException("Dužina ključa nije dijeljiva s 2!");
        }

        byte[] byteArray = new byte[keyText.length() / 2];

        for (int i = 0; i < keyText.length(); i += 2) {

            byteArray[i / 2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4)
                    + Character.digit(keyText.charAt(i + 1), 16));
        }

        return byteArray;
    }

    /**
     * This class is used for transforming byte array to String.
     *
     * @param bytearray byte array
     * @return String
     */
    public static String bytetohex(byte[] bytearray) {
        StringBuilder sb = new StringBuilder();

        for (Byte b : bytearray) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

}
