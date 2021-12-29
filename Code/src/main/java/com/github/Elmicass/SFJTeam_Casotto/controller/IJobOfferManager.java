package com.github.Elmicass.SFJTeam_Casotto.controller;

import java.time.LocalDateTime;

import com.github.Elmicass.SFJTeam_Casotto.model.JobOffer;

public interface IJobOfferManager extends EntityManager<JobOffer, String>{

    boolean createNewJobOffer(String name, String description, LocalDateTime expiration);

}
