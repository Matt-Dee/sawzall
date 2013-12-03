package org.sawzall.message.index.request;

/**
 * Created with IntelliJ IDEA.
 * User: mdonnelly
 * Date: 12/2/13
 * Time: 10:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class NewIndexRequest {
    String location;

    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewIndexRequest that = (NewIndexRequest) o;

        if (location != null ? !location.equals(that.location) : that.location != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return location != null ? location.hashCode() : 0;
    }
}
