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


import jdbm.helper.StringComparator;
import org.apache.ldap.common.schema.ComparableComparator;
import org.apache.ldap.common.util.BigIntegerComparator;

import javax.naming.NamingException;
import java.util.Comparator;



/**
 * A producer of Comparator objects for the eve schema.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev$
 */
public class ApacheComparatorProducer extends AbstractBootstrapProducer
{
    public ApacheComparatorProducer()
    {
        super( ProducerTypeEnum.COMPARATOR_PRODUCER );
    }


    // ------------------------------------------------------------------------
    // BootstrapProducer Methods
    // ------------------------------------------------------------------------


    /**
     * @see org.apache.ldap.server.schema.bootstrap.BootstrapProducer#produce(org.apache.ldap.server.schema.bootstrap.BootstrapRegistries, ProducerCallback)
     */
    public void produce( BootstrapRegistries registries, ProducerCallback cb )
        throws NamingException
    {
        Comparator comparator;

        // For exactDnAsStringMatch -> 1.2.6.1.4.1.18060.1.1.1.2.1
        comparator = new ComparableComparator();
        cb.schemaObjectProduced( this, "1.2.6.1.4.1.18060.1.1.1.2.1", comparator );

        // For bigIntegerMatch -> 1.2.6.1.4.1.18060.1.1.1.2.2
        comparator = new BigIntegerComparator();
        cb.schemaObjectProduced( this, "1.2.6.1.4.1.18060.1.1.1.2.2", comparator );

        // For jdbmStringMatch -> 1.2.6.1.4.1.18060.1.1.1.2.3
        comparator = new StringComparator();
        cb.schemaObjectProduced( this, "1.2.6.1.4.1.18060.1.1.1.2.3", comparator );

    }
}
