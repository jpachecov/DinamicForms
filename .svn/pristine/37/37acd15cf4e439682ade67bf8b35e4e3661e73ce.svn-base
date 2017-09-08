package mx.ine.observadoresINE.bsd.impl;

import java.util.Date;
import java.util.List;

import mx.ine.observadoresINE.as.ASAccionesPromocionInterface;
import mx.ine.observadoresINE.bsd.BSDAccionesPromocionInterface;
import mx.ine.observadoresINE.dto.DTOUsuarioLogin;
import mx.ine.observadoresINE.dto.db.DTOAccionesPromocion;
import mx.ine.observadoresINE.dto.db.DTOAccionesPromocionPK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("bsdAccionesPromocion")
@Scope("prototype")
public class BSDAccionesPromocionImpl implements BSDAccionesPromocionInterface {

	@Autowired
	@Qualifier("asAccionesPromocion")
	private ASAccionesPromocionInterface asAccionesPromocion;

	@Override
	public void guardar(DTOAccionesPromocion accionDePromocion,
			DTOUsuarioLogin usuario) throws Exception {

		Integer idProceso = usuario.getIdProcesoElectoral();
		Integer idDetalle = usuario.getIdDetalleProceso();
		Integer idEstado = usuario.getIdEstadoSeleccionado();
		Integer idDistrito = usuario.getIdDistritoSeleccionado();

		DTOAccionesPromocionPK pk = new DTOAccionesPromocionPK();
		pk.setIdProcesoElectoral(idProceso);
		pk.setIdDetalleProceso(idDetalle);
		accionDePromocion.setDtoAccionesPromocionPK(pk);

		// Se completan datos del objeto a persistir
		accionDePromocion.setIdDistrito(idDistrito == null ? 0 : idDistrito);
		accionDePromocion.setIdEstado(idEstado == null ? 0 : idEstado);
		accionDePromocion.setUsuario(usuario.getUsuario());
		accionDePromocion.setFechaHora(new Date());

		asAccionesPromocion.guardar(accionDePromocion);
	}

	@Override
	public List<DTOAccionesPromocion> consultaAccionesPromocion(
			DTOAccionesPromocion filtros, DTOUsuarioLogin usuario)
			throws Exception {

		Integer idProceso = usuario.getIdProcesoElectoral();
		Integer idDetalle = usuario.getIdDetalleProceso();
		Integer idEstado = usuario.getIdEstadoSeleccionado();
		Integer idDistrito = usuario.getIdDistritoSeleccionado();

		return asAccionesPromocion.consultaAccionesPromocion(filtros,
				idProceso, idDetalle, idEstado, idDistrito);
	}

	@Override
	public void eliminar(DTOAccionesPromocion accionDePromocion)
			throws Exception {
		asAccionesPromocion.eliminar(accionDePromocion);
	}

	@Override
	public void modificar(DTOAccionesPromocion accionDePromocion)
			throws Exception {
		asAccionesPromocion.modificar(accionDePromocion);
	}

}
