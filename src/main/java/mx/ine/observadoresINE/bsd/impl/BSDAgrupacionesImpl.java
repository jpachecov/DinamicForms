package mx.ine.observadoresINE.bsd.impl;

import java.util.List;

import mx.ine.observadoresINE.as.ASAgrupacionesInterface;
import mx.ine.observadoresINE.bsd.BSDAgrupacionesInterface;
import mx.ine.observadoresINE.dto.DTODatosAgrupaciones;
import mx.ine.observadoresINE.dto.db.DTOAgrupaciones;
import mx.ine.observadoresINE.dto.db.DTOAgrupacionesPK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Clase BSD para el módulo de agrupaciones.
 * @author Jean Pierre Pacheco Avila
 */
@Component("bsdAgrupaciones")
@Scope("prototype")
public class BSDAgrupacionesImpl implements BSDAgrupacionesInterface {

	@Autowired
	@Qualifier("asAgrupaciones")
	private ASAgrupacionesInterface asAgrupaciones;
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DTOAgrupaciones obtenAgrupacion(DTOAgrupacionesPK pk) throws Exception{
		return asAgrupaciones.obtenAgrupacion(pk);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void guardaActualiza(DTODatosAgrupaciones dto) throws Exception{
		asAgrupaciones.guardaActualiza(dto);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DTOAgrupaciones> obtenAgrupaciones(Integer idProceso, Integer idDetalle) throws Exception {
		return asAgrupaciones.obtenAgrupaciones(idProceso, idDetalle);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actualizaAgrupacion(DTOAgrupacionesPK pk, DTODatosAgrupaciones datos) throws Exception {
		asAgrupaciones.actualizaAgrupacion(pk, datos);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DTODatosAgrupaciones mapeaDatos(DTOAgrupaciones dto) {
		return asAgrupaciones.mapeaDatos(dto);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean esNombreTitularValido(DTODatosAgrupaciones dto) throws Exception {
		return asAgrupaciones.esNombreTitularValido(dto);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean sonApellidosInvalidos(DTODatosAgrupaciones dto) throws Exception {
		return asAgrupaciones.sonApellidosInvalidos(dto);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean existeNombreAgrupacion(DTOAgrupacionesPK pk, DTODatosAgrupaciones dto) throws Exception {
		return asAgrupaciones.existeNombreAgrupacion(pk, dto);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean existeAbreviAgrupacion(DTOAgrupacionesPK pk, DTODatosAgrupaciones dto) throws Exception {
		return asAgrupaciones.existeAbreviAgrupacion(pk, dto);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void eliminaAgrupacion(DTOAgrupacionesPK pk, DTODatosAgrupaciones datos) throws Exception {
		asAgrupaciones.eliminaAgrupacion(pk, datos);
	}

}
