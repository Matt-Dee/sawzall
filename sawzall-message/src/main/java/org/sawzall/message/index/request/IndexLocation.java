package org.sawzall.message.index.request;

/**
 * User: mdonnelly
 * Date: 12/5/13
 * Time: 6:31 AM
 */
public class IndexLocation {
    String physicalLocation;

    public IndexLocation(String physicalLocation) {
        this.physicalLocation = physicalLocation;
    }

    public String getPhysicalLocation() {
        return physicalLocation;
    }

    public void setPhysicalLocation(String physicalLocation) {
        this.physicalLocation = physicalLocation;
    }
}
