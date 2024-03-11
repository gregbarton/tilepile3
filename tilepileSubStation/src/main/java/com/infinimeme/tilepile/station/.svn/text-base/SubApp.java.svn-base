package com.infinimeme.tilepile.station;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.infinimeme.tilepile.common.Location;
import com.infinimeme.tilepile.common.MainStation;
import com.infinimeme.tilepile.common.MainStationRemote;
import com.infinimeme.tilepile.common.Mural;
import com.infinimeme.tilepile.common.MuralLocation;
import com.infinimeme.tilepile.common.Palette;
import com.infinimeme.tilepile.common.Station;
import com.infinimeme.tilepile.common.StationFactory;
import com.infinimeme.tilepile.common.StationRemote;
import com.infinimeme.tilepile.common.StationRemoteImpl;
import com.infinimeme.tilepile.common.StationRemoteListener;
import com.infinimeme.tilepile.common.TilepileException;
import com.infinimeme.tilepile.common.TilepileUtils;
import com.infinimeme.tilepile.data.DataManagerDiscovery;
import com.infinimeme.tilepile.data.DataManagerRemote;

/**
 * DOCUMENT ME!
 * 
 * @author Greg Barton The contents of this file are released under the GPL.
 *         Copyright 2004-2010 Greg Barton
 */
public class SubApp extends JFrame implements StationRemoteListener {

    // ~ Static fields/initializers
    // *****************************************************************

	private static final long serialVersionUID = 7366442369467484934L;

	/** DOCUMENT ME! */
    static Preferences PACKAGE_PREFS = Preferences.userNodeForPackage(SubApp.class);

    public static final String PREF_KEY_X = "x";

    public static final String PREF_KEY_Y = "y";

    public static final String PREF_KEY_WIDTH = "width";

    public static final String PREF_KEY_HEIGHT = "height";

    static {

        // Setup codebase property for RMI stub loading
        String stationURL = StationRemote.class.getClassLoader().getResource(
            StationRemote.class.getName().replace('.', '/') + ".class").toString();
        stationURL = stationURL.substring(0, stationURL.indexOf('!') + 2);

        String mainStationURL = StationRemote.class.getClassLoader().getResource(
            MainStationRemote.class.getName().replace('.', '/') + ".class").toString();
        mainStationURL = mainStationURL.substring(0, mainStationURL.indexOf('!') + 2);

        System.out.println("RESOURCE URL: " + stationURL + " " + mainStationURL);
        System.setProperty("java.rmi.server.codebase", stationURL + " " + mainStationURL);
    }

    // ~ Instance fields
    // ****************************************************************************

    private GraphicsConfiguration graphicsConfiguration = null;
    
    /** DOCUMENT ME! */
    private DataManagerRemote dataManager = null;

    /** DOCUMENT ME! */
    private JPanel tilePanelHolder = new JPanel();
    private JLabel stationNumberLabel = null;

    /** DOCUMENT ME! */
    private Station station = null;

    /** DOCUMENT ME! */
    private StationRemoteImpl stationRemote = null;

    private byte stationNumber = 0;
    
    // ~ Constructors
    // *******************************************************************************

    /**
     * Creates a new SubApp object.
     * 
     * @param owner
     *            DOCUMENT ME!
     * @param gc
     *            DOCUMENT ME!
     * @param dataManager
     *            DOCUMENT ME!
     */
    SubApp(GraphicsConfiguration gc, DataManagerRemote dataManager) throws RemoteException {
        super(gc);

        byte[] address = TilepileUtils.getDefaultLocalAddress().getAddress();
        setStationNumber(address[address.length - 1]);
        
        setTitle("Tilepile SubStation " + getStationNumber());

        final Station station = StationFactory.make(getDataManager(), gc, getStationNumber());
        
        stationNumberLabel = new JLabel(Byte.toString(getStationNumber()), SwingConstants.CENTER);
        stationNumberLabel.setFont(new Font("MONOSPACED", Font.BOLD, 512));
        stationNumberLabel.setForeground(TilepileUtils.getContrasting(station.getColor()));
        
        setGraphicsConfiguration(gc);
        setDataManager(dataManager);

        StationRemoteImpl stationRemote = new StationRemoteImpl(getDataManager(), this);

        System.out.println("Binding: " + station.getRemoteNameStub());
        TilepileUtils.getRegistry().rebind(station.getRemoteNameStub(), stationRemote);

        if (getDataManager().containsStation(station.getName())) {
            getDataManager().setStation(station);
        } else {
            getDataManager().addStation(station);
        }

        System.out.println("Station added");

        setStation(station);
        setStationRemote(stationRemote);

        setBackground(getStation().getColor());
        tilePanelHolder.setBackground(getStation().getColor());
        
        Container content = getContentPane();

        content.setLayout(new BorderLayout());

        content.setBackground(getStation().getColor());

        tilePanelHolder.setLayout(new BorderLayout());
        tilePanelHolder.add(stationNumberLabel, BorderLayout.CENTER);
        content.add(tilePanelHolder, BorderLayout.CENTER);

        Rectangle maxBounds = gc.getBounds();

        setBounds(new Rectangle((int) PACKAGE_PREFS.getDouble(PREF_KEY_X, maxBounds.getX()), (int) PACKAGE_PREFS
            .getDouble(PREF_KEY_Y, maxBounds.getY()), (int) PACKAGE_PREFS.getDouble(PREF_KEY_WIDTH, maxBounds
            .getWidth()), (int) PACKAGE_PREFS.getDouble(PREF_KEY_HEIGHT, maxBounds.getHeight())));

        addComponentListener(new ComponentAdapter() {
            public void componentMoved(ComponentEvent e) {
                Rectangle bounds = SubApp.this.getBounds();
                PACKAGE_PREFS.putDouble(PREF_KEY_X, bounds.getX());
                PACKAGE_PREFS.putDouble(PREF_KEY_Y, bounds.getY());
            }

            public void componentResized(ComponentEvent e) {
                Rectangle bounds = SubApp.this.getBounds();
                PACKAGE_PREFS.putDouble(PREF_KEY_WIDTH, bounds.getWidth());
                PACKAGE_PREFS.putDouble(PREF_KEY_HEIGHT, bounds.getHeight());
            }
        });

        // Close app when main frame closed
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Cleaning up");
                cleanup();
                System.out.println("Exiting");
                System.exit(0);
            }
        });

    }

    // ~ Methods
    // ************************************************************************************

    private void setGraphicsConfiguration(GraphicsConfiguration graphicsConfiguration) {
        this.graphicsConfiguration = graphicsConfiguration;
    }
    
    public GraphicsConfiguration getGraphicsConfiguration() {
        return graphicsConfiguration;
    }
    
    /**
     * DOCUMENT ME!
     * 
     * @param dataManager
     *            DOCUMENT ME!
     * @return DOCUMENT ME!
     */
    public static final GraphicsDevice[] getAvailableGraphicsDevices(DataManagerRemote dataManager)
        throws RemoteException {

        Set<GraphicsDevice> gdSet = new HashSet<GraphicsDevice>();

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        GraphicsDevice[] gds = ge.getScreenDevices();

        for (int i = 0; i < gds.length; i++) {
            gdSet.add(gds[i]);
        }

        Collection<Station> stations = dataManager.instancesOfStation();

        for (Iterator<GraphicsDevice> i = gdSet.iterator(); i.hasNext();) {

            GraphicsDevice gd = (GraphicsDevice) i.next();

            for (Station station : stations) {

                // We don't care about stations at other addresses.
                if (station.getName().indexOf(TilepileUtils.getDefaultLocalAddress().getHostAddress()) == -1) {

                    continue;
                }

                // Remove graphics device if another station is on it.
                if (station.getName().indexOf(TilepileUtils.getNormalizedName(gd.getIDstring())) != -1) {
                    i.remove();

                    break;
                }
            }
        }

        return gdSet.toArray(new GraphicsDevice[0]);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param dataManager
     *            DOCUMENT ME!
     * @return DOCUMENT ME!
     */
    public static final GraphicsDevice pickAvailableGraphicsDevice(DataManagerRemote dataManager)
        throws RemoteException {

        GraphicsDevice[] gds = getAvailableGraphicsDevices(dataManager);

        GraphicsDevice gd = null;

        if (gds.length > 1) {

            Object[] options = new Object[gds.length];

            for (int i = 0; i < gds.length; i++) {
                options[i] = gds[i].getIDstring();
            }

            int option = JOptionPane.showOptionDialog(
                null,
                "Which screen should the SubStation use?",
                "Pick screen",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

            if (option == JOptionPane.CLOSED_OPTION || option == JOptionPane.CANCEL_OPTION) {
                JOptionPane.showMessageDialog(
                    null,
                    "OK.  Thanks anyway...",
                    "SubStation canceled",
                    JOptionPane.INFORMATION_MESSAGE);

                return null;
            }

            gd = gds[option];
        } else if (gds.length == 1) {
            gd = gds[0];
        }

        return gd;
    }

    /**
     * Main method for app.
     * 
     * @param args
     *            Command line arguments.
     */
    public static void main(String[] args) {

        try {

            TilepileUtils.appPrep(SubApp.class);

            DataManagerRemote dataManager = DataManagerDiscovery.getDataManager(DataManagerDiscovery.DiscoverableType.STATION);

            GraphicsDevice gd = pickAvailableGraphicsDevice(dataManager);

            if (gd == null) {
                JOptionPane.showMessageDialog(null, "No screens available for Station on this computer!");

                return;
            }

            TilepileUtils.showSplashScreen(gd);

            try {

                // Set global look and feel
                TilepileUtils.setLookAndFeel();
            } catch (TilepileException te) {
                TilepileUtils.exceptionReport(te);
            }

            final SubApp app = new SubApp(gd.getDefaultConfiguration(), dataManager);

            app.setVisible(true);

        } catch (RemoteException re) {
            TilepileUtils.exceptionReport(re);
            DataManagerDiscovery.clearStoredAddress();
            System.exit(1);
        } catch (NotBoundException nbe) {
            TilepileUtils.exceptionReport(nbe);
            DataManagerDiscovery.clearStoredAddress();
            System.exit(1);
        } catch (IOException ioe) {
            TilepileUtils.exceptionReport(ioe);
            System.exit(1);
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void cleanup() {

        try {
            System.out.println("Removing station");
            getDataManager().removeStation(getStation().getName());
            System.out.println("Unbinding station");
            TilepileUtils.getRegistry().unbind(station.getRemoteNameStub());
        } catch (RemoteException re) {
            TilepileUtils.exceptionReport(re);
        } catch (NotBoundException nbe) {
            TilepileUtils.exceptionReport(nbe);
        }
    }

    /**
     * DOCUMENT ME!
     * 
     * @param mainStation
     *            DOCUMENT ME!
     * @param muralLocation
     *            DOCUMENT ME!
     */
    public void muralLocationSet(MainStation mainStation, MuralLocation muralLocation) {

        if (muralLocation == null) {
            tilePanelHolder.removeAll();
            tilePanelHolder.add(stationNumberLabel, BorderLayout.CENTER);
        } else {

            try {
                Location location = muralLocation.getLocation();
                Mural mural = dataManager.getMural(muralLocation.getMuralName());
                Palette palette = dataManager.getPalette(mural.getPaletteName());

                tilePanelHolder.removeAll();
                tilePanelHolder.add(
                    new TilePanel(
                        muralLocation, 
                        new DynamicSectionPanel(
                            mural.getSections()[location.getY()][location.getX()], 
                            palette,
                            true, 
                            getGraphicsConfiguration()
                        ), 
                        getStation(), 
                        getStationRemote(), 
                        mainStation, 
                        dataManager
                    ),
                    BorderLayout.CENTER
                );
                tilePanelHolder.validate();

            } catch (TilepileException tpe) {
                TilepileUtils.exceptionReport(tpe);
            } catch (RemoteException re) {
                TilepileUtils.exceptionReport(re);
            }
        }

        System.out.println("muralLocationSet: " + muralLocation);

        tilePanelHolder.validate();
        tilePanelHolder.repaint();
    }

    /**
     * DOCUMENT ME!
     * 
     * @param dataManager
     *            DOCUMENT ME!
     */
    private void setDataManager(DataManagerRemote dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    private DataManagerRemote getDataManager() {
        return dataManager;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param station
     *            DOCUMENT ME!
     */
    private void setStation(Station station) {
        this.station = station;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    private Station getStation() {
        return station;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param stationRemote
     *            DOCUMENT ME!
     */
    private void setStationRemote(StationRemoteImpl stationRemote) {
        this.stationRemote = stationRemote;
        stationRemote.setListener(this);
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    private StationRemoteImpl getStationRemote() {
        return stationRemote;
    }

    public byte getStationNumber() {
        return this.stationNumber;
    }

    public void setStationNumber(byte stationNumber) {
        this.stationNumber = stationNumber;
    }
}
