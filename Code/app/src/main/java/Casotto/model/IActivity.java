package Casotto.model;

/**
 * TODO
 */
public interface IActivity {

    /**
     * TODO
     * @return
     */
    Integer getMaxEntries();

    /**
     * TODO
     * @return
     */
    Integer getParticipantsNumber();

    /**
     * TODO
     * @param person
     * @return
     */
    boolean addReservation(Guest person);

    /**
     * TODO
     * @param person
     * @return
     */
    boolean removeReservation(Guest person);
    
}
