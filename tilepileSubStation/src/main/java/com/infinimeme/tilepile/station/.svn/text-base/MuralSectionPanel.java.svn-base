/**
 * 
 */
package com.infinimeme.tilepile.station;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.util.List;

import com.infinimeme.tilepile.common.Palette;
import com.infinimeme.tilepile.common.States;
import com.infinimeme.tilepile.common.TilepileUtils;
import com.infinimeme.tilepile.common.Mural.Section;

/**
 * @author greg
 *
 */
public class MuralSectionPanel extends StaticSectionPanel {
    
	private static final long serialVersionUID = 795293400971681135L;

	/**
     * @param section
     * @param palette
     */
    public MuralSectionPanel(Section section, Palette palette, boolean withGrid, GraphicsConfiguration graphicsConfiguration, int x, int y) {
        super(section, palette, withGrid, graphicsConfiguration);
        makeToolTip(x, y);
    }

    /**
     * @param section
     * @param palette
     * @param state
     */
    public MuralSectionPanel(Section section, Palette palette, boolean withGrid, int state, GraphicsConfiguration graphicsConfiguration, int x, int y) {
        super(section, palette, withGrid, state, graphicsConfiguration);
        makeToolTip(x, y);
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public Dimension getMaximumSize() {
        return getMinimumSize();
    }
    /**
     * Get preferred size
     * 
     * @return Sreferred size Dimension
     */
    public Dimension getPreferredSize() {
        return getMinimumSize();

    }

    private void makeToolTip(int x, int y) {
        
        StringBuffer sb = new StringBuffer();
        sb.append(x);
        sb.append("-");
        sb.append(TilepileUtils.indexToCharacter(y));

        if(States.isFinished(getState())) {
            sb.append(" FINISHED ");
        } 
        
        if(States.isLocked(getState())) {
            sb.append(" LOCKED ");
        } 
        
        List<Palette.PaletteColor> colors = getUnstockedColors();
        
        if(!colors.isEmpty()) {
            
            sb.append(" ");
            
            for(Palette.PaletteColor pc : colors) {
                sb.append(pc.getName());
                sb.append(" ");
            }
        }
        
        setToolTipText(sb.toString());
    }
}
