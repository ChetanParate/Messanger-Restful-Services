package com.restful.demo.messager.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.Uri;

import com.restful.demo.messager.model.Message;
import com.restful.demo.messager.service.MessageService;

@Path("/messages")
@Consumes(value={MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
@Produces(value={MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
public class MessageResource {
	
	MessageService messageService = new MessageService();
	
	@GET
	public List<Message> getMessage(@QueryParam("year") @DefaultValue("0") int year,
									@QueryParam("start") @DefaultValue("0") int start,
									@QueryParam("size") @DefaultValue("0") int size){
		if(year>0){
			return messageService.getAllMessagesForYear(year);
		}
		if(start>=0 && size >0){
			return messageService.getAllMessagesPaginated(start, size);
		}
		
		return messageService.getAllMessage();
	}
	
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo){
		Message newMessage = messageService.addMessage(message);
		String id =String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();
		//return Response.status(Status.CREATED).entity(newMessage).build();
		//return Response.created(new URI("messager/webapi/messages/" + newMessage.getId())).entity(newMessage).build();
		return Response.created(uri).entity(newMessage).build();
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message){
		message.setId(id);
		return messageService.updateMessage(message);	
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id){
		messageService.removeMessage(id);
		
	}
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo){
		Message message =  messageService.getMessage(id);
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComments(uriInfo, message), "comments");
		
		return message;
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(MessageResource.class,"getCommentResource")
				.path(CommentResources.class)
				.resolveTemplate("messageId", message.getId())
				.build().toString();
		return uri;
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(ProfileResource.class)
				.path(message.getAuthor())
				.build().toString();
		return uri;
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(Long.toString(message.getId()))
				.build().toString();
		return uri;
	}
	
	@Path("/{messageId}/comments")
	public CommentResources getCommentResource(){
		return new CommentResources();
	}
	
	
	

}
