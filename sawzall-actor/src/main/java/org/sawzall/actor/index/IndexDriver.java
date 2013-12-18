package org.sawzall.actor.index;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.BroadcastRouter;
import akka.routing.RoundRobinRouter;
import org.sawzall.message.index.IndexUpdaterMessages;
import org.sawzall.message.index.request.DocumentToIndex;
import org.sawzall.message.index.request.LuceneQuery;
import org.sawzall.message.index.response.LuceneIndex;

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
        }else if( message instanceof LuceneQuery ){
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
        for(int i = 0; i < numberWriters; i++){
            for(LuceneIndex index : indexes){
               refList.add(this.getContext().actorOf(Props.create(ClassicIndexReader.class, index.getPhysicalLocation()), "reader_" + count++));
            }
        }

        for(ActorRef ref : refList){
            list.add(ref.path().toString());
        }
        return list;
    }


}

