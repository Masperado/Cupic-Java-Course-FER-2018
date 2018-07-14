package hr.fer.zemris.java.gui.calc;

/**
 * This interface represents CalcValueListener. It is used as a observer for {@link CalcModel}.
 */
public interface CalcValueListener {

    /**
     * This method is called everytime change happened in {@link CalcModel}.
     *
     * @param model {@link CalcModel}
     */
    void valueChanged(CalcModel model);
}