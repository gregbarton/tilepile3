package com.infinimeme.tilepile.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * DOCUMENT ME!
 *
 * @author Greg Barton The contents of this file are released under the GPL.  Copyright
 *         2004-2010 Greg Barton
 **/
public class StatesFactory {

    public static final String BACKUP_EXTENSION = ".tps";
    
    //~ Methods ************************************************************************************

    /**
     * DOCUMENT ME!
     *
     * @param mural DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public static final States make(Mural mural) {

        return new States(mural);
    }

    /**
     * DOCUMENT ME!
     *
     * @param mural DOCUMENT ME!
     *
     * @throws IOException DOCUMENT ME!
     **/
    public static final void save(States states)
                           throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(states.getName() +
                                                                       BACKUP_EXTENSION)));

        bw.write(states.getName());
        bw.newLine();

        int[][] tiles = states.getStates();

        bw.write(Integer.toString(tiles.length));
        bw.newLine();

        bw.write(Integer.toString(tiles[0].length));
        bw.newLine();

        for(int i = 0; i < tiles.length; i++) {

            for(int j = 0; j < tiles[i].length; j++) {
                bw.write(Integer.toString(tiles[i][j]));
                bw.write("\t");
            }
            bw.newLine();
        }

        bw.flush();
        bw.close();
    }
}
