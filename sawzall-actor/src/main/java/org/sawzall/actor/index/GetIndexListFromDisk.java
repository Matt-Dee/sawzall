package org.sawzall.actor.index;

import akka.actor.UntypedActor;
import org.sawzall.message.index.response.IndexSet;
import org.sawzall.message.index.response.NewLuceneIndexResponse;

import java.io.*;

/**
 * User: mdonnelly
 * Date: 12/5/13
 * Time: 8:36 PM
 */
public class GetIndexListFromDisk extends UntypedActor {
    IndexSet set = new IndexSet();

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof  String){
            getSender().tell(this.getListAtStartup(message.toString()), this.getSelf());
        }
//        else if(message instanceof NewLuceneIndexResponse){
//            set.add(((NewLuceneIndexResponse) message).getPhysicalLocation());
//            getSender().tell(set, getSelf());
//        }
//
    }

    //Should only be called at startup
    public IndexSet getListAtStartup(String filename){

        BufferedReader fr = null;
        try {
            fr = new BufferedReader(new FileReader(filename));

            String s;

            while ((s = fr.readLine()) != null) {
                set.add(s);
            }

            fr.close();
        } catch (Exception e) {
            if (fr != null) {
                try {
                    fr.close();
                } catch (Exception ee) {
                }
            }
        }

        return null;

    }
}
