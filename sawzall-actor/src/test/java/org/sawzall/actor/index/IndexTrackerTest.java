package org.sawzall.actor.index;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sawzall.message.index.request.NewLuceneIndexRequest;
import org.sawzall.message.index.response.NewLuceneIndexResponse;

/**
 * User: mdonnelly
 * Date: 12/5/13
 * Time: 7:32 PM
 */
public class IndexTrackerTest {

    static ActorSystem system;

    @Before
    public void setUp() throws Exception {
        system = ActorSystem.create();
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testRecordIndexLocation() throws Exception {
        final Props props = Props.create(IndexTracker.class);
        final TestActorRef<IndexTracker> ref = TestActorRef.create(system, props, "testA");
        final IndexTracker actor = ref.underlyingActor();

        Assert.assertTrue( actor.recordIndexLocation("./") );
    }
}
