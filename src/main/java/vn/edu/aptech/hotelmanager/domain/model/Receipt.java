package vn.edu.aptech.hotelmanager.domain.model;

import lombok.Data;

import java.awt.geom.Arc2D;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Receipt {
    private  long id;
    private  String productName;
    private ReceiptType type;
    private String Importer;
    private String createAt;
    private Double totalPayment;
    private int quantityImport;
    private Double inputPrice;
    private Double outputPrice;
    private String unit;
    private int receiptCode;
    private  int status;
}
