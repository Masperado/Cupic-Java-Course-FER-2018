package hr.fer.zemris.math;


import java.util.List;

/**
 * This class represents complex rooted polynomial  (z-z1)*(z-z2)*...*(z-zn). It supports transforming to
 * {@link ComplexPolynomial}, getting index of closest root for given complex number and calculating value at given
 * comlpex point.
 */
public class ComplexRootedPolynomial {

    /**
     * Roots of polynomial.
     */
    private Complex[] roots;

    /**
     * Basic constructor.
     *
     * @param roots Roots of polynomial
     */
    public ComplexRootedPolynomial(Complex... roots) {
        this.roots = roots;
    }

    public ComplexRootedPolynomial(List<Complex> complexNumbers) {
        this.roots = complexNumbers.toArray(new Complex[]{});
    }

    /**
     * This method is used for calculating value at given point.
     *
     * @param z Complex point
     * @return Complex value
     */
    public Complex apply(Complex z) {
        return toComplexPolynom().apply(z);
    }

    /**
     * This method is used for transforming polynom to {@link ComplexPolynomial}.
     *
     * @return {@link ComplexPolynomial} polynom
     */
    public ComplexPolynomial toComplexPolynom() {
        ComplexPolynomial polynomial = new ComplexPolynomial(Complex.ONE);

        for (Complex complex : roots) {
            polynomial = polynomial.multiply(new ComplexPolynomial(complex.negate(), Complex.ONE));
        }

        return polynomial;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("f(z) = ");
        for (Complex complex : roots) {
            sb.append("(z-");
            sb.append(complex.toString());
            sb.append(")*");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    /**
     * This method is used for calculating index of closest root for given complex number that is within treshold.
     *
     * @param z        Complex number
     * @param treshold Treshold
     * @return Index of closest root
     */
    public int indexOfClosestRootFor(Complex z, double treshold) {
        int index = -1;
        double distance = treshold;

        for (int i = 0; i < roots.length; i++) {
            if (z.sub(roots[i]).getMag() < treshold) {
                index = i;
            }
        }

        return index;
    }
}
