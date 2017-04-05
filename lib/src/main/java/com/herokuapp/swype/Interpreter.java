package com.herokuapp.swype;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *@author Abdel-Rahim Abdalla
 *@version 1.0
 *@since 1.0
 */

public class Interpreter {

    //set up regex expressions
    private String fwdString = "(FWD)( +)([0-9]{1,2})";
    private String rtString = "(RT)( +)([0-9]{1,3})";
    private String ltString = "(LT)( +)([0-9]{1,3})";
    private String penString = "(PEN(UP|DOWN))";
    private String repeatString = "(REPEAT)( +)([0-9]+)( *)(\\[)( *)(@@placeholder@@)( *)(\\])";
    private String generalString = "(?i)(^ *)@@placeholder@@( *|;([A-Za-z0-9 ])* *)$";
    private Pattern fwdPattern = Pattern.compile(generalString.replace("@@placeholder@@", fwdString));
    private Pattern rtPattern = Pattern.compile(generalString.replace("@@placeholder@@",rtString));
    private Pattern ltPattern = Pattern.compile(generalString.replace("@@placeholder@@",ltString));
    private Pattern penPattern = Pattern.compile(generalString.replace("@@placeholder@@", penString));
    private Pattern repeatCommPattern = Pattern.compile(generalString.replace("@@placeholder@@", repeatString).replace("@@placeholder@@", fwdString));
    private Pattern repeatPenPattern = Pattern.compile(generalString.replace("@@placeholder@@", repeatString).replace("@@placeholder@@", penString));

    /**
     * Initialises {@link Interpreter} to allow the use of its methods
     */
    public Interpreter() {
        System.out.println(penPattern.toString());
    }

    //for testing
    public static void main(String[] args){
        new Interpreter();
    }

    /**
     * Converts commands in a String array to a format understood by the interpreter
     *
     * @param commandStrings An array of string commands
     * @return Returns an array of {@link Command} objects
     */
    public command[] getCommandTypes(String[] commandStrings) {

        command[] commands = new command[commandStrings.length];

        for (int i = 0; i < commandStrings.length; i++) {
            commands[i] = getCommandType(commandStrings[i]);
        }

        return commands;
    }

    /**
     * Converts a command in a String to a format understood by the interpreter
     *
     * @param commandString A string command
     * @return Returns a {@link Command} object
     */
    public command getCommandType(String commandString) {
        //match string using specified regex expressions
        Matcher fwdMatcher = fwdPattern.matcher(commandString);
        Matcher rtMatcher = rtPattern.matcher(commandString);
        Matcher ltMatcher = ltPattern.matcher(commandString);
        Matcher penMatcher = penPattern.matcher(commandString);
        Matcher repeatStandardMatcher = repeatCommPattern.matcher(commandString);
        Matcher repeatPenMatcher = repeatPenPattern.matcher(commandString);

        //give command correct enum
        if (fwdMatcher.matches()) {
            return command.FWD;
        } else if (rtMatcher.matches()) {
            return command.RT;
        } else if (ltMatcher.matches()) {
            return command.LT;
        } else if (penMatcher.matches()) {
            return command.PEN;
        } else if (repeatStandardMatcher.matches()) {
            return command.REPEAT_STANDARD;
        } else if (repeatPenMatcher.matches()) {
            return command.REPEAT_PEN;
        } else {
            return command.INVALID;
        }
    }

    /**
     * Enums signifying command types
     */
    public enum command {
        FWD,
        RT,
        LT,
        PEN,
        REPEAT_STANDARD,
        REPEAT_PEN,
        INVALID
    }

}
