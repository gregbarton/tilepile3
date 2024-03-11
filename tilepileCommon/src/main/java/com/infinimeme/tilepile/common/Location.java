package com.infinimeme.tilepile.common;

import java.io.Serializable;

/**
 * DOCUMENT ME!
 *
 * @author Greg Barton The contents of this file are released under the GPL.  Copyright
 *         2004-2010 Greg Barton
 **/
public class Location
    implements Serializable,
               Comparable<Location> {

    //~ Instance fields ****************************************************************************

    private static final long serialVersionUID = -2852427180879061445L;

    /** persistent field */
    private int x;

    /** persistent field */
    private int y;

    //~ Constructors *******************************************************************************

    /**
     * full constructor
     *
     * @param x DOCUMENT ME!
     * @param y DOCUMENT ME!
     **/
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //~ Methods ************************************************************************************

    /**
     * DOCUMENT ME!
     *
     * @param x DOCUMENT ME!
     **/
    public final void setX(int x) {
        this.x = x;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public final int getX() {

        return this.x;
    }

    /**
     * DOCUMENT ME!
     *
     * @param y DOCUMENT ME!
     **/
    public final void setY(int y) {
        this.y = y;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public final int getY() {

        return this.y;
    }

    /**
     * DOCUMENT ME!
     *
     * @param o DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public int compareTo(Location other) {

        if(x < other.x) {

            return -1;
        } else if(x > other.x) {

            return 1;
        } else {

            if(y < other.y) {

                return -1;
            } else if(y > other.y) {

                return 1;
            } else {

                return 0;
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param o DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public boolean equals(Object o) {

        if(o instanceof Location) {

            Location other = (Location)o;

            return (x == other.x) && (y == other.y);
        } else {

            return false;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public int hashCode() {

        long bits = (long)getX();
        bits ^= (((long)getY()) * 31);

        return (((int)bits)^((int)(bits >> 32)));
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public String toString() {

        StringBuffer sb = new StringBuffer();
        sb.append(x);
        sb.append(",");
        sb.append(y);

        return sb.toString();
    }
}
