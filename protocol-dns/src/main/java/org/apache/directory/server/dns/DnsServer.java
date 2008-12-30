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
package org.apache.directory.server.dns;


import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.directory.server.dns.protocol.DnsProtocolHandler;
import org.apache.directory.server.dns.store.RecordStore;
import org.apache.directory.server.dns.store.jndi.JndiRecordStoreImpl;
import org.apache.directory.server.protocol.shared.DirectoryBackedService;
import org.apache.directory.server.protocol.shared.transport.TcpTransport;
import org.apache.directory.server.protocol.shared.transport.UdpTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Contains the configuration parameters for the DNS protocol provider.
 *
 * @org.apache.xbean.XBean
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev$, $Date$
 */
public class DnsServer extends DirectoryBackedService
{
    private static final long serialVersionUID = 6943138644427163149L;

    /** logger for this class */
    private static final Logger LOG = LoggerFactory.getLogger( DnsServer.class.getName() );
    
    /** The default IP port. */
    private static final int DEFAULT_IP_PORT = 53;

    /** The default service pid. */
    private static final String SERVICE_PID_DEFAULT = "org.apache.directory.server.dns";

    /** The default service name. */
    private static final String SERVICE_NAME_DEFAULT = "ApacheDS DNS Service";


    /**
     * Creates a new instance of DnsConfiguration.
     */
    public DnsServer()
    {
        super.setServiceId( SERVICE_PID_DEFAULT );
        super.setServiceName( SERVICE_NAME_DEFAULT );
        setTcpTransport( new TcpTransport( DEFAULT_IP_PORT ) );
        setUdpTransport( new UdpTransport( DEFAULT_IP_PORT ) );
    }


    /**
     * @throws IOException if we cannot bind to the specified ports
     */
    public void start() throws IOException
    {
        RecordStore store = new JndiRecordStoreImpl( getSearchBaseDn(), getSearchBaseDn(), getDirectoryService() );

        if ( getDatagramAcceptor() != null )
        {
            getDatagramAcceptor().setHandler( new DnsProtocolHandler( this, store ) );
            getDatagramAcceptor().bind( 
                new InetSocketAddress( getUdpTransport().getAddress(), getUdpTransport().getPort() ) );
        }

        if ( getSocketAcceptor() != null )
        {
            getSocketAcceptor().setCloseOnDeactivation( false );
            getSocketAcceptor().setReuseAddress( true );
            getSocketAcceptor().setHandler( new DnsProtocolHandler( this, store ) );
            getSocketAcceptor().bind(
                new InetSocketAddress( getTcpTransport().getAddress(), getTcpTransport().getPort() ) );
        }
        
        LOG.info( "DSN service started." );
        System.out.println( "DSN service started." );
    }


    public void stop() {
        if ( getDatagramAcceptor() != null )
        {
            getDatagramAcceptor().unbind( 
                new InetSocketAddress( getUdpTransport().getAddress(), getUdpTransport().getPort() ) );
        }
        if ( getSocketAcceptor() != null )
        {
            getSocketAcceptor().unbind( 
                new InetSocketAddress( getTcpTransport().getAddress(), getUdpTransport().getPort() ) );
        }
        
        LOG.info( "DSN service stopped." );
        System.out.println( "DSN service stopped." );
    }
}
