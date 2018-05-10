package co.edu.udea.iw.ws;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.iw.BL.ClienteBL;
import co.edu.udea.iw.DTO.Cliente;
import co.edu.udea.iw.EXCEPTION.ClassException;
import co.edu.udea.iw.dto.Customer;

@Path("Cliente")
@Component
public class CustomerService {

	@Autowired
	private ClienteBL clienteBL;
	
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public List<Customer> obtener() throws RemoteException {
		
		List<Customer> clientes = new ArrayList<Customer>();
		List<Cliente> clientesSpring = null;
		try {
			
			clientesSpring = clienteBL.obtener();
			
			for(Cliente cliente: clientesSpring) {
				Customer customer = new Customer();
				customer.setCedula(cliente.getCedula());
				customer.setNombres(cliente.getNombres());
				customer.setApellidos(cliente.getApellidos());
				customer.setEmail(cliente.getEmail());
				
				clientes.add(customer);
			}
		} catch (ClassException ex) {
			throw new RemoteException(ex.getMessage());
		}
		
		return clientes;
	}
	
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public String guardarCliente(@QueryParam("cedula") String cedula, @QueryParam("nombres") String nombres, @QueryParam("apellidos") String apellidos, @QueryParam("email") String email, @QueryParam("usuario") String usuario) {
		
		Cliente cliente = new Cliente();
		cliente.setCedula(cedula);
		cliente.setNombres(nombres);
		cliente.setApellidos(apellidos);
		cliente.setEmail(email);
		cliente.setUsuarioCrea(usuario);
		try {
			
			clienteBL.insertar(cliente);
		} catch (Exception e) {
			return e.getMessage();
		}
		
		return "Ok";
	}
	
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public String actualizarCliente(@QueryParam("cedula") String cedula, @QueryParam("nombres") String nombres, @QueryParam("apellidos") String apellidos, @QueryParam("email") String email, @QueryParam("usuario") String usuario) {
		
		Cliente cliente = new Cliente();
		cliente.setCedula(cedula);
		cliente.setNombres(nombres);
		cliente.setApellidos(apellidos);
		cliente.setEmail(email);
		cliente.setUsuarioModifica(usuario);
		cliente.setFechaModificacion(new Date());
		cliente.setEliminado(true);
		try {
			
			clienteBL.actualizar(cliente);
		} catch (Exception e) {
			return e.getMessage();
		}
		
		return "Ok";
	}
	
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public String eliminaarCliente(@QueryParam("cedula") String cedula, @QueryParam("nombres") String nombres, @QueryParam("apellidos") String apellidos, @QueryParam("email") String email, @QueryParam("usuario") String usuario) {
		
		Cliente cliente = new Cliente();
		cliente.setCedula(cedula);
		cliente.setNombres(nombres);
		cliente.setApellidos(apellidos);
		cliente.setEmail(email);
		cliente.setUsuarioElimina(usuario);
		cliente.setFechaEliminacion(new Date());
		try {
			
			clienteBL.eliminar(cliente);
		} catch (Exception e) {
			return e.getMessage();
		}
		
		return "Ok";
	}
}
