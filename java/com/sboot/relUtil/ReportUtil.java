package com.sboot.relUtil;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class ReportUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Retorna nosso pdf em btye para dawnload no navegador
	 **/
	@SuppressWarnings("rawtypes")
	public byte[] geraRelatorio(List listaDados, String relatorio, ServletContext context) throws Exception 
	{
				/* Cria a lista de dados para o relatorio com a lista de objetos para imprimir*/
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(listaDados);
			
			/** Carrega o caminho do arquivo jasper compilado
			 * 
			 * "relatorios" -> pasta onde está o relatorio de pessoa.jasper
			 * **/
			String caminhoJasper = context.getRealPath("relatorios") + File.separator + relatorio + ".jasper";
			
			
			/* Carrega o arquivo jasper passando os dados */
			
			@SuppressWarnings("unchecked")
			JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashMap(), jrBeanCollectionDataSource);
			
			
			/*Exporta para byte[] para fazer o download do PEDF */
			return JasperExportManager.exportReportToPdf(impressoraJasper);
			
					
				
			
		
	}

}
