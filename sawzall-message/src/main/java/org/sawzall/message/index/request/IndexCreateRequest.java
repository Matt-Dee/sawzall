package org.sawzall.message.index.request;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mdonnelly
 * Date: 12/2/13
 * Time: 9:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexCreateRequest {
    List<SearchField> searchField;
    DateTime date;
    Long value;

    public List<SearchField> getSearchField() {return searchField;}
    public void setSearchField(List<SearchField> searchField) {this.searchField = searchField;}

//    public DateTime getDate() {return date;}
//    public void setDate(DateTime date) {this.date = date;}

//    public Long getValue() {return value;}
//    public void setValue(Long value) {this.value = value;}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndexCreateRequest that = (IndexCreateRequest) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (searchField != null ? !searchField.equals(that.searchField) : that.searchField != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = searchField != null ? searchField.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
