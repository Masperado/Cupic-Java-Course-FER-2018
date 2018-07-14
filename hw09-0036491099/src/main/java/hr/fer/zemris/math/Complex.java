package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents Complex number. It supports basic arithmetic operations with complex numbers.
 */
public class Complex {

    /**
     * 0+0i.
     */
    public static final Complex ZERO = new Complex(0, 0);

    /**
     * 1+0i.
     */
    public static final Complex ONE = new Complex(1, 0);

    /**
     * -1+0i.
     */
    public static final Complex ONE_NEG = new Complex(-1, 0);

    /**
     * 0+i.
     */
    public static final Complex IM = new Complex(0, 1);

    /**
     * 0-i.
     */
    public static final Complex IM_NEG = new Complex(0, -1);

    /**
     * Real part.
     */
    private double re;

    /**
     * Imaginary part.
     */
    private double im;

    /**
     * Magnitude.
     */
    private double mag;

    /**
     * Angle.
     */
    private double ang;

    /**
     * Basic constructor.
     *
     * @param re Real part
     * @param im Imaginary part
     */
    public Complex(double re, double im) {


        this.re = re;
        this.im = im;
        this.mag = Math.sqrt(re * re + im * im);
        this.ang = Math.atan2(im, re);
    }

    public static Complex parse(String s) {

        Objects.requireNonNull(s,"String cannot be null!");

        // First detecting numbers with no imaginary part
        if (!s.contains("i")) {
            try {
                return new Complex(Double.parseDouble(s), 0);
            } catch (NumberFormatException ex) {
                throw new RuntimeException("Broj nije ispravno zadan!");
            }
        } else {

            if (s.equals("i")) {
                return new Complex(0, 1);
            }

            String[] parts = s.split("i");
            if (parts.length > 1) {
                s = parts[0] + parts[1] + "i";
            }
            s = s.replaceAll("\\s", "");

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
                return new Complex(real, imaginary);
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
                    return new Complex(real, imaginary);
                }

            }
        }

        throw new RuntimeException("Kompleksan broj nije dobro zadan!");
    }

    /**
     * This method is used for calculating magnitude of complex number.
     *
     * @return magnitude
     */
    public double module() {
        return mag;
    }

    /**
     * This method is used for muliplying complex numbers.
     *
     * @param c Complex number to be multiplied with
     * @return Product
     */
    public Complex multiply(Complex c) {
        Objects.requireNonNull(c,"Complex number cannot be null!");
        return new Complex(re * c.getRe() - im * c.getIm(), re * c.getIm() + im * c.getRe());
    }

    /**
     * This method is used for dividing complex numbers.
     *
     * @param c Complex number to be divided with
     * @return Complex coeeficient
     */
    public Complex divide(Complex c) {
        Objects.requireNonNull(c,"Complex number cannot be null!");
        if (Double.compare(c.getMag(), 0) == 0) {
            throw new IllegalArgumentException("Can't divide by zero!");
        }

        double squaredDivisorNorm = c.getMag() * c.getMag();
        return new Complex((re * c.getRe() + im * c.getIm()) / squaredDivisorNorm, (im * c.getRe() - re * c.getIm())
                / squaredDivisorNorm);
    }

    /**
     * This method is used for adding two complex numbers.
     *
     * @param c Number to be added with
     * @return Complex sum
     */
    public Complex add(Complex c) {
        Objects.requireNonNull(c,"Complex number cannot be null!");
        return new Complex(re + c.getRe(), im + c.getIm());
    }


    /**
     * This method is used for subbing two complex numbers.
     *
     * @param c Number to be subbed with
     * @return Complex difference
     */
    public Complex sub(Complex c) {
        Objects.requireNonNull(c,"Complex number cannot be null!");
        return new Complex(re - c.getRe(), im - c.getIm());
    }

    /**
     * This method is used for negating complex number.
     *
     * @return Negation of complex number
     */
    public Complex negate() {
        return new Complex(-re, -im);
    }

    /**
     * This method is used for raising complex number to given exponent.
     *
     * @param n Exponent
     * @return Power
     * @throws IllegalArgumentException if n<0
     */
    public Complex power(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Exponent can't be smaller than 0!");
        }

        double powerMag = Math.pow(mag, n);

        return new Complex(powerMag * Math.cos(n * ang), powerMag * Math.sin(n * ang));
    }

    /**
     * Getter for real part.
     *
     * @return Real part
     */
    public double getRe() {
        return re;
    }

    /**
     * Getter for imaginary part.
     *
     * @return Imaginary part
     */
    public double getIm() {
        return im;
    }

    /**
     * Getter for magnitude,
     *
     * @return Magnitutde
     */
    public double getMag() {
        return mag;
    }

    /**
     * Getter for angle.
     *
     * @return Angle
     */
    public double getAng() {
        return ang;
    }

    /**
     * This method calculates n-th roots of complex nubmer.
     *
     * @param n Root exponent
     * @return List of complex roots
     * @throws IllegalArgumentException if n<1
     */
    public List<Complex> root(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Root exponent can't be smaller than 1!");
        }

        List<Complex> roots = new ArrayList<>();
        double rootMag = Math.pow(mag, 1.0 / n);

        for (int k = 0; k < n; k++) {
            roots.add(new Complex(rootMag * Math.cos((ang + 2 * k * Math.PI) / n), rootMag * Math.sin((ang + 2 * k * Math.PI) / n)));
        }

        return roots;

    }

    @Override
    public String toString() {
        return String.format("%s %6f %s %6fi", re >= 0 ? "" : "-", Math.abs(re), im >= 0 ? "+" : "-", Math.abs(im));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complex complex = (Complex) o;
        return Math.abs(complex.re - re) < 1E-7 && Math.abs(complex.im - im) < 1E-7;
    }

    @Override
    public int hashCode() {

        return Objects.hash(re, im);
    }
}
