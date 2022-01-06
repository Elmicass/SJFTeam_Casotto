package com.github.Elmicass.SFJTeam_Casotto.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.github.Elmicass.SFJTeam_Casotto.exception.QrCodeNotFoundException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Entity
@Table(name = "QrCode")
public class QrCode {

    protected static final AtomicInteger count = new AtomicInteger(0);

    @Id
    @Column(name = "ID")
    private final String ID;

    private final File qrcodeFile;

    private final String path;
    private final String name;
    private final String string;

    private final String charset = "UTF-8";
    private final int qrCodeheight = 250;
    private final int qrCodewidth = 250;

    private final Map hintMap;

    @OneToOne(mappedBy = "qrCode")
    private Sunshade sunshade;

    public QrCode(Sunshade sunshade) throws WriterException, IOException {
        this.ID = String.valueOf(count.getAndIncrement());
        setSunshade(sunshade);
        this.hintMap = new HashMap<>();
        this.name = "Sunshade_n" + sunshade.getID() + ".png";
        this.path = "./src/main/resources/" + name;
        this.string = "This QrCode refers to the sunshade number: " + sunshade.getID()
                + ", currently used in beach place number: " + sunshade.getCurrentlyUsedIn().getID() + ".";
        this.qrcodeFile = createQRCode(string, path, charset, hintMap, qrCodeheight, qrCodewidth);
    }

    public Sunshade getSunshade() {
        return sunshade;
    }

    public void setSunshade(Sunshade sunshade) {
        this.sunshade = Objects.requireNonNull(sunshade, "Sunshade is null");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((qrcodeFile == null) ? 0 : qrcodeFile.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        QrCode other = (QrCode) obj;
        if (qrcodeFile == null) {
            if (other.qrcodeFile != null)
                return false;
        } else if (!qrcodeFile.equals(other.qrcodeFile))
            return false;
        return true;
    }

    public static File createQRCode(String qrCodeData, String filePath,
            String charset, Map hintMap, int qrCodeheight, int qrCodewidth)
            throws WriterException, IOException {
        try {
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(qrCodeData.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(matrix, "PNG", path);
            File file = new File(filePath);
            return file;
        } catch (WriterException e) {
            throw new WriterException("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            throw new IOException("Could not generate QR Code, IOException :: " + e.getMessage());
        }
    }

    public static String readQRcode(String charset, Map map, File fileToRead)
            throws FileNotFoundException, IOException, NotFoundException, QrCodeNotFoundException {
        try {
            BinaryBitmap binaryBitmap = new BinaryBitmap(
                    new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(fileToRead))));
            Result rslt = new MultiFormatReader().decode(binaryBitmap);
            return rslt.getText();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Could not find the passed File, FileNotFoudException ::" + e.getMessage());
        } catch (IOException e) {
            throw new IOException("Could not read the passed file, IOException :: " + e.getMessage());
        } catch (NotFoundException e) {
            throw new QrCodeNotFoundException(
                    "Could not find the QrCode in the passed file image, NotFoundException ::" + e.getMessage());
        }
    }



}
