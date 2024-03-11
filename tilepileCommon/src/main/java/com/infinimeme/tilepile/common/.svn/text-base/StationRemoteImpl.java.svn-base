package com.infinimeme.tilepile.common;

import com.infinimeme.tilepile.data.DataManagerRemote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JFrame;

/**
 * DOCUMENT ME!
 *
 * @author Greg Barton The contents of this file are released under the GPL.  Copyright
 *         2004-2010 Greg Barton
 **/
public class StationRemoteImpl
    extends UnicastRemoteObject
    implements StationRemote {

    private static final long serialVersionUID = 1594502302456487172L;

    //~ Instance fields ****************************************************************************

    /** DOCUMENT ME! */
    private DataManagerRemote dataManager = null;

    /** DOCUMENT ME! */
    private JFrame app = null;

    /** DOCUMENT ME! */
    private MuralLocation muralLocation = null;

    /** DOCUMENT ME! */
    private StationRemoteListener listener = null;

    //~ Constructors *******************************************************************************

    /**
     * Creates a new StationRemoteImpl object.
     *
     * @param dataManager DOCUMENT ME!
     * @param app DOCUMENT ME!
     *
     * @throws RemoteException DOCUMENT ME!
     **/
    public StationRemoteImpl(DataManagerRemote dataManager, JFrame app)
                      throws RemoteException {
        super();
        setDataManager(dataManager);
        setApp(app);
    }

    //~ Methods ************************************************************************************

    /**
     * DOCUMENT ME!
     *
     * @param listener DOCUMENT ME!
     **/
    public void setListener(StationRemoteListener listener) {
        this.listener = listener;
    }

    /**
     * DOCUMENT ME!
     *
     * @param mainStationName DOCUMENT ME!
     * @param muralLocation DOCUMENT ME!
     **/
    public void setMuralLocation(String mainStationName, MuralLocation muralLocation) throws RemoteException {
        this.muralLocation = muralLocation;
        listener.muralLocationSet(getDataManager().getMainStation(mainStationName), muralLocation);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public MuralLocation getMuralLocation() {

        return muralLocation;
    }

    /**
     * DOCUMENT ME!
     **/
    public void close() {
        getApp().setVisible(false);
    }

    /**
     * DOCUMENT ME!
     *
     * @param app DOCUMENT ME!
     **/
    private void setApp(JFrame app) {
        this.app = app;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    private JFrame getApp() {

        return app;
    }

    /**
     * DOCUMENT ME!
     *
     * @param dataManager DOCUMENT ME!
     **/
    private void setDataManager(DataManagerRemote dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    private DataManagerRemote getDataManager() {

        return dataManager;
    }
}
