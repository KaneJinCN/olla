package cn.kanejin.olla.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kane Jin
 */
public abstract class UpdateForm implements Serializable {

    private Long id;

    public UpdateForm(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    private List<String> updateFileds = new ArrayList<String>();

    public boolean needUpdate(String filedName) {
        return updateFileds.contains(filedName);
    }

    protected void addUpdateField(String filedName) {
        updateFileds.add(filedName);
    }
}
