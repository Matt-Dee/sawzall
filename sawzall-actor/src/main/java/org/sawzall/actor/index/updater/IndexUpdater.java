package org.sawzall.actor.index.updater;

import akka.actor.UntypedActor;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.sawzall.message.index.IndexUpdaterMessages;
import org.sawzall.message.index.lucene.DocumentToIndex;
import org.sawzall.message.index.lucene.SearchField;
import org.sawzall.message.index.lucene.LuceneIndex;

import java.io.IOException;

/**
 * User: mdonnelly
 * Date: 12/9/13
 * Time: 8:18 PM
 */
public class IndexUpdater extends UntypedActor {

    IndexWriterConfig luceneConfig = null;
    Directory indexLocation = null;

    // 5 million documents
    Integer maxDocs = 3000000;

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
       }else if(message instanceof IndexUpdaterMessages.MaxDocsToIndex) {
           maxDocs = ((IndexUpdaterMessages.MaxDocsToIndex)message).getNumDocsToIndex();
       }else if(message instanceof LuceneIndex){
            this.luceneConfig = ((LuceneIndex)message).getLuceneConfig();
            this.indexLocation = ((LuceneIndex)message).getIndexLocation();
       }else if(message instanceof IndexUpdaterMessages.FlushIndex){
            flushIndex();
       }else if(message instanceof IndexUpdaterMessages.ShutDown){
            shutDownWriter();
       }else{
            getSender().tell(message, getSelf());
       }

       //todo send message to remove this updater from the list of updaters.
    }

    public void addDoc(DocumentToIndex s) throws IOException {

        if( w == null ){
            w = new IndexWriter(indexLocation, luceneConfig.clone());
        }

        if(w.numDocs() >= maxDocs){
            shutDownWriter();
        }

        Document doc = new Document();

        for(SearchField field : s.getSearchField()){
            doc.add(field.getField());
        }
        w.addDocument(doc);

//        System.out.println("Doc Written = " + s.toString());
    }

    public void flushIndex() throws IOException, Exception{
        w.commit();

        if(w.numDocs() >= maxDocs){
            shutDownWriter();
        }
    }

    public void shutDownWriter() throws IOException{
        w.close();
    }
}
