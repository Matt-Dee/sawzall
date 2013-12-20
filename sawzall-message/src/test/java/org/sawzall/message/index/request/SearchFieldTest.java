package org.sawzall.message.index.request;

import org.junit.Test;
import org.sawzall.message.index.lucene.SearchField;

/**
 * User: mdonnelly
 * Date: 12/9/13
 * Time: 8:58 PM
 */
public class SearchFieldTest {

    @Test
    public void testSearchFieldTypeEnum(){
        SearchField.TypeEnum  typeEnum = SearchField.TypeEnum.fromString("test");

        System.out.println(typeEnum.toString());
    }
}