package src.classes;


public class Bicycle {

    protected TypeOfBicycle type;
    protected Integer Id;
//    protected TypeOfBicycle typeOfBicycle;

    private static Integer uniqueId = 0; //Unique numerical ID

    //toString
    @Override
    public String toString() {
        return "Bicycle :" + "\n" +
                "Id : " + Id + "\n" +
                "Type : " + this.type.name();
    }

    //Constructors
    public Bicycle(TypeOfBicycle type) {
        Id = uniqueId;
        uniqueId++;
        this.type = type;
    }

    //Getters and Setters

    public TypeOfBicycle getType() {
        return type;
    }

    public void setType(TypeOfBicycle type) {
        this.type = type;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
