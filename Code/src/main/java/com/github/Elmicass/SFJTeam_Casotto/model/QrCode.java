package com.github.Elmicass.SFJTeam_Casotto.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
public class QrCode implements Serializable {

    public final static String QRCODE_DEFAULT_FILE_NAME = "Sunshade_n" + "%s" + ".png";

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Integer id;

    @Column(name = "File")
    private final File qrcodeFile;

    @Column(name = "Path")
    private final String path;

    @Column(name = "Name")
    private final String name;

    @Column(name = "String")
    private final String string;
    
    @OneToOne(mappedBy = "qrCode")
    @JoinColumn(name = "Sunshade")
    private Sunshade sunshade;
    
    @Transient
    private final String charset = "UTF-8";

    @Transient
    private final int qrCodeheight = 250;

    @Transient
    private final int qrCodewidth = 250;

    @Transient
    private final Map hintMap;

    public QrCode() {
        this.name = "";
        this.path = "";
        this.string = "";
        this.hintMap = null;
        this.qrcodeFile = null;
    }

    public QrCode(Sunshade sunshade) throws WriterException, IOException {
        setSunshade(sunshade);
        this.hintMap = new HashMap<>();
        this.name = String.format(QRCODE_DEFAULT_FILE_NAME, sunshade.getID());
        this.path = "./src/main/resources/" + name;
        this.string = "This QrCode refers to the sunshade number: " + sunshade.getID()
                + ", currently used in beach place number: " + sunshade.getCurrentlyUsedIn().getID() + ".";
        this.qrcodeFile = createQRCode(string, path, charset, hintMap, qrCodeheight, qrCodewidth);
    }

    public Integer getID() {
        return id;
    }

    public Sunshade getSunshade() {
        return sunshade;
    }

    public void setSunshade(Sunshade sunshade) {
        Objects.requireNonNull(sunshade, "Sunshade is null");
        this.sunshade = sunshade;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
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

    public static boolean delete(QrCode qrCode) {
        return qrCode.qrcodeFile.delete();
    }

    



}
