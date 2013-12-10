package org.sawzall.actor.index;

import akka.actor.UntypedActor;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.sawzall.message.index.request.IndexCreateRequest;
import org.sawzall.message.index.request.SearchField;
import org.sawzall.message.index.response.NewLuceneIndexResponse;

import java.io.IOException;

/**
 * User: mdonnelly
 * Date: 12/9/13
 * Time: 8:18 PM
 */
public class IndexWriter extends UntypedActor {

    IndexWriterConfig luceneConfig;
    Directory indexLocation;
    org.apache.lucene.index.IndexWriter w;

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof NewLuceneIndexResponse){
            this.luceneConfig = ((NewLuceneIndexResponse)message).getLuceneConfig();
            this.indexLocation = ((NewLuceneIndexResponse)message).getIndexLocation();
            org.apache.lucene.index.IndexWriter w = new org.apache.lucene.index.IndexWriter(indexLocation, luceneConfig);
        }else if(message instanceof IndexCreateRequest){
            addDoc((IndexCreateRequest)message);
        }
    }

    public void addDoc(IndexCreateRequest s) throws IOException {
        Document doc = new Document();

        for(SearchField field : s.getSearchField()){
            doc.add(field.getField());
        }
        w.addDocument(doc);
    }
}
