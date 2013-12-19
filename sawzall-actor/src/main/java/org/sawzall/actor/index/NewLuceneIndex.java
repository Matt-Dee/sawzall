package org.sawzall.actor.index;

import akka.actor.UntypedActor;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import org.sawzall.message.index.request.NewLuceneIndexRequest;
import org.sawzall.message.index.LuceneIndex;

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

    public LuceneIndex createIndex(NewLuceneIndexRequest request){
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);
        LuceneIndex response = new LuceneIndex();
        Directory index;
        IndexWriterConfig config;

        try{
            File f = new File(request.getLocation());
            index = new NIOFSDirectory(new File(request.getLocation()));
            config = new IndexWriterConfig(Version.LUCENE_46, analyzer);

            response.setProcessed(true);
            response.setPhysicalLocation(f.getAbsolutePath());
        }catch(Exception e){
            index = null;
            config = null;
            response.setProcessed(false);
        }

        response.setLuceneConfig(config);
        response.setIndexLocation(index);

        return response;
    }
}
