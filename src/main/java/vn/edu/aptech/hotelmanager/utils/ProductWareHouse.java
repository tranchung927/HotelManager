package vn.edu.aptech.hotelmanager.utils;

public class ProductWareHouse {
    private String name;
    private Integer id;

    private Integer quantityImport;
    private  String userImport;
    private Double priceInput;
    private String dateInput;
    private Unit unit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUserImport() {
        return userImport;
    }

    public void setUserImport(String userImport) {
        this.userImport = userImport;
    }

    public Double getPriceInput() {
        return priceInput;
    }

    public void setPriceInput(Double priceInput) {
        this.priceInput = priceInput;
    }

    public String getDateInput() {
        return dateInput;
    }

    public void setDateInput(String dateInput) {
        this.dateInput = dateInput;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Integer getQuantityImport() {
        return quantityImport;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuantityImport(Integer quantityImport) {
        this.quantityImport = quantityImport;
    }

    public ProductWareHouse(String name, Integer id, Integer quantityImport, String userImport, Double priceInput, String dateInput, Unit unit) {
        this.name = name;
        this.id = id;
        this.quantityImport = quantityImport;
        this.userImport = userImport;
        this.priceInput = priceInput;
        this.dateInput = dateInput;
        this.unit = unit;
    }

    public ProductWareHouse(String thăngLong, int i, int i1, Object o, double v, Unit lon) {
    }

    @Override
    public String toString() {
        return "ProductWareHouse{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", quantityImport=" + quantityImport +
                ", userImport='" + userImport + '\'' +
                ", priceInput=" + priceInput +
                ", dateInput='" + dateInput + '\'' +
                ", unit=" + unit +
                '}';
    }

    public ProductWareHouse() {

    }
}
