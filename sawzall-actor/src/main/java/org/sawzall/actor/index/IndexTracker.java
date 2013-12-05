package org.sawzall.actor.index;

import akka.actor.UntypedActor;
import org.sawzall.message.index.response.NewLuceneIndexResponse;

import java.io.FileWriter;

/**
 * User: mdonnelly
 * Date: 12/4/13
 * Time: 10:07 PM
 *
 * This just needs to be a single thread running.
 */
public class IndexTracker extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof NewLuceneIndexResponse){
            recordIndexLocation((NewLuceneIndexResponse)message);
//            getSender().tell("",getSelf());

            // need to recover if file write fails.  probably check for a string wrapped message type to just concat to list of lucene
            // indexes.  We don't want duplicate indexes either.
        }
    }

    public void recordIndexLocation(NewLuceneIndexResponse response){
        String filename= "lucene-index-locations.txt";

        try{
            FileWriter fw = new FileWriter(filename,true);
            fw.write(response.getPhysicalLocation() + "\n");
            fw.close();
        }catch(Exception e){

        }
    }

}
