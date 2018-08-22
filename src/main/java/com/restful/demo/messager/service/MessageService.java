package com.restful.demo.messager.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.restful.demo.messager.database.DataBaseClass;
import com.restful.demo.messager.exception.DataNotFoundException;
import com.restful.demo.messager.model.Message;

public class MessageService {
	
	private Map<Long, Message> messages = DataBaseClass.getMessages();
	
	public MessageService() {
		messages.put(001L, new Message(001L,"Hello Chetan",new Date(),"Chetan"));
		messages.put(002L, new Message(002L,"Hey Chetan",new Date(), "Chetan"));
	}

	public List<Message> getAllMessage(){	
		return new ArrayList<Message>(messages.values());
	}
	
	public List<Message> getAllMessagesForYear(int year){	
		List<Message> messageForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for (Message message :messages.values()){
			cal.setTime(message.getCreated());
			if(cal.get(Calendar.YEAR) == year){
				messageForYear.add(message);
			}
		}
		return messageForYear;
	}
	
	public List<Message> getAllMessagesPaginated(int start, int size){	
		
		List<Message> list = new ArrayList<Message>(messages.values());
		if(start+size > list.size())return new ArrayList<Message>();
		return list.subList(start, start+size);
	}
	
	
	public Message getMessage(Long id){
		Message message = messages.get(id);
		if(message == null){
			throw new DataNotFoundException("Message with id :"+ id +" not found."); 
		}
		return message;
	}
	
	public Message addMessage(Message message){
		message.setId(messages.size()+1);
		messages.put(message.getId(),message);
		return message;
	}

	public Message updateMessage(Message message){
		if(message.getId() <=0){
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message removeMessage(Long id){
		return messages.remove(id);
	}
}
