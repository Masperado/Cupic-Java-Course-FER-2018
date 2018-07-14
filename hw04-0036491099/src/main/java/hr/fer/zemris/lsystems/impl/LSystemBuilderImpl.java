package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;
import hr.fer.zemris.math.Vector2D;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * This class is used for implementing {@link LSystemBuilder}. It is used for building {@link LSystem} which is used
 * for representing. Each builder have 5 parameters (unitLength, unitLengthDegreeScaler,origin,angle and axiom) and
 * two {@link Dictionary} commands and productions. Parameters and dictionaries define how {@link LSystem} will look
 * like. Parameters can be changed via setter and dictionaries via register methods. Parameter and dictionaries can
 * also be loaded from String array. {@link LSystem} can be built with build method.
 */
public class LSystemBuilderImpl implements LSystemBuilder {

    /**
     * Unit length.
     */
    private double unitLength = 0.1;

    /**
     * Unit length degree scaler.
     */
    private double unitLengthDegreeScaler = 1;

    /**
     * Origin.
     */
    private Vector2D origin = new Vector2D(0, 0);

    /**
     * Angle.
     */
    private double angle = 0;

    /**
     * Axiom.
     */
    private String axiom = "";

    /**
     * Commands dictionary.
     */
    private Dictionary commands;

    /**
     * Productions dictionary.
     */
    private Dictionary productions;

    /**
     * Basic constructor.
     */
    public LSystemBuilderImpl() {
        this.commands = new Dictionary();
        this.productions = new Dictionary();
    }

    @Override
    public LSystemBuilder setUnitLength(double v) {
        unitLength = v;

        return this;
    }

    @Override
    public LSystemBuilder setOrigin(double v, double v1) {
        origin = new Vector2D(v, v1);

        return this;
    }

    @Override
    public LSystemBuilder setAngle(double v) {
        angle = v;

        return this;
    }

    @Override
    public LSystemBuilder setAxiom(String s) {
        axiom = s;

        return this;
    }

    @Override
    public LSystemBuilder setUnitLengthDegreeScaler(double v) {
        unitLengthDegreeScaler = v;

        return this;
    }

    @Override
    public LSystemBuilder registerCommand(char c, String s) {
        String[] parts = s.split("\\s+");

        switch (parts[0]) {
            case "draw":
                try {
                    double step = Double.parseDouble(parts[1]);
                    DrawCommand drawCommand = new DrawCommand(step);
                    commands.put(c, drawCommand);
                } catch (NumberFormatException ex) {
                    throw new RuntimeException("Broj u komandi crtanja se ne može parsirati!");
                }
                break;
            case "skip":
                try {
                    double step = Double.parseDouble(parts[1]);
                    SkipCommand skipCommand = new SkipCommand(step);
                    commands.put(c, skipCommand);
                } catch (NumberFormatException ex) {
                    throw new RuntimeException("Broj u komandi skippanja se ne može parsirati!");
                }
                break;
            case "scale":
                try {
                    double skip = Double.parseDouble(parts[1]);
                    SkipCommand skipCommand = new SkipCommand(skip);
                    commands.put(c, skipCommand);
                } catch (NumberFormatException ex) {
                    throw new RuntimeException("Broj u komandi scaleanja se ne može parsirati!");
                }
                break;
            case "rotate":
                try {
                    double rotate = Double.parseDouble(parts[1]);
                    RotateCommand rotateCommand = new RotateCommand(rotate * Math.PI / 180);
                    commands.put(c, rotateCommand);
                } catch (NumberFormatException ex) {
                    throw new RuntimeException("Broj u komandi rotiranja se ne može parsirati!");
                }
                break;
            case "push":
                commands.put(c, new PushCommand());
                break;
            case "pop":
                commands.put(c, new PopCommand());
                break;
            case "color":
                try {
                    Color color = Color.decode("#" + parts[1]);
                    ColorCommand colorCommand = new ColorCommand(color);
                    commands.put(c, colorCommand);
                } catch (NumberFormatException ex) {
                    throw new RuntimeException("Broj u komandi bojanja se ne može parsirati!");
                }
                break;
            default:
                throw new RuntimeException("Nepoznata akcija!");
        }


        return this;
    }

    @Override
    public LSystemBuilder registerProduction(char c, String s) {
        productions.put(c, s);

        return this;
    }

    @Override
    public LSystemBuilder configureFromText(String[] strings) {

        for (String line : strings) {
            if (line.equals("")) {
                continue;
            }

            String[] parts = line.split("\\s+");

            switch (parts[0]) {
                case "origin":
                    if (parts.length != 3) {
                        throw new RuntimeException("Origin nije dobro zadan!");
                    }

                    try {
                        double x = Double.parseDouble(parts[1]);
                        double y = Double.parseDouble(parts[2]);

                        origin = new Vector2D(x, y);
                    } catch (NumberFormatException ex) {
                        throw new RuntimeException("Origin nije dobro zadan!");
                    }
                    break;
                case "angle":
                    if (parts.length != 2) {
                        throw new RuntimeException("Angle nije dobro zadan!");
                    }

                    try {
                        this.angle = Double.parseDouble(parts[1]);
                    } catch (NumberFormatException ex) {
                        throw new RuntimeException("Angle nije dobro zadan!");
                    }
                    break;
                case "unitLength":
                    if (parts.length != 2) {
                        throw new RuntimeException("Unit Length nije dobro zadan!");
                    }

                    try {
                        this.unitLength = Double.parseDouble(parts[1]);
                    } catch (NumberFormatException ex) {
                        throw new RuntimeException("Unit Length Degree Scaler nije dobro zadan!");
                    }
                    break;
                case "unitLengthDegreeScaler":
                    if (parts.length != 2 && parts.length != 3 && parts.length != 4) {
                        throw new RuntimeException("Angle nije dobro zadan!");
                    }

                    String[] unitDegreeArray = Arrays.copyOfRange(parts, 1, parts.length);

                    String unitDegree = Arrays.stream(unitDegreeArray).collect(Collectors.joining(""));

                    String[] unitDegreeParts = unitDegree.split("/");

                    if (unitDegreeParts.length != 1 && unitDegreeParts.length != 2) {
                        throw new RuntimeException("Unit Length Degree Scaler nije dobro zadan!");
                    }


                    try {
                        double firstNumber = Double.parseDouble(unitDegreeParts[0]);
                        double secondNumber = 1;

                        if (unitDegreeParts.length == 2) {

                            secondNumber = Double.parseDouble(unitDegreeParts[1]);
                        }

                        this.unitLengthDegreeScaler = firstNumber / secondNumber;
                    } catch (NumberFormatException ex) {
                        throw new RuntimeException("Angle nije dobro zadan!");
                    }
                    break;
                case "command":
                    if (parts.length != 3 && parts.length != 4) {
                        throw new RuntimeException("Command nije dobro zadan!");
                    }

                    if (parts[1].length() != 1) {
                        throw new RuntimeException("Simbol commande nije dobro zadan!");
                    }

                    char symbol = parts[1].charAt(0);
                    String command = parts[2];

                    if (parts.length == 4) {
                        command += " " + parts[3];
                    }

                    this.registerCommand(symbol, command);
                    break;
                case "axiom":
                    if (parts.length != 2) {
                        throw new RuntimeException("Axiom nije dobro zadan!");
                    }

                    this.axiom = parts[1];
                    break;
                case "production":
                    if (parts.length != 3) {
                        throw new RuntimeException("Production nije dobro zadan!");
                    }

                    if (parts[1].length() != 1) {
                        throw new RuntimeException("Simbol produkcije nije dobro zadan!");
                    }

                    char symbolProduction = parts[1].charAt(0);
                    this.registerProduction(symbolProduction, parts[2]);
                    break;
                default:
                    throw new RuntimeException("Nepoznata direktiva!");
            }


        }

        return this;
    }

    @Override
    public LSystem build() {
        return new LSystem() {
            @Override
            public String generate(int i) {
                String generation = axiom;
                StringBuilder newGeneration = new StringBuilder();

                while (i > 0) {

                    for (int j = 0; j < generation.length(); j++) {
                        String production = (String) LSystemBuilderImpl.this.productions.get(generation.charAt(j));
                        if (production != null) {
                            newGeneration.append(production);
                        } else {
                            newGeneration.append(generation.charAt(j));
                        }
                    }
                    generation = newGeneration.toString();
                    newGeneration.setLength(0);
                    i--;
                }

                return generation;
            }

            @Override
            public void draw(int i, Painter painter) {
                Context context = new Context();
                TurtleState turtleState = new TurtleState(origin, new Vector2D(1, 0).rotated(angle).normalized(), Color
                        .BLACK,
                        unitLength * Math.pow(unitLengthDegreeScaler, i));
                context.pushState(turtleState);
                char[] commands = generate(i).toCharArray();

                for (char c : commands) {
                    Command command = (Command) LSystemBuilderImpl.this.commands.get(c);
                    if (command != null) {
                        command.execute(context, painter);
                    }
                }

            }
        };
    }
}
