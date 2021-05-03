package src.classes;

import src.classes.TypeOfBicycle;

public class Bicycle {
    protected Integer Id;
    protected TypeOfBicycle typeOfBicycle;

    private static Integer uniqueId = 0; //Unique numerical ID

    //toString
    @Override
    public String toString() {
        return "Bicycle :" + "\n" +
                "Id : " + Id + "\n" + typeOfBicycle;
    }

    //Constructors
    public Bicycle(TypeOfBicycle typeOfBicycle) {
        Id = uniqueId;
        uniqueId++;
        this.typeOfBicycle = typeOfBicycle;
    }

    //Getters and Setters
    public Integer getId() {
        return Id;
    }
    public void setId(Integer id) {
        Id = id;
    }
    public TypeOfBicycle getTypeOfBicycle() {
        return typeOfBicycle;
    }
    public void setTypeOfBicycle(TypeOfBicycle typeOfBicycle) {
        this.typeOfBicycle = typeOfBicycle;
    }
}
