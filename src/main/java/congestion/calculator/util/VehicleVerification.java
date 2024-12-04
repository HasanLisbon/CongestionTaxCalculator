package congestion.calculator.util;

import congestion.calculator.model.*;

import java.util.Optional;

public class VehicleVerification {
    public Vehicle getVehicle(String vehicleName){

        if (vehicleName.isEmpty() || vehicleName==null)
            return null;

        switch (vehicleName.toLowerCase()){
            case "bus":
                return new Bus();
            case "car":
                return new Car();
            case "diplomat":
                return new DiplomatVehicle();
            case "emergency":
                return new EmergencyVehicle();
            case "foreign":
                return new ForeignVehicle();
            case "military":
                return new MilitaryVehicle();
            case "motorcycle":
                return new Bus();
            default:
                return  new Car();
        }
    }
}
