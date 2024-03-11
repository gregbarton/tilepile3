package com.infinimeme.tilepile.station;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;

import com.infinimeme.tilepile.common.Location;
import com.infinimeme.tilepile.common.MainStation;
import com.infinimeme.tilepile.common.MainStationRemote;
import com.infinimeme.tilepile.common.Mural;
import com.infinimeme.tilepile.common.MuralLocation;
import com.infinimeme.tilepile.common.Palette;
import com.infinimeme.tilepile.common.Station;
import com.infinimeme.tilepile.common.StationRemoteImpl;
import com.infinimeme.tilepile.common.TilepileException;
import com.infinimeme.tilepile.common.TilepileUtils;
import com.infinimeme.tilepile.data.DataManagerRemote;

/**
 * DOCUMENT ME!
 * 
 * @author Greg Barton The contents of this file are released under the GPL.
 *         Copyright 2004-2010 Greg Barton
 */
public class TilePanel extends JPanel {

    // ~ Static fields/initializers
    // *****************************************************************

	private static final long serialVersionUID = 6278957919666906593L;
	
	/** DOCUMENT ME! */
    private static final String NO_COLOR_LABEL = "All";

    // ~ Constructors
    // *******************************************************************************

    /**
     * Creates a new TilePanel object.
     * 
     * @param muralLocation
     *            DOCUMENT ME!
     * @param panel
     *            DynamicSectionPanel which paints the subgrid (Mural.Section)
     * @param station
     *            DOCUMENT ME!
     * @param stationRemote
     *            DOCUMENT ME!
     * @param mainStation
     *            DOCUMENT ME!
     * @param dataManager
     *            DOCUMENT ME!
     * @throws TilepileException
     *             DOCUMENT ME!
     */
    public TilePanel(
        final MuralLocation muralLocation,
        final DynamicSectionPanel panel,
        final Station station,
        final StationRemoteImpl stationRemote,
        final MainStation mainStation,
        final DataManagerRemote dataManager) throws TilepileException, RemoteException {
        super(false);

        Mural.Section section = panel.getSection();

        // Panel that holds frame controls
        final JPanel controlPanel = new JPanel();

        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        
        controlPanel.setBackground(station.getColor());

        Location location = muralLocation.getLocation();

        JPanel upperPanel = new JPanel();

        upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.Y_AXIS));
        
        upperPanel.setBackground(station.getColor());

        controlPanel.add(upperPanel);

        JLabel lblSectionLocation = new JLabel(location.getX() + ":" + TilepileUtils.indexToCharacter(location.getY()));

        lblSectionLocation.setBackground(station.getColor());
        lblSectionLocation.setOpaque(true);

        lblSectionLocation.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        upperPanel.add(lblSectionLocation);

        // The current color label shows the current color being dispalyed in
        // the TilePanel
        final JLabel lblCurrentColor = new JLabel(NO_COLOR_LABEL);

        lblCurrentColor.setOpaque(true);

        lblCurrentColor.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        upperPanel.add(lblCurrentColor);

        // The Finished button indicates that all tiles have been
        // placed in the grid
        final JButton btnFinished = new JButton("Finished");

        btnFinished.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {

                    MainStationRemote mainStationRemote = (MainStationRemote) Naming
                        .lookup(mainStation.getRemoteName());

                    mainStationRemote.releaseSection(muralLocation);

                    stationRemote.setMuralLocation(mainStation.getName(), null);

                } catch (RemoteException re) {
                    TilepileUtils.exceptionReport(re);
                } catch (NotBoundException nbe) {
                    TilepileUtils.exceptionReport(nbe);
                } catch (MalformedURLException mue) {
                    TilepileUtils.exceptionReport(mue);
                }
            }
        });
        
        btnFinished.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        upperPanel.add(btnFinished);

        final ButtonGroup colorGroup = new ButtonGroup();

        JButton btnShowAll = new JButton("Show All");

        // The "Show All" button causes the TilePanel to show all colors
        btnShowAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.resetActiveColors();
                lblCurrentColor.setText(NO_COLOR_LABEL);
                lblCurrentColor.setForeground(Color.BLACK);
                lblCurrentColor.setBackground(controlPanel.getBackground());
                repaint();

                ButtonModel model = colorGroup.getSelection();

                if (model != null) {
                    colorGroup.setSelected(model, false);
                }
            }
        });

        btnShowAll.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        upperPanel.add(btnShowAll);

        final JToggleButton togShowNumbers = new JToggleButton("Numbers");

        // The "Numbers" button causes the TilePanel to display color numbers
        togShowNumbers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.setShowNumbers(togShowNumbers.isSelected());
                panel.repaint();
            }
        });

        togShowNumbers.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        upperPanel.add(togShowNumbers);

        final Palette palette = dataManager.getPalette(section.getMural().getPaletteName());

        JPanel buttonPanel = new JPanel();

        Map<Integer,Mural.Counter> histogram = section.getHistogram();

        buttonPanel.setLayout(new GridLayout(histogram.keySet().size(), 1));

        controlPanel.add(
            new JScrollPane(
                buttonPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED)
        );

        final LinkedList<Integer> keyList = new LinkedList<Integer>();

        for (final Integer key : histogram.keySet()) {

            keyList.add(key);

            final Color color = palette.getColor(key.intValue());
            final String name = palette.getName(key.intValue());

            StringBuffer sb = new StringBuffer();
            sb.append(name);

            // Append number of tiles of each color to name
            // sb.append(" (");
            // sb.append(histogram.get(key));
            // sb.append(")");
            final JToggleButton button = new JToggleButton(sb.toString());
            colorGroup.add(button);

            button.setBackground(color);

            button.setForeground(TilepileUtils.getContrasting(color));

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    if (button.isSelected()) {

                        // Tell TilePanel to display a color
                        panel.resetActiveColors();
                        panel.addActiveColor(key.intValue());
                        panel.repaint();

                        // Report currently displayed color
                        lblCurrentColor.setBackground(color);
                        lblCurrentColor.setText(name);

                        // Set button text color depending on brightness
                        lblCurrentColor.setForeground(TilepileUtils.getContrasting(color));
                    }
                }
            });

            buttonPanel.add(button);
        }

        Preferences prefs = SubApp.PACKAGE_PREFS.node("TilePanel");

        // SpringLayout layout = new SpringLayout();
        // setLayout(layout);
        int padding = prefs.getInt("padding", 30);
        prefs.putInt("padding", padding);

        JPanel paddingPanel = new JPanel();
        paddingPanel.setLayout(new BoxLayout(paddingPanel, BoxLayout.X_AXIS));
        paddingPanel.add(Box.createHorizontalStrut(padding));
        paddingPanel.add(controlPanel);

        setLayout(new BorderLayout());

        add(paddingPanel, BorderLayout.EAST);
        add(panel, BorderLayout.CENTER);

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UP");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_KP_UP, 0), "UP");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "DOWN");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_KP_DOWN, 0), "DOWN");

        getActionMap().put("UP", new AbstractAction() {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {

                ButtonModel selected = colorGroup.getSelection();

                if (selected != null) {

                    AbstractButton prev = null;

                    for (Enumeration<AbstractButton> en = colorGroup.getElements(); en.hasMoreElements();) {

                        AbstractButton button = en.nextElement();

                        if (button.getModel().equals(selected)) {

                            break;
                        }
                        prev = button;
                    }

                    if (prev != null) {
                        prev.doClick();
                    }
                }
            }
        });

        getActionMap().put("DOWN", new AbstractAction() {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {

                ButtonModel selected = colorGroup.getSelection();

                if (selected != null) {

                    AbstractButton next = null;

                    for (Enumeration<AbstractButton> en = colorGroup.getElements(); en.hasMoreElements();) {

                        AbstractButton button = en.nextElement();

                        if (button.getModel().equals(selected)) {

                            if (en.hasMoreElements()) {
                                next = (AbstractButton) en.nextElement();

                                break;
                            }
                        }
                    }

                    if (next != null) {
                        next.doClick();
                    }
                }
            }
        });

        invalidate();
        setVisible(true);
    }
}
