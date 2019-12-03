/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newgen.SupplyPoInvoices;

import com.newgen.Webservice.GetSetGateEntryData;
import com.newgen.Webservice.GetSetPurchaseOrderData;
import com.newgen.Webservice.PostGRN;
import java.util.HashMap;
import java.util.List;
import javax.faces.validator.ValidatorException;
import com.newgen.common.General;
import com.newgen.common.ReadProperty;
import com.newgen.omniforms.FormConfig;
import com.newgen.omniforms.FormReference;
import com.newgen.omniforms.component.IRepeater;
import com.newgen.omniforms.component.PickList;
import com.newgen.omniforms.context.FormContext;
import com.newgen.omniforms.event.ComponentEvent;
import com.newgen.omniforms.event.FormEvent;
import com.newgen.omniforms.listener.FormListener;
import static javafx.beans.binding.Bindings.and;
import static javafx.beans.binding.Bindings.select;

public class QualityUser implements FormListener {

    FormReference formObject = null;
    FormConfig formConfig = null;
    PickList objPicklist;
    General objGeneral = null;
    ReadProperty objReadProperty = null;
    GetSetPurchaseOrderData objGetSetPurchaseOrderData = null;
    GetSetGateEntryData objGetSetGateEntryData = null;
    PostGRN objPostGRN = null;

    String activityName = null;
    String engineName = null;
    String sessionId = null;
    String folderId = null;
    String FILE = null;
    String serverUrl = null;
    String processInstanceId = null;
    String workItemId = null;
    String userName = null;
    String processDefId = null;
    String Query = null;
    List<List<String>> result;
    private String webserviceStatus;

    @Override
    public void continueExecution(String arg0, HashMap<String, String> arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public void eventDispatched(ComponentEvent pEvent) throws ValidatorException {
        // TODO Auto-generated method stub
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();

        objGeneral = new General();
        objGetSetPurchaseOrderData = new GetSetPurchaseOrderData();
        objGetSetGateEntryData = new GetSetGateEntryData();
        objReadProperty = new ReadProperty();
        formObject.setNGValue("processid", processInstanceId);
        IRepeater RepeaterControlFrame5 = formObject.getRepeaterControl("Frame5");
        String GateEntryLineLV = "q_gateentrylines";
        String PoLineLV = "q_polines";
        switch (pEvent.getType().name()) {

            case "VALUE_CHANGED":

                switch (pEvent.getSource().getName()) {

                    case "":
                        break;

                }
                break;

            case "MOUSE_CLICKED":

                switch (pEvent.getSource().getName()) {
                   case "Btn_updateQuaratinedetails":
                        System.out.println("inside farman Btn_updateQuaratinedetails ");
                        formObject.ExecuteExternalCommand("NGAddRow", "q_quarantinemanagement");
                        break;
                }

                break;
            case "TAB_CLICKED":
                switch (pEvent.getSource().getName()) {
                    case "":
                        break;
                }

                break;
        }
    }

    @Override
    public void formLoaded(FormEvent arg0) {
        System.out.println(" -------------------Intiation Workstep Loaded from formloaded.----------------");
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        try {

            activityName = formObject.getWFActivityName();
            engineName = formConfig.getConfigElement("EngineName");
            sessionId = formConfig.getConfigElement("DMSSessionId");
            folderId = formConfig.getConfigElement("FolderId");
            serverUrl = formConfig.getConfigElement("ServletPath");
            processInstanceId = formConfig.getConfigElement("ProcessInstanceId");
            workItemId = formConfig.getConfigElement("WorkitemId");
            userName = formConfig.getConfigElement("UserName");
            processDefId = formConfig.getConfigElement("ProcessDefId");

            System.out.println("ProcessInstanceId===== " + processInstanceId);
            System.out.println("Activityname=====" + activityName);
            System.out.println("CabinetName====" + engineName);
            System.out.println("sessionId====" + sessionId);
            System.out.println("Username====" + userName);
            System.out.println("workItemId====" + workItemId);
        } catch (Exception e) {
            System.out.println("Exception in FieldValueBagSet::::" + e.getMessage());
        }
    }

    @Override
    public void formPopulated(FormEvent arg0) {
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        System.out.println("----------------------Intiation Workstep Loaded from form populated.---------------------------");
        formObject.setSheetEnable("Tab2", 0, false);
        formObject.setSheetEnable("Tab2", 1, false);
        if (formObject.getNGValue("itemtypeflag").equalsIgnoreCase("Quarantine")) {
            String Query = "select itemnumber,quarantinemanagement from cmplx_poline where "
                    + "pinstanceid ='" + processInstanceId + "' and "
                    + "itemnumber in (select itemid from cmplx_gateentryline where pinstanceid ='" + processInstanceId + "')";
            result = formObject.getDataFromDataSource(Query);

            for (int i = 0; i < result.size(); i++) {
                String quarantine = result.get(0).get(1).toString();

                if ("1".equalsIgnoreCase(quarantine)) {
                    formObject.addComboItem("Quality_itemselect", result.get(i).get(0).toString(), result.get(i).get(0).toString());
                }
            }
        }
    }

    @Override
    public void saveFormCompleted(FormEvent arg0) throws ValidatorException {
        System.out.print("-------------------save form completed---------");
    }

    @Override
    public void saveFormStarted(FormEvent arg0) throws ValidatorException {
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();

    }

    @Override
    public void submitFormCompleted(FormEvent arg0) throws ValidatorException {
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();

    }

    @Override
    public void submitFormStarted(FormEvent arg0) throws ValidatorException {
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();

        System.out.println("******activityName****" + activityName);
    }

    @Override
    public void initialize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String encrypt(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String decrypt(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
