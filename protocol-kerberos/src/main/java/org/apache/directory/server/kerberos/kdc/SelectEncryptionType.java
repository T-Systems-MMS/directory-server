/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */
package org.apache.directory.server.kerberos.kdc;


import org.apache.directory.server.kerberos.shared.crypto.encryption.EncryptionType;
import org.apache.directory.server.kerberos.shared.exceptions.ErrorType;
import org.apache.directory.server.kerberos.shared.exceptions.KerberosException;
import org.apache.mina.common.IoSession;
import org.apache.mina.handler.chain.IoHandlerCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev$, $Date$
 */
public class SelectEncryptionType implements IoHandlerCommand
{
    /** The log for this class. */
    private static final Logger log = LoggerFactory.getLogger( SelectEncryptionType.class );

    private String contextKey = "context";


    public void execute( NextCommand next, IoSession session, Object message ) throws Exception
    {
        KdcContext kdcContext = ( KdcContext ) session.getAttribute( getContextKey() );
        KdcServer config = kdcContext.getConfig();

        EncryptionType[] requestedTypes = kdcContext.getRequest().getEType();

        EncryptionType bestType = getBestEncryptionType( requestedTypes, config.getEncryptionTypes() );

        log.debug( "Session will use encryption type {}.", bestType );

        if ( bestType == null )
        {
            throw new KerberosException( ErrorType.KDC_ERR_ETYPE_NOSUPP );
        }

        kdcContext.setEncryptionType( bestType );

        next.execute( session, message );
    }


    protected EncryptionType getBestEncryptionType( EncryptionType[] requestedTypes, EncryptionType[] configuredTypes )
    {
        for ( int ii = 0; ii < requestedTypes.length; ii++ )
        {
            for ( int jj = 0; jj < configuredTypes.length; jj++ )
            {
                if ( requestedTypes[ii] == configuredTypes[jj] )
                {
                    return configuredTypes[jj];
                }
            }
        }

        return null;
    }


    protected String getContextKey()
    {
        return ( this.contextKey );
    }
}
