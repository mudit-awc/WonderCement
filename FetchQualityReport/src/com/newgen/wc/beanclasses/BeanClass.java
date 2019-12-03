/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newgen.wc.beanclasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Anchal Dua
 */
public class BeanClass {

    public BeanClass() {
    }

    private String errorMessage;
    private String fromDate;
    private String isSuccess;
    private String itemId;
    private String itemName;
    private String orderStatus;
    private String qODate;
    private String qty;
    private String qualityOrderID;
    private String site;
    private String source;
    private String testGroup;
    private String toDate;
    private String transporterId;
    private String transporterName;
    private String vendorId;
    private String vendorName;


  List<SXAQORMContractList> arrays1 = new ArrayList();  

    public List<SXAQORMContractList> getArrays1() {
        return arrays1;
    }

    public void setArrays1(List<SXAQORMContractList> arrays1) {
        this.arrays1 = arrays1;
    }

    public List<SXAQORMGRNContractList> getArrays2() {
        return arrays2;
    }

    public void setArrays2(List<SXAQORMGRNContractList> arrays2) {
        this.arrays2 = arrays2;
    }
 List<SXAQORMGRNContractList> arrays2 = new ArrayList();

  

   
    
  

//    public String getMaxValue() {
//        return maxValue;
//    }
//
//    public void setMaxValue(String maxValue) {
//        this.maxValue = maxValue;
//    }
//
//    public String getMinValue() {
//        return minValue;
//    }
//
//    public void setMinValue(String minValue) {
//        this.minValue = minValue;
//    }
//
//    public String getStandardValue() {
//        return standardValue;
//    }
//
//    public void setStandardValue(String standardValue) {
//        this.standardValue = standardValue;
//    }
//
//    public String getTestId() {
//        return testId;
//    }
//
//    public void setTestId(String testId) {
//        this.testId = testId;
//    }
//
//    public String getTestQty() {
//        return testQty;
//    }
//
//    public void setTestQty(String testQty) {
//        this.testQty = testQty;
//    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getqODate() {
        return qODate;
    }

    public void setqODate(String qODate) {
        this.qODate = qODate;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getQualityOrderID() {
        return qualityOrderID;
    }

    public void setQualityOrderID(String qualityOrderID) {
        this.qualityOrderID = qualityOrderID;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTestGroup() {
        return testGroup;
    }

    public void setTestGroup(String testGroup) {
        this.testGroup = testGroup;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getTransporterId() {
        return transporterId;
    }

    public void setTransporterId(String transporterId) {
        this.transporterId = transporterId;
    }

    public String getTransporterName() {
        return transporterName;
    }

    public void setTransporterName(String transporterName) {
        this.transporterName = transporterName;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

}
