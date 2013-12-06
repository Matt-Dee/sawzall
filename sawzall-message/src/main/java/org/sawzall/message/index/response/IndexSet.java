package org.sawzall.message.index.response;

import java.util.Set;

/**
 * User: mdonnelly
 * Date: 12/5/13
 * Time: 8:38 PM
 */
public class IndexSet {

    Set<String> set;

    public Set<String> getSet() {
        return set;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    public Set<String> add(String s){
        this.set.add(s);
        return set;
    }
}
