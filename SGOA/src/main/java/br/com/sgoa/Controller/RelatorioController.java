package br.com.sgoa.Controller;

import br.com.sgoa.Entidade.*;
import br.com.sgoa.Enums.TipoPlanoPagamento;
import br.com.sgoa.Facade.RelatorioFacade;
import br.com.sgoa.util.ReportsUtil;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;

/**
 * @author Matheus
 */
@Named
@ViewScoped
public class RelatorioController implements Serializable {

    @Inject
    private RelatorioFacade relatorioFacade;
    @Getter
    @Setter
    private Date filtro_data = new Date();
    @Getter
    @Setter
    private TipoPlanoPagamento tipoPlano = TipoPlanoPagamento.APRAZO;

    @Getter
    @Setter
    private Estado estado;

    @Getter
    @Setter
    private Cidade cidade;

    @Getter
    @Setter
    private Produto produto;

    @Getter
    @Setter
    private Produto pessoa;

    @Getter
    @Setter
    private Marca marca;

    @Getter
    @Setter
    private Grupo grupo;

    @Getter
    @Setter
    private String filtroNome;

    @Getter
    @Setter
    private Boolean ativarFiltro = false;

    public void gerarRelPdf(String rel, Map<String, Object> param) {
        try {
            Connection conn;
            conn = RelatorioFacade.getConnection();
            ReportsUtil reportUtils = new ReportsUtil();
            reportUtils.gerarRelatorioPDF(param, rel, conn);
        } catch (SQLException | IOException | JRException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public Map<String, Object> getParameters() {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("SUBREPORT_DIR", getCaminhoReal() + "WEB-INF/reports/");
        parametros.put("p_user", "oi");
        return parametros;
    }

    public String getCaminhoReal() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
        return sc.getRealPath("/");
    }

    public void gerarRelAlunoDisciplina() {
        String relatorio = "WEB-INF/relatoriosSGOA/Localidade/Estado/relEstado.jasper";
        gerarRelPdf(relatorio, getParameters());
    }

    public void gerarRelEstado() {
        try {
            String relatorio = "WEB-INF/reports/Localidade/Estado/relEstado.jasper";
            Map<String, Object> param = getParameters();
            gerarRelPdf(relatorio, param);
        } catch (Exception e) {
            System.out.println("Teste");
        }
    }

    public void gerarRelCidade() {
        try {
            String relatorio = "WEB-INF/reports/Localidade/Cidade/relCidade.jasper";
            Map<String, Object> param = getParameters();
            if (ativarFiltro && estado != null) {
                param.put("Filtro", " WHERE cid.estado_cid = " + estado.getId() + " ");
            }
            gerarRelPdf(relatorio, param);
        } catch (Exception e) {
            System.out.println("Teste");
        }
    }

    public void gerarRelPlanoPag() {
        try {
            String relatorio = "WEB-INF/reports/FormaPagamento/relFomaPagamentos.jasper";
            Map<String, Object> param = getParameters();
            if (ativarFiltro && tipoPlano != null) {
                param.put("Filtro", " WHERE forma.forma_pag_tipo = " + tipoPlano + " ");
            }
            gerarRelPdf(relatorio, param);
        } catch (Exception e) {
            System.out.println("Teste");
        }
    }

    public void gerarRelCliente() {
        try {
            String relatorio = "WEB-INF/reports/Pessoa/Cliente/relCliente.jasper";
            Map<String, Object> param = getParameters();
            if (ativarFiltro) {
                String filtro = "";
                if (cidade != null) {
                    filtro += " AND c.idcidade = " + cidade.getId() + " ";
                }
                if (filtroNome != null && !filtroNome.isEmpty()) {
                    filtro += " AND nome_pessoa LIKE '%" + filtroNome + "%' ";
                }
                param.put("Filtro", filtro);
            }
            gerarRelPdf(relatorio, param);
        } catch (Exception e) {
            System.out.println("Teste");
        }
    }

    public void gerarRelFornecedor() {
        try {
            String relatorio = "WEB-INF/reports/Pessoa/Fornecedor/relFornecedor.jasper";
            Map<String, Object> param = getParameters();
            if (ativarFiltro) {
                String filtro = "";
                if (cidade != null) {
                    filtro += " AND c.idcidade = " + cidade.getId() + " ";
                }
                if (filtroNome != null && !filtroNome.isEmpty()) {
                    filtro += " AND nome_pessoa LIKE '%" + filtroNome + "%' ";
                }
                param.put("Filtro", filtro);
            }
            gerarRelPdf(relatorio, param);
        } catch (Exception e) {
            System.out.println("Teste");
        }
    }


    public void gerarRelFuncionario() {
        try {
            String relatorio = "WEB-INF/reports/Pessoa/Funcionario/relFuncionario.jasper";
            Map<String, Object> param = getParameters();
            if (ativarFiltro) {
                String filtro = "";
                if (cidade != null) {
                    filtro += " AND c.idcidade = " + cidade.getId() + " ";
                }
                if (filtroNome != null && !filtroNome.isEmpty()) {
                    filtro += " AND nome_pessoa LIKE '%" + filtroNome + "%' ";
                }
                param.put("Filtro", filtro);
            }
            gerarRelPdf(relatorio, param);
        } catch (Exception e) {
            System.out.println("Teste");
        }
    }

    public void gerarRelEstoqueAtual() {
        try {
            String relatorio = "WEB-INF/reports/Produto/EstoqueAtual/relEstoque.jasper";
            Map<String, Object> param = getParameters();
            if (ativarFiltro) {
                String filtro = "";
                if (produto != null) {
                    filtro += " AND idproduto = " + produto.getId() + " ";
                }
                if (pessoa != null) {
                    filtro += " AND idPessoa = " + pessoa.getId() + " ";
                }
                if (filtroNome != null && !filtroNome.isEmpty()) {
                    filtro += " AND produto_nome LIKE '%" + filtroNome + "%' ";
                }
                param.put("Filtro", filtro);
            }
            gerarRelPdf(relatorio, param);
        } catch (Exception e) {
            System.out.println("Teste");
        }
    }


    public void gerarRelProdutoFornecedor() {
        try {
            String relatorio = "WEB-INF/reports/Produto/ProdutoPorFornecedor/relProdFornecedor.jasper";
            Map<String, Object> param = getParameters();
            if (ativarFiltro) {
                String filtro = "";
                if (produto != null) {
                    filtro += " AND idproduto = " + produto.getId() + " ";
                }
                if (pessoa != null) {
                    filtro += " AND idproduto = " + pessoa.getId() + " ";
                }
                if (filtroNome != null && !filtroNome.isEmpty()) {
                    filtro += " AND produto_nome LIKE '%" + filtroNome + "%' ";
                }
                param.put("Filtro", filtro);
            }
            gerarRelPdf(relatorio, param);
        } catch (Exception e) {
            System.out.println("Teste");
        }
    }

    public void gerarRelProdutoFiltrado() {
        try {
            String relatorio = "WEB-INF/reports/Produto/ProdutoPorGrupoeMarca/relProdGrupoMarca.jasper";
            Map<String, Object> param = getParameters();
            if (ativarFiltro) {
                String filtro = "";
                if (produto != null) {
                    filtro += " AND idproduto = " + produto.getId() + " ";
                }
                if (grupo != null) {
                    filtro += " AND produto_grupo = " + grupo.getId() + " ";
                }
                if (marca != null) {
                    filtro += " AND produto_marca = " + marca.getId() + " ";
                }
                if (filtroNome != null && !filtroNome.isEmpty()) {
                    filtro += " AND produto_nome LIKE '%" + filtroNome + "%' ";
                }
                param.put("Filtro", filtro);
            }
            gerarRelPdf(relatorio, param);
        } catch (Exception e) {
            System.out.println("Teste");
        }
    }

    public Date getFiltro_data() {
        return filtro_data;
    }

    public void setFiltro_data(Date filtro_data) {
        this.filtro_data = filtro_data;
    }

}
