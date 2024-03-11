package com.infinimeme.tilepile.common;

/**
 * DOCUMENT ME!
 *
 * @author Greg Barton The contents of this file are released under the GPL.  Copyright
 *         2004-2010 Greg Barton
 **/
public class TilepileException
    extends Exception {

    //~ Constructors *******************************************************************************

    private static final long serialVersionUID = 7859828086258531492L;

    /**
     * Creates a new TilepileException object.
     **/
    public TilepileException() {
        super();
    }

    /**
     * Creates a new TilepileException object.
     *
     * @param message DOCUMENT ME!
     **/
    public TilepileException(String message) {
        super(message);
    }

    /**
     * Creates a new TilepileException object.
     *
     * @param cause DOCUMENT ME!
     **/
    public TilepileException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new TilepileException object.
     *
     * @param message DOCUMENT ME!
     * @param cause DOCUMENT ME!
     **/
    public TilepileException(String message, Throwable cause) {
        super(message, cause);
    }
}
