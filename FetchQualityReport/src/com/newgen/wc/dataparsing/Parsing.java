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
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Anchal Dua
 */
public class Parsing {

    public Parsing() {
    }
    
    BeanClass objofbean = new BeanClass();

    public BeanClass parsingjson(String jsonData) throws JSONException {
        System.out.println("inside parsing class function");

        JSONObject obj = new JSONObject(jsonData);
        objofbean.setErrorMessage(obj.optString("ErrorMessage"));
        objofbean.setFromDate(obj.optString("FromDate"));
        objofbean.setIsSuccess(obj.optString("IsSuccess"));
        objofbean.setItemId(obj.optString("ItemId"));
        objofbean.setItemName(obj.optString("ItemName"));
        objofbean.setOrderStatus(obj.optString("OrderStatus"));
        objofbean.setqODate(obj.optString("QODate"));
        objofbean.setQty(obj.optString("Qty"));
        objofbean.setQualityOrderID(obj.optString("QualityOrderID"));
        objofbean.setSite(obj.optString("Site"));
        objofbean.setSource(obj.optString("Source"));
        objofbean.setTestGroup(obj.optString("TestGroup"));
        objofbean.setToDate(obj.optString("ToDate"));
        objofbean.setTransporterId(obj.optString("TransporterId"));
        objofbean.setTransporterName(obj.optString("TransporterName"));
        objofbean.setVendorId(obj.optString("VendorId"));
        objofbean.setVendorName(obj.optString("VendorName"));

//array
//        objofbean.setMaxValue(obj.optString("MaxValue"));
//        objofbean.setMinValue(obj.optString("MinValue"));
//        objofbean.setStandardValue(obj.optString("StandardValue"));
//        objofbean.setTestId(obj.optString("TestId"));
//        objofbean.setTestQty(obj.optString("TestQty"));
//        objofbean.setPONumber(obj.optString("PONumber"));
//        objofbean.setPackingSlipId(obj.optString("PackingSlipId"));

      
         List<SXAQORMContractList> array1 = new ArrayList();
        org.json.JSONArray firstarray = obj.getJSONArray("SXAQORMContractList");
       
        for (int i = 0; i < firstarray.length(); i++) {
            SXAQORMContractList obj1=new SXAQORMContractList();
            obj1.setMaxValue(firstarray.getJSONObject(i).optString("MaxValue"));
            obj1.setMinValue(firstarray.getJSONObject(i).optString("MinValue"));
            obj1.setStandardValue(firstarray.getJSONObject(i).optString("StandardValue"));
            obj1.setTestId(firstarray.getJSONObject(i).optString("TestId"));
            obj1.setTestQty(firstarray.getJSONObject(i).optString("TestQty"));
            array1.add(obj1);
        }
        
           List<SXAQORMGRNContractList> array2 = new ArrayList();
        org.json.JSONArray secondarray = obj.getJSONArray("SXAQORMGRNContractList");
       
        for (int i = 0; i < secondarray.length(); i++) {
           SXAQORMGRNContractList obj2=new SXAQORMGRNContractList();
            obj2.setPONumber(secondarray.getJSONObject(i).optString("PONumber"));
            obj2.setPackingSlipId(secondarray.getJSONObject(i).optString("PackingSlipId"));
            array2.add(obj2);
        }
        
        
            objofbean.setArrays1(array1);
            objofbean.setArrays2(array2);
            

        return objofbean;
    }

}
