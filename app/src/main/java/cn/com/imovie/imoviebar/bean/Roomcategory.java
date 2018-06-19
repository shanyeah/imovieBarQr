package cn.com.imovie.imoviebar.bean;

/**
 * Created by glin on 1/26/16.
 */
public class Roomcategory {
    private int id;
    private String name;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }


    public Roomcategory() {

    }

    public Roomcategory(int id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
