package org.sawzall.message;

import org.joda.time.DateTime;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mdonnelly
 * Date: 12/2/13
 * Time: 9:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexRequest {
    Map<String, String> searchFieldsAndTerms;
    DateTime date;

    public Map<String, String> getSearchFieldsAndTerms() {return searchFieldsAndTerms;}
    public void setSearchFieldsAndTerms(Map<String, String> searchFieldsAndTerms) {this.searchFieldsAndTerms = searchFieldsAndTerms;}
    public void addSearchFieldAndTerm(String field, String term){ searchFieldsAndTerms.put(field, term); }

    public DateTime getDate() {return date;}
    public void setDate(DateTime date) {this.date = date;}
}
