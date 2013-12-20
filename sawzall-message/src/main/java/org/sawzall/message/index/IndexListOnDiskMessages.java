package org.sawzall.message.index;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * User: mdonnelly
 * Date: 12/19/13
 * Time: 9:03 PM
 */
public class IndexListOnDiskMessages {
    public class IndexSet {

        Set<String> set;

        public Set<String> getSet() {
            return set;
        }

        public void setSet(Set<String> set) {
            this.set = set;
        }

        public Set<String> add(String s){
            if(set == null){
                set = new LinkedHashSet<String>();
            }

            this.set.add(s);
            return set;
        }
    }
}
