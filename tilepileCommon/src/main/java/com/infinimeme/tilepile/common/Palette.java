package com.infinimeme.tilepile.common;

import java.awt.Color;
import java.awt.image.IndexColorModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * DOCUMENT ME!
 * 
 * @author Greg Barton The contents of this file are released under the GPL.
 *         Copyright 2004-2010 Greg Barton
 */
public class Palette implements TilepileObject {

    // ~ Static fields/initializers
    // *****************************************************************

    /** DOCUMENT ME! */
    public static final Color NON_COLOR_APPEARANCE = Color.BLACK;

    /** DOCUMENT ME! */
    public static final String NON_COLOR_NAME = "NO COLOR";

    /** DOCUMENT ME! */
    public static final int NON_COLOR_INDEX = -1;
    
    private static final String NO_SUBSTITUTION = "NONE";

    /** DOCUMENT ME! */
    static final long serialVersionUID = -20049967519769825L;

    // ~ Instance fields
    // ****************************************************************************

    /** DOCUMENT ME! */
    private transient IndexColorModel colorModel = null;

    /** DOCUMENT ME! */
    private transient int black = NON_COLOR_INDEX;

    /** DOCUMENT ME! */
    private String name = null;

    // List of PaletteColor objects
    private SortedMap<Integer, PaletteColor> colors = new TreeMap<Integer, PaletteColor>();

    public static class PaletteColor implements Serializable {

        public static final long serialVersionUID = 5720068908541245373L;

        private int index = 0;
        private String name = null;
        private Color color = null;
        private int countInStock = 0;
        private String substitution = NO_SUBSTITUTION;
        private double kilosInDesign = 0.0;
        private double kilosInStock = 0.0;
        private double kilosOnOrder = 0.0;

        private transient int precalculatedHashCode = 0;

        public PaletteColor(int index, String name, Color color) {
            this.index = index;
            this.name = name;
            this.color = color;
        }

        public PaletteColor(int index, String name, Color color, int countInStock) {
            this(index, name, color);
            this.countInStock = countInStock;
        }

        public PaletteColor(PaletteColor c) {
            this(c.index, c.name, c.color);
            this.countInStock = c.countInStock;
            this.substitution = c.substitution;
            this.kilosInDesign = c.kilosInDesign;
            this.kilosInStock = c.kilosInStock;
            this.kilosOnOrder = c.kilosOnOrder;
        }

        /**
         * @return Returns the index.
         */
        public int getIndex() {
            return this.index;
        }

        /**
         * @param index
         *            The index to set.
         */
        public void setIndex(int index) {
            this.index = index;
            calcHashCode();
        }

        /**
         * @return Returns the color.
         */
        public Color getColor() {
            return this.color;
        }

        /**
         * @param color
         *            The color to set.
         */
        public void setColor(Color color) {
            this.color = color;
            calcHashCode();
        }

        /**
         * @return Returns the countInStock.
         */
        public int getCountInStock() {
            return this.countInStock;
        }

        /**
         * @param inStock
         *            The inStock to set.
         */
        public void setCountInStock(int countInStock) {
            this.countInStock = countInStock;
        }

        /**
         * @return Returns the kilosInDesign.
         */
        public double getKilosInDesign() {
            return this.kilosInDesign;
        }

        /**
         * @param kilosInDesign
         *            The kilosInDesign to set.
         */
        public void setKilosInDesign(double kilosInDesign) {
            this.kilosInDesign = kilosInDesign;
        }

        /**
         * @return Returns the kilosInStock.
         */
        public double getKilosInStock() {
            return this.kilosInStock;
        }

        /**
         * @param kilosInStock
         *            The kilosInStock to set.
         */
        public void setKilosInStock(double kilosInStock) {
            this.kilosInStock = kilosInStock;
        }

        /**
         * @return Returns the kilosOnOrder.
         */
        public double getKilosOnOrder() {
            return this.kilosOnOrder;
        }

        /**
         * @param kilosOnOrder
         *            The kilosOnOrder to set.
         */
        public void setKilosOnOrder(double kilosOnOrder) {
            this.kilosOnOrder = kilosOnOrder;
        }

        /**
         * @return Returns the name.
         */
        public String getName() {
            return this.name;
        }

        /**
         * @param name
         *            The name to set.
         */
        public void setName(String name) {

            if (name == null || name.equals("")) {
                throw new IllegalArgumentException(
                        "Palette name can not be blank!");
            }

            this.name = name.intern();
            calcHashCode();
        }

        /**
         * @return Returns the substitution.
         */
        public String getSubstitution() {
            return this.substitution;
        }

        /**
         * @param substitution
         *            The substitution to set.
         */
        public void setSubstitution(String substitution) {
            this.substitution = substitution;
        }

        private void calcHashCode() {
            precalculatedHashCode = new Integer(getIndex()).hashCode()
                    ^ getColor().hashCode() ^ getName().hashCode();
        }

        public int hashCode() {

            if (precalculatedHashCode == 0) {
                calcHashCode();
            }

            return precalculatedHashCode;

        }

        public boolean equals(Object o) {
            if (o instanceof PaletteColor) {

                PaletteColor pc = (PaletteColor) o;

                return pc.getIndex() == getIndex()
                        && pc.getColor() == getColor()
                        && pc.getName() == getName();

            }
            else {
                return false;
            }
        }

        public String toString() {

            StringBuffer sb = new StringBuffer();

            sb.append("PaletteColor ");
            sb.append(getName());
            sb.append(" ");
            sb.append(getColor());

            return sb.toString();
        }

        private void readObject(ObjectInputStream in) throws IOException,
                ClassNotFoundException {
            in.defaultReadObject();
            name = name.intern();
        }

    }

    public static final PaletteColor NON_COLOR = new PaletteColor(
            NON_COLOR_INDEX, NON_COLOR_NAME, NON_COLOR_APPEARANCE, Integer.MAX_VALUE);

    /** DOCUMENT ME! */
    private int precalculatedHash = TilepileUtils.NOT_CALCULATED;

    // ~ Constructors
    // *******************************************************************************

    /**
     * Creates a new Palette object.
     * 
     * @param name
     *            DOCUMENT ME!
     * @param colors
     *            DOCUMENT ME!
     * @param names
     *            DOCUMENT ME!
     * 
     * @throws IllegalArgumentException
     *             DOCUMENT ME!
     */
    Palette(String name, List<PaletteColor> newColors) {

        setName(name);
        
        int i = 0;
        for (PaletteColor c : newColors) {
        	PaletteColor newPC = new PaletteColor(c);
        	newPC.setIndex(i++);
            colors.put(newPC.getIndex(), newPC);
        }

        initColorModel();
    }

    /**
     * Creates a new Palette object.
     * 
     * @param name
     *            DOCUMENT ME!
     */
    Palette(String name) {
        setName(name);
    }

    public static final int getMaxIndex(Palette palette) {

        int max = 0;

        for (Iterator<PaletteColor> i = palette.getColors().values().iterator(); i.hasNext();) {
            Palette.PaletteColor pc = (Palette.PaletteColor) i.next();
            int index = pc.getIndex();
            if (index > max) {
                max = index;
            }
        }

        return max;
    }

    public void addColors(List<PaletteColor> colorList) {

    }

    public void addColor(PaletteColor pc) {
        colors.put(new Integer(pc.getIndex()), pc);
        initColorModel();
    }

    // ~ Methods
    // ************************************************************************************

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public int getBlack() {

        if (black == NON_COLOR_INDEX) {

            int minColorSum = 3 * 255;

            for (Iterator<PaletteColor> i = getColors().values().iterator(); i.hasNext();) {

                PaletteColor pc = i.next();
                Color color = pc.getColor();

                int colorSum = color.getRed() + color.getGreen()
                        + color.getBlue();

                if (colorSum < minColorSum) {
                    black = pc.getIndex();
                    minColorSum = colorSum;
                }
            }
        }

        return black;
    }

    /**
     * Get an indexed PaletteColor object.
     * 
     * @param i
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     * 
     * @throws TilepileException
     *             DOCUMENT ME!
     */
    public final PaletteColor getPaletteColor(Integer i) {

        PaletteColor pc = getColors().get(i);

        if (pc == null) {
            if (i.intValue() == NON_COLOR_INDEX) {
                return NON_COLOR;
            }
            else {
                TilepileUtils.getLogger().warning(
                        "Illegal palette color " + i
                                + " requested from palette " + getName());
            }
        }

        return pc;

    }

    /**
     * Get an indexed PaletteColor object.
     * 
     * @param i
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     * 
     * @throws TilepileException
     *             DOCUMENT ME!
     */
    public final PaletteColor getPaletteColor(int i) {
        return getColors().get(new Integer(i));

    }

    /**
     * Get color from an indexed PaletteColor object.
     * 
     * @param i
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     * 
     * @throws TilepileException
     *             DOCUMENT ME!
     */
    public final Color getColor(int i) {

        if (i == NON_COLOR_INDEX) {

            return NON_COLOR_APPEARANCE;
        }

        PaletteColor pc = getPaletteColor(i);

        if (pc == null) {
            return NON_COLOR_APPEARANCE;
        }
        else {
            return pc.getColor();
        }
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public IndexColorModel getColorModel() {

        if (colorModel == null) {
            initColorModel();
        }

        return colorModel;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public Map<Integer, PaletteColor> getColors() {
        return Collections.unmodifiableMap(colors);
    }
    
    public Map<Integer, PaletteColor> getIndexMap() {
    	return getColors();
    }
    
    public Map<Color, PaletteColor> getColorMap() {
    	Map<Color, PaletteColor> map = new TreeMap<Color, PaletteColor>(new Comparator<Color>() {
			@Override
			public int compare(Color c0, Color c1) {
				if(c0.getRGB() < c1.getRGB()) {
					return -1;
				} else if(c0.getRGB() > c1.getRGB()) {
					return 1;
				} else {
					return 0;
				}
			}
		});
    	for(Palette.PaletteColor c : colors.values()) {
    		map.put(c.color, c);
    	}
    	return Collections.unmodifiableMap(map);
    }
    
    public Map<String, PaletteColor> getNameMap() {
    	Map<String, PaletteColor> map = new TreeMap<String, PaletteColor>();
    	for(Palette.PaletteColor c : colors.values()) {
    		map.put(c.name, c);
    	}
    	return Collections.unmodifiableMap(map);
    }

    /**
     * Get name from an indexed PaletteColor object.
     * 
     * @param i
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     * 
     * @throws TilepileException
     *             DOCUMENT ME!
     */
    public final String getName(int i) {

        if (i == NON_COLOR_INDEX) {

            return NON_COLOR_NAME;
        }

        PaletteColor pc = getPaletteColor(i);

        if (pc == null) {
            return NON_COLOR_NAME;
        }
        else {
            return pc.getName();
        }
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public String getName() {
        return name;
    }

    /**
     * Creates a new getSize object.
     * 
     * @return DOCUMENT ME!
     */
    public final int getSize() {
        return colors.keySet().size();
    }

    /**
     * DOCUMENT ME!
     * 
     * @param o
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public boolean equals(Object o) {

        if (o instanceof Palette) {

            Palette other = (Palette) o;

            if (!getName().equals(other.getName())) {

                return false;
            }

            if (getSize() != other.getSize()) {

                return false;
            }

            Iterator<PaletteColor> i = colors.values().iterator();
            Iterator<PaletteColor> j = other.getColors().values().iterator();

            while (i.hasNext()) {

                PaletteColor color = i.next();
                PaletteColor otherColor = j.next();

                if (!color.equals(otherColor)) {

                    return false;
                }
            }

            return true;
        }
        else {

            return false;
        }
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     * 
     * @throws RuntimeException
     *             DOCUMENT ME!
     */
    public int hashCode() {

        if (precalculatedHash == TilepileUtils.NOT_CALCULATED) {

            int precalculatedHash = 0;

            for (Iterator<PaletteColor> i = colors.values().iterator(); i.hasNext();) {
                precalculatedHash ^= i.next().hashCode();
            }
        }

        return precalculatedHash;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public String toString() {

        StringBuffer sb = new StringBuffer();

        sb.append("Palette: ");
        sb.append(getName());
        sb.append("\n");

        for (Iterator<PaletteColor> i = colors.values().iterator(); i.hasNext();) {
            sb.append(i.next());
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * DOCUMENT ME!
     * 
     * @param name
     *            DOCUMENT ME!
     */
    private void setName(String name) {
        this.name = name;
    }

    /**
     * DOCUMENT ME!
     */
    private void initColorModel() {

        int paletteSize = getMaxIndex(this) + 1;

        byte[] r = new byte[paletteSize];
        byte[] g = new byte[paletteSize];
        byte[] b = new byte[paletteSize];

        for (Iterator<PaletteColor> i = colors.values().iterator(); i.hasNext();) {
            PaletteColor pc = i.next();
            Color color = pc.getColor();
            int index = pc.getIndex();
            r[index] = TilepileUtils.i2b(color.getRed());
            g[index] = TilepileUtils.i2b(color.getGreen());
            b[index] = TilepileUtils.i2b(color.getBlue());
        }

        colorModel = new IndexColorModel((int) Math.ceil(Math.log(paletteSize) / Math.log(2)), paletteSize, r, g, b);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException,
            ClassNotFoundException {
        in.defaultReadObject();
        black = NON_COLOR_INDEX;
    }
}
