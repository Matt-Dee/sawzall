package org.sawzall.message.index;

import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.sawzall.message.index.request.NewLuceneIndexRequest;

/**
 * Created with IntelliJ IDEA.
 * User: mdonnelly
 * Date: 12/2/13
 * Time: 10:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class LuceneIndex {
    boolean processed;
    IndexWriterConfig luceneConfig;
    Directory indexLocation;
    int numberCreationTries;
    String physicalLocation;

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public IndexWriterConfig getLuceneConfig() {
        return luceneConfig;
    }

    public void setLuceneConfig(IndexWriterConfig luceneConfig) {
        this.luceneConfig = luceneConfig;
    }

    public Directory getIndexLocation() {
        return indexLocation;
    }

    public void setIndexLocation(Directory indexLocation) {
        this.indexLocation = indexLocation;
    }

    public int getNumberCreationTries() {
        return numberCreationTries;
    }

    public void setNumberCreationTries(int numberCreationTries) {
        this.numberCreationTries = numberCreationTries;
    }

    public String getPhysicalLocation() {
        return physicalLocation;
    }

    public void setPhysicalLocation(String physicalLocation) {
        this.physicalLocation = physicalLocation;
    }
}
