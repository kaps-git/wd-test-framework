package com.korg.entity.deserializer;

import java.lang.reflect.Type;

import com.korg.entity.PaymentMethod;
import com.korg.entity.TestUser;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class UserDeserializer implements JsonDeserializer<TestUser> {
	  
	  @Override ////eclipse Preference->Java->Compiler "Compiler compliance level". You must choose "1.6" or higher for this annotation to work.
	  public TestUser deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
	      throws JsonParseException {
	    final JsonObject jsonObject = json.getAsJsonObject();
	    
	    PaymentMethod[] paymentMethods = context.deserialize(jsonObject.get("paymentmethods"), PaymentMethod[].class);
	    
	    final TestUser user = new TestUser();
	    user.setUserid(jsonObject.get("userid").getAsString());
	    user.setYuid(jsonObject.get("yuid").getAsString());
	    user.setPassword(jsonObject.get("password").getAsString());
	    user.setPaymentMethodList(paymentMethods);
	    return user;
	  }
	
}
