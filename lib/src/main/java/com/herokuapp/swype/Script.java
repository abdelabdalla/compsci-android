package com.herokuapp.swype;

/**
 *@author Abdel-Rahim Abdalla
 *@version 1.0
 *@since 1.0
 */

public class Script {

    private int print_id;
    private int id;
    private String requester;
    private String name;
    private String creator;
    private String time_created;
    private String script;
    private int print_count;
    private boolean printed = false;


    /**
     * Used by {@link com.google.gson.Gson}
     */
    public Script() {
    }

    /**
     * Constructor for Saving scripts to server
     *
     * @param name Name of scripts
     * @param creator Username of script creator
     * @param script Script being saved
     * @param print_count 0 for save, 1 for print
     */
    public Script(String name, String creator, String script, int print_count) {
        this.name = name;
        this.creator = creator;
        this.script = script;
        this.print_count = print_count;
    }

    /**
     * Constructor for scripts fetched from server
     *
     * @param name Name of script
     * @param creator Username of script's creator
     * @param script Script
     * @param print_count Number of prints
     * @param id Script's unique ID
     * @param time_created Time when script was created
     */
    public Script(String name, String creator, String script, int print_count, int id, String time_created) {
        this.name = name;
        this.creator = creator;
        this.script = script;
        this.print_count = print_count;
        this.id = id;
        this.time_created = time_created;
    }

    /**
     * Constructor for scripts to be printed
     *
     * @param print_id Script's position in queue
     * @param id Script's unique ID
     * @param requester Name of user that requested the print
     * @param name Name of script
     * @param creator Username of script's creator
     * @param time_created Time when script was created
     * @param script Script
     * @param print_count Number of prints
     */
    public Script(int print_id, int id, String requester, String name, String creator, String time_created, String script, int print_count) {
        this.print_id = print_id;
        this.id = id;
        this.requester = requester;
        this.name = name;
        this.creator = creator;
        this.time_created = time_created;
        this.script = script;
        this.print_count = print_count;
    }

    /**
     *
     * @return Get script name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return Get script creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     *
     * @return Get script
     */
    public String getScript() {
        return script;
    }

    /**
     *
     * @return Get number of prints
     */
    public int getPrint_count() {
        return print_count;
    }

    /**
     *
     * @return Get script ID
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return Get the script's time of creation
     */
    public String getTime_created() {
        return time_created;
    }

    /**
     *
     * @return Get the script's position in queue
     */
    public int getPrint_id() {
        return print_id;
    }

    /**
     *
     * @return Get print requester's username
     */
    public String getRequester() {
        return requester;
    }

    /**
     *
     * @return Get print status
     */
    public boolean isPrinted() {
        return printed;
    }

    /**
     *
     * @param printed Set print status
     */
    public void setPrinted(boolean printed) {
        this.printed = printed;
    }
}
