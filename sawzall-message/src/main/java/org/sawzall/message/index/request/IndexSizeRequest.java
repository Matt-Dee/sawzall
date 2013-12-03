package org.sawzall.message.index.request;

/**
 * Created with IntelliJ IDEA.
 * User: mdonnelly
 * Date: 12/2/13
 * Time: 10:09 PM
 *
 * Used to communicate the size of the index.
 */
public class IndexSizeRequest {
    int size;

    public int getSize(){return size;}
    public void setSize(int size){this.size = size;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndexSizeRequest that = (IndexSizeRequest) o;

        if (size != that.size) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return size;
    }
}
