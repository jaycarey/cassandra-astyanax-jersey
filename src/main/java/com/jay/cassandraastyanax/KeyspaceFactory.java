package com.jay.cassandraastyanax;

import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;

import static com.netflix.astyanax.connectionpool.NodeDiscoveryType.RING_DESCRIBE;

/**
 * @author jaycarey
 */
public class KeyspaceFactory {

    public Keyspace getKeyspace(String name) {

        AstyanaxContext<Keyspace> context = createKeyspaceContext(name);

        context.start();

        return context.getClient();
    }

    private AstyanaxContext<Keyspace> createKeyspaceContext(String name) {
        return new AstyanaxContext.Builder()
                .forCluster("ClusterName")
                .forKeyspace(name)
                .withAstyanaxConfiguration(createAstyanaxConfiguration())
                .withConnectionPoolConfiguration(createConnectionPool())
                .withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
                .buildKeyspace(ThriftFamilyFactory.getInstance());
    }

    private AstyanaxConfigurationImpl createAstyanaxConfiguration() {
        return new AstyanaxConfigurationImpl()
                .setCqlVersion("3.0.0")
                .setTargetCassandraVersion("2.0")
                .setDiscoveryType(RING_DESCRIBE);
    }

    private ConnectionPoolConfigurationImpl createConnectionPool() {
        return new ConnectionPoolConfigurationImpl("MyConnectionPool")
                .setPort(9160)
                .setMaxConnsPerHost(1)
                .setSeeds("127.0.0.1:9160");
    }
}
