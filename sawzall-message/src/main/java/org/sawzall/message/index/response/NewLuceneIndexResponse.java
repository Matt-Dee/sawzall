package org.sawzall.message.index.response;

import org.sawzall.message.index.request.NewLuceneIndexRequest;

/**
 * Created with IntelliJ IDEA.
 * User: mdonnelly
 * Date: 12/2/13
 * Time: 10:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class NewLuceneIndexResponse {
    NewLuceneIndexRequest request;
    boolean processed;

    public NewLuceneIndexRequest getRequest() {
        return request;
    }

    public void setRequest(NewLuceneIndexRequest request) {
        this.request = request;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
