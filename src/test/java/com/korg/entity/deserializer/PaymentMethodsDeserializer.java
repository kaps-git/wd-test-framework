package com.korg.entity.deserializer;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.korg.entity.PaymentMethod;

public class PaymentMethodsDeserializer implements JsonDeserializer {
	
	@Override ////eclipse Preference->Java->Compiler "Compiler compliance level". You must choose "1.6" or higher for this annotation to work.
	public PaymentMethod deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
	      throws JsonParseException {
	    final JsonObject jsonObject = json.getAsJsonObject();

	    final PaymentMethod paymentMethod = new PaymentMethod();
	    paymentMethod.setCcType(jsonObject.get("cctype").getAsString());
	    paymentMethod.setCcNumber(jsonObject.get("ccNumber").getAsString());
	    paymentMethod.setCvv(jsonObject.get("cvv").getAsString());
	    paymentMethod.setExpiryMonth(jsonObject.get("expiryMonth").getAsString());
	    paymentMethod.setExpiryYear(jsonObject.get("expiryYear").getAsString());
	    return paymentMethod;
	  }
}//end class
