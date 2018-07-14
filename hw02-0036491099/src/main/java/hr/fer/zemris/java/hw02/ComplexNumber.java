package hr.fer.zemris.java.hw02;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used for representing complex number. It supports basic algebra with complex numbers.
 */
public class ComplexNumber {

    /**
     * Real part of complex number.
     */
    private double real;

    /**
     * Imaginary part of complex number.
     */
    private double imaginary;

    /**
     * Constructor which initializes complex number with given real and imaginary part.
     *
     * @param real Real part
     * @param imaginary Imaginary part
     */
    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * This method constructs complex number from given real part.
     *
     * @param real Real part
     * @return Complex number
     */
    public static ComplexNumber fromReal(double real) {
        return new ComplexNumber(real, 0);
    }

    /**
     * This method constructs complex number from given imaginary part.
     *
     * @param imaginary Imaginary part
     * @return Complex number
     */
    public static ComplexNumber fromImaginary(double imaginary) {
        return new ComplexNumber(0, imaginary);
    }

    /**
     * This method constructs complex number from given magnitude and angle.
     *
     * @param magnitude Magnitude of complex number
     * @param angle Angle of complex number
     * @return Complex number
     */
    public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
        return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
    }

    /**
     * This method is used for parsing complex number from string.
     *
     * @param s String represenation of complex number
     * @return Complex number
     */
    public static ComplexNumber parse(String s) {

        // First detecting numbers with no imaginary part
        if (!s.contains("i")) {
            try {
                return new ComplexNumber(Double.parseDouble(s), 0);
            } catch (NumberFormatException ex) {
                throw new RuntimeException("Broj nije ispravno zadan!");
            }
        } else {

            // Regex for detecting complex numbers
            Pattern pattern = Pattern.compile("^([-,+]?[0-9]+[.]?[0-9]*)([-,+]?[0-9]*[.]?[0-9]*i)$");
            Matcher matcher = pattern.matcher(s.trim());

            if (matcher.find() && !matcher.group(2).replace('i', ' ').trim().equals("")) {

                double real = Double.parseDouble(matcher.group(1));

                String imaginaryString = matcher.group(2).replace('i', ' ').trim();
                if (imaginaryString.length() == 1) {
                    imaginaryString += "1";
                }

                double imaginary = Double.parseDouble(imaginaryString);
                return new ComplexNumber(real, imaginary);
            } else {

                // Regex for detecting complex numbers with no real part
                pattern = Pattern.compile("^([-,+]?[0-9]*[.]?[0-9]*i)$");
                matcher = pattern.matcher(s.trim());

                if (matcher.find()) {
                    double real = 0;

                    String imaginaryString = matcher.group(1).replace('i', ' ').trim();
                    if (imaginaryString.length() == 1) {
                        imaginaryString += "1";
                    }

                    double imaginary = Double.parseDouble(imaginaryString);
                    return new ComplexNumber(real, imaginary);
                }

            }
        }

        throw new RuntimeException("Kompleksan broj nije dobro zadan!");
    }


    /**
     * Getter for real part of complex number.
     *
     * @return Real part
     */
    public double getReal() {
        return real;
    }

    /**
     * Getter for imaginary part of complex number.
     *
     * @return Imaginary part
     */
    public double getImaginary() {
        return imaginary;
    }

    /**
     * This method is used for getting magnitude of complex number.
     *
     * @return Magnitude of complex number
     */
    public double getMagnitude() {
        return Math.sqrt(real * real + imaginary * imaginary);
    }

    /**
     * This method is used for getting angle of complex number.
     *
     * @return Angle of complex number
     */
    public double getAngle() {
        return Math.atan2(imaginary, real);
    }

    /**
     * This method is used for adding complex number to this complex number.
     *
     * @param c Number that will be added
     * @return Sum of two complex numbers
     */
    public ComplexNumber add(ComplexNumber c) {
        return new ComplexNumber(c.real + real, c.imaginary + imaginary);
    }

    /**
     * This method is used for subbing complex number to this complex number.
     *
     * @param c Number that will be subbed
     * @return  Difference of two complex numbers
     */
    public ComplexNumber sub(ComplexNumber c) {
        return new ComplexNumber(real - c.real, imaginary - c.imaginary);
    }

    /**
     * This method is used for multiplying complex number with this complex number.
     *
     * @param c Number that will be multiplied with
     * @return Product of two complex numbers
     */
    public ComplexNumber mul(ComplexNumber c) {
        return new ComplexNumber(c.real * real - c.imaginary * imaginary, c.real * imaginary + c.imaginary * real);
    }

    /**
     * This method is used for dividing this complex number with given complex number.
     *
     * @param c Number that will be divided with
     * @return Quotient of two complex numbers
     */
    public ComplexNumber div(ComplexNumber c) {
        double denominator = c.real * c.real + c.imaginary * c.imaginary;

        if (Double.compare(denominator, 0.0) == 0) {
            throw new IllegalArgumentException("Kompleksni broj kojim se dijeli ne smije biti nula!");
        }

        double realNumerator = real * c.real + imaginary * c.imaginary;
        double imaginaryNumerator = imaginary * c.real - real * c.imaginary;
        return new ComplexNumber(realNumerator / denominator, imaginaryNumerator / denominator);
    }

    /**
     * This method is used for raising complex number to given exponent.
     *
     * @param n Exponent
     * @return Complex number raised to exponent
     */
    public ComplexNumber power(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Potencija ne može biti negativna!");
        }

        double magnitude = Math.pow(getMagnitude(), n);
        double angle = getAngle() * n;

        while (angle > (2 * Math.PI)) {
            angle -= 2 * Math.PI;
        }

        return fromMagnitudeAndAngle(magnitude, angle);
    }

    /**
     * This method is used for calculating nth roots of complex number.
     *
     * @param n Root exponent
     * @return Roots array of complex number
     */
    public ComplexNumber[] root(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Korijen mora biti veći od 0!");
        }

        double magnitude = Math.pow(getMagnitude(), 1.0 / n);
        double currentAngle = getAngle();

        ComplexNumber[] roots = new ComplexNumber[n];

        for (int i = 0; i < n; i++) {
            double angle = (currentAngle + 2 * i * Math.PI) / n;
            roots[i] = fromMagnitudeAndAngle(magnitude, angle);
        }

        return roots;
    }

    @Override
    public String toString() {
        return String.format("%f + (%f i)", real, imaginary);
    }
}
