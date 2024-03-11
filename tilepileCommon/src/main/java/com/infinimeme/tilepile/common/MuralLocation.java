package com.infinimeme.tilepile.common;

import java.io.Serializable;

/**
 * DOCUMENT ME!
 *
 * @author Greg Barton The contents of this file are released under the GPL.  Copyright
 *         2004-2010 Greg Barton
 **/
public class MuralLocation
    implements Serializable {

    //~ Instance fields ****************************************************************************

    private static final long serialVersionUID = 6306353769670136771L;

    /** DOCUMENT ME! */
    private Location location = null;

    /** DOCUMENT ME! */
    private String muralName = null;

    //~ Constructors *******************************************************************************

    /**
     * full constructor
     *
     * @param location DOCUMENT ME!
     * @param muralName DOCUMENT ME!
     **/
    public MuralLocation(Location location, String muralName) {
        setLocation(location);
        setMuralName(muralName);
    }

    //~ Methods ************************************************************************************

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public Location getLocation() {

        return location;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public String getMuralName() {

        return muralName;
    }

    /**
     * DOCUMENT ME!
     *
     * @param o DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public boolean equals(Object o) {

        if(o instanceof MuralLocation) {

            MuralLocation other = (MuralLocation)o;

            return getMuralName().equals(other.getMuralName()) &&
                       getLocation().equals(other.getLocation());
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

        return getMuralName().hashCode()^getLocation().hashCode();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public String toString() {

        StringBuffer sb = new StringBuffer();
        sb.append(getMuralName());
        sb.append(":");
        sb.append(getLocation());

        return sb.toString();
    }

    /**
     * DOCUMENT ME!
     *
     * @param location DOCUMENT ME!
     **/
    private void setLocation(Location location) {
        this.location = location;
    }

    /**
     * DOCUMENT ME!
     *
     * @param muralName DOCUMENT ME!
     **/
    private void setMuralName(String muralName) {
        this.muralName = muralName;
    }
}
