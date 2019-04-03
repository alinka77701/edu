package client.application;


import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

public class PasswordHandler implements CallbackHandler {
	@Override
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException{
			for (Callback callback : callbacks) {
				String password, login;
				WSPasswordCallback wspc = (WSPasswordCallback) callback;
				String identifier = wspc.getIdentifier();
			
			    String file ="credentials"+identifier+".txt";
			    Scanner scanner = new Scanner(new File(file));
			    scanner.useDelimiter(" ");
			    login = scanner.next();
			    password = scanner.next();
			    scanner.close();
		    	if(identifier.equals(login)){
		    		wspc.setPassword(password);	
		    	}
			}
	}
}
