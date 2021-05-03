package src.classes;

public class TypeOfBicycle {
    protected String type;

    //toString
    @Override
    public String toString() {
        return "Type of bicycle : " + this.type;
    }

    //Constructors
    public TypeOfBicycle(String type) {
        if (type.equalsIgnoreCase("MECHANICAL")){
            this.type = "mechanical";
        }
        else if (type.equalsIgnoreCase("ELECTRICAL")){
            this.type = "electrical";
        }
        else{
            System.out.println("Type of bicycle not found, type must be mechanical or electrical");
        }
    }

    //Getters and Setters
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
