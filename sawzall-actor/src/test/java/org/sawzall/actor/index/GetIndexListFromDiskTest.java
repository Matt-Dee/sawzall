package org.sawzall.actor.index;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * User: mdonnelly
 * Date: 12/9/13
 * Time: 7:09 PM
 */
public class GetIndexListFromDiskTest {

    static ActorSystem system;

    @Before
    public void setUp() throws Exception {
        system = ActorSystem.create();
    }

    @After
    public void tearDown() throws Exception {
        new File("./lucene-index-locations.txt").delete();
    }


    @Test
    public void testOnReceive() throws Exception {
        final Props createProps = Props.create(IndexTracker.class);
        final TestActorRef<IndexTracker> createPropsRef = TestActorRef.create(system, createProps, "testA");
        final IndexTracker createPropsActor = createPropsRef.underlyingActor();

        Assert.assertTrue(createPropsActor.recordIndexLocation("./testGetIndexListFromDisk"));

        final Props readProps = Props.create(GetIndexListFromDisk.class);
        final TestActorRef<GetIndexListFromDisk> readPropsRef = TestActorRef.create(system, readProps, "testB");
        final GetIndexListFromDisk readPropsActor = readPropsRef.underlyingActor();

        Assert.assertTrue( readPropsActor.getListAtStartup("./lucene-index-locations.txt").getSet().size() == 1 );


    }
}
