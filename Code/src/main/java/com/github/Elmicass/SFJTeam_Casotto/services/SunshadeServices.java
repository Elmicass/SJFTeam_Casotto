package com.github.Elmicass.SFJTeam_Casotto.services;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.BeachPlace;
import com.github.Elmicass.SFJTeam_Casotto.model.PriceList;
import com.github.Elmicass.SFJTeam_Casotto.model.QrCode;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade.SunshadeType;
import com.github.Elmicass.SFJTeam_Casotto.repository.ISunshadesRepository;
import com.google.zxing.WriterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;

@Service
@Transactional
public class SunshadeServices implements ISunshadeServices {

    @Autowired
    private ISunshadesRepository sunshadeRepository;

    @Autowired
    private IQrCodeServices qrCodeServices;

    @Override
    public Sunshade getInstance(Integer id) throws EntityNotFoundException {
        return sunshadeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No sunshades found with the given id: " + id));
    }

    @Override
    public List<Sunshade> getAll() {
        return sunshadeRepository.findAll();
    }

    @Override
    public Sunshade save(Sunshade sunshade) {
        return sunshadeRepository.save(sunshade);
    }

    @Override
    public Sunshade createSunshade(SunshadeType type, BeachPlace bp, PriceList pl) throws AlreadyExistingException {
        if (sunshadeRepository.findByBeachPlace(bp).isPresent())
            throw new AlreadyExistingException("The sunshade you are trying to create already exists, used in the same beach place: " + bp.getID());
        Sunshade sunshade = new Sunshade(type, bp, pl);
        return sunshade;
    }

    @Override
    public Sunshade generateQrCode(@NonNull Sunshade sunshade) throws WriterException, IOException, AlreadyExistingException {
        Sunshade saved = save(sunshade);
        saved.setQrCode(qrCodeServices.save(qrCodeServices.createQrCode(saved)));
        return save(saved);
    }

    @Override
    public boolean delete(Integer id) {
        if (id.toString().isBlank()) throw new IllegalArgumentException("The sunshade ID is empty");
        if (!(exists(id))) throw new EntityNotFoundException("The sunshade with ID: " + id + " does not exist");
        QrCode.delete(getInstance(id).getQrCode());
        qrCodeServices.delete(getInstance(id).getQrCode().getID());
        sunshadeRepository.deleteById(id);
        return !exists(id);
    }

    @Override
    public boolean exists(Integer id) {
        if (id.toString().isBlank()) throw new IllegalArgumentException("The sunshade ID value is empty");
        return sunshadeRepository.existsById(id);
    }

    
    
    
}
