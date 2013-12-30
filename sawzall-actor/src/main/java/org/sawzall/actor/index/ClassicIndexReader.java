package org.sawzall.actor.index;

import akka.actor.UntypedActor;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import org.sawzall.message.index.IndexReaderMessages;
import org.sawzall.message.index.lucene.SearchField;
import org.sawzall.message.index.lucene.LuceneIndex;

import java.io.File;
import java.io.IOException;

/**
 * User: mdonnelly
 * Date: 12/12/13
 * Time: 8:18 PM
 */
public class ClassicIndexReader extends UntypedActor {
    //    IndexWriterConfig luceneConfig = null;
    Directory indexLocation = null;


    public ClassicIndexReader(String constructor) throws Exception{
        indexLocation = new NIOFSDirectory(new File(constructor));
    }

    @Override
    public void onReceive(Object message) throws Exception {

        if(message instanceof LuceneIndex){
            this.indexLocation = ((LuceneIndex)message).getIndexLocation();
            getSender().tell("", getSelf());
        }else if (message instanceof IndexReaderMessages.LuceneQuery){
            try{
            query((IndexReaderMessages.LuceneQuery)message);
            }catch (Exception e){e.printStackTrace();}
//            getSender().tell(new String(""), getSelf());
        }
    }

    public void setIndexLocation(Directory location){
        this.indexLocation = location;
    }

    public void query(IndexReaderMessages.LuceneQuery query) throws IOException, ParseException {

        StringBuilder querystr = new StringBuilder();
        DirectoryReader reader = null;

        for (SearchField sf : query.getSearchField()) {
            querystr.append(sf.getFieldId()).append(":").append(sf.getValue()).append(" AND ");
        }

        querystr.delete(querystr.lastIndexOf(" AND "), querystr.length() - 1);

//        System.out.println(querystr);

        WhitespaceAnalyzer analyzer = new WhitespaceAnalyzer(Version.LUCENE_46);

        // the "id" arg specifies the default field to use
        // when no field is explicitly specified in the query.
        try {
            QueryParser qp = new QueryParser(Version.LUCENE_46, "id", analyzer);

            Query q = new QueryParser(Version.LUCENE_46, "id", analyzer).parse(querystr.toString().trim());

            // 3. search
            int hitsPerPage = 10;

            reader = DirectoryReader.open(indexLocation);
            IndexSearcher searcher = new IndexSearcher(reader);
            TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
            searcher.search(q, collector);
            ScoreDoc[] hits = collector.topDocs().scoreDocs;

            // 4. display results
            for (int i = 0; i < hits.length; ++i) {
                int docId = hits[i].doc;
                Document d = searcher.doc(docId);
                System.out.println(d.get("id") + "\t\t" + d.get("field") + "\t" + indexLocation.toString());
            }

            // reader can only be closed when there
            // is no need to access the documents any more.
 //           reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

}
