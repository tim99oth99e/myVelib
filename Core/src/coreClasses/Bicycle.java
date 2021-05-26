package src.coreClasses;


import src.enums.TypeOfBicycle;

/**
 * This class describes a bicycle.
 */
public class Bicycle {

    /**
     * The unique identifier of the bicycle.
     */
    protected Integer Id;
    /**
     * The type of the bicycle.
     */
    protected TypeOfBicycle type;

    private static Integer uniqueId = 0; //Unique numerical ID

    //toString
    @Override
    public String toString() {
        return "Bicycle :" + "\n" +
                "Id : " + Id + "\n" +
                "Type : " + this.type.name();
    }

    /**
     * Instantiates a new Bicycle.
     *
     * @param type the bicycle type can be electrical or mechanical.
     */

    public Bicycle(TypeOfBicycle type) {
        Id = uniqueId;
        uniqueId++;
        this.type = type;
    }

    //Getters and Setters

    /**
     * Gets type.
     *
     * @return the type
     */
    public TypeOfBicycle getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(TypeOfBicycle type) {
        this.type = type;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return Id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        Id = id;
    }
}
