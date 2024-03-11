package com.infinimeme.tilepile.data;

import com.infinimeme.tilepile.common.*;
import java.io.File;

public interface DataConstants {

    public static enum Type {
        MURAL {
    		public String getKeyPrefix() {
    			return "mural";
    		}
        },
        PALETTE {
    		public String getKeyPrefix() {
    			return "palette";
    		}
        },
        STATES {
    		public String getKeyPrefix() {
    			return "states";
    		}
        },
        STATION {
    		public String getKeyPrefix() {
    			return "station";
    		}
        },
        MAINSTATION {
    		public String getKeyPrefix() {
    			return "mainStation";
    		}
        };

    	public abstract String getKeyPrefix();
    }

    public static enum PersistentType {
    	MURAL {
    		public Type getType() {
    			return Type.MURAL;
    		}
    		public Class<Mural> getPersistentClass() {
    			return Mural.class;
    		}
    	},
    	PALETTE {
    		public Type getType() {
    			return Type.PALETTE;
    		}
    		public Class<Palette> getPersistentClass() {
    			return Palette.class;
    		}
    	},
    	STATES {
    		public Type getType() {
    			return Type.STATES;
    		}
    		public Class<States> getPersistentClass() {
    			return States.class;
    		}
    	};
    	
    	public abstract Type getType();
    	public abstract Class<? extends TilepileObject> getPersistentClass();
    	
    };

    public static final File DATA_DIRECTORY = new File("data");

    public static final String DATA_FILE_EXTENSION = ".ser.gz";

}
