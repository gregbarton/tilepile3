package com.infinimeme.tilepile.common;

import com.infinimeme.tilepile.common.TilepileUtils;
import java.awt.Color;

/**
 * DOCUMENT ME!
 *
 * @author Greg Barton The contents of this file are released under the GPL.  Copyright
 *         2004-2010 Greg Barton
 **/
public class Station
    implements TilepileObject {

    //~ Static fields/initializers *****************************************************************

    /** DOCUMENT ME! */
    static final long serialVersionUID = -573805948011321798L;

    //~ Instance fields ****************************************************************************

    /** DOCUMENT ME! */
    private Color color = null;

    /** Name of this Station */
    private String name = null;

    /** DOCUMENT ME! */
    private int number = 0;

    /** DOCUMENT ME! */
    private String remoteName = null;

    //~ Constructors *******************************************************************************

    /**
     * Creates a new Station object.
     *
     * @param name DOCUMENT ME!
     * @param color DOCUMENT ME!
     * @param remoteNameStub DOCUMENT ME!
     **/
    Station(String name, Color color, int number, String remoteNameStub) {
        setName(name);
        setColor(color);
        setNumber(number);
        setRemoteName("//" + TilepileUtils.getDefaultLocalAddress().getHostAddress() + "/" +
                      remoteNameStub);
        System.out.println("INFO: Creating station " + getName() + " with remote name " +
                           getRemoteName());
    }

    //~ Methods ************************************************************************************

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public Color getColor() {

        return color;
    }

    /**
     * Set the name of this Station
     *
     * @param name name of this Station
     **/
    public void setName(String name) {
        
        if(name == null || name.equals("")) {
            throw new IllegalArgumentException("Station name can not be blank!");
        }
        
        this.name = name;
    }

    /**
     * Get the name of this Station
     *
     * @return name of this Station
     **/
    public String getName() {

        return name;
    }

    /**
     * DOCUMENT ME!
     *
     * @param number DOCUMENT ME!
     **/
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Get the number of this Station
     *
     * @return number of this Station
     **/
    public int getNumber() {

        return number;
    }

    /**
     * DOCUMENT ME!
     *
     * @param remoteName DOCUMENT ME!
     **/
    public void setRemoteName(String remoteName) {
        this.remoteName = remoteName;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public String getRemoteName() {

        return remoteName;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public String getRemoteNameStub() {

        return getRemoteName().substring(getRemoteName().lastIndexOf('/') + 1);
    }

    /**
     * DOCUMENT ME!
     *
     * @param o DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public boolean equals(Object o) {

        return o instanceof Station && (o.hashCode() == hashCode());
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public int hashCode() {

        return getRemoteName().hashCode()^getName().hashCode()^getColor().hashCode();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public String toString() {

        return "Station: " + getName();
    }

    /**
     * DOCUMENT ME!
     *
     * @param color DOCUMENT ME!
     **/
    private void setColor(Color color) {
        this.color = color;
    }
}
