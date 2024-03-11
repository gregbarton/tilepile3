package com.infinimeme.tilepile.common;

import java.util.Comparator;

/**
 * DOCUMENT ME!
 *
 * @author Greg Barton The contents of this file are released under the GPL.  Copyright
 *         2004-2010 Greg Barton
 **/
public class LocationComparator
    implements Comparator<Location> {

    //~ Methods ************************************************************************************

    /**
     * DOCUMENT ME!
     *
     * @param o1 DOCUMENT ME!
     * @param o2 DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public int compare(Location l1, Location l2) {

        if(l1.getX() > l2.getX()) {

            return 1;
        } else if(l1.getX() < l2.getX()) {

            return -1;
        } else {

            if(l1.getY() > l2.getY()) {

                return 1;
            } else if(l1.getY() < l2.getY()) {

                return -1;
            } else {

                return 0;
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param obj DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     **/
    public boolean equals(Object obj) {

        if(obj instanceof LocationComparator) {

            return true;
        } else {

            return false;
        }
    }
}
