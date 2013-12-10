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
 * Date: 12/4/13
 * Time: 8:44 PM
 */
public class NewLuceneIndexTest {
    static ActorSystem system;

    @Before
    public void setUp() throws Exception {
        system = ActorSystem.create();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testOnReceive() throws Exception {
        final Props props = Props.create(NewLuceneIndex.class);
        final TestActorRef<NewLuceneIndex> ref = TestActorRef.create(system, props, "testA");
        final NewLuceneIndex actor = ref.underlyingActor();

        NewLuceneIndexRequest request = new NewLuceneIndexRequest("index/");
        NewLuceneIndexResponse response = actor.createIndex(request);

        Assert.assertTrue(response.isProcessed());
    }
}
