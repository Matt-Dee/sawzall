package org.sawzall.message.index;

import org.sawzall.message.index.lucene.SearchField;

import java.util.LinkedList;
import java.util.List;

/**
 * User: mdonnelly
 * Date: 12/18/13
 * Time: 7:51 PM
 */
public class IndexReaderMessages {

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
}
