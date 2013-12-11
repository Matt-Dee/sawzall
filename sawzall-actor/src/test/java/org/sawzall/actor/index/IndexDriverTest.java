package org.sawzall.actor.index;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sawzall.message.index.request.DocumentToIndex;
import org.sawzall.message.index.request.NewLuceneIndexRequest;
import org.sawzall.message.index.request.SearchField;
import org.sawzall.message.index.response.LuceneIndex;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * User: mdonnelly
 * Date: 12/10/13
 * Time: 8:59 PM
 */
public class IndexDriverTest {

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

        int numberOfIndexWriters = 100;

        final Props props = Props.create(NewLuceneIndex.class);
        final TestActorRef<NewLuceneIndex> ref = TestActorRef.create(system, props, "create-indexes-for-testing");
        final NewLuceneIndex actor = ref.underlyingActor();

        List<LuceneIndex> indexes = new LinkedList<LuceneIndex>();

        for(int i=0;i<numberOfIndexWriters;i++){
            NewLuceneIndexRequest indexRequest = new NewLuceneIndexRequest("index_" + i + "/");
            LuceneIndex index = actor.createIndex(indexRequest);
            indexes.add(index);
        }

        final Props createProps = Props.create(IndexDriver.class);
        final TestActorRef<IndexDriver> createPropsRef = TestActorRef.create(system, createProps, "test-index-write");
        final IndexDriver createPropsActor = createPropsRef.underlyingActor();

        createPropsActor.onReceive(100);

        for(LuceneIndex index : indexes){
            createPropsActor.onReceive(index);
        }

        System.out.println(new DateTime().toString("hh:mm:ss"));
        for(int i = 0; i < 100000; i++){
            createPropsActor.onReceive(createDocument(i));
        }
        System.out.println(new DateTime().toString("hh:mm:ss"));

//        Assert.assertTrue(createPropsActor.recordIndexLocation("./testGetIndexListFromDisk"));

    }

    private DocumentToIndex createDocument(int id){
        DocumentToIndex dti = new DocumentToIndex();
        new Random().nextInt();

        dti.addSearchField(getId(id));
        dti.addSearchField(getField());

        return dti;
    }

    private SearchField getId(int id){
        SearchField sf = new SearchField();
        sf.setType(SearchField.TypeEnum.string.toString());
        sf.setFieldId("id");
//        sf.setValue("" + new Random().nextInt());
        sf.setValue(""+id);
        sf.setStore(true);

        return sf;
    }

    private SearchField getField(){
        SearchField sf = new SearchField();
        sf.setType(SearchField.TypeEnum.string.toString());
        sf.setFieldId("field");
        sf.setValue("foo");
        sf.setStore(true);

        return sf;
    }

}
