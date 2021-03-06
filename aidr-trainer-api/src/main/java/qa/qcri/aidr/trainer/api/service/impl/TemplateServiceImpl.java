package qa.qcri.aidr.trainer.api.service.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import qa.qcri.aidr.common.exception.PropertyNotSetException;
import qa.qcri.aidr.dbmanager.dto.CrisisDTO;
import qa.qcri.aidr.dbmanager.ejb.remote.facade.CrisisResourceFacade;
import qa.qcri.aidr.trainer.api.entity.Client;
import qa.qcri.aidr.trainer.api.entity.ClientApp;
//import qa.qcri.aidr.trainer.api.entity.Crisis;
import qa.qcri.aidr.trainer.api.entity.CustomUITemplate;
import qa.qcri.aidr.trainer.api.service.*;
import qa.qcri.aidr.trainer.api.store.CodeLookUp;
import qa.qcri.aidr.trainer.api.store.StatusCodeType;
import qa.qcri.aidr.trainer.api.store.URLReference;
import qa.qcri.aidr.trainer.api.template.CrisisApplicationListFormatter;
import qa.qcri.aidr.trainer.api.template.CrisisApplicationListModel;
import qa.qcri.aidr.trainer.api.template.CrisisLandingHtmlModel;
import qa.qcri.aidr.trainer.api.template.CrisisLandingStatusModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 10/27/13
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */

@Service("templateService")
@Transactional(readOnly = false)
public class TemplateServiceImpl implements TemplateService {

	@Autowired
	private ClientService clientService;

	@Autowired
	private ClientAppService clientAppService;

	@Autowired
	private TaskQueueService taskQueueService;

	//@Autowired
	//private CrisisService crisisService;

	@Autowired
	private CrisisResourceFacade crisisService;

	@Autowired
	private CustomUITemplateService customUITemplateService;

	@Override
	public List<CrisisApplicationListModel> getApplicationListHtmlByCrisisID(Long cririsID) {


		Client client;
		List<CrisisApplicationListModel> applicationListModelList = new ArrayList<CrisisApplicationListModel>();
		List<ClientApp> clientAppList = clientAppService.getAllClientAppByCrisisID(cririsID);
		if(clientAppList != null){
			if(clientAppList.size() > 0){
				client = clientService.findClientbyID("clientID", clientAppList.get(0).getClientID());
				for(int i=0; i < clientAppList.size(); i++){
					ClientApp clientApp = clientAppList.get(i);

					if(!clientApp.getStatus().equals(StatusCodeType.CLIENT_APP_INACTIVE) && !clientApp.getStatus().equals(StatusCodeType.CLIENT_APP_DISABLED)){

						CrisisApplicationListFormatter formatter = new CrisisApplicationListFormatter(clientApp,client,taskQueueService) ;
						String url = formatter.getURLLink();
						Integer remaining = formatter.getRemaining();
						Integer totalCount = formatter.getTotalTaskNumber() - formatter.getRemaining();
						if(totalCount < 0){
							totalCount = 0;
						}
						String attNameValue = clientApp.getName();
						String[] array = attNameValue.split("\\:");
						String attName =null;

						if(array.length > 1){
							attName = array[1];
						}

						applicationListModelList.add(new CrisisApplicationListModel(clientApp.getNominalAttributeID(),attName.trim(),url,remaining, totalCount));
					}

				}

			}
		}

		return applicationListModelList;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public List<CrisisApplicationListModel> getApplicationListHtmlByCrisisCode(String crisisCode) {

		List<CrisisApplicationListModel> crisisApplicationListModelList = null;
		List<CrisisDTO> crisisList;
		try {
			crisisList = crisisService.findByCriteria("code", crisisCode);
			if(crisisList!=null){
				if(crisisList.size() > 0){
					CrisisDTO crisis = crisisList.get(0);
					crisisApplicationListModelList = getApplicationListHtmlByCrisisID(crisis.getCrisisID());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return crisisApplicationListModelList;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public CrisisLandingHtmlModel getCrisisLandingHtmlByCrisisCode(String crisisCode) {
		CrisisLandingHtmlModel crisisLandingHtmlModel = null;
		List<CrisisDTO> crisisList = null;
		try {
			crisisList = crisisService.findByCriteria("code", crisisCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(crisisList != null){
			if(crisisList.size() > 0){
				CrisisDTO crisis = crisisList.get(0);
				crisisLandingHtmlModel = getCrisisLandingHtmlByCrisisID(crisis.getCrisisID());
			}
		}

		return crisisLandingHtmlModel;
	}

	@Override
	public CrisisLandingHtmlModel getCrisisLandingHtmlByCrisisID(Long crisisID) {

		CrisisLandingHtmlModel crisisLandingHtmlModel = null;
		try {
			//Crisis crisis =  crisisService.findByCrisisID(crisisID);
			CrisisDTO crisis =  crisisService.findCrisisByID(crisisID);
			List<ClientApp> clientAppList = clientAppService.getAllClientAppByCrisisID(crisisID);
			if(clientAppList != null & crisis!= null){
				if(clientAppList.size() > 0){
					Client client = clientService.findClientbyID("clientID", clientAppList.get(0).getClientID());
					List<CrisisApplicationListModel> crisisApplicationListModelList = null;

					crisisApplicationListModelList = getApplicationListHtmlByCrisisID(crisisID);
					crisisLandingHtmlModel   = new CrisisLandingHtmlModel(crisis.getCode(), crisis.getName(), crisisApplicationListModelList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return crisisLandingHtmlModel;
	}

	@Override
	public String getCrisisLandingJSONPByCrisisID(Long crisisID) {
		CrisisLandingHtmlModel crisisLandingHtmlModel = null;
		JSONObject json = new JSONObject();
		CrisisDTO crisis = null;
		try {
			//Crisis crisis =  crisisService.findByCrisisID(crisisID);
			crisis =  crisisService.findCrisisByID(crisisID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<ClientApp> clientAppList = clientAppService.getAllClientAppByCrisisID(crisisID);
		// customUITemplateService
		if(clientAppList != null & crisis!= null){
			if(clientAppList.size() > 0){
				Client client = clientService.findClientbyID("clientID", clientAppList.get(0).getClientID());
				List<CrisisApplicationListModel> crisisApplicationListModelList = null;

				crisisApplicationListModelList = getApplicationListHtmlByCrisisID(crisisID);

				json.put("crisisName", crisis.getName()) ;
				json.put("crisisCode", crisis.getCode()) ;

				JSONArray list = new JSONArray();

				for(int i= 0; i < crisisApplicationListModelList.size(); i++ ){
					JSONObject app = new JSONObject();
					CrisisApplicationListModel item = crisisApplicationListModelList.get(i);
					app.put("name" , item.getName());
					app.put("nominalAttributeID" , item.getNominalAttributeID());
					app.put("url" , item.getUrl());
					app.put("remaining" , item.getRemaining());
					app.put("totaltaskNumber" , item.getTotaltaskNumber());
					list.add(app) ;
				}

				json.put("app", list);

			}
		}

		List<CustomUITemplate> uiTemps =  customUITemplateService.getCustomTemplateForLandingPage(crisisID);

		for(CustomUITemplate iTemplate: uiTemps){
			if(iTemplate.getTemplateType().equals(CodeLookUp.CURATOR_NAME)){
				json.put("curator", iTemplate.getTemplateValue());
			}
			if(iTemplate.getTemplateType().equals(CodeLookUp.PUBLIC_LANDING_PAGE_TOP)){
				json.put("topStory", iTemplate.getTemplateValue());
			}
			if(iTemplate.getTemplateType().equals(CodeLookUp.PUBLIC_LANDING_PAGE_BOTTOM)){
				json.put("bottomStory", iTemplate.getTemplateValue());
			}
		}


		String returnValue = "";
		if(json.toString().trim().length() > 5){
			returnValue = "jsonp(" +  json.toJSONString() + ");";
		}

		return returnValue;
	}

	@Override
	public String getCrisisLandingJSONPByCrisisCode(String crisisCode) {
		String returnValue = "";
		List<CrisisDTO> crisisList = null;
		try {
			crisisList = crisisService.findByCriteria("code", crisisCode);
			if(crisisList != null){
				if(crisisList.size() > 0){
					CrisisDTO crisis = crisisList.get(0);
					returnValue = getCrisisLandingJSONPByCrisisID(crisis.getCrisisID()) ;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public CrisisLandingStatusModel getCrisisLandingStatusByCrisisCode(String crisisCode) {
		CrisisLandingStatusModel crisisLandingStatusModel = null;
		List<CrisisDTO> crisisList = null;
		try {
			crisisList = crisisService.findByCriteria("code", crisisCode);
			System.out.println("crisisList size = " + (crisisList != null ? crisisList.size() : "null"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean isReadyToShow = false;
		if(crisisList != null){
			if(crisisList.size() > 0){
				CrisisDTO crisis = crisisList.get(0);
				List<ClientApp> clientAppList = clientAppService.getAllClientAppByCrisisID(crisis.getCrisisID());
				System.out.println("clientAppList size = " + (clientAppList != null ? clientAppList.size() : "null"));
				if(clientAppList != null ){
					if(clientAppList.size() > 0){
						isReadyToShow = true;
					}
				}
			}
		}
		System.out.println("isReadyToShow = " + isReadyToShow);
		if(isReadyToShow){
			String url = URLReference.PUBLIC_LINK + crisisCode;
			crisisLandingStatusModel = new CrisisLandingStatusModel(url, StatusCodeType.CRISIS_PYBOSSA_SERVICE_READY, "ready" );
		}
		else{
			crisisLandingStatusModel = new CrisisLandingStatusModel("", StatusCodeType.CRISIS_PYBOSSA_SERVICE_NOT_READY, "Initializing trainer task. Please come back in a few minutes." );
		}

		return crisisLandingStatusModel;
	}
}
