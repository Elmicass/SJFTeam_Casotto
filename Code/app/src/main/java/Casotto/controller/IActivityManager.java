package Casotto.controller;

import Casotto.model.*;

/**
 * TODO
 */
public interface IActivityManager {

    /**
     * TODO
     * @param ID
     * @return
     */
    Activity getActivityInstance(String activityID);

    /**
     * TODO
     * @param email
     * @return
     */
    boolean newBooking(String email);

    /**
     * TODO
     * @param email
     * @return boolean, se la cancellazione è eseguita correttamente
     */
    boolean cancelBooking(String email);


}
