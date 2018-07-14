package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * This class represents newton-raphson fractals. It gets from console roots of complex polynom and then displays
 * fractal for given polynom.
 */
public class Newton {


    /**
     * Pool used for parallelization fractal calculation.
     */
    static ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), r -> {
        Thread radnik = new Thread(r);
        radnik.setDaemon(true);
        return radnik;
    });

    /**
     * List used for storing results of parallelization calculation.
     */
    static List<Future<Void>> rezultati = new ArrayList<>();

    /**
     * This class represents {@link IFractalProducer} implementation. It is used for displaying fractals.
     */
    private static class NewtonProducer implements IFractalProducer {

        /**
         * Polynomial in rooted form.
         */
        private ComplexRootedPolynomial polynomialRooted;

        /**
         * Polynomial in factor form.
         */
        private ComplexPolynomial polynomial;

        /**
         * Basic constructor.
         *
         * @param polynomial Rooted polynomial
         */
        public NewtonProducer(ComplexRootedPolynomial polynomial) {
            this.polynomialRooted = polynomial;
            this.polynomial = polynomial.toComplexPolynom();
        }

        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long
                requestNo, IFractalResultObserver observer) {
            short[] data = new short[width * height];
            int numberOfTracks = 8 * Runtime.getRuntime().availableProcessors();
            int numberOfYByTrack = height / numberOfTracks;

            for (int i = 0; i < numberOfTracks; i++) {
                int yMin = i * numberOfYByTrack;
                int yMax = (i + 1) * numberOfYByTrack - 1;
                if (i == numberOfTracks - 1) {
                    yMax = height - 1;
                }
                Callable<Void> posao = new NewtonJob(reMin, reMax, imMin, imMax, width, height,
                        yMin, yMax, data, polynomial, polynomialRooted);
                rezultati.add(pool.submit(posao));
            }
            for (Future<Void> posao : rezultati) {
                try {
                    posao.get();
                } catch (InterruptedException | ExecutionException e) {
                }
            }
            System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
            observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);

        }


    }

    /**
     * This class reprents NewtonJob. It is used for parallelization of calculation of fractals.
     */
    private static class NewtonJob implements Callable<Void> {

        /**
         * Minimum real value.
         */
        private double reMin;

        /**
         * Maximum real value.
         */
        private double reMax;

        /**
         * Minimum imaginary value.
         */
        private double imMin;

        /**
         * Maximum imaginary value.
         */
        private double imMax;

        /**
         * Width of screen.
         */
        private int width;

        /**
         * Height of screen.
         */
        private int height;

        /**
         * Minimum y value.
         */
        private int yMin;

        /**
         * Maximum y value.
         */
        private int yMax;

        /**
         * Array used for storing results.
         */
        private short[] data;

        /**
         * Polynomial in factor form.
         */
        private ComplexPolynomial polynomial;

        /**
         * Polynomial in rooted form.
         */
        private ComplexRootedPolynomial polynomialRooted;

        /**
         * Maximum number of iterations.
         */
        private int maxIter = 16 * 16 * 16;

        /**
         * Treshold for convergence.
         */
        private double convergenceTreshold = 0.001;

        /**
         * Treshold for distance from root.
         */
        private double rootTreshold = 0.002;

        /**
         * Basic constructor.
         *
         * @param reMin            Minimum real value.
         * @param reMax            Maximum real value.
         * @param imMin            Minimum imaginary value.
         * @param imMax            Maximum imaginary value.
         * @param width            Width of screen.
         * @param height           Height of screen.
         * @param yMin             Minimum y value
         * @param yMax             Maximum y value
         * @param data             Data for storing results
         * @param polynomial       Polynomial in factor form
         * @param polynomialRooted Polynomial in rooted form
         */
        public NewtonJob(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin, int yMax, short[] data, ComplexPolynomial polynomial, ComplexRootedPolynomial polynomialRooted) {
            this.reMin = reMin;
            this.reMax = reMax;
            this.imMin = imMin;
            this.imMax = imMax;
            this.width = width;
            this.height = height;
            this.yMin = yMin;
            this.yMax = yMax;
            this.data = data;
            this.polynomial = polynomial;
            this.polynomialRooted = polynomialRooted;
        }

        @Override
        public Void call() throws Exception {
            for (int y = yMin; y <= yMax; y++) {
                for (int x = 0; x <= width; x++) {

                    double re = (double) x / (width - 1) * (reMax - reMin) + reMin;
                    double im = (double) (height - 1 - y) / (height - 1) * (imMax - imMin) + imMin;

                    Complex zn = new Complex(re, im);

                    int iter = 0;
                    ComplexPolynomial derived = polynomial.derive();
                    double module;

                    do {
                        Complex numerator = polynomial.apply(zn);
                        Complex denominator = derived.apply(zn);
                        Complex fraction = numerator.divide(denominator);
                        Complex zn1 = zn.sub(fraction);
                        module = zn1.sub(zn).module();
                        zn = zn1;
                        iter++;
                    } while (module > convergenceTreshold && iter < maxIter);

                    short index = (short) polynomialRooted.indexOfClosestRootFor(zn, rootTreshold);

                    if (index == -1) {
                        data[y * height + x] = 0;
                        System.out.println(zn);
                    } else {
                        data[y * height + x] = (short) (index + 1);
                    }
                }
            }

            return null;

        }

    }

    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {

        System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\n" +
                "Please enter at least two roots, one root per line. Enter 'done' when done.");

        int rootNumber = 0;
        Scanner sc = new Scanner(System.in);
        List<Complex> complexNumbers = new ArrayList<>();

        while (true) {
            System.out.print("Root " + rootNumber + " > ");
            String input = sc.nextLine().trim();
            if (input.equals("done")) {
                break;
            }
            try {
                complexNumbers.add(Complex.parse(input));
            } catch (RuntimeException e){
                System.out.println("Root isn't valid!");
                rootNumber--;
            }
            rootNumber++;
        }

        if (complexNumbers.size()==0){
            System.out.println("You didn't entered any root!");
            System.exit(1);
        }

        ComplexRootedPolynomial polynomial = new ComplexRootedPolynomial(complexNumbers);

        FractalViewer.show(new NewtonProducer(polynomial));
    }


}
