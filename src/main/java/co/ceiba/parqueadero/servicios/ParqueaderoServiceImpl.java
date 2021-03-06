package co.ceiba.parqueadero.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.ceiba.parqueadero.excepciones.ParqueaderoExcepciones;
import co.ceiba.parqueadero.excepciones.ParqueaderoLogicaExcepciones;

import co.ceiba.parqueadero.excepciones.ParqueaderoServiciosExcepciones;
import co.ceiba.parqueadero.logica.ParqueaderoLogica;
import co.ceiba.parqueadero.modelo.Parqueadero;
import co.ceiba.parqueadero.repositorio.ParqueaderoRepository;
import co.ceiba.parqueadero.utils.Constantes;
import co.ceiba.parqueadero.utils.Mensajes;

@Transactional
@Service
public class ParqueaderoServiceImpl implements ParqueaderoService {
	
	@Autowired
	ParqueaderoLogica parqueaderoLogica;
	
	@Autowired
	ParqueaderoRepository parqueaderorepository;
	
	@Override
	public boolean ingresarVehiculoParqueadero(String placa, int cilindraje) throws ParqueaderoServiciosExcepciones {
		if(placa.isEmpty()) {
			throw new ParqueaderoServiciosExcepciones(Mensajes.PLACA_VACIA);
		}
		if(!placa.matches(Constantes.PATRON_PLACA)) {
			throw new ParqueaderoServiciosExcepciones(Mensajes.PLACA_INVALIDA);
		}
		if(cilindraje <0) {
			throw new ParqueaderoServiciosExcepciones(Mensajes.CILINDRAJE_INVALIDO);
		}
		try {
			return parqueaderoLogica.ingresarVehiculo(placa, cilindraje);
		} catch (ParqueaderoLogicaExcepciones e) {
			throw new ParqueaderoServiciosExcepciones(e.getCause().toString().split(":")[1]);
		}
	}

	@Override
	public double salidaVehiculoParqueadero(String placa) throws ParqueaderoServiciosExcepciones {
		if(placa.isEmpty()) {
			throw new ParqueaderoServiciosExcepciones(Mensajes.PLACA_VACIA);
		}
		if(!placa.matches(Constantes.PATRON_PLACA)) {
			throw new ParqueaderoServiciosExcepciones(Mensajes.PLACA_INVALIDA);
		}
		try {
			return parqueaderoLogica.salidaParqueadero(placa);
		} catch (ParqueaderoLogicaExcepciones e) {
			throw new ParqueaderoServiciosExcepciones(Mensajes.ERROR_SALIDA_VEHICULO,e);
		}
}

	@Override
	public List<Parqueadero> obtenerVehiculos() throws ParqueaderoExcepciones {
		return parqueaderorepository.obtenerVehiculos();
	}

}
