package org.sawzall.actor.index;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sawzall.message.index.IndexReaderMessages;
import org.sawzall.message.index.IndexUpdaterMessages;
import org.sawzall.message.index.request.DocumentToIndex;
import org.sawzall.message.index.request.NewLuceneIndexRequest;
import org.sawzall.message.index.request.SearchField;
import org.sawzall.message.index.LuceneIndex;

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

        int numberOfIndexWriters = 10;

        final Props props = Props.create(NewLuceneIndex.class);
        final TestActorRef<NewLuceneIndex> ref = TestActorRef.create(system, props, "create-indexes-for-testing");
        final NewLuceneIndex actor = ref.underlyingActor();

        List<LuceneIndex> indexes = new LinkedList<LuceneIndex>();

        //Create all the indexes
        for(int i=0;i<numberOfIndexWriters;i++){
            NewLuceneIndexRequest indexRequest = new NewLuceneIndexRequest("index_" + i + "/");
            LuceneIndex index = actor.createIndex(indexRequest);
            indexes.add(index);
        }

        final Props createProps = Props.create(IndexDriver.class);
        final TestActorRef<IndexDriver> createPropsRef = TestActorRef.create(system, createProps, "test-index-write");
        final IndexDriver indexDriverActor = createPropsRef.underlyingActor();

        //Tell the Driver how many updaters we want
        indexDriverActor.onReceive(numberOfIndexWriters);

        //Create the indexes writers
        for(LuceneIndex index : indexes){
            indexDriverActor.onReceive(index);
        }

        //Create a bunch of random documents for indexing.
        System.out.println("Index start:  " + new DateTime().toString("hh:mm:ss:SSS"));
        for(int i = 0; i < 100001; i++){
            indexDriverActor.onReceive(createDocument(i));
        }
        System.out.println("Index end:    " + new DateTime().toString("hh:mm:ss:SSS"));

        Thread.sleep(8000);

        //Flush all the docs out to disk for the indexes.
        IndexUpdaterMessages.FlushIndex flush = new IndexUpdaterMessages().new FlushIndex();
        for(int i = 0; i < numberOfIndexWriters; i++) {
            indexDriverActor.onReceive(flush);
        }

        Thread.sleep(1000);


        //try to read random doc id's.  Don't know why the broadcast router in the driver just keeps
        //spamming all of the readers.  It will keep reading until the test exits.
        indexDriverActor.onReceive(new String("FOO"));
        System.out.println("Index start:  " + new DateTime().toString("hh:mm:ss:SSS"));
        IndexReaderMessages.LuceneQuery query = new IndexReaderMessages().new LuceneQuery();
        SearchField sf = new SearchField();
        sf.setFieldId("id");
        sf.setValue("100");
        query.addSearchField(sf);

        indexDriverActor.onReceive(query);

        query = new IndexReaderMessages().new LuceneQuery();
        sf = new SearchField();
        sf.setFieldId("id");
        sf.setValue("1");
        query.addSearchField(sf);

        indexDriverActor.onReceive(query);

        query = new IndexReaderMessages().new LuceneQuery();
        sf = new SearchField();
        sf.setFieldId("id");
        sf.setValue("2");
        query.addSearchField(sf);

        indexDriverActor.onReceive(query);

        query = new IndexReaderMessages().new LuceneQuery();
        sf = new SearchField();
        sf.setFieldId("id");
        sf.setValue("3");
        query.addSearchField(sf);

        indexDriverActor.onReceive(query);

        query = new IndexReaderMessages().new LuceneQuery();
        sf = new SearchField();
        sf.setFieldId("id");
        sf.setValue("4");
        query.addSearchField(sf);

        indexDriverActor.onReceive(query);

        System.out.println("Index end:    " + new DateTime().toString("hh:mm:ss:SSS"));

        Thread.sleep(3000);

//        Assert.assertTrue(indexDriverActor.recordIndexLocation("./testGetIndexListFromDisk"));

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
        sf.setType(SearchField.TypeEnum.text.toString());
        sf.setFieldId("id");
//        sf.setValue("" + new Random().nextInt());
        sf.setValue(""+id);
        sf.setStore(true);

        return sf;
    }

    private SearchField getField(){
        SearchField sf = new SearchField();
        sf.setType(SearchField.TypeEnum.text.toString());
        sf.setFieldId("field");
        sf.setValue("foo");
        sf.setStore(true);

        return sf;
    }

}
