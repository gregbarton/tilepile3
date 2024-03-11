package com.infinimeme.tilepile.common;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * DOCUMENT ME!
 *
 * @author Greg Barton The contents of this file are released under the GPL.  Copyright
 *         2004-2010 Greg Barton
 **/
public class Mural
    implements TilepileObject {

    //~ Static fields/initializers *****************************************************************

    /** DOCUMENT ME! */
    public static final int NO_COLOR = Palette.NON_COLOR_INDEX;

    /** DOCUMENT ME! */
    static final long serialVersionUID = 3808560687392385523L;

    //~ Instance fields ****************************************************************************

    /** DOCUMENT ME! */
    private String name = null;

    /** DOCUMENT ME! */
    private String paletteName = null;

    /** DOCUMENT ME! */
    private Section[][] sections = null;

    /** DOCUMENT ME! */
    private int[][] tiles = null;

    /** DOCUMENT ME! */
    private int gridHeight = 0;

    /** DOCUMENT ME! */
    private int gridWidth = 0;

    //~ Constructors *******************************************************************************

    /**
     * Creates a new Mural object.
     *
     * @param name DOCUMENT ME!
     * @param gridWidth DOCUMENT ME!
     * @param gridHeight DOCUMENT ME!
     * @param tiles DOCUMENT ME!
     * @param palette DOCUMENT ME!
     **/
    Mural(String name, int gridWidth, int gridHeight, int[][] tiles, Palette palette) {
        setName(name);
        setGridWidth(gridWidth);
        setGridHeight(gridHeight);
        setTiles(tiles);
        setPaletteName(palette.getName());
    }

    //~ Methods ************************************************************************************

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public int getGridHeight() {
        return gridHeight;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public int getGridWidth() {
        return gridWidth;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public int getHeight() {
        return tiles.length;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public String getName() {
        return name;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public String getPaletteName() {
        return paletteName;
    }

    /**
     * DOCUMENT ME!
     *
     * @param location DOCUMENT ME!
     * @param width DOCUMENT ME!
     * @param height DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws TilepileException DOCUMENT ME!
     **/
    public Section getSection(Location location, int width, int height)
                       throws TilepileException {
        return new Section(location, width, height);
    }

    /**
     * DOCUMENT ME!
     *
     * @param location DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws TilepileException DOCUMENT ME!
     **/
    public Section getSection(Location location)
                       throws TilepileException {
        return new Section(location);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public Set<Location> getSectionLocationSet() {

        Set<Location> locationSet = new TreeSet<Location>(new LocationComparator());

        int dataWidth = getWidth();
        int dataHeight = getHeight();
        int gridWidth = getGridWidth();
        int gridHeight = getGridHeight();

        int tx = (int)Math.ceil((double)dataWidth / gridWidth);
        int ty = (int)Math.ceil((double)dataHeight / gridHeight);

        Section[][] s = getSections();

        for(int y = 0; y < ty; y++) {

            for(int x = 0; x < tx; x++) {
                locationSet.add(s[y][x].getLocation());
            }
        }

        return locationSet;
    }

    /**
     * DOCUMENT ME!
     *
     * @param muralX DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public int getSectionX(int muralX) {
        return muralX / getGridWidth();
    }

    /**
     * DOCUMENT ME!
     *
     * @param muralY DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public int getSectionY(int muralY) {
        return muralY / getGridWidth();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public Section[][] getSections() {

        if(sections == null) {
            initSections();
        }

        return sections;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public int[][] getTiles() {
        return tiles;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public int getWidth() {
        return tiles[0].length;
    }
    
    public String toString() {
        return "Mural: " + getName() + " (" + getHeight() + "x" + getWidth() + ") usng palette: " + getPaletteName();
    }

    /**
     * DOCUMENT ME!
     *
     * @param tiles DOCUMENT ME!
     **/
    void setTiles(int[][] tiles) {
        this.tiles = tiles;
    }

    /**
     * DOCUMENT ME!
     *
     * @param gridHeight DOCUMENT ME!
     **/
    private void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    /**
     * DOCUMENT ME!
     *
     * @param gridWidth DOCUMENT ME!
     **/
    private void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    /**
     * DOCUMENT ME!
     *
     * @param name DOCUMENT ME!
     **/
    private void setName(String name) {
        
        if(name == null || name.equals("")) {
            throw new IllegalArgumentException("Mural name can not be blank!");
        }
        
        this.name = name;
    }

    /**
     * DOCUMENT ME!
     *
     * @param paletteName DOCUMENT ME!
     **/
    private void setPaletteName(String paletteName) {
        this.paletteName = paletteName;
    }

    /**
     * DOCUMENT ME!
     **/
    private void initSections() {

        int dataWidth = getWidth();
        int dataHeight = getHeight();
        int gridWidth = getGridWidth();
        int gridHeight = getGridHeight();

        int tx = (int)Math.ceil((double)dataWidth / gridWidth);
        int ty = (int)Math.ceil((double)dataHeight / gridHeight);

        sections = new Section[ty][tx];

        for(int x = 0; x < tx; x++) {

            int muralX = x * gridWidth;

            for(int y = 0; y < ty; y++) {

                int muralY = y * gridHeight;

                sections[y][x] = new Section(new Location(muralX, muralY));
            }
        }
    }

    //~ Inner Classes ******************************************************************************

    /**
     * DOCUMENT ME!
     *
     * @author Greg Barton
     **/
    public class Counter {

        //~ Instance fields ************************************************************************

        /** DOCUMENT ME! */
        private int count = 0;

        //~ Methods ********************************************************************************

        /**
         * DOCUMENT ME!
         *
         * @return DOCUMENT ME!
         **/
        public int getCount() {
            return count;
        }

        /**
         * DOCUMENT ME!
         **/
        public void inc() {
            count++;
        }

        /**
         * DOCUMENT ME!
         *
         * @return DOCUMENT ME!
         **/
        public String toString() {
            return Integer.toString(count);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @author Greg Barton
     **/
    public class Section
        implements Serializable {

        //~ Instance fields ************************************************************************

        private static final long serialVersionUID = 9051704698638277250L;

        /** DOCUMENT ME! */
        private Location location = null;

        /** DOCUMENT ME! */
        private Map<Integer,Counter> histogram = null;

        /** TODO: make transient in next version */
        private int[][] colorCache = null;

        /** DOCUMENT ME! */
        private int height = getGridHeight();

        /** DOCUMENT ME! */
        private int precalculatedHash = Integer.MIN_VALUE;

        /** DOCUMENT ME! */
        private int width = getGridWidth();

        //~ Constructors ***************************************************************************

        /**
         * Creates a new Section object.
         *
         * @param location DOCUMENT ME!
         **/
        public Section(Location location) {
            setLocation(location);
            initColorCache();
        }

        /**
         * Creates a new Section object.
         *
         * @param location DOCUMENT ME!
         * @param width DOCUMENT ME!
         * @param height DOCUMENT ME!
         **/
        public Section(Location location, int width, int height) {
            setLocation(location);
            setWidth(width);
            setHeight(height);
            initColorCache();
        }

        //~ Methods ********************************************************************************

        /**
         * DOCUMENT ME!
         *
         * @param loc DOCUMENT ME!
         *
         * @return DOCUMENT ME!
         **/
        public final Location getAbsoluteLocation(Location loc) {

            return new Location(
                getLocation().getX() + loc.getX(), 
                getLocation().getY() + loc.getY()
            );
        }

        /**
         * DOCUMENT ME!
         *
         * @param x DOCUMENT ME!
         *
         * @return DOCUMENT ME!
         **/
        public final int getAbsoluteX(int x) {
            return getLocation().getX() + x;
        }

        /**
         * DOCUMENT ME!
         *
         * @param y DOCUMENT ME!
         *
         * @return DOCUMENT ME!
         **/
        public final int getAbsoluteY(int y) {
            return getLocation().getY() + y;
        }

        /**
         * DOCUMENT ME!
         *
         * @param x DOCUMENT ME!
         * @param y DOCUMENT ME!
         * @param color DOCUMENT ME!
         *
         * @throws IllegalArgumentException DOCUMENT ME!
         **/
        public final void setColorNumber(int x, int y, int color) {
            
            colorCache[x][y] = color;

            int absoluteX = getAbsoluteX(x);

            if(absoluteX >= getMural().getWidth()) {
                throw new IllegalArgumentException("Attempted to set color number " + color +
                                                   " at location " + x + "," + y);
            }

            int absoluteY = getAbsoluteY(y);

            if(absoluteY >= getMural().getHeight()) {
                throw new IllegalArgumentException("Attempted to set color number " + color +
                                                   " at location " + x + "," + y);
            }

            int[][] tiles = getTiles();

            tiles[absoluteY][absoluteX] = color;
        }

        /**
         * DOCUMENT ME!
         *
         * @param x DOCUMENT ME!
         * @param y DOCUMENT ME!
         *
         * @return DOCUMENT ME!
         **/
        public final int getColorNumber(int x, int y) {

            int color = colorCache[x][y];

            if(color == NO_COLOR) {

                int absoluteX = getAbsoluteX(x);

                if(absoluteX >= getMural().getWidth()) {

                    return NO_COLOR;
                }

                int absoluteY = getAbsoluteY(y);

                if(absoluteY >= getMural().getHeight()) {

                    return NO_COLOR;
                }

                int[][] tiles = getTiles();

                color = tiles[absoluteY][absoluteX];
                
                colorCache[x][y] = color;
            }

            return color;
        }

        /**
         * DOCUMENT ME!
         *
         * @return DOCUMENT ME!
         **/
        public int getHeight() {
            return height;
        }

        /**
         * DOCUMENT ME!
         *
         * @return DOCUMENT ME!
         **/
        public Map<Integer,Counter> getHistogram() {

            if(histogram == null) {
                calcHistogram();
            }

            return histogram;
        }

        /**
         * DOCUMENT ME!
         *
         * @return DOCUMENT ME!
         **/
        public Location getLocation() {
            return location;
        }

        /**
         * DOCUMENT ME!
         *
         * @return DOCUMENT ME!
         **/
        public Mural getMural() {
            return Mural.this;
        }

        /**
         * DOCUMENT ME!
         *
         * @return DOCUMENT ME!
         **/
        public int getMuralX() {
            return getLocation().getX();
        }

        /**
         * DOCUMENT ME!
         *
         * @return DOCUMENT ME!
         **/
        public int getMuralY() {
            return getLocation().getY();
        }

        /**
         * DOCUMENT ME!
         *
         * @return DOCUMENT ME!
         **/
        public int getWidth() {
            return width;
        }
        
        /**
         * DOCUMENT ME!
         *
         * @return DOCUMENT ME!
         **/
        public int hashCode() {

            if(precalculatedHash == Integer.MIN_VALUE) {

                int locationCode = getMuralX();
                locationCode <<= 16;
                locationCode ^= getMuralY();

                int tilesCode = 1;

                for(int x = 0; x < getWidth(); x++) {

                    for(int y = 0; y < getHeight(); y++) {
                        tilesCode *= getColorNumber(x, y);
                        tilesCode >>>= 1;
                    }
                }

                precalculatedHash = tilesCode^locationCode;
            }

            return precalculatedHash;
        }

        /**
         * DOCUMENT ME!
         *
         * @param height DOCUMENT ME!
         **/
        private void setHeight(int height) {
            this.height = height;
        }

        /**
         * DOCUMENT ME!
         *
         * @param location DOCUMENT ME!
         **/
        private void setLocation(Location location) {
            this.location = location;
        }

        /**
         * DOCUMENT ME!
         *
         * @param width DOCUMENT ME!
         **/
        private void setWidth(int width) {
            this.width = width;
        }

        /**
         * DOCUMENT ME!
         **/
        private void calcHistogram() {

            Map<Integer,Counter> hist = new TreeMap<Integer,Counter>();

            for(int y = 0; y < getHeight(); y++) {

                for(int x = 0; x < getWidth(); x++) {

                    int colorNumber = getColorNumber(x, y);

                    Integer key = new Integer(colorNumber);
                    Counter counter = (Counter)hist.get(key);

                    if(counter == null) {
                        counter = new Counter();
                        hist.put(key, counter);
                    }

                    counter.inc();

                }
            }

            histogram = Collections.unmodifiableMap(hist);
        }

        /**
         * DOCUMENT ME!
         **/
        private void initColorCache() {
            
            colorCache = new int[getWidth()][getHeight()];

            for(int i = 0; i < colorCache.length; i++) {
                Arrays.fill(colorCache[i], NO_COLOR);
            }
        }
        
        public String toString() {
            return "Section " + getLocation();
        }
    }
}
