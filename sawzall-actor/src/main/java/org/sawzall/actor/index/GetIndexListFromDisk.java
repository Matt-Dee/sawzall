package org.sawzall.actor.index;

import akka.actor.UntypedActor;
import org.sawzall.message.index.request.NewLuceneIndexRequest;
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
            IndexSet set = this.getListAtStartup(message.toString());
            for(String s : set.getSet()){
                getSender().tell( new NewLuceneIndexRequest(s), this.getSelf());
            }
        }
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

            return set;
        } catch (Exception e) {
            e.printStackTrace();
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
