package org.sawzall.actor.index;

import akka.actor.UntypedActor;
import org.sawzall.message.index.response.NewLuceneIndexResponse;

import java.io.FileWriter;

/**
 * User: mdonnelly
 * Date: 12/4/13
 * Time: 10:07 PM
 */
public class IndexTracker extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof NewLuceneIndexResponse){
            recordIndexLocation((NewLuceneIndexResponse)message);
            getSender().tell("",getSelf());
        }
    }

    public void recordIndexLocation(NewLuceneIndexResponse response){
        String filename= "lucene-index-locations.txt";

        try{
            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            fw.write(response.getPhysicalLocation() + "\n");//appends the string to the file
            fw.close();
        }catch(Exception e){

        }
    }

}
