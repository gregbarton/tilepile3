package com.infinimeme.tilepile.station;

import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collection;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.infinimeme.tilepile.common.MainStation;
import com.infinimeme.tilepile.common.MainStationFactory;
import com.infinimeme.tilepile.common.MainStationRemote;
import com.infinimeme.tilepile.common.MainStationRemoteImpl;
import com.infinimeme.tilepile.common.StationRemote;
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
public class MainApp extends JFrame {

    // ~ Static fields/initializers
    // *****************************************************************

	private static final long serialVersionUID = 6196969702794268085L;

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
    private MainStation mainStation = null;

    /** DOCUMENT ME! */
    private MainStationRemoteImpl mainStationRemote = null;

    // ~ Constructors
    // *******************************************************************************

    /**
     * Creates a new MainApp object.
     * 
     * @param dataManager
     *            DOCUMENT ME!
     * @throws TilepileException
     *             DOCUMENT ME!
     */
    MainApp(GraphicsConfiguration graphicsConfiguration, DataManagerRemote dataManager) throws TilepileException, RemoteException {
        super("Tilepile MainStation");

        setGraphicsConfiguration(graphicsConfiguration);
        setDataManager(dataManager);

        MainStation mainStation = MainStationFactory.make();

        MainStationRemoteImpl mainStationRemote = null;

        try {
            mainStationRemote = new MainStationRemoteImpl();

            TilepileUtils.getRegistry().rebind(MainStationRemote.REMOTE_NAME, mainStationRemote);

        } catch (RemoteException re) {
            TilepileUtils.exceptionReport(re);
        }

        getDataManager().addMainStation(mainStation);

        setMainStation(mainStation);
        setMainStationRemote(mainStationRemote);

        Container content = getContentPane();

        final JDesktopPane desktop = new JDesktopPane();

        content.add(desktop);

        setJMenuBar(createMenu(desktop));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                MainApp.this.cleanup();
            }
        });
    }

    // ~ Methods
    // ************************************************************************************

    /**
     * DOCUMENT ME!
     */
    public void cleanup() {

        try {
            getDataManager().removeMainStation(getMainStation().getName());
            TilepileUtils.getRegistry().unbind(MainStationRemote.REMOTE_NAME);
        } catch (RemoteException re) {
            TilepileUtils.exceptionReport(re);
        } catch (NotBoundException nbe) {
            TilepileUtils.exceptionReport(nbe);
        }
    }

    /**
     * DOCUMENT ME!
     * 
     * @param args
     *            DOCUMENT ME!
     */
    public static void main(String[] args) {

        TilepileUtils.appPrep(MainApp.class);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gds = ge.getScreenDevices();

        GraphicsDevice gd = gds[0];

        TilepileUtils.showSplashScreen(gd);

        try {
            DataManagerRemote dataManager = DataManagerDiscovery.getDataManager(DataManagerDiscovery.DiscoverableType.MAIN_STATION);

            if (dataManager.getMainStation(MainStation.getDefaultName()) != null) {
                if (JOptionPane.showConfirmDialog(null, "Station may already open on this computer.  Continue?") != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            
            for (String station : dataManager.namesOfStation()) {
                dataManager.removeStation(station);
            }

            // Set global look and feel
            TilepileUtils.setLookAndFeel();

            MainApp app = new MainApp(gd.getDefaultConfiguration(), dataManager);

            // Close app when main frame closed
            app.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

            Rectangle screenBounds = gd.getDefaultConfiguration().getBounds();

            app.setSize(screenBounds.getSize());

            app.setVisible(true);

        } catch (TilepileException te) {
            TilepileUtils.exceptionReport(te);
            System.exit(1);
        } catch (RemoteException re) {
            TilepileUtils.exceptionReport(re);
            DataManagerDiscovery.clearStoredAddress();
            System.exit(1);
        } catch (IOException ioe) {
            TilepileUtils.exceptionReport(ioe);
            System.exit(1);
        } catch (NotBoundException nbe) {
            TilepileUtils.exceptionReport(nbe);
            DataManagerDiscovery.clearStoredAddress();
            System.exit(1);
        }
    }

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
     * @param mainStation
     *            DOCUMENT ME!
     */
    private void setMainStation(MainStation mainStation) {
        this.mainStation = mainStation;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    private MainStation getMainStation() {
        return mainStation;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param desktop
     *            DOCUMENT ME!
     * @return DOCUMENT ME!
     * @throws TilepileException
     *             DOCUMENT ME!
     */
    private final JMenuBar createMenu(final JDesktopPane desktop) throws TilepileException, RemoteException {

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem resetItem = new JMenuItem("Reset DataServer");

        resetItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DataManagerDiscovery.clearStoredAddress();
            }
        });

        resetItem.setAccelerator(KeyStroke.getKeyStroke(new Character('r'), InputEvent.CTRL_MASK));

        fileMenu.add(resetItem);
        
        JMenuItem quitItem = new JMenuItem("Quit");

        quitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        quitItem.setAccelerator(KeyStroke.getKeyStroke(new Character('q'), InputEvent.CTRL_MASK));

        fileMenu.add(quitItem);

        JMenu muralMenu = new JMenu("Mural");
        menuBar.add(muralMenu);

        Collection<String> muralNames = dataManager.namesOfMural();

        for (final String name : muralNames) {

            JMenuItem muralItem = new JMenuItem(name);

            muralItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {

                    try {
                        MuralFrame frame = new MuralFrame(
                            dataManager.getMural(name),
                            getMainStation(),
                            getMainStationRemote(),
                            dataManager, 
                            getGraphicsConfiguration());
                        desktop.add(frame);
                    } catch (Exception e) {
                        TilepileUtils.exceptionReport(e);
                    }
                }
            });

            muralMenu.add(muralItem);
        }

        return menuBar;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param mainStationRemote
     *            DOCUMENT ME!
     */
    private void setMainStationRemote(MainStationRemoteImpl mainStationRemote) {
        this.mainStationRemote = mainStationRemote;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    private MainStationRemoteImpl getMainStationRemote() {
        return mainStationRemote;
    }
}
