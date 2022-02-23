package com.github.Elmicass.SFJTeam_Casotto.services;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.QrCode;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade;
import com.github.Elmicass.SFJTeam_Casotto.repository.IQrRepository;
import com.google.zxing.WriterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;

@Service
@Transactional
public class QrCodeServices implements IQrCodeServices {

    @Autowired
    private IQrRepository qrRepository;

    @Override
    public QrCode getInstance(@NonNull Integer id) throws EntityNotFoundException {
        return qrRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No qrcode found with the given id: " + id));
    }

    @Override
    public List<QrCode> getAll() {
        return qrRepository.findAll();
    }

    @Override
    public QrCode save(QrCode qr) {
        return qrRepository.save(qr);
    }

    @Override
    public boolean delete(@NonNull Integer id) throws EntityNotFoundException {
        if (!(exists(id)))
            throw new EntityNotFoundException("The qrcode with ID: " + id + " does not exist");
        QrCode.delete(getInstance(id));
        qrRepository.deleteById(id);
        return !exists(id);
    }

    @Override
    public boolean exists(@NonNull Integer id) {
        if (id.toString().isBlank())
            throw new IllegalArgumentException("The qrcode ID value is empty");
        return qrRepository.existsById(id);
    }

    @Override
    public QrCode createQrCode(@NonNull Sunshade sunshade) throws WriterException, IOException, AlreadyExistingException {
        String name = String.format(QrCode.QRCODE_DEFAULT_FILE_NAME, sunshade.getID());
        if (qrRepository.findByName(name).isPresent())
            throw new AlreadyExistingException("The qrcode you are trying to create already exists, with the same name: " + name);
        QrCode qrCode = new QrCode(sunshade);
        return qrCode;
    }

}
