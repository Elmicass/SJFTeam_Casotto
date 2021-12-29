package com.github.Elmicass.SFJTeam_Casotto.controller;

import com.github.Elmicass.SFJTeam_Casotto.model.Activity;

public interface IActivityManager extends EntityManager<Activity, String> {

    boolean createNewActivity(String name, String description, int maxEntries);

    boolean newBooking(String activityID, String email);

    boolean cancelBooking(String activityID, String email);

}
