package org.sawzall.actor.index;

import akka.actor.UntypedActor;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.sawzall.message.index.IndexUpdaterMessages;
import org.sawzall.message.index.request.DocumentToIndex;
import org.sawzall.message.index.request.SearchField;
import org.sawzall.message.index.LuceneIndex;

import java.io.IOException;

/**
 * User: mdonnelly
 * Date: 12/9/13
 * Time: 8:18 PM
 */
public class IndexUpdater extends UntypedActor {

    IndexWriterConfig luceneConfig = null;
    Directory indexLocation = null;

    IndexWriter w = null;

    @Override
    public void onReceive(Object message) throws Exception {

        //Add the document to the index.  If this updater has
        //not been initialized, put the document back on the bus
        //for another updater to process.
        if(message instanceof DocumentToIndex){
            if(luceneConfig == null || indexLocation == null){
                getSender().tell(message, getSelf());
                return;
            }
            addDoc((DocumentToIndex)message);
        }else if(message instanceof LuceneIndex){
            this.luceneConfig = ((LuceneIndex)message).getLuceneConfig();
            this.indexLocation = ((LuceneIndex)message).getIndexLocation();
       }else if(message instanceof IndexUpdaterMessages.FlushIndex){
            flushIndex();
       }else if(message instanceof IndexUpdaterMessages.CloseIndex){
            closeIndex();
       }else{
            getSender().tell(message, getSelf());
       }
    }

    public void addDoc(DocumentToIndex s) throws IOException {

        if( w == null ){
            w = new IndexWriter(indexLocation, luceneConfig.clone());
        }

        Document doc = new Document();

        for(SearchField field : s.getSearchField()){
            doc.add(field.getField());
        }
        w.addDocument(doc);
        System.out.println("Doc Written = " + s.toString());
    }

    public void flushIndex() throws IOException{
        w.commit();
    }

    public void closeIndex() throws IOException{
        w.close();
    }

}
