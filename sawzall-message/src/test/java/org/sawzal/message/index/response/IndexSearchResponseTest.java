package org.sawzal.message.index.response;

import org.sawzall.message.index.lucene.IndexSearchRequest;

/**
 * Created with IntelliJ IDEA.
 * User: mdonnelly
 * Date: 12/2/13
 * Time: 10:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexSearchResponseTest {
    IndexSearchRequest request;
    boolean processed;

    public IndexSearchRequest getRequest() {
        return request;
    }

    public void setRequest(IndexSearchRequest request) {
        this.request = request;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
