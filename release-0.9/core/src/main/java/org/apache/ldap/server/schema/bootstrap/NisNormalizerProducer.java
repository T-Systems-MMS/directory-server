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
package org.apache.ldap.server.schema.bootstrap;


import org.apache.ldap.common.schema.CachingNormalizer;
import org.apache.ldap.common.schema.DeepTrimNormalizer;
import org.apache.ldap.common.schema.Normalizer;

import javax.naming.NamingException;



/**
 * A producer of Normalizer objects for the nis schema.  This code has been
 * automatically generated using schema files in the OpenLDAP format along with
 * the eve schema plugin for maven.  This has been done to facilitate
 * Eve<->OpenLDAP schema interoperability.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev$
 */
public class NisNormalizerProducer extends AbstractBootstrapProducer
{
    public NisNormalizerProducer()
    {
        super( ProducerTypeEnum.NORMALIZER_PRODUCER );
    }


    // ------------------------------------------------------------------------
    // BootstrapProducer Methods
    // ------------------------------------------------------------------------


    /**
     * @see org.apache.ldap.server.schema.bootstrap.BootstrapProducer#produce(BootstrapRegistries, ProducerCallback)
     */
    public void produce( BootstrapRegistries registries, ProducerCallback cb )
        throws NamingException
    {
        Normalizer normalizer;

        /* Really an openLDAP matching rule but its used in he nis so its here
         *
            ( 1.3.6.1.4.1.4203.1.2.1 NAME 'caseExactIA5SubstringsMatch'
             SYNTAX 1.3.6.1.4.1.1466.115.121.1.26 )
         */
        normalizer = new CachingNormalizer( new DeepTrimNormalizer() );
        cb.schemaObjectProduced( this, "1.3.6.1.4.1.4203.1.2.1", normalizer );

    }
}
