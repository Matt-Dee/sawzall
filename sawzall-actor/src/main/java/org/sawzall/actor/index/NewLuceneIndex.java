package org.sawzall.actor.index;

import akka.actor.UntypedActor;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.sawzall.message.index.request.NewLuceneIndexRequest;
import org.sawzall.message.index.response.NewLuceneIndexResponse;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: mdonnelly
 * Date: 12/4/13
 * Time: 6:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class NewLuceneIndex extends UntypedActor{

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof NewLuceneIndexRequest){
            getSender().tell(createIndex((NewLuceneIndexRequest)message),getSelf());
        }
    }

    public NewLuceneIndexResponse createIndex(NewLuceneIndexRequest request){
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);
        NewLuceneIndexResponse response = new NewLuceneIndexResponse();
        Directory index;
        IndexWriterConfig config;

        try{
            index = new NIOFSDirectory(new File(request.getLocation()));
            config = new IndexWriterConfig(Version.LUCENE_46, analyzer);
            response.setProcessed(true);
        }catch(Exception e){
            index = null;
            config = null;
            response.setProcessed(false);
        }


        response.setRequest(request);
        response.setLuceneConfig(config);
        response.setIndexLocation(index);

        return response;
    }
}
