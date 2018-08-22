package com.restful.demo.messager.resources;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.restful.demo.messager.resources.beans.MessageFilterBean;

@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResouces {
	
	@GET
	@Path("annotations")
	public String getParamsUsingAnnotations(@BeanParam MessageFilterBean filterBean){
		return "Matrix Params :"+filterBean.getMatrixParam()+" HeaderParam :"+filterBean.getAuth();	
	}
	
	@GET
	@Path("context")
	public String getParamsUsingContext(@Context UriInfo uriInfo,
										@Context HttpHeaders httpHeader) {
		
		String path = uriInfo.getAbsolutePath().toString();		
		String cookies = httpHeader.getCookies().toString();
		return "Path :"+path+" Cookies :"+ cookies;
		
	}

}
