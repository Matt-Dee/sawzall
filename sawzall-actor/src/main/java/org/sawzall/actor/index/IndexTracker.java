package org.sawzall.actor.index;

import akka.actor.UntypedActor;
import org.sawzall.message.index.request.IndexLocation;
import org.sawzall.message.index.LuceneIndex;

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
        String fileLocation = null;

        if(message instanceof LuceneIndex){
            fileLocation = ((LuceneIndex)message).getPhysicalLocation();
        }else if(message instanceof IndexLocation){
            fileLocation = ((IndexLocation)message).getPhysicalLocation();
        }

        if( !recordIndexLocation(fileLocation) ){
            getSender().tell(new IndexLocation(fileLocation),getSelf());
        }
    }

    public boolean recordIndexLocation(String fileLocation){
        String filename= "./lucene-index-locations.txt";

        try{
            FileWriter fw = new FileWriter(filename,true);
            fw.write(fileLocation + "\n");
            fw.close();
        }catch(Exception e){
            return false;
        }
        return true;
    }

}
