package com.infinimeme.tilepile.station;

import com.infinimeme.tilepile.common.Mural;
import com.infinimeme.tilepile.common.Palette;
import com.infinimeme.tilepile.common.TilepileException;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;

/**
 * DOCUMENT ME!
 * 
 * @author Greg Barton The contents of this file are released under the GPL.
 *         Copyright 2004-2010 Greg Barton
 */
public class DynamicSectionPanel extends StatefulSectionPanel {

    // ~ Constructors
    // *******************************************************************************

	private static final long serialVersionUID = -1257771762789438923L;

	/**
     * Creates a new DynamicSectionPanel object.
     * 
     * @param section
     *            Mural.Section to be displayed
     * @param palette
     *            Palette backing the section
     */
    public DynamicSectionPanel(Mural.Section section, Palette palette, boolean withGrid, GraphicsConfiguration graphicsConfiguration) {
        super(section, palette, withGrid);
    }

    protected DynamicSectionPanel(Mural.Section section, Palette palette, boolean withGrid, int state, GraphicsConfiguration graphicsConfiguration) {
        super(section, palette, withGrid, state);
    }

    // ~ Methods
    // ************************************************************************************

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public Dimension getMinimumSize() {
        return new Dimension(getSection().getWidth(), getSection().getHeight());
    }

    /**
     * Get preferred size
     * 
     * @return Sreferred size Dimension
     */
    public Dimension getPreferredSize() {
        
        double tileSize = (double) getWidth() / getSection().getWidth();

        return new Dimension(getWidth(), (int) (tileSize * getSection().getHeight()));

    }

    /**
     * Draw all tiles in color
     * 
     * @param g
     *            Graphics context
     * @throws TilepileException
     *             See drawAll()
     */
    protected void showAll(Graphics2D g) throws TilepileException {
        drawAll(g, getBounds().getSize());
    }

    /**
     * Draw all tiles of active colors
     * 
     * @param g
     *            Graphics context
     * @throws TilepileException
     *             See drawColor()
     * @throws IllegalStateException
     *             If no active colors set
     */
    protected void showColors(Graphics2D g) throws TilepileException {

        if (numActiveColors() == 0) {
            throw new IllegalStateException("No active colors set!");
        }

        drawColor(g, getBounds().getSize());
    }
}
