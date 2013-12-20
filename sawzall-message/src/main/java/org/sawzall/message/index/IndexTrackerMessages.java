package org.sawzall.message.index;

/**
 * User: mdonnelly
 * Date: 12/19/13
 * Time: 8:25 PM
 */
public class IndexTrackerMessages {
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
}
