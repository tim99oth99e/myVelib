package src;

public class Bicycle {
    protected Integer Id;
    protected String typeOfBicycle;

    //Constructors
    public Bicycle(Integer id, String typeOfBicycle) {
        Id = id;
        this.typeOfBicycle = typeOfBicycle;
    }

    //Getters and Setters
    public Integer getId() {
        return Id;
    }
    public void setId(Integer id) {
        Id = id;
    }
    public String getTypeOfBicycle() {
        return typeOfBicycle;
    }
    public void setTypeOfBicycle(String typeOfBicycle) {
        this.typeOfBicycle = typeOfBicycle;
    }
}
