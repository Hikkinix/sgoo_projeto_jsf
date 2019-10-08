package br.com.sgoa.Controller;

import br.com.sgoa.Abstract.AbstractController;
import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.FormaPagamento;
import br.com.sgoa.util.Conexao;
import br.com.sgoa.util.JavaUtil;
import br.com.sgoa.util.ReportsUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Named(value = "relatorio")
public class RelatorioControllera implements Serializable {

    @Inject
    private EntityManager em;
    private Date dataInicio;
    private Date dataFim;
    @Inject
    private LoginController loginControle;
    @Inject
    private JavaUtil javaUtil;

    public Map<String, Object> informacoesPadroes(){
        Map<String, Object> param = new HashMap<>();
        param.put("p_user", "Teste");
        return param;
    }



    public void relatorioEstado() {
        Conexao com = null;
        try {
            ReportsUtil util = new ReportsUtil();
            Map<String, Object> param = new HashMap<>();
            param.put("p_user", "Teste");
            gerarRelatorioPDF(param, "WEB-INF/relatoriosSGOA/template.jasper",  Conexao.getConnection());

        } catch (IOException | JRException | ClassNotFoundException | SQLException ex) {
            javaUtil.errorMensagem("Erro Relatorio" + ex);
        }
    }


    public void gerarRelatorioCidadeEstado() {
        try {
            JasperReport relatorio;
            String arquivoJasper = "template.jasper";
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            //gera relatorio com as classes do jasper
            HashMap p = new HashMap();
            p.put("p_user", "Teste");
            JasperPrint jasperPrint = JasperFillManager.fillReport("/home/orin/Documentos/ESTAGIO/projetos/SGOA/src/main/webapp/WEB-INF/relatoriosSGOA/template.jasper", p, Conexao.getConnection());
            ByteArrayOutputStream dadosByte = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, dadosByte);
            exporter.exportReport();
            byte[] bytes = dadosByte.toByteArray();
            if (bytes != null && bytes.length > 0) {
                int recorte = arquivoJasper.indexOf(".");
                String nomePDF = "RelatorioCidadePDF";
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"" + nomePDF + ".pdf\"");
                response.setContentLength(bytes.length);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
                outputStream.close();
            }
        } catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
        }
    }
    public void relatorioVendaPeriodo() {
        try {
            ReportsUtil util = new ReportsUtil();
            Map<String, Object> param = new HashMap<>();
            param.put("DATA_INICIO", dataInicio);
            param.put("DATA_FIM", dataFim);
            util.gerarRelatorioPDF(param, "WEB-INF/reports/vendaPorPeriodo.jasper",  Conexao.getConnection());
        } catch (IOException | JRException | ClassNotFoundException | SQLException ex) {
            javaUtil.errorMensagem("Erro Relatorio" + ex);
        }
    }


    public void gerarRelatorioPDF(Map<String, Object> parametros, String caminho, Connection conn) throws IOException, JRException, ClassNotFoundException, SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline;filename=relatorio.pdf");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        InputStream reportStream = context.getExternalContext().getResourceAsStream(caminho);
        JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, parametros, conn);
        servletOutputStream.flush();
        servletOutputStream.close();
        context.responseComplete();
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
}
