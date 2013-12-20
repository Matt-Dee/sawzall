package org.sawzall.message.index.lucene;

import org.apache.lucene.document.*;

/**
 * Created with IntelliJ IDEA.
 * User: mdonnelly
 * Date: 12/3/13
 * Time: 10:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchField {
    String type;
    String fieldId;
    String value;
    boolean store;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String field) {
        this.fieldId = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isStore() {
        return store;
    }

    public void setStore(boolean store) {
        this.store = store;
    }

    public Field getField(){
        TypeEnum typeEnum = TypeEnum.fromString(this.type);
        Field.Store store;

        if(this.store){
            store = Field.Store.YES;
        }else{
            store = Field.Store.NO;
        }


        if(typeEnum == TypeEnum.text){
            return new TextField(fieldId, value, store);
        }else if(typeEnum == TypeEnum.string){
            return new StringField(fieldId, value, store);
        }else if(typeEnum == TypeEnum.longField){
            return new LongField(fieldId, Long.parseLong(value), store);
        }

        return null;
    }


    public enum TypeEnum{
        text("text"),
        string("string"),
        longField("long");

        String val;

        private TypeEnum(String s){
           val = s;
        }

        public String toString(){
            return super.toString();
        }

        static public TypeEnum fromString(String s){
            for(TypeEnum val : TypeEnum.values()){
                val.toString().equalsIgnoreCase(s);
                return val;
            }
            return null;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchField that = (SearchField) o;

        if (fieldId != null ? !fieldId.equals(that.fieldId) : that.fieldId != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (fieldId != null ? fieldId.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
