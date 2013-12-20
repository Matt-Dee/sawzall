package org.sawzall.message.index;

import org.sawzall.message.index.lucene.DocumentToIndex;

/**
 * User: mdonnelly
 * Date: 12/19/13
 * Time: 8:39 PM
 */
public class NewLuceneIndexMessages {
    public class CreateIndex {
        String location;

        public CreateIndex(String location){
            this.location = location;
        }

        public String getLocation() {return location;}
        public void setLocation(String location) {this.location = location;}

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CreateIndex that = (CreateIndex) o;

            if (location != null ? !location.equals(that.location) : that.location != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return location != null ? location.hashCode() : 0;
        }
    }

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
}
