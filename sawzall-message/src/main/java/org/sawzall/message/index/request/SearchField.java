package org.sawzall.message.index.request;

/**
 * Created with IntelliJ IDEA.
 * User: mdonnelly
 * Date: 12/3/13
 * Time: 10:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchField {
    String type;
    String field;
    String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchField that = (SearchField) o;

        if (field != null ? !field.equals(that.field) : that.field != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (field != null ? field.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
