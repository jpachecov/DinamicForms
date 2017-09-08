package mx.ine.observadoresINE.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import mx.ine.observadoresINE.dao.DAOConfiguarionCatalogosInterface;
import mx.ine.observadoresINE.dto.db.DTOAccionesPromocion;
import mx.ine.observadoresINE.dto.db.DTOCAcciones;
import mx.ine.observadoresINE.dto.db.DTOCCargoResponsable;
import mx.ine.observadoresINE.dto.db.DTOCEscolaridades;
import mx.ine.observadoresINE.dto.db.DTOCEvaluacion;
import mx.ine.observadoresINE.dto.db.DTOCJustificaciones;
import mx.ine.observadoresINE.dto.db.DTOCursos;
import mx.ine.observadoresINE.dto.db.DTOObservadores;
import mx.ine.observadoresINE.dto.db.DTOReglasEvalucaion;
import mx.org.ine.servicios.dto.DTOBase;

/**
 * <code>DAOConfiguracionCatalogosImpl.java</code>Descripcion de la clase
 *
 * @author Gerardo López
 * @version 1.0
 * @since 27/06/2017
 */
@Repository("daoConfiguracionCatalogos")
@Scope("prototype")
public class DAOConfiguracionCatalogosImpl extends DAOGeneric<DTOBase, DTOBase>
		implements DAOConfiguarionCatalogosInterface {

	/**
	 * LOGGER
	 */
	private static final Log log = LogFactory
			.getLog(DAOConfiguracionCatalogosImpl.class);

	// ******************** GUARDAR Y/O ACTUALIZAR CATALOGOS ***************

	@Override
	public void guardaActualizaCAcciones(DTOCAcciones acciones)
			throws Exception {
		try {
			guardarOactualizar(acciones);
		} catch (HibernateException e) {
			log.error("Error al guardar catálogo de acciones en guardaActualizaCAcciones de DAOConfiguracionCatalogoImpl");
			log.error(e);
		}

	}

	@Override
	public void guardaActualizaCCargoResponsable(
			DTOCCargoResponsable cargoResponsable) throws Exception {
		try {
			guardarOactualizar(cargoResponsable);
		} catch (HibernateException e) {
			log.error("Error al guardar catálogo de cargo responsable en guardaActualizaCCargoResponsable de DAOConfiguracionCatalogoImpl");
			log.error(e);
		}

	}

	@Override
	public void guardaActualizaCEscolaridades(DTOCEscolaridades escolaridades)
			throws Exception {
		try {
			guardarOactualizar(escolaridades);
		} catch (HibernateException e) {
			log.error("Error al guardar catálogo de escolaridades en guardaActualizaCEscolaridades de DAOConfiguracionCatalogoImpl");
			log.error(e);
		}

	}

	@Override
	public void guardaActualizaCJustificaciones(
			DTOCJustificaciones justificaciones) throws Exception {
		try {
			guardarOactualizar(justificaciones);
		} catch (HibernateException e) {
			log.error("Error al guardar catálogo de justificaciones en guardaActualizaCJustificaciones de DAOConfiguracionCatalogoImpl");
			log.error(e);
		}

	}

	@Override
	public void guardaActualizaCEvaluaciones(DTOCEvaluacion evaluaciones)
			throws Exception {
		try {
			guardarOactualizar(evaluaciones);
		} catch (HibernateException e) {
			log.error("Error al guardar catálogo de evaluaciones en guardaActualizaCEvaluaciones de DAOConfiguracionCatalogoImpl");
			log.error(e);
		}

	}

	@Override
	public void guardaActualizaReglasEvaluacion(
			DTOReglasEvalucaion reglasEvaluacion) throws Exception {
		try {
			guardarOactualizar(reglasEvaluacion);
		} catch (HibernateException e) {
			log.error("Error al guardar reglas de evaluacion en guardaActualizaReglasEvaluacion de DAOConfiguracionCatalogoImpl");
			log.error(e);
		}

	}

	// ***************************** ELIMINAR CATALOGOS ***********************

	@Override
	public void eliminaCAcciones(List<DTOCAcciones> acciones) {
		try {
			for (DTOCAcciones accion : acciones) {
				eliminar(accion);
			}
		} catch (HibernateException e) {
			log.error("Error al eliminar catálogo de acciones en eliminaCAcciones de DAOConfiguracionCatalogoImpl");
			log.error(e);
		}

	}

	@Override
	public void eliminaCCargoResponsable(
			List<DTOCCargoResponsable> cargoResponsable) {
		try {
			for (DTOCCargoResponsable cargoRespon : cargoResponsable) {
				eliminar(cargoRespon);
			}
		} catch (HibernateException e) {
			log.error("Error al eliminar catálogo de cargo responsable en eliminaCCargoResponsable de DAOConfiguracionCatalogoImpl");
			log.error(e);
		}

	}

	@Override
	public void eliminaCEscolaridades(List<DTOCEscolaridades> escolaridades) {
		try {
			for (DTOCEscolaridades escolaridad : escolaridades) {
				eliminar(escolaridad);
			}
		} catch (HibernateException e) {
			log.error("Error al eliminar catálogo de escolaridades en eliminaCEscolaridades de DAOConfiguracionCatalogoImpl");
			log.error(e);
		}

	}

	@Override
	public void eliminaCJustificaciones(
			List<DTOCJustificaciones> justificaciones) {
		try {
			for (DTOCJustificaciones justificacion : justificaciones) {
				eliminar(justificacion);
			}
		} catch (HibernateException e) {
			log.error("Error al eliminar catálogo de justificaciones en eliminaCJustificaciones de DAOConfiguracionCatalogoImpl");
			log.error(e);
		}

	}

	@Override
	public void eliminaCEvaluaciones(List<DTOCEvaluacion> evaluaciones) {
		try {
			for (DTOCEvaluacion evaluacion : evaluaciones) {
				eliminar(evaluacion);
			}
		} catch (HibernateException e) {
			log.error("Error al eliminar catálogo de evaluaciones en eliminaCEvaluaciones de DAOConfiguracionCatalogoImpl");
			log.error(e);
		}

	}

	@Override
	public void eliminaReglasEvaluacion(
			List<DTOReglasEvalucaion> reglasEvaluacion) {
		try {
			for (DTOReglasEvalucaion regla : reglasEvaluacion) {
				eliminar(regla);
			}
		} catch (HibernateException e) {
			log.error("Error al eliminar reglas de evaluación en eliminaReglasEvaluacion de DAOConfiguracionCatalogoImpl");
			log.error(e);
		}

	}

	// ***************************** OBTENER CATALOGOS *************************

	@SuppressWarnings("unchecked")
	@Override
	public List<DTOCAcciones> getCAcciones(Integer idProceso, Integer idDetalle)
			throws Exception {

		if (idProceso != null && idDetalle != null) {
			log.info("ENTRA A LA CONDICION DIFERENTES DE NULL");
			Criteria criteria;
			criteria = getSession().createCriteria(DTOCAcciones.class);

			criteria.add(Restrictions.eq("dTOCAccionesPK.idProcesoElectoral",
					idProceso));
			criteria.add(Restrictions.eq("dTOCAccionesPK.idDetalleProceso",
					idDetalle));

			return (List<DTOCAcciones>) criteria.list();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DTOCCargoResponsable> getCCargoResponsable(Integer idProceso,
			Integer idDetalle) throws Exception {

		if (idProceso != null && idDetalle != null) {
			Criteria criteria;
			criteria = getSession().createCriteria(DTOCCargoResponsable.class);

			criteria.add(Restrictions.eq("id.idProcesoElectoral", idProceso));
			criteria.add(Restrictions.eq("id.idDetalleProceso", idDetalle));

			return (List<DTOCCargoResponsable>) criteria.list();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DTOCEscolaridades> getCEscolaridades(Integer idProceso,
			Integer idDetalle) throws Exception {

		if (idProceso != null && idDetalle != null) {
			Criteria criteria;
			criteria = getSession().createCriteria(DTOCEscolaridades.class);

			criteria.add(Restrictions.eq(
					"dTOCEscolaridadesPK.idProcesoElectoral", idProceso));
			criteria.add(Restrictions.eq(
					"dTOCEscolaridadesPK.idDetalleProceso", idDetalle));

			return (List<DTOCEscolaridades>) criteria.list();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DTOCJustificaciones> getCJustificaciones(Integer idProceso,
			Integer idDetalle) throws Exception {

		if (idProceso != null && idDetalle != null) {
			Criteria criteria;
			criteria = getSession().createCriteria(DTOCJustificaciones.class);

			criteria.add(Restrictions.eq(
					"dTOCJustificacionesPK.idProcesoElectoral", idProceso));
			criteria.add(Restrictions.eq(
					"dTOCJustificacionesPK.idDetalleProceso", idDetalle));

			return (List<DTOCJustificaciones>) criteria.list();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DTOCEvaluacion> getCEvaluaciones(Integer idProceso,
			Integer idDetalle) throws Exception {

		if (idProceso != null && idDetalle != null) {
			Criteria criteria;
			criteria = getSession().createCriteria(DTOCEvaluacion.class);

			criteria.add(Restrictions.eq("dTOCEvaluacionPK.idProcesoElectoral",
					idProceso));
			criteria.add(Restrictions.eq("dTOCEvaluacionPK.idDetalleProceso",
					idDetalle));

			return (List<DTOCEvaluacion>) criteria.list();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DTOReglasEvalucaion> getReglasEvaluacion(Integer idProceso,
			Integer idDetalle, Integer idEvaluacion) throws Exception {

		if (idProceso != null && idDetalle != null && idEvaluacion != null) {
			Criteria criteria;
			criteria = getSession().createCriteria(DTOReglasEvalucaion.class);

			criteria.add(Restrictions.eq(
					"dTOReglasEvalucaionPK.idProcesoElectoral", idProceso));
			criteria.add(Restrictions.eq(
					"dTOReglasEvalucaionPK.idDetalleProceso", idDetalle));
			criteria.add(Restrictions.eq("dTOReglasEvalucaionPK.idEvaluacion",
					idEvaluacion));

			return (List<DTOReglasEvalucaion>) criteria.list();
		}

		return null;
	}

	// ******************* Encontrar registros en los demás módulos *******
	@SuppressWarnings("unchecked")
	@Override
	public boolean promocionesXCAcciones(Integer idProceso, Integer idDetalle)
			throws Exception {
		boolean existe = false;
		if (idProceso != null && idDetalle != null) {
			Criteria criteria;
			criteria = getSession().createCriteria(DTOAccionesPromocion.class);

			criteria.add(Restrictions.eq(
					"dtoAccionesPromocionPK.idProcesoElectoral", idProceso));
			criteria.add(Restrictions.eq(
					"dtoAccionesPromocionPK.idDetalleProceso", idDetalle));

			List<DTOAccionesPromocion> promocionesEncontradas = (List<DTOAccionesPromocion>) criteria
					.list();
			if (promocionesEncontradas != null
					&& !promocionesEncontradas.isEmpty()) {
				existe = true;
			}
		}

		return existe;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean cursosXCCargoResponsable(Integer idProceso, Integer idDetalle)
			throws Exception {
		boolean existe = false;
		if (idProceso != null && idDetalle != null) {
			Criteria criteria;
			criteria = getSession().createCriteria(DTOCursos.class);

			criteria.add(Restrictions.eq("pk.idProcesoElectoral", idProceso));
			criteria.add(Restrictions.eq("pk.idDetalleProceso", idDetalle));

			List<DTOCursos> cursosEncontrados = (List<DTOCursos>) criteria
					.list();
			if (cursosEncontrados != null && !cursosEncontrados.isEmpty()) {
				existe = true;
			}
		}

		return existe;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean observadoresXCEscolarXCJustificaXCEvaluaciones(
			Integer idProceso, Integer idDetalle) throws Exception {
		boolean existe = false;
		if (idProceso != null && idDetalle != null) {

			short idProcesoElectoral = idProceso.shortValue();
			short idDetalleProceso = idDetalle.shortValue();

			Criteria criteria;
			criteria = getSession().createCriteria(DTOObservadores.class);

			criteria.add(Restrictions.eq(
					"dTOObservadoresPK.idProcesoElectoral", idProcesoElectoral));
			criteria.add(Restrictions.eq("dTOObservadoresPK.idDetalleProceso",
					idDetalleProceso));

			List<DTOObservadores> observadoresEncontrados = (List<DTOObservadores>) criteria
					.list();

			if (observadoresEncontrados != null
					&& !observadoresEncontrados.isEmpty()) {
				existe = true;
			}
		}

		return existe;
	}

}
