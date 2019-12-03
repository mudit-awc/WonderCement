package schedulerpackage;

import java.io.Serializable;
import javax.management.Query;
import org.json.*;

// Referenced classes of package com.newgen.Webservice.GetSetPurchaseOrderData:
//            GetPOLineContract, GetPOLineChargesContract, SetPOData
public class GetSetPurchaseOrderData implements Serializable {

    String attr = null, listviewattr = null;

    public String parsePoOutputJSON(String content) throws JSONException {

        String POLineContractXML = "";
        JSONObject objJSONObject = new JSONObject(content);

        //Check webservice IsSuccess status
        String IsSuccess = objJSONObject.optString("IsSucceess");
        String ErrorMessage = objJSONObject.optString("ErrorMessage");
        LogProcessing.logSumm.info("IsSuccess : " + IsSuccess);
        LogProcessing.logSumm.info("ErrorMessage : " + ErrorMessage);
        if (IsSuccess.equalsIgnoreCase("true")) {
            //Set Header Details
            attr = attr + "<suppliercode>" + objJSONObject.optString("VendorCode") + "</suppliercode>"
                    + "<suppliername>" + objJSONObject.optString("VendorName") + "</suppliername>"
                    + "<loadingcity>" + objJSONObject.optString("LoadingCity") + "</loadingcity>"
                    + "<site>" + objJSONObject.optString("BusinessUnit") + "</site>"
                    + "<purchaseorderdate>" + objJSONObject.optString("AccountingDate") + "</purchaseorderdate>";
            //Set Line Details
            JSONArray objJSONArray_POLineContract = objJSONObject.getJSONArray("POLineContract");
            for (int i = 0; i < objJSONArray_POLineContract.length(); i++) {
                listviewattr = listviewattr + "<q_polines>"
                        + "<linenumber>" + objJSONArray_POLineContract.getJSONObject(i).optString("LineNumber") + "</linenumber>"
                        + "<itemnumber>" + objJSONArray_POLineContract.getJSONObject(i).optString("ItemNumber") + "</itemnumber>"
                        + "<productname>" + objJSONArray_POLineContract.getJSONObject(i).optString("ProductName") + "</productname>"
                        + "<itemmodelgroupid>" + objJSONArray_POLineContract.getJSONObject(i).optString("ItemModelGroupID") + "</itemmodelgroupid>"
                        + "<quantity>" + objJSONArray_POLineContract.getJSONObject(i).optString("PurchQty") + "</quantity>"
                        + "<unit>" + objJSONArray_POLineContract.getJSONObject(i).optString("Unit") + "</unit>"
                        + "<unitprice>" + objJSONArray_POLineContract.getJSONObject(i).optString("UnitPrice") + "</unitprice>"
                        + "<discountpercent>" + objJSONArray_POLineContract.getJSONObject(i).optString("DiscountPercentage") + "</discountpercent>"
                        + "<discountamount>" + objJSONArray_POLineContract.getJSONObject(i).optString("DiscountAmount") + "</discountamount>"
                        + "<tdsgroup>" + objJSONArray_POLineContract.getJSONObject(i).optString("TDSGroup") + "</tdsgroup>"
                        + "<inventorysite>" + objJSONArray_POLineContract.getJSONObject(i).optString("InventorySite") + "</inventorysite>"
                        + "<inventorylocation>" + objJSONArray_POLineContract.getJSONObject(i).optString("InventoryLocation") + "</inventorylocation>"
                        + "<businessunit>" + objJSONArray_POLineContract.getJSONObject(i).optString("BusinessUnit") + "</businessunit>"
                        + "<costcentre>" + objJSONArray_POLineContract.getJSONObject(i).optString("CostCenter") + "</costcentre>"
                        + "<costelement>" + objJSONArray_POLineContract.getJSONObject(i).optString("CostElement") + "</costelement>"
                        + "<department>" + objJSONArray_POLineContract.getJSONObject(i).optString("Department") + "</department>"
                        + "<gla>" + objJSONArray_POLineContract.getJSONObject(i).optString("GLA") + "</gla>"
                        + "<ledgeraccount>" + objJSONArray_POLineContract.getJSONObject(i).optString("LedgerAccount") + "</ledgeraccount>"
                        + "<taxgroup>" + objJSONArray_POLineContract.getJSONObject(i).optString("TaxGroup") + "</taxgroup>"
                        + "<itemtaxgroup>" + objJSONArray_POLineContract.getJSONObject(i).optString("ItemTaxGroup") + "</itemtaxgroup>"
                        + "<gstin_gdi_uid>" + objJSONArray_POLineContract.getJSONObject(i).optString("GstinGdiUid") + "</gstin_gdi_uid>"
                        + "<hsn>" + objJSONArray_POLineContract.getJSONObject(i).optString("HSN") + "</hsn>"
                        + "<sac>" + objJSONArray_POLineContract.getJSONObject(i).optString("SAC") + "</sac>"
                        + "<vendorgstin>" + objJSONArray_POLineContract.getJSONObject(i).optString("VendorGSTIN") + "</vendorgstin>"
                        + "<itemtrackingdimension>" + objJSONArray_POLineContract.getJSONObject(i).optString("ItemTrackingDimension") + "</itemtrackingdimension>"
                        + "<quarantinemanagement>" + objJSONArray_POLineContract.getJSONObject(i).optString("QuarantineManagement") + "</quarantinemanagement>"
                        + "<ppbagmanagement>" + objJSONArray_POLineContract.getJSONObject(i).optString("PPBagManagement") + "</ppbagmanagement>"
                        + "<hlmanagement>" + objJSONArray_POLineContract.getJSONObject(i).optString("HLManagement") + "</hlmanagement>"
                        + "<rmmanagement>" + objJSONArray_POLineContract.getJSONObject(i).optString("RMManagement") + "</rmmanagement>"
                        + "</q_polines>";
            }
            // System.out.println((new StringBuilder()).append("PO Line XML :").append(POLineContractXML).toString());

        }
        LogProcessing.logAttributes.info("Purchase Order Data Attributes :::: " + attr + listviewattr);
        return attr + listviewattr;
    }
}
