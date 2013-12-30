package org.sawzall.message.index;

import org.sawzall.message.index.exceptions.InvalidIndexSizeException;

/**
 * User: mdonnelly
 * Date: 12/17/13
 * Time: 9:11 PM
 */
public class IndexUpdaterMessages {

    public class FlushIndex{}
    public class CloseIndex{}
    public class ShutDown{}

    public class MaxDocsToIndex{
        int numDocsToIndex;

        public MaxDocsToIndex(int numDocs){
            this.numDocsToIndex = numDocs;
        }

        public int getNumDocsToIndex() {
            return numDocsToIndex;
        }

        public void setNumDocsToIndex(int numDocsToIndex) throws InvalidIndexSizeException{
            if(numDocsToIndex <= 0){
                throw new InvalidIndexSizeException("Index size cannot be " + numDocsToIndex);
            }
            this.numDocsToIndex = numDocsToIndex;
        }
    }

}
