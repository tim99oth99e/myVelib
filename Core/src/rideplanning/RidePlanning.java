package src.rideplanning;

import src.classes.*;
import src.enums.*;

import java.util.ArrayList;

public interface RidePlanning {
    public Station findStartStation(ArrayList<Station> stations, TypeOfBicycle typeOfBicycle);
    public Station findDestinationStation(ArrayList<Station> stations);
}
