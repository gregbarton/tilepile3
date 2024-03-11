package com.infinimeme.tilepile.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * DOCUMENT ME!
 *
 * @author Greg Barton The contents of this file are released under the GPL.  Copyright
 *         2004-2010 Greg Barton
 **/
public interface StationRemote
    extends Remote {

    //~ Static fields/initializers *****************************************************************

    /** DOCUMENT ME! */
    public static final String REMOTE_NAME = "SR";

    //~ Methods ************************************************************************************

    /**
     * DOCUMENT ME!
     *
     * @param muralLocation DOCUMENT ME!
     **/
    public void setMuralLocation(String mainStationName, MuralLocation muralLocation)
                          throws RemoteException;

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public MuralLocation getMuralLocation()
                                   throws RemoteException;

    /**
     * DOCUMENT ME!
     *
     * @throws RemoteException DOCUMENT ME!
     **/
    public void close()
               throws RemoteException;
}
