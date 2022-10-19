package cz.muni.fi.pv168.cashflow.business.model;

public class Currency extends Entity {

    private String name;
    private String code;
    private double rate;

    public Currency(String guid, String name, String code, double rate) {
        super(guid);
        this.name = name;
        this.code = code;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
