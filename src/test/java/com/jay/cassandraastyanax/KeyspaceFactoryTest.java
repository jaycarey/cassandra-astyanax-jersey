package com.jay.cassandraastyanax;

import com.jay.cassandraastyanax.domain.KeyspaceFactory;
import com.netflix.astyanax.Keyspace;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author jaycarey
 */
public class KeyspaceFactoryTest {

    private KeyspaceFactory keyspaceFactory;

    @Before
    public void before() throws Exception {

        keyspaceFactory = new KeyspaceFactory();
    }

    @Test
    public void canCreateKeyspace() throws Exception {
        Keyspace keyspace = keyspaceFactory.getKeyspace("test");

        assertThat(keyspace.getKeyspaceName(), equalTo("test"));
    }
}

