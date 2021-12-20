package Casotto.controller;

import Casotto.model.Activity;
import Casotto.model.Guest;

public class ActivityManager implements IActivityManager {


    public ActivityManager(){

    }

    @Override

    public Activity getActivityInstance(String activityID) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public boolean newBooking(Activity activity, Guest guest) {
        //controlla il numero di iscrizioni massime per quell'attività
		//se == 0 vuol dire che l'attività non ha un numero massimo di partecipanti
        if(activity.getMaxEntries() == 0){
            //aggiungi l'ospite all'attività
            activity.addReservation(guest);
            return true;
        }
        else if(activity.getMaxEntries() > activity.getParticipantsNumber()){
            activity.addReservation(guest);
            return true;
        }else
            return false;
    }

    @Override
    public boolean cancelBooking(String activityID, String email) {
        // TODO Auto-generated method stub
        return false;
    }
}
