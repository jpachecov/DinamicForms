/**
 * @(#)DAOAcreditacionGafeteImpl.java 30/09/2016
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.dao.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import mx.ine.observadoresINE.dao.DAOAcreditacionGafeteInterface;
import mx.ine.observadoresINE.dto.db.DTOAcreditacionGafetes;
import mx.ine.observadoresINE.dto.db.DTOAcreditacionGafetesPK;
import mx.ine.observadoresINE.dto.form.FormAcreditacionGafete;
import mx.ine.observadoresINE.helper.HLPAcreditacionGafete;
import mx.ine.observadoresINE.helper.HLPAcreditacionesGafeteAutocomplete;
import mx.ine.observadoresINE.util.Utilidades;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.CalendarType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * <code>DAOAcreditacionGafeteImpl.java</code> 
 *
 * @author Pablo Zuñiga Mata
 * @version 0.0.1
 * @since 30/09/2016
 */
@Scope("prototype")
@Repository("daoAcreditacionGafeteImpl")
public class DAOAcreditacionGafeteImpl extends DAOGeneric<DTOAcreditacionGafetes, DTOAcreditacionGafetesPK> implements DAOAcreditacionGafeteInterface{

	/*
	 * (non-Javadoc)
	 * @see mx.ine.observadoresINE.dao.DAOAcreditacionGafete#getObservadoresByNombreApellidosLike(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<HLPAcreditacionesGafeteAutocomplete> getObservadoresByNombreApellidosLike(
		Integer proceso, Integer detalle, Integer estado, Integer distrito, String nombre)  throws Exception{
		String sql = this.getContainer().getQuery("query_observadores_nombreLike");
		Query query = getSession().createSQLQuery(sql)
				      .addScalar("nombreObservador", StringType.INSTANCE )
				      .addScalar("idObservador", IntegerType.INSTANCE )
				      .addScalar("rutaFoto", StringType.INSTANCE )
				      .addScalar("claveElector", StringType.INSTANCE);
				      
		
		query.setString("nombre","%"+ nombre +"%" );
        query.setInteger("PROCESO", proceso);
        query.setInteger("DETALLE", detalle);
        query.setInteger("idEstado", estado);
        query.setInteger("idDistrito", distrito);
        
        query.setResultTransformer(Transformers.aliasToBean(HLPAcreditacionesGafeteAutocomplete.class));
        
		
		return (List<HLPAcreditacionesGafeteAutocomplete>)query.list();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.ine.observadoresINE.dao.DAOAcreditacionGafete#getObservadoresByNombreAgrupacionLike(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<HLPAcreditacionesGafeteAutocomplete> getObservadoresByNombreAgrupacionLike(
			Integer proceso, Integer detalle, Integer estado, Integer distrito, String nombre)  throws Exception{
		String sql = this.getContainer().getQuery("query_observadores_nombreAgrupacionLike");
		Query query = getSession().createSQLQuery(sql)
				      .addScalar("nombreAgrupacion", StringType.INSTANCE )
				      .addScalar("idAgrupacion", IntegerType.INSTANCE );
				      
		
		query.setString("nombre","%"+ nombre +"%" );
        query.setInteger("PROCESO", proceso);
        query.setInteger("DETALLE", detalle);
        query.setInteger("idEstado", estado);
        query.setInteger("idDistrito", distrito);
        
        query.setResultTransformer(Transformers.aliasToBean(HLPAcreditacionesGafeteAutocomplete.class));
        
		
		return (List<HLPAcreditacionesGafeteAutocomplete>)query.list();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.ine.observadoresINE.dao.DAOAcreditacionGafete#getConfirmaExisteVocal(mx.observadores.dto.DTOFiltroAcreditacionGafete)
	 */
	@SuppressWarnings("unchecked")
	public String getConfirmaExisteVocal(FormAcreditacionGafete dtoFiltro) throws Exception {
		
		String query = "";
		
		if(dtoFiltro.getAcreditacionGafete() == 1){
			query = this.getContainer().getQuery("query_validaConsejeroPresidente");
		}else if(dtoFiltro.getAcreditacionGafete() == 2){
			query = this.getContainer().getQuery("query_validaConsejeroPresidenteGafete");
		}
		Query queryGafe = getSession().createSQLQuery(query)
				.addScalar("vocalPresidenteD", StringType.INSTANCE);	  
		queryGafe.setResultTransformer(Transformers.aliasToBean(HLPAcreditacionGafete.class))
		.setInteger("ID_ESTADO",dtoFiltro.getIdEstado())
		.setInteger("ID_DISTRITO",dtoFiltro.getIdDistrito());
		
		List<HLPAcreditacionGafete> lista= (List<HLPAcreditacionGafete>)queryGafe.list();
		
		if(dtoFiltro.getAcreditacionGafete() == 1 && (lista.size() < 1 && lista.size() > 2)){
			return Utilidades.mensajeProperties("mensaje_validaConsejeroPresidente");
		}else if(dtoFiltro.getAcreditacionGafete() == 2 && lista.size() != 1){
			return Utilidades.mensajeProperties("mensaje_validaConsejeroPresidenteGafete");
		}
		
		return "";
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.ine.observadoresINE.dao.DAOObservadoresInterface#getGafete(mx.ine.observadoresINE.dto.DTOFiltroAcreditacionGafete)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HLPAcreditacionGafete> getInfoAcreditacionGafete(FormAcreditacionGafete dtoFiltro) throws Exception {
		
		String query = "";
		
		String indAgrup = "" ; 
		String  datebetween = "" ;
		if(dtoFiltro.getIdBusqueda() != null){
			if (dtoFiltro.getTipoBusqueda() == 1)
				indAgrup =" OBS.ID_OBSERVADOR = " + dtoFiltro.getIdBusqueda() + " and ";
				 
			if (dtoFiltro.getTipoBusqueda() == 2)
				indAgrup = " OBS.ID_AGRUPACION = " + dtoFiltro.getIdBusqueda() + " and ";
		}else{
			indAgrup = " OBS.ID_AGRUPACION IS NULL AND ";
		}
		if (dtoFiltro.getPorFecha()){

			SimpleDateFormat formatdate = new SimpleDateFormat("dd-MM-yyyy");
		
			datebetween = "AND OBS.FECHA_SESION BETWEEN TO_DATE('"+formatdate.format(dtoFiltro.getInicioFecha().getTime())+"','DD/MM/YYYY') "
					+ "AND TO_DATE('"+formatdate.format(dtoFiltro.getFinFecha().getTime())+"','DD/MM/YYYY')";
		}

		SimpleDateFormat formatdate = new SimpleDateFormat("dd-MM-yyyy");
		query =this.getContainer().getQuery("query_informacion_acreditacionGafete")
				.replace("<!COND_NUM_OBSERVADORES>", indAgrup)
				.replace("<!COND_RANGO_FECHAS>", datebetween);
		Query queryGafe = getSession().createSQLQuery(query)
				.addScalar("idJustificacion", IntegerType.INSTANCE)
				.addScalar("idOservador", IntegerType.INSTANCE)
				.addScalar("sexoObs", StringType.INSTANCE)
				.addScalar("acreditado", StringType.INSTANCE)
				.addScalar("claveelector", StringType.INSTANCE)
				.addScalar("apellidoPaterno", StringType.INSTANCE)
				.addScalar("apellidoMaterno", StringType.INSTANCE)
				.addScalar("fechaacreditacion", CalendarType.INSTANCE)
				.addScalar("fechasesion", CalendarType.INSTANCE)
				.addScalar("nombre", StringType.INSTANCE)
				.addScalar("sexo", StringType.INSTANCE)
				.addScalar("folio", StringType.INSTANCE)
				.addScalar("foto", StringType.INSTANCE)
				.addScalar("vocalPresidenteD", StringType.INSTANCE)
				.addScalar("vocalSecretarioD", StringType.INSTANCE)
				.addScalar("vocalPresidenteE", StringType.INSTANCE)
				.addScalar("vocalSecretarioE", StringType.INSTANCE)
				.addScalar("fotoVocalPresidenteD", StringType.INSTANCE)
				.addScalar("fotoVocalSecretarioD", StringType.INSTANCE)
				.addScalar("fotoVocalPresidenteE", StringType.INSTANCE)
				.addScalar("fotoVocalSecretarioE", StringType.INSTANCE)
				.addScalar("nombreestado", StringType.INSTANCE)
				.addScalar("estado", IntegerType.INSTANCE)
				.addScalar("distrito", IntegerType.INSTANCE);
		         
		queryGafe.setResultTransformer(Transformers.aliasToBean(HLPAcreditacionGafete.class))
		.setParameter("fechaexped",formatdate.format(dtoFiltro.getFechaExpedicion().getTime()))
        .setInteger("PROCESO", dtoFiltro.getIdProceso())
        .setInteger("DETALLE", dtoFiltro.getIdDetalleProceso())
		.setInteger("ID_ESTADO",dtoFiltro.getIdEstado())
		.setInteger("ID_DISTRITO",dtoFiltro.getIdDistrito());
		
		return (List<HLPAcreditacionGafete>)queryGafe.list();
	}

	/* (non-Javadoc)
	 * @see mx.ine.observadoresINE.dao.DAOAcreditacionGafeteInterface#guarda(mx.ine.observadoresINE.dto.db.DTOAcreditacionGafetes)
	 */
	@Override
	public void guarda(DTOAcreditacionGafetes acreGafete) throws Exception {
		guardarOactualizar(acreGafete);
	}

	/* (non-Javadoc)
	 * @see mx.ine.observadoresINE.dao.DAOAcreditacionGafeteInterface#modifica(mx.ine.observadoresINE.dto.db.DTOAcreditacionGafetes)
	 */
	@Override
	public void modifica(DTOAcreditacionGafetes acreGafete) throws Exception {
		modifica(acreGafete);
	}

	/* (non-Javadoc)
	 * @see mx.ine.observadoresINE.dao.DAOAcreditacionGafeteInterface#elimina(mx.ine.observadoresINE.dto.db.DTOAcreditacionGafetes)
	 */
	@Override
	public void elimina(DTOAcreditacionGafetes acreGafete) throws Exception {
		elimina(acreGafete);
	}
}
