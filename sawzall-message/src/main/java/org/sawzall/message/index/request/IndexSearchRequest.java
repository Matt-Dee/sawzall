package org.sawzall.message.index.request;

import org.joda.time.DateTime;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mdonnelly
 * Date: 12/2/13
 * Time: 8:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexSearchRequest {
    Map<String, String> searchFieldsAndTerms;
    DateTime date;
    Long value;

    public Map<String, String> getSearchFieldsAndTerms() {return searchFieldsAndTerms;}
    public void setSearchFieldsAndTerms(Map<String, String> searchFieldsAndTerms) {this.searchFieldsAndTerms = searchFieldsAndTerms;}
    public void addSearchFieldAndTerm(String field, String term){ searchFieldsAndTerms.put(field, term); }

    public DateTime getDate() {return date;}
    public void setDate(DateTime date) {this.date = date;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndexSearchRequest that = (IndexSearchRequest) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (searchFieldsAndTerms != null ? !searchFieldsAndTerms.equals(that.searchFieldsAndTerms) : that.searchFieldsAndTerms != null)
            return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = searchFieldsAndTerms != null ? searchFieldsAndTerms.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
