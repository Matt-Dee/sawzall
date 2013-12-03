package org.sawzall.message.index.response;

import org.sawzall.message.index.request.NewIndexRequest;

/**
 * Created with IntelliJ IDEA.
 * User: mdonnelly
 * Date: 12/2/13
 * Time: 10:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class NewIndexResponse {
    NewIndexRequest request;
    boolean processed;

    public NewIndexRequest getRequest() {
        return request;
    }

    public void setRequest(NewIndexRequest request) {
        this.request = request;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
