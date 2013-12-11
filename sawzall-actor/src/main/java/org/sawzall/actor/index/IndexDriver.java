package org.sawzall.actor.index;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;
import org.sawzall.message.index.request.DocumentToIndex;
import org.sawzall.message.index.response.LuceneIndex;

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

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof Integer){
            this.instantiateIndex((Integer) message);
        }else if(message instanceof LuceneIndex){
            indexUpdateRouter.tell(message, getSelf());
        }else if( ( message instanceof DocumentToIndex ) && ( indexUpdateRouter != null )){
            indexUpdateRouter.tell(message, getSelf());
        }else{
            getSender().tell(message, getSelf());
        }
    }

    private void instantiateIndex(int numWriters){
        this.numberWriters = numWriters;
        indexUpdateRouter = this.getContext().actorOf(Props.create(IndexUpdater.class).withRouter(new RoundRobinRouter(numberWriters)),"index-updater");
    }

}

