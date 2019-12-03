/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newgen.wc.dataparsing;

import com.newgen.wc.beanclasses.BeanClass;
import com.newgen.wc.beanclasses.SXAQORMContractList;
import com.newgen.wc.beanclasses.SXAQORMGRNContractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anchal Dua
 */
public class PrepareAttribute {

    String attribute = "";

    public String prepareAttribute(BeanClass bc) {

        List<SXAQORMContractList> array1getter = bc.getArrays1();
        List<SXAQORMGRNContractList> array2getter = bc.getArrays2();

        for (int i = 0; i < array1getter.size(); i++) {
            attribute = attribute + "<q_hospital_birth_childs>"
                    + "<maxvalue>" + array1getter.get(i).getMaxValue() + "</maxvalue>"
                    + "<minvalue>" + array1getter.get(i).getMinValue() + "</minvalue>"
                    + "<standardvalue>" + array1getter.get(i).getStandardValue() + "</standardvalue>"
                    + "<testid>" + array1getter.get(i).getTestId() + "</testid>"
                    + "<testqty>" + array1getter.get(i).getTestQty() + "</testqty>";

            for (int A = 0; A < array2getter.size(); A++) {
                attribute = attribute + "<ponumber>" + array2getter.get(A).getPONumber() + "</ponumber>"
                        + "<packingslipid>" + array2getter.get(A).getPackingSlipId() + "</packingslipid>";
            }
            attribute = getAttribute(attribute, "errormessage", bc.getErrorMessage());
            attribute = getAttribute(attribute, "fromdate", bc.getFromDate());
            attribute = getAttribute(attribute, "issuccess", bc.getIsSuccess());
            attribute = getAttribute(attribute, "itemid", bc.getItemId());
            attribute = getAttribute(attribute, "itemname", bc.getItemName());
            attribute = getAttribute(attribute, "orderstatus", bc.getOrderStatus());
            attribute = getAttribute(attribute, "qodate", bc.getqODate());
            attribute = getAttribute(attribute, "qty", bc.getQty());
            attribute = getAttribute(attribute, "qualityorderid", bc.getQualityOrderID());
            attribute = getAttribute(attribute, "site", bc.getSite());
            attribute = getAttribute(attribute, "source", bc.getSource());
            attribute = getAttribute(attribute, "testgroup", bc.getTestGroup());
            attribute = getAttribute(attribute, "todate", bc.getToDate());
            attribute = getAttribute(attribute, "transporterid", bc.getTransporterId());
            attribute = getAttribute(attribute, "transportername", bc.getTransporterName());
            attribute = getAttribute(attribute, "vendorid", bc.getVendorId());
            attribute = getAttribute(attribute, "vendorname", bc.getVendorName());
            attribute = attribute + "</q_hospital_birth_childs>";
        }

        return attribute;
    }

    public String getAttribute(String attribute, String tagName, String tagValue) {
        if (tagValue == null) {
            tagValue = "";
        }
        attribute = attribute + "<" + tagName + ">" + tagValue + "</" + tagName + ">";

        return attribute;
    }
}
