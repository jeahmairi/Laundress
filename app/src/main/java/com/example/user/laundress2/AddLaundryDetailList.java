package com.example.user.laundress2;

public class AddLaundryDetailList {
    private  String itemTag;
    private  String itemBrand;
    private  String itemDescription;
    private  String itemColor;
    private  String categoryname;
    private  String allNameDet;
    private  String photo;
    private  int itemNoofPieces;
    private  int selectitemNoofPieces;
    private  int clientId;
    private  int categoryId;
    private  int cinv_no;
    private boolean selected;

    public String getAllNameDet() {
        return allNameDet;
    }

    public void setAllNameDet(String allNameDet) {
        this.allNameDet = allNameDet;
    }

    public String getItemTag() {
        return itemTag;
    }

    public void setItemTag(String itemTag) {
        this.itemTag = itemTag;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public void setItemBrand(String itemBrand) {
        this.itemBrand = itemBrand;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public int getItemNoofPieces() {
        return itemNoofPieces;
    }

    public void setItemNoofPieces(int itemNoofPieces) {
        this.itemNoofPieces = itemNoofPieces;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getCinv_no() {
        return cinv_no;
    }

    public void setCinv_no(int cinv_no) {
        this.cinv_no = cinv_no;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getSelectitemNoofPieces() {
        return selectitemNoofPieces;
    }

    public void setSelectitemNoofPieces(int selectitemNoofPieces) {
        this.selectitemNoofPieces = selectitemNoofPieces;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
