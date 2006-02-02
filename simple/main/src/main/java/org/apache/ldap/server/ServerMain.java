/*
 *   Copyright 2004 The Apache Software Foundation
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package org.apache.ldap.server;


import java.io.File;

import org.apache.directory.daemon.InstallationLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The command line main for the server.  Warning this used to be a simple test
 * case so there really is not much here.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev$
 */
public class ServerMain
{
    private static final Logger log = LoggerFactory.getLogger( "main" );

    /**
     * Takes a single argument, the path to the installation home, which contains 
     * the configuration to load with server startup settings.
     *
     * @param args the arguments
     */
    public static void main( String[] args ) throws Exception
    {
        if ( log.isInfoEnabled() )
        {
            printBanner();
        }
        
        DirectoryServer server = new DirectoryServer();

        if ( args.length > 0 && new File( args[0] ).isDirectory() )
        {
            server.init( new InstallationLayout( args[0] ), null );
            server.start();
        }
        else if ( args.length > 0 && new File( args[0] ).isFile() )
        {
            server.init( null, args );
            server.start();
        }
        else
        {
            server.init( null, null );
            server.start();
        }
    }
    
    public static final String BANNER = 
        "           _                     _          ____  ____   \n" +
        "          / \\   _ __   __ _  ___| |__   ___|  _ \\/ ___|  \n" +
        "         / _ \\ | '_ \\ / _` |/ __| '_ \\ / _ \\ | | \\___ \\   \n" +
        "        / ___ \\| |_) | (_| | (__| | | |  __/ |_| |___) |  \n" +
        "       /_/   \\_\\ .__/ \\__,_|\\___|_| |_|\\___|____/|____/   \n" +
        "               |_|                                                               \n";

    public static void printBanner()
    {
        System.out.println( BANNER );
    }
}
