package org.sawzall.message.index.request;

import java.util.LinkedList;
import java.util.List;

/**
 * User: mdonnelly
 * Date: 12/12/13
 * Time: 8:02 PM
 */
public class LuceneQuery {
    List<SearchField> searchField;

    public List<SearchField> getSearchField() {return searchField;}
    public void setSearchFields(List<SearchField> searchField) {this.searchField = searchField;}

    public void addSearchField(SearchField searchField) {
        if(this.searchField == null){
            this.searchField = new LinkedList<SearchField>();
        }
        this.searchField.add(searchField);
    }

}
