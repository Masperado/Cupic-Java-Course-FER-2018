package hr.fer.zemris.java.hw07.shell;

/**
 * This class is used as a utility class for {@link MyShell} and {@link ShellCommand}. It defines two static method, parseName and
 * parsePath that are used for parsing name from line or parsing path from line.
 */
public class ShellUtility {


    /**
     * This method is used for parsing name from line.
     *
     * @param line Line
     * @return Name of {@link ShellCommand}
     */
    public static String parseName(String line) {
        if (!(line.contains(" "))) {
            return line;
        }

        return line.substring(0, line.indexOf(" "));

    }

    /**
     * This method is used for parsing file path from line.
     *
     * @param line Line
     * @return File path
     */
    public static String parsePath(String line) {
        if (line.startsWith("\"")) {
            StringBuilder sb = new StringBuilder();
            int currentIndex = 0;
            currentIndex++;

            while (true) {
                if (currentIndex == line.length()) {
                    throw new ShellIOException("Nepravilan qoute!");
                }

                if (line.charAt(currentIndex) == '\\') {
                    currentIndex++;
                    if (currentIndex > line.length()) {
                        throw new ShellIOException("Greška prilikom escapeanja!");
                    }
                    if (line.charAt(currentIndex) == '\"' || line.charAt(currentIndex) == '\\') {
                        sb.append(line.charAt(currentIndex++));
                    } else {
                        sb.append("\\").append(line.charAt(currentIndex++));
                    }

                } else if (line.charAt(currentIndex) == '\"') {
                    currentIndex++;
                    break;
                } else {
                    sb.append(line.charAt(currentIndex++));
                }
            }

            if (currentIndex == line.length() || Character.isSpaceChar(line.charAt(currentIndex))) {
                return sb.toString();
            } else {
                throw new ShellIOException("Nepravilan kraj qoutea!");
            }
        }

        if (line.contains(" ")) {
            return line.substring(0, line.indexOf(" "));
        } else {
            return line;
        }

    }
}
