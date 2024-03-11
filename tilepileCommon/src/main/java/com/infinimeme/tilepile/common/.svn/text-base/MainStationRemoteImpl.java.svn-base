package com.infinimeme.tilepile.common;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

/**
 * DOCUMENT ME!
 *
 * @author Greg Barton The contents of this file are released under the GPL.  Copyright
 *         2004-2010 Greg Barton
 **/
public class MainStationRemoteImpl
    extends UnicastRemoteObject
    implements MainStationRemote {

    //~ Instance fields ****************************************************************************

    private static final long serialVersionUID = 2995984304314561396L;
    
    /** DOCUMENT ME! */
    private List<MainStationRemoteListener> listeners = new LinkedList<MainStationRemoteListener>();

    //~ Constructors *******************************************************************************

    /**
     * Creates a new MainStationRemoteImpl object.
     *
     * @throws RemoteException DOCUMENT ME!
     **/
    public MainStationRemoteImpl()
                          throws RemoteException {
        super();
    }

    //~ Methods ************************************************************************************

    /**
     * DOCUMENT ME!
     *
     * @param listener DOCUMENT ME!
     **/
    public void addListener(MainStationRemoteListener listener) {
        listeners.add(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param listener DOCUMENT ME!
     **/
    public void removeListener(MainStationRemoteListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param muralLocation DOCUMENT ME!
     **/
    public void releaseSection(MuralLocation muralLocation) {
        for(MainStationRemoteListener listener : listeners) {
            listener.sectionReleased(muralLocation);
        }
    }
}
