package mx.ine.observadoresINE.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mx.ine.observadoresINE.bsd.BSDReporteAgrupacionesInterface;
import mx.ine.observadoresINE.util.Utilidades;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HLPReporteAgrupaciones implements Serializable {

	private static final long serialVersionUID = 200720170123L;
	private static final Log logger = LogFactory.getLog(HLPReporteAgrupaciones.class);
	public static final Integer COLUMNAS = 4;
	
	private String tituloReporte;
	private List<HLPReportesEncabezado> listaEncabezados;
	private List<Object[]> listaDatos;

	public HLPReporteAgrupaciones() {

	}

	public HLPReporteAgrupaciones(
			BSDReporteAgrupacionesInterface bsdReporteAgrupaciones,
			Integer idFiltroAgrupacion) throws Exception {
		this.listaDatos = bsdReporteAgrupaciones
				.consultaDatosReporte(idFiltroAgrupacion);
		obtenTituloReporte();
		crearTablaAgrupacion();

	}

	private void obtenTituloReporte() {
		setTituloReporte(Utilidades.mensajeProperties("etiqueta_reportes_agrupaciones_titulo"));
	}

	private void crearTablaAgrupacion() {
		logger.info("Se inicializan encabezados");
		listaEncabezados = new ArrayList<>();
		HLPReportesEncabezado hlpEncabezado = null;
		// Primer encabezado
		/*hlpEncabezado = new HLPReportesEncabezado();
		hlpEncabezado.ingresarEncabezado(0, 3, 1, Utilidades.mensajeProperties("etiqueta_reportes_agrupaciones_listados"), 1);
		hlpEncabezado.ingresarEncabezado(1, 1, 1,
				Utilidades.mensajeProperties("etiqueta_reportes_agrupaciones_total") + listaDatos.size(), 1);
		listaEncabezados.add(hlpEncabezado);*/

		// Segundo encabezado
		hlpEncabezado = new HLPReportesEncabezado();
		hlpEncabezado.ingresarEncabezado(0, 1, 1, Utilidades.mensajeProperties("etiqueta_reportes_agrupaciones_nombre"), 1);
		hlpEncabezado.ingresarEncabezado(1, 1, 1, Utilidades.mensajeProperties("etiqueta_reportes_agrupaciones_siglas"), 1);
		hlpEncabezado.ingresarEncabezado(2, 1, 1, Utilidades.mensajeProperties("etiqueta_reportes_agrupaciones_titular"), 1);
		hlpEncabezado.ingresarEncabezado(3, 1, 1, Utilidades.mensajeProperties("etiqueta_reportes_agrupaciones_domicilio"), 1);
		listaEncabezados.add(hlpEncabezado);

	}

	public List<HLPReportesEncabezado> getListaEncabezados() {
		return listaEncabezados;
	}

	public void setListaEncabezados(List<HLPReportesEncabezado> listaEncabezados) {
		this.listaEncabezados = listaEncabezados;
	}

	public List<Object[]> getListaDatos() {
		return listaDatos;
	}

	public void setListaDatos(List<Object[]> listaDatos) {
		this.listaDatos = listaDatos;
	}

	public String getTituloReporte() {
		return tituloReporte;
	}

	public void setTituloReporte(String tituloReporte) {
		this.tituloReporte = tituloReporte;
	}

}
