package co.edu.udea.iw.ws;

import java.rmi.RemoteException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.iw.BL.UsuarioBL;

@Path("Usuario")
@Component
public class UserService {

	@Autowired
	private UsuarioBL usuarioBL;
	
	@Produces(MediaType.TEXT_PLAIN)
	@GET
	public String validar(String login, String clave) throws RemoteException {
		
		try {
			
			usuarioBL.autenticar(login, clave);
		} catch (Exception e) {
			return e.getMessage();
		}
		
		return "";
	}
}
