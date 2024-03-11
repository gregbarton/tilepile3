package com.infinimeme.tilepile.common;

/**
 * DOCUMENT ME!
 *
 * @author Greg Barton The contents of this file are released under the GPL.  Copyright
 *         2004-2010 Greg Barton
 **/
public class States
    implements TilepileObject {

    public static final int STATE_NEUTRAL = 0;
    public static final int STATE_FINISHED = 1;
    public static final int STATE_LOCKED = STATE_FINISHED << 1;
    
    //~ Static fields/initializers *****************************************************************

    /** DOCUMENT ME! */
    static final long serialVersionUID = -8977274065946888827L;

    //~ Instance fields ****************************************************************************

    /** DOCUMENT ME! */
    private String name = null;

    /** DOCUMENT ME! */
    private int[][] states = null;

    //~ Constructors *******************************************************************************

    /**
     * Creates a new SectionStates object.
     *
     * @param mural DOCUMENT ME!
     **/
    States(Mural mural) {
        setName(mural.getName());
        init(mural);
    }
    
    //~ Methods ************************************************************************************

    public int[][] getStates() {
        return states;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param x DOCUMENT ME!
     * @param y DOCUMENT ME!
     **/
    public void setFinished(int x, int y) {
        states[y][x] |= STATE_FINISHED;
    }

    /**
     * DOCUMENT ME!
     *
     * @param location DOCUMENT ME!
     **/
    public void setFinished(Location location) {
        states[location.getY()][location.getX()] |= STATE_FINISHED;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param x DOCUMENT ME!
     * @param y DOCUMENT ME!
     **/
    public void toggleFinished(int x, int y) {
        states[y][x] ^= STATE_FINISHED;
    }

    /**
     * DOCUMENT ME!
     *
     * @param location DOCUMENT ME!
     **/
    public void toggleFinished(Location location) {
        states[location.getY()][location.getX()] ^= STATE_FINISHED;
    }
     
    /**
     * DOCUMENT ME!
     *
     * @param x DOCUMENT ME!
     * @param y DOCUMENT ME!
     **/
    public void setLocked(int x, int y) {
        states[y][x] |= STATE_LOCKED;
    }

    /**
     * DOCUMENT ME!
     *
     * @param location DOCUMENT ME!
     **/
    public void setLocked(Location location) {
        states[location.getY()][location.getX()] |= STATE_LOCKED;
    }
     
    /**
     * DOCUMENT ME!
     *
     * @param x DOCUMENT ME!
     * @param y DOCUMENT ME!
     **/
    public void toggleLocked(int x, int y) {
        states[y][x] ^= STATE_LOCKED;
    }

    /**
     * DOCUMENT ME!
     *
     * @param location DOCUMENT ME!
     **/
    public void toggleLocked(Location location) {
        states[location.getY()][location.getX()] ^= STATE_LOCKED;
    }

    public static final boolean isFinished(int state) {
        return (state & STATE_FINISHED) == STATE_FINISHED;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param x DOCUMENT ME!
     * @param y DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public boolean isFinished(int x, int y) {
        return isFinished(get(x, y));
    }

    /**
     * DOCUMENT ME!
     *
     * @param location DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public boolean isFinished(Location location) {
        return isFinished(get(location));
    }

    public static final boolean isLocked(int state) {
        return (state & STATE_LOCKED) == STATE_LOCKED;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param x DOCUMENT ME!
     * @param y DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public boolean isLocked(int x, int y) {
        return isLocked(get(x, y));
    }

    /**
     * DOCUMENT ME!
     *
     * @param location DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public boolean isLocked(Location location) {
        return isLocked(get(location));
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public int getHeight() {
        return states.length;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public String getName() {
        return name;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public int getWidth() {
        return states[0].length;
    }

    /**
     * DOCUMENT ME!
     *
     * @param other DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public boolean add(States other) {

        if(getHeight() != other.getHeight()) {
            return false;
        }

        if(getWidth() != other.getWidth()) {
            return false;
        }

        for(int y = 0; y < states.length; y++) {

            for(int x = 0; x < states[y].length; x++) {
                states[y][x] = states[y][x] | other.states[y][x];
            }
        }

        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param x DOCUMENT ME!
     * @param y DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public int get(int x, int y) {
        return states[y][x];
    }

    /**
     * DOCUMENT ME!
     *
     * @param location DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public int get(Location location) {
        return states[location.getY()][location.getX()];
    }

    /**
     * DOCUMENT ME!
     **/
    public void reset() {

        for(int y = 0; y < states.length; y++) {

            for(int x = 0; x < states[y].length; x++) {
                states[y][x] = STATE_NEUTRAL;
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public String toString() {
        return "States: " + getName();
    }

    /**
     * DOCUMENT ME!
     *
     * @param name DOCUMENT ME!
     **/
    private void setName(String name) {
        
        if(name == null || name.equals("")) {
            throw new IllegalArgumentException("States name can not be blank!");
        }
        
        this.name = name;
    }

    /**
     * DOCUMENT ME!
     *
     * @param mural DOCUMENT ME!
     **/
    private void init(Mural mural) {

        Mural.Section[][] sections = mural.getSections();

        states = new int[sections.length][];

        for(int i = 0; i < sections.length; i++) {
            states[i] = new int[sections[i].length];

            for(int j = 0; j < states[i].length; j++) {
                states[i][j] = STATE_NEUTRAL;
            }
        }
    }
}
