package hr.fer.zemris.math;

/**
 * This class represents complex polynomial zn*z**n+z(n-1)*z**(n-1)+...+z2*z**2+z1*z+z0. It supports multiplying
 * polynoms, deriving it and calculating value at given comlpex point.
 */
public class ComplexPolynomial {

    /**
     * Factors of polynom.
     */
    private Complex[] factors;

    /**
     * Basic constructor.
     *
     * @param factors Complex factors
     */
    public ComplexPolynomial(Complex... factors) {
        this.factors = factors;
    }

    /**
     * This method is used for calculating order of polynom.
     *
     * @return Order
     */
    public short order() {
        return (short) (factors.length - 1);
    }

    /**
     * This method is used for multipling two {@link ComplexPolynomial}.
     *
     * @param p {@link ComplexPolynomial}
     * @return Product {@link ComplexPolynomial}
     */
    public ComplexPolynomial multiply(ComplexPolynomial p) {
        Complex[] newFactors = new Complex[order() + p.order() + 1];

        for (int i = 0; i < newFactors.length; i++) {
            newFactors[i] = Complex.ZERO;
        }

        Complex[] pFactors = p.getFactors();

        for (int i = 0; i < factors.length; i++) {
            for (int j = 0; j < pFactors.length; j++) {
                newFactors[i + j] = newFactors[i + j].add(factors[i].multiply(pFactors[j]));
            }
        }

        return new ComplexPolynomial(newFactors);

    }

    /**
     * This method is used for deriving {@link ComplexPolynomial}.
     *
     * @return Derived {@link ComplexPolynomial}
     */
    public ComplexPolynomial derive() {
        Complex[] newFactors = new Complex[order()];

        for (int i = 1; i < factors.length; i++) {
            newFactors[i - 1] = factors[i].multiply(new Complex(i, 0));
        }

        return new ComplexPolynomial(newFactors);
    }

    /**
     * This method is used for calculating value at given point.
     *
     * @param z Complex point
     * @return Complex value
     */
    public Complex apply(Complex z) {
        Complex result = Complex.ZERO;

        for (int i = 0; i < factors.length; i++) {
            result = result.add(z.power(i).multiply(factors[i]));
        }

        return result;
    }

    /**
     * Getter for factors.
     *
     * @return Factors
     */
    public Complex[] getFactors() {
        return factors;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("f(z) = ");
        for (int i = factors.length - 1; i >= 0; i--) {
            sb.append("(");
            sb.append(factors[i]);
            sb.append(")");
            sb.append("*z^");
            sb.append(i);
            sb.append(" + ");
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
}
