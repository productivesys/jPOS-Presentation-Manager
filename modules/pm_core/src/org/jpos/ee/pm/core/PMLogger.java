/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2010 Alejandro P. Revilla
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jpos.ee.pm.core;

import org.jpos.util.Log;
import org.jpos.util.LogEvent;
import org.jpos.util.Logger;

/**Helper class for event log.
 * 
 * @author yero jeronimo.paoletti@gmail.com
 * @see Logger
 *  
 * */
public class PMLogger {
    private static Log _log;
    private static boolean debug;
    
    /**Internal getter for pm logger
     * @return The PM logger*/
    public static Log getLog(){
        //return Log.getLog ("pm-logger", "presentation-manager");
        return _log;
    }
    
    /**Generate an info entry on the local logger*/
    public static void info(Object o){
        LogEvent evt = getLog().createInfo();
        evt.addMessage(o);
        Logger.log(evt);
    }

    /**Generate a warn entry on the local logger*/
    public static void warn(Object o){
        LogEvent evt = getLog().createWarn();
        evt.addMessage(o);
        Logger.log(evt);
    }

    /**Generate an error entry on the local logger*/
    public static void error(Object o){
        LogEvent evt = getLog().createError();
        evt.addMessage(o);
        Logger.log(evt);
    }

    public static void debug(Object invoquer, Object msg) {
        if(!debug) return;
        LogEvent evt = getLog().createDebug();
        evt.addMessage("["+invoquer.getClass().getName()+"]");
        evt.addMessage(msg);
        Logger.log(evt);
    }

    public static void setLog(Log log) {
        _log = log;        
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        PMLogger.debug = debug;
    }
}
