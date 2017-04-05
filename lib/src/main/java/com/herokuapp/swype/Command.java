package com.herokuapp.swype;

import java.util.ArrayList;

/**
 *@author Abdel-Rahim Abdalla
 *@version 1.0
 *@since 1.0
 */
public class Command {

    private Interpreter.command command;
    private int magnitude;
    private ArrayList<Command> commands;

    /**
     * Used by {@link com.google.gson.Gson}
     */
    public Command() {
    }

    /**
     * Constructor for FWD, RT, LT and PEN commands
     *
     * @param command {@link com.herokuapp.swype.Interpreter.command} Command type
     * @param magnitude {@link Integer} Size of movement
     */
    public Command(Interpreter.command command, int magnitude) {
        this.command = command;
        this.magnitude = magnitude;
    }

    /**
     * Constructor for REPEAT_STANDARD and REPEAT_PEN commands
     *
     * @param command {@link com.herokuapp.swype.Interpreter.command} Command type
     * @param magnitude {@link Integer} Size of movement
     * @param commands {@link com.herokuapp.swype.Interpreter.command} Commands to be repeated
     */
    public Command(Interpreter.command command, int magnitude, ArrayList<Command> commands) {
        this.command = command;
        this.magnitude = magnitude;
        this.commands = commands;
    }

    /**
     * @return Command type of object
     */
    public Interpreter.command getCommand() {
        return command;
    }

    /**
     * @param command New command type for object
     */
    public void setCommand(Interpreter.command command) {
        this.command = command;
    }

    /**
     * @return Magnitude of object
     */
    public int getMagnitude() {
        return magnitude;
    }

    /**
     * @param magnitude New magnitude of object
     */
    public void setMagnitude(int magnitude) {
        this.magnitude = magnitude;
    }

    /**
     * @return Commands to be repeated
     */
    public ArrayList<Command> getCommands() {
        return commands;
    }

    /**
     * @param commands New commands to be repeated
     */
    public void setCommands(ArrayList<Command> commands) {
        this.commands = commands;
    }
}
