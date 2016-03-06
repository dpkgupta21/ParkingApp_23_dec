package app.parking.com.parkingapp.iClasses;

import java.util.ArrayList;

import app.parking.com.parkingapp.model.ListOfServicesDTO;

/**
 * Created by Harish on 3/6/2016.
 */
public interface RemoveServices {

    public void onServicesRemoved(ArrayList<ListOfServicesDTO> listOfServicesDTOs);
}
