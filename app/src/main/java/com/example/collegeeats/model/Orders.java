package com.example.collegeeats.model;

public class Orders {

    private String oid,ostatus,ototal,oitem,ouid;

    public Orders(String oid, String ostatus, String ototal, String oitem, String ouid) {
        this.oid = oid;
        this.ostatus = ostatus;
        this.ototal = ototal;
        this.oitem = oitem;
        this.ouid = ouid;
    }

    public Orders() {
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOstatus() {
        return ostatus;
    }

    public void setOstatus(String ostatus) {
        this.ostatus = ostatus;
    }

    public String getOtotal() {
        return ototal;
    }

    public void setOtotal(String ototal) {
        this.ototal = ototal;
    }

    public String getOitem() {
        return oitem;
    }

    public void setOitem(String oitem) {
        this.oitem = oitem;
    }

    public String getOuid() {
        return ouid;
    }

    public void setOuid(String ouid) {
        this.ouid = ouid;
    }
}
