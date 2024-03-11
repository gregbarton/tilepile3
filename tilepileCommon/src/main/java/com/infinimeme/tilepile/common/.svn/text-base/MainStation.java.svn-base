package com.infinimeme.tilepile.common;

import com.infinimeme.tilepile.common.TilepileUtils;

/**
 * DOCUMENT ME!
 *
 * @author Greg Barton The contents of this file are released under the GPL.  Copyright
 *         2004-2010 Greg Barton
 **/
public class MainStation
    implements TilepileObject {

    //~ Static fields/initializers *****************************************************************

    /** DOCUMENT ME! */
    static final long serialVersionUID = -1362094956127367266L;

    //~ Instance fields ****************************************************************************

    /** Name of this Station */
    private String name = null;

    /** DOCUMENT ME! */
    private String remoteName = null;

    //~ Constructors *******************************************************************************

    /**
     * Creates a new MainStation object.
     **/
    MainStation() {
        setName(getDefaultName());
        setRemoteName("//" + getName() + "/" + MainStationRemote.REMOTE_NAME);
    }

    //~ Methods ************************************************************************************

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public static final String getDefaultName() {

        return TilepileUtils.getDefaultLocalAddress().getHostAddress();
    }

    /**
     * DOCUMENT ME!
     *
     * @param name DOCUMENT ME!
     **/
    public void setName(String name) {
        
        if(name == null || name.equals("")) {
            throw new IllegalArgumentException("MainStation name can not be blank!");
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
     * @param o DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public boolean equals(Object o) {

        return o instanceof MainStation && (o.hashCode() == hashCode());
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public int hashCode() {

        return getRemoteName().hashCode()^getName().hashCode();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public String toString() {

        return "MainStation: " + getName();
    }
}
