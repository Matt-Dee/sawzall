package org.sawzall.message.index.response;

import org.sawzall.message.index.request.DocumentToIndex;

/**
 * Created with IntelliJ IDEA.
 * User: mdonnelly
 * Date: 12/2/13
 * Time: 10:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexCreateResponse {
    DocumentToIndex request;
    boolean processed;

    public DocumentToIndex getRequest() {
        return request;
    }

    public void setRequest(DocumentToIndex request) {
        this.request = request;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
