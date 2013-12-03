package org.sawzall.message.index.response;

import org.sawzall.message.index.request.IndexCreateRequest;

/**
 * Created with IntelliJ IDEA.
 * User: mdonnelly
 * Date: 12/2/13
 * Time: 10:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexCreateResponse {
    IndexCreateRequest request;
    boolean processed;

    public IndexCreateRequest getRequest() {
        return request;
    }

    public void setRequest(IndexCreateRequest request) {
        this.request = request;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
