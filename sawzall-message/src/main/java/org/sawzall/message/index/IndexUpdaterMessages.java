package org.sawzall.message.index;

import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;

/**
 * User: mdonnelly
 * Date: 12/17/13
 * Time: 9:11 PM
 */
public class IndexUpdaterMessages {

    public class FlushIndex{}
    public class CloseIndex{}

    public class LuceneIndex {
        boolean processed;
        IndexWriterConfig luceneConfig;
        Directory indexLocation;
        int numberCreationTries;
        String physicalLocation;

        public boolean isProcessed() {return processed;}
        public void setProcessed(boolean processed) {this.processed = processed;}

        public IndexWriterConfig getLuceneConfig() {return luceneConfig;}
        public void setLuceneConfig(IndexWriterConfig luceneConfig) {this.luceneConfig = luceneConfig;}

        public Directory getIndexLocation() {return indexLocation;}
        public void setIndexLocation(Directory indexLocation) {this.indexLocation = indexLocation;}

        public int getNumberCreationTries() {return numberCreationTries;}
        public void setNumberCreationTries(int numberCreationTries) {this.numberCreationTries = numberCreationTries;}

        public String getPhysicalLocation() {return physicalLocation;}
        public void setPhysicalLocation(String physicalLocation) {this.physicalLocation = physicalLocation;}
    }

}
