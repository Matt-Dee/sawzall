package org.sawzall.actor.index.reader.classic;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;
import org.sawzall.message.index.IndexReaderMessages;
import org.sawzall.message.index.lucene.LuceneIndex;

import java.util.LinkedList;
import java.util.List;

/**
 * User: mdonnelly
 * Date: 12/26/13
 * Time: 11:31 AM
 */
public class ClassicIndexReaderPool extends UntypedActor {

    Integer numberReaders = 10;
    private ActorRef indexReaderRouter = null;


    public void onReceive(Object message){

        if(message instanceof Integer){
            numberReaders = ((Integer)message).intValue();
        }
        else if(message instanceof LuceneIndex){
            createPool( (LuceneIndex) message );
        }else if( message instanceof IndexReaderMessages.LuceneQuery ){
            indexReaderRouter.tell(message, getSelf());
        }

    }

    private void createPool(LuceneIndex index){
        List<ActorRef> refList = new LinkedList<ActorRef>();
        List<String> list = new LinkedList<String>();

        for(int i = 0; i < numberReaders; i++){
            refList.add(this.getContext().actorOf(Props.create(ClassicIndexReader.class, index.getPhysicalLocation()), "_reader_" + i));
        }

        for(ActorRef ref : refList){

            list.add(ref.path().toString());
        }

        indexReaderRouter = this.getContext().actorOf(Props.empty().withRouter(new RoundRobinRouter(list)));
    }
}
