package com.infinimeme.tilepile.data;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.infinimeme.tilepile.common.MainStation;
import com.infinimeme.tilepile.common.Mural;
import com.infinimeme.tilepile.common.Palette;
import com.infinimeme.tilepile.common.States;
import com.infinimeme.tilepile.common.Station;
import com.infinimeme.tilepile.common.TilepileObject;
import com.infinimeme.tilepile.common.TilepileUtils;

/**
 * DOCUMENT ME!
 * 
 * @author Greg Barton The contents of this file are released under the GPL.
 *         Copyright 2004-2010 Greg Barton
 */
public class DataManager extends UnicastRemoteObject implements DataConstants,
        DataManagerRemote {

    // ~ Static fields/initializers
    // *****************************************************************

    private static final long serialVersionUID = -6185854062232657260L;

    /** DOCUMENT ME! */

    private static final FileFilter DATA_OBJECT_FILE_FILTER = new FileFilter() {
        public boolean accept(File pathname) {
            return pathname.getName().endsWith(DATA_FILE_EXTENSION)
                    && pathname.isFile();
        }
    };

    /** DOCUMENT ME! */
    private static DataManager INSTANCE = null;

    // ~ Instance fields
    // ****************************************************************************

    /** Backing store for Mural objects. */
    private Map<String, Mural> murals = new TreeMap<String, Mural>();

    private Map<String, Palette> palettes = new TreeMap<String, Palette>();

    private Map<String, States> states = new TreeMap<String, States>();

    private Map<String, Station> stations = new TreeMap<String, Station>();

    private Map<String, MainStation> mainStations = new TreeMap<String, MainStation>();
    
    // ~ Constructors
    // *******************************************************************************

    private final void load(final File dataDir) {
    	
    	class Loader<T extends TilepileObject> {
    		public void load(PersistentType type, Class<T> clazz,  Map<String, T> map) {
    			File dir = new File(dataDir, type.getType().getKeyPrefix());

                if (dir.exists() && dir.isDirectory()) {

                    File[] members = dir.listFiles(DATA_OBJECT_FILE_FILTER);

                    for (int j = 0; j < members.length; j++) {

                        try {
                            TilepileUtils.getLogger().info("Loading " + members[j].getAbsolutePath());
                            T object = DataUtils.readFile(members[j], clazz);
        
                            TilepileUtils.getLogger().info("Loading "
                                    + type
                                    + " with "
                                    + object);
        
                            map.put(object.getName(), object);
                            
                        } catch (ClassNotFoundException cnfe) {
                            TilepileUtils.exceptionReport(cnfe);
                        } catch(IOException ioe) {
                            TilepileUtils.exceptionReport(ioe);
                        }
                    }
                }
    		}
    	}
    	new Loader<Mural>().load(PersistentType.MURAL, Mural.class, murals);
    	new Loader<Palette>().load(PersistentType.PALETTE, Palette.class, palettes);
    	new Loader<States>().load(PersistentType.STATES, States.class, states);
    }

    /**
     * Creates a new DataManager object.
     * 
     * @param persistent
     *            DOCUMENT ME!
     */
    private DataManager() throws RemoteException {

        super();

        DATA_DIRECTORY.mkdirs();

        // Init object categories
        for (PersistentType type : PersistentType.values()) {
            new File(DATA_DIRECTORY, type.getType().getKeyPrefix()).mkdirs();
        }

        load(DATA_DIRECTORY);

    }

    // ~ Methods
    // ************************************************************************************

    /**
     * DOCUMENT ME!
     * 
     * @param persistent
     *            DOCUMENT ME!
     * @return DOCUMENT ME!
     */
    static final DataManager getInstance() throws RemoteException {

        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }

        return INSTANCE;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param name
     *            DOCUMENT ME!
     * @return DOCUMENT ME!
     */
    public final MainStation getMainStation(String name) {

        return mainStations.get(name);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param name
     *            DOCUMENT ME!
     * @return DOCUMENT ME!
     */
    public final Mural getMural(String name) {

        return murals.get(name);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param name
     *            DOCUMENT ME!
     * @return DOCUMENT ME!
     */
    public final Palette getPalette(String name) {

        return palettes.get(name);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param name
     *            DOCUMENT ME!
     * @return DOCUMENT ME!
     */
    public final States getStates(String name) {

        return states.get(name);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param name
     *            DOCUMENT ME!
     * @return DOCUMENT ME!
     */
    public final Station getStation(String name) {

        return stations.get(name);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param name
     *            DOCUMENT ME!
     * @return DOCUMENT ME!
     */
    public final boolean containsMainStation(String name) {

        return mainStations.containsKey(name);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param name
     *            DOCUMENT ME!
     * @return DOCUMENT ME!
     */
    public final boolean containsMural(String name) {

        return murals.containsKey(name);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param name
     *            DOCUMENT ME!
     * @return DOCUMENT ME!
     */
    public final boolean containsPalette(String name) {

        return palettes.containsKey(name);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param name
     *            DOCUMENT ME!
     * @return DOCUMENT ME!
     */
    public final boolean containsStates(String name) {

        return states.containsKey(name);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param name
     *            DOCUMENT ME!
     * @return DOCUMENT ME!
     */
    public final boolean containsStation(String name) {

        return stations.containsKey(name);
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public final Collection<MainStation> instancesOfMainStation() {
        return new LinkedList<MainStation>(mainStations.values());
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public final Collection<Mural> instancesOfMural() {
        return new LinkedList<Mural>(murals.values());
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public final Collection<Palette> instancesOfPalette() {
        return new LinkedList<Palette>(palettes.values());
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public final Collection<States> instancesOfStates() {
        return new LinkedList<States>(states.values());
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public final Collection<Station> instancesOfStation() {
        return new LinkedList<Station>(stations.values());
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public final Set<String> namesOfMainStation() {
        return new TreeSet<String>(mainStations.keySet());
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public final Set<String> namesOfMural() {
        return new TreeSet<String>(murals.keySet());
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public final Set<String> namesOfPalette() {
        return new TreeSet<String>(palettes.keySet());
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public final Set<String> namesOfStates() {
        return new TreeSet<String>(states.keySet());
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public final Set<String> namesOfStation() {
        return new TreeSet<String>(stations.keySet());
    }

    /**
     * DOCUMENT ME!
     * 
     * @param mainStation
     *            DOCUMENT ME!
     */
    public final void addMainStation(MainStation mainStation) {
        setMainStation(mainStation);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param mural
     *            DOCUMENT ME!
     */
    public final void addMural(Mural mural) {
        setMural(mural);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param palette
     *            DOCUMENT ME!
     */
    public final void addPalette(Palette palette) {
        setPalette(palette);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param states
     *            DOCUMENT ME!
     */
    public final void addStates(States state) {
        setStates(state);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param station
     *            DOCUMENT ME!
     */
    public final void addStation(Station station) {
        setStation(station);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param mainStation
     *            DOCUMENT ME!
     */
    public final void setMainStation(MainStation mainStation) {
        mainStations.put(mainStation.getName(), mainStation);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param mural
     *            DOCUMENT ME!
     */
    public final void setMural(Mural mural) {
        murals.put(mural.getName(), mural);
        DataUtils.save(Type.MURAL, mural);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param palette
     *            DOCUMENT ME!
     */
    public final void setPalette(Palette palette) {
        palettes.put(palette.getName(), palette);
        DataUtils.save(Type.PALETTE, palette);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param states
     *            DOCUMENT ME!
     */
    public final void setStates(States state) {
        states.put(state.getName(), state);
        DataUtils.save(Type.STATES, state);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param station
     *            DOCUMENT ME!
     */
    public final void setStation(Station station) {
        stations.put(station.getName(), station);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param mainStation
     *            DOCUMENT ME!
     */
    public final void removeMainStation(String name) {
        mainStations.remove(name);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param mural
     *            DOCUMENT ME!
     */
    public final void removeMural(String name) {
        murals.remove(name);
        DataUtils.delete(Type.MURAL, name);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param palette
     *            DOCUMENT ME!
     */
    public final void removePalette(String name) {
        palettes.remove(name);
        DataUtils.delete(Type.PALETTE, name);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param states
     *            DOCUMENT ME!
     */
    public final void removeStates(String name) {
        states.remove(name);
        DataUtils.delete(Type.STATES, name);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param station
     *            DOCUMENT ME!
     */
    public final void removeStation(String name) {
        stations.remove(name);
    }
}
