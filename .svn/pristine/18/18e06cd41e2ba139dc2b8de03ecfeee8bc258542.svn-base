/**
 * @(#)CustomPDFExporter.java 22/03/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.helper;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import mx.ine.common.fechas.impl.ValidacionFechas;
import mx.ine.common.ws.administracion.dto.response.DTODetalleDistritoProcesoWS;
import mx.ine.common.ws.administracion.dto.response.DTODetalleEstadoProcesoWS;
import mx.ine.observadoresINE.util.Constantes;
import mx.ine.observadoresINE.util.Utilidades;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.component.api.DynamicColumn;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.PDFExporter;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Clase de ayuda para exportar a PDF
 * 
 * @author José Antonio López Torres
 * @since 23/05/2017
 * @copyright Direccion de sistemas - INE
 */
public class HLPPDFExporter extends PDFExporter {
	/**
	 * Elemento de generacion de log
	 */
	private static final Log LOGGER = LogFactory.getLog(HLPPDFExporter.class);

	private Font facetFont;
	private Font cellFontHist;
	private Font facetFontHist;
	private Map<String, Serializable> parametros;
	private Color borderColor = new Color(162, 162, 162);
	private Color dataColor = new Color(56, 56, 56);

	/**
	 * Constructor para reportes de historicos
	 *
	 * @param parametros
	 *            : Parámetros
	 */
	public HLPPDFExporter(Map<String, Serializable> parametros) {
		this.parametros = parametros;
	}

	@Override
	public void export(FacesContext context, DataTable table, String filename, boolean pageOnly, boolean selectionOnly,
			String encodingType, MethodExpression preProcessor, MethodExpression postProcessor) throws IOException {
		try {
			// Se agrega tamaño de pagina, orientacion y margenes
			Document document = new Document(PageSize.A4.rotate(), 20, 20, 20, 20);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			this.facetFont = FontFactory.getFont(FontFactory.TIMES, encodingType, Font.DEFAULTSIZE, Font.BOLD);
			this.cellFontHist = FontFactory.getFont(FontFactory.HELVETICA, encodingType, 8, Font.NORMAL);
			this.facetFontHist = FontFactory.getFont(FontFactory.HELVETICA, encodingType, 10, Font.NORMAL, dataColor);
			if (preProcessor != null) {
				preProcessor.invoke(context.getELContext(), new Object[] { document });
			}

			agregaNumeroDePagina(writer, facetFontHist);

			if (!document.isOpen()) {
				document.open();
			}

			document.add(creaTituloHistoricos());
			document.add(creaEncabezadoHistoricos());

			document.add(exportPDFTable(context, table, pageOnly, selectionOnly, encodingType));

			if (postProcessor != null) {
				postProcessor.invoke(context.getELContext(), new Object[] { document });
			}

			document.close();

			writePDFToResponse(context.getExternalContext(), baos, filename);

		} catch (DocumentException e) {
			throw new IOException(e.getMessage(), e);
		}
	}

	@Override
	protected PdfPTable exportPDFTable(FacesContext context, DataTable table, boolean pageOnly, boolean selectionOnly,
			String encoding) {

		int columnsCount = getColumnsCount(table);
		PdfPTable pdfTable = new PdfPTable(columnsCount);

		pdfTable.setWidthPercentage(95);
		pdfTable.setSpacingBefore(20);
		if (parametros != null && parametros.containsKey(Constantes.PARAMETRO_ANCHOS_COLUMNAS)) {

			@SuppressWarnings("unchecked")
			Map<Integer, Float> map = (Map<Integer, Float>) parametros.get(Constantes.PARAMETRO_ANCHOS_COLUMNAS);
			try {
				float[] widths = new float[columnsCount];
				for (int i = 0; i < columnsCount; i++) {
					widths[i] = 1f;
				}
				for (Entry<Integer, Float> entry : map.entrySet()) {
					widths[entry.getKey()] = entry.getValue();
				}
				pdfTable.setWidths(widths);
			} catch (DocumentException e) {
				LOGGER.error("Ocurrio un error al exportar el documento", e);
			}
		}

		addColumnFacets(table, pdfTable, ColumnType.HEADER);
		creaTablaDatos(table, pdfTable);
                addColumnFooters(table, pdfTable, ColumnType.FOOTER);

		// if (pageOnly) {
		// exportPageOnly(context, table, pdfTable);
		// } else if (selectionOnly) {
		// exportSelectionOnly(context, table, pdfTable);
		// } else {
		// exportAll(context, table, pdfTable);
		// }
		//
		// if (table.hasFooterColumn()) {
		// addColumnFacets(table, pdfTable, ColumnType.FOOTER);
		// }

		table.setRowIndex(-1);

		return pdfTable;
	}

	@Override
	protected void exportCells(DataTable table, Object document) {
		PdfPTable pdfTable = (PdfPTable) document;
		for (UIColumn col : table.getColumns()) {
			if (col instanceof DynamicColumn) {
				((DynamicColumn) col).applyStatelessModel();
			}

			if (col.isRendered() && col.isExportable()) {
				addColumnValue(pdfTable, col.getChildren(), this.cellFontHist);
			}
		}
	}

	@Override
	protected void addColumnFacets(DataTable table, PdfPTable pdfTable, ColumnType columnType) {
		if (table.getColumnGroup(columnType.facet()) != null) {
			for (UIComponent row : table.getColumnGroup(columnType.facet()).getChildren()) {
				for (UIComponent column : row.getChildren()) {
					UIComponent facet = column.getFacet(columnType.facet());
					String value = facet == null ? "" : exportValue(FacesContext.getCurrentInstance(), facet);
					if (facet != null) {
						PdfPCell cell = new PdfPCell(new Paragraph(value, this.facetFontHist));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBackgroundColor(Color.LIGHT_GRAY);
						cell.setBorderColor(borderColor);
						cell.setColspan((int) column.getAttributes().get("colspan"));
                                                cell.setRowspan((int) column.getAttributes().get("rowspan"));
						pdfTable.addCell(cell);
					}
				}
				pdfTable.completeRow();
			}
		}

		/*
		 * for (UIColumn col : table.getColumns()) { if (col instanceof
		 * DynamicColumn) { ((DynamicColumn) col).applyStatelessModel(); } if
		 * (col.isRendered() && col.isExportable()) { UIComponent facet =
		 * col.getFacet(columnType.facet()); String value = facet == null ? "" :
		 * exportValue(FacesContext.getCurrentInstance(), facet);
		 * 
		 * if (facet != null) { PdfPCell cell = new PdfPCell(new
		 * Paragraph(value, this.facetFontHist));
		 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		 * cell.setBackgroundColor(Color.LIGHT_GRAY); pdfTable.addCell(cell); }
		 * } }
		 */
	}

	/**
	 * Método encargado de enumerar las páginas del reporte
	 * 
	 * @param writer
	 * @param facetFont
	 */
	private void agregaNumeroDePagina(PdfWriter writer, final Font font) {
		PdfPageEventHelper event = new PdfPageEventHelper() {
			@Override
			public void onEndPage(PdfWriter writer, Document document) {
				ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
						new Phrase(String.format("%d", writer.getPageNumber()), font), 421f, 10f, 0);
			};
		};
		writer.setPageEvent(event);
	}

	/**
	 * Método encargado de generar el encabezado de los reportes de historicos
	 *
	 * @return PdfPTable : Encabezado
	 *
	 * @throws DocumentException
	 *             : En caso de error
	 * @throws IOException
	 *             : En caso de error
	 * @author José Antonio López Torres
	 * @since 28/03/2017
	 */
	private PdfPTable creaTituloHistoricos() throws DocumentException, IOException {
		PdfPTable encabezado = new PdfPTable(2);
		encabezado.setWidthPercentage(95);
		encabezado.setWidths(new float[] { 1, 5 });
		// Logo
		Image img = Image.getInstance(FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath(Utilidades.mensajeProperties("ruta_reportes_img_ine")));
		img.scalePercent(45);
		PdfPCell cell = new PdfPCell(img);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(0);
		cell.setRowspan(2);
		encabezado.addCell(cell);
		// Titulo
		String titulo = parametros.get(Constantes.PARAMETRO_STRING_TITULO).toString().toUpperCase();
		cell = new PdfPCell(new Phrase(titulo));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorder(0);
		cell.setRowspan(2);
		encabezado.addCell(cell);
		// Partido
		cell = new PdfPCell(new Phrase(" "));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(0);
		cell.setRowspan(1);
		encabezado.addCell(cell);
		return encabezado;
	}

	/**
	 * Método encargado de generar el encabezado donde se muestra estado,
	 * distrito y fecha
	 *
	 * @return PdfPTable : Encabezado
	 *
	 * @throws DocumentException
	 *             : En caso de error
	 * @author José Antonio López Torres
	 * @since 28/03/2017
	 */
	private PdfPTable creaEncabezadoHistoricos() throws DocumentException {
		PdfPTable encabezado = new PdfPTable(4);
		encabezado.setWidthPercentage(95);
		encabezado.setWidths(new float[] { 1, (float) 0.5, 1, 1 });
		encabezado.setSpacingBefore(10);
		encabezado.setSpacingAfter(1);
		PdfPCell cell;
		if (parametros.containsKey(Constantes.PARAMETRO_OBJECT_ESTADO)) {
			DTODetalleEstadoProcesoWS estado = (DTODetalleEstadoProcesoWS) parametros
					.get(Constantes.PARAMETRO_OBJECT_ESTADO);
			// ETIQUETA
			cell = new PdfPCell(
					new Phrase(Utilidades.mensajeProperties("etiqueta_generales_entidad_federativa"), facetFontHist));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(1);
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			cell.setBorderColor(borderColor);
			cell.setPaddingTop(4);
			cell.setPaddingBottom(6);
			encabezado.addCell(cell);
			// ESTADO
			cell = new PdfPCell(new Phrase(
					estado == null ? "OFICINAS CENTRALES" : estado.getNombreEstado().toUpperCase(), facetFontHist));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(3);
			cell.setBorderColor(borderColor);
			cell.setPaddingTop(4);
			cell.setPaddingBottom(6);
			encabezado.addCell(cell);
		}
		if (parametros.containsKey(Constantes.PARAMETRO_OBJECT_DISTRITO)) {
			DTODetalleDistritoProcesoWS distrito = (DTODetalleDistritoProcesoWS) parametros
					.get(Constantes.PARAMETRO_OBJECT_DISTRITO);
			// ETIQUETA
			cell = new PdfPCell(new Phrase(Utilidades.mensajeProperties("etiqueta_sistema_distrito"), facetFontHist));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(1);
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			cell.setBorderColor(borderColor);
			cell.setPaddingTop(4);
			cell.setPaddingBottom(6);
			encabezado.addCell(cell);
			// ID DISTRITO
			cell = new PdfPCell(new Phrase(distrito.getIdDistrito().toString(), facetFontHist));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(1);
			cell.setBorderColor(borderColor);
			cell.setPaddingTop(4);
			cell.setPaddingBottom(6);
			encabezado.addCell(cell);
			// ETIQUETA
			cell = new PdfPCell(
					new Phrase(Utilidades.mensajeProperties("etiqueta_generales_cabecera_distrital"), facetFontHist));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(1);
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			cell.setBorderColor(borderColor);
			cell.setPaddingTop(4);
			cell.setPaddingBottom(6);
			encabezado.addCell(cell);
			// DISTRITO
			cell = new PdfPCell(new Phrase(distrito.getNombreDistrito().toUpperCase(), facetFontHist));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(1);
			cell.setBorderColor(borderColor);
			cell.setPaddingTop(4);
			cell.setPaddingBottom(6);
			encabezado.addCell(cell);
		}

		// FECHA

		cell = new PdfPCell(new Phrase(""));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setBorder(0);
		encabezado.addCell(cell);

		cell = new PdfPCell(new Phrase("Fecha de creación", facetFontHist));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		// cell.setColspan((int)parametros.get(Constantes.PARAMETRO_INTEGER_COLUMNAS));
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setColspan(1);
		cell.setBorderColor(borderColor);
		cell.setPaddingTop(4);
		cell.setPaddingBottom(6);
		encabezado.addCell(cell);

		cell = new PdfPCell(new Phrase(ValidacionFechas.dateToString("dd/MMMM/yyyy HH:mm", new Date()).concat(" hrs."),
				facetFontHist));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		// cell.setColspan((int)parametros.get(Constantes.PARAMETRO_INTEGER_COLUMNAS));
		cell.setColspan(1);
		cell.setPaddingTop(4);
		cell.setPaddingBottom(6);
		cell.setBorderColor(borderColor);
		encabezado.addCell(cell);

		if (parametros.containsKey(Constantes.PARAMETRO_STRING_TOTALES)) {
			// TOTALES

			cell = new PdfPCell(new Phrase(""));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(2);
			cell.setBorder(0);
			encabezado.addCell(cell);

			cell = new PdfPCell(new Phrase("Total", facetFontHist));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			// cell.setColspan((int)parametros.get(Constantes.PARAMETRO_INTEGER_COLUMNAS));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			cell.setColspan(1);
			cell.setBorderColor(borderColor);
			cell.setPaddingTop(4);
			cell.setPaddingBottom(6);
			encabezado.addCell(cell);

			cell = new PdfPCell(
					new Phrase(parametros.get(Constantes.PARAMETRO_STRING_TOTALES).toString(), facetFontHist));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderColor(borderColor);
			cell.setColspan(1);
			cell.setBorderColor(borderColor);
			cell.setPaddingTop(4);
			cell.setPaddingBottom(6);
			encabezado.addCell(cell);
		}

		return encabezado;
	}

	/**
	 * Agrega objetos PdfPCell para crear tabla con los datos
	 * 
	 * @param table
	 * @param pdfTable
	 * @author jpachecov
	 */
	private void creaTablaDatos(DataTable table, PdfPTable pdfTable) {
		for (int rowIndex = 0; rowIndex < table.getRowCount(); rowIndex++) {
			table.setRowIndex(rowIndex);
			List<UIColumn> columns = table.getColumns();
			for (UIColumn c : columns) {
				for (UIComponent cm : c.getChildren()) {
					String value = exportValue(FacesContext.getCurrentInstance(), cm);
					PdfPCell cell = new PdfPCell(new Paragraph(value, this.facetFontHist));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBackgroundColor(Color.WHITE);
					cell.setBorderColor(borderColor);
					cell.setColspan(c.getColspan());
					cell.setMinimumHeight(25);
					pdfTable.addCell(cell);
				}
			}
		}
	}

	public Font getFacetFont() {
		return facetFont;
	}
        /**
         * Método que agrega el FOOTER de la tabla
         * @param table
         * @param pdfTable
         * @param columnType 
         * @author Helaine Flores
         */
        protected void addColumnFooters(DataTable table, PdfPTable pdfTable, ColumnType columnType) {
		if (table.getColumnGroup(columnType.facet()) != null) {
			for (UIComponent row : table.getColumnGroup(columnType.facet()).getChildren()) {
				for (UIComponent column : row.getChildren()) {
					UIColumn footer = (UIColumn) column;
                                        String value = footer.getFooterText();
					if (value != null) {
						PdfPCell cell = new PdfPCell(new Paragraph(value, this.facetFontHist));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBackgroundColor(Color.LIGHT_GRAY);
						cell.setBorderColor(borderColor);
						cell.setColspan((int) column.getAttributes().get("colspan"));
                                                cell.setRowspan((int) column.getAttributes().get("rowspan"));
						pdfTable.addCell(cell);
					}
				}
				pdfTable.completeRow();
			}
		}

	}
        }
