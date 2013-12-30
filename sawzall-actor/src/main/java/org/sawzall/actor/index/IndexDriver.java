package org.sawzall.actor.index;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.BroadcastRouter;
import akka.routing.RoundRobinRouter;
import org.sawzall.message.index.IndexReaderMessages;
import org.sawzall.message.index.IndexUpdaterMessages;
import org.sawzall.message.index.lucene.DocumentToIndex;
import org.sawzall.message.index.lucene.LuceneIndex;

import java.util.LinkedList;
import java.util.List;

/**
 * User: mdonnelly
 * Date: 12/10/13
 * Time: 9:23 AM
 *
 * 1.) Tell the updater driver how many updater threads we need.
 * 2.) Pass in all the updater indexes.
 * 3.) We can start processing documents
 *
 */
public class IndexDriver extends UntypedActor {

    private int numberWriters = 10;
    private ActorRef indexUpdateRouter = null;
    private ActorRef indexReaderRouter = null;

    List<LuceneIndex> indexes;

    public IndexDriver(){
        indexes = new LinkedList<LuceneIndex>();
    }

    @Override
    public void onReceive(Object message) throws Exception {
        try{
        if(message instanceof Integer){
            this.instantiateIndexWriters((Integer) message);
        }else if(message instanceof String){
            this.instantiateIndexReaders(10);
        }else if(message instanceof LuceneIndex){
            indexUpdateRouter.tell(message, getSelf());
            indexes.add((LuceneIndex) message);
        }else if( ( message instanceof DocumentToIndex ) && ( indexUpdateRouter != null )){
            indexUpdateRouter.tell(message, getSelf());
        }else if( message instanceof IndexReaderMessages.LuceneQuery ){
            indexReaderRouter.tell(message, getSelf());
        }else if( ( message instanceof IndexUpdaterMessages.FlushIndex ) || ( message instanceof  IndexUpdaterMessages.CloseIndex ) ){
            indexUpdateRouter.tell(message, getSelf());
        }else{
            getSender().tell(message, getSelf());
        }
        }catch(Exception e){e.printStackTrace();}
    }

    private void instantiateIndexWriters(int numWriters){
        this.numberWriters = numWriters;

        indexUpdateRouter = this.getContext().actorOf(Props.create(IndexUpdater.class).withRouter(new RoundRobinRouter(numberWriters)),"index-updater");
    }

    private void instantiateIndexReaders(int numReaders){
//        this.numReaders = numReaders;

        //indexReaderRouter = this.getContext().actorOf(Props.create(IndexUpdater.class).withRouter(new BroadcastRouter.create(getReaderActors())),"index-reader");

//        List<ActorRef> readers = getReaderActors();
        indexReaderRouter = this.getContext().actorOf(Props.empty().withRouter(new BroadcastRouter(getReaderActors())));
    }

    private List<String> getReaderActors(){
        List<ActorRef> refList = new LinkedList<ActorRef>();
        List<String> list = new LinkedList<String>();

        int count = 0;
        for (LuceneIndex index : indexes) {
            ActorRef ref = this.getContext().actorOf(Props.create(ClassicIndexReaderPool.class), "reader_" + count++);
            ref.tell(index, getSelf());
            ref.tell(numberWriters, getSelf());
            refList.add(ref);
        }


        for (ActorRef ref : refList) {

            list.add(ref.path().toString());
        }

        return list;
    }


}

