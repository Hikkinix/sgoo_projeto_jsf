package br.com.sgoa.Facade;

import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.*;
import br.com.sgoa.Enums.*;
import br.com.sgoa.util.Transacional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transacional
public class ReceberPagarFacade extends AbstractFacade<ReceberPagar> {

    @Inject
    private EntityManager em;

    @Inject
    private CompraFacade compraFacade;

    @Inject
    private ContaBancariaFacade contaBancariaFacade;
    @Inject
    private BancoFacade bancoFacade;

    @Inject
    private MovimentoFinaceiroFacade movimentoFacade;

    @Override
    public ReceberPagar salvar(ReceberPagar objeto) throws Exception {
        if (objeto != null && objeto.getStatus().equals(StatusReceberPagar.PAGA)) {
            efetuarPagamento(objeto);
        }

        return super.salvar(objeto);
    }

    public void estornar(ReceberPagar objeto) throws Exception {
        if (objeto.getConta() != null && objeto.getStatus().equals(StatusReceberPagar.PAGA)) {
            objeto.getConta().setSaldoDebitoConta(
                    objeto.getConta().getSaldoDebitoConta()
                            .subtract(objeto.getValorLiquidar()));
        }
        if (objeto.getParcelaCompra() != null) {
            Compra compra = objeto.getParcelaCompra().getCompra();
            compraFacade.estorno(compra);
        }
        objeto.setStatus(StatusReceberPagar.PROCESSAMENTO);
        em.merge(objeto);
    }

    public void gerarMovimentacao(ReceberPagar pagamento) throws Exception {
        if (pagamento.getStatus().equals(StatusReceberPagar.PAGA)) {
            MovimentaFinanceiro movimenta = new MovimentaFinanceiro();
            movimenta.setConta(pagamento.getConta());
            movimenta.setDataMovimento(new Date());
            movimenta.setValorMovimento(pagamento.getValorLiquidar());

            if (pagamento.getTipoReceberPagar().equals(TipoReceberPagar.PAGAR)) {
                movimenta.setTipoMovimento(TipoMovimento.DEBITO);
                movimenta.setOrigemMovimento(OrigemMovimento.CONTAPAGA);
                movimenta.setIdOrigem(pagamento.getId());

            } else if (pagamento.getTipoReceberPagar().equals(TipoReceberPagar.RECEBER)) {
                movimenta.setTipoMovimento(TipoMovimento.CREDITO);
                movimenta.setOrigemMovimento(OrigemMovimento.CONTARECEBER);
                movimenta.setIdOrigem(pagamento.getId());
            }

//        if (pagamento.getParcelaCompra() != null) {
//            movimenta.setOrigemMovimento(OrigemMovimento.COMPRA);
//            movimenta.setIdOrigem(pagamento.getParcelaCompra().getCompra().getId());
//        } else if (pagamento.getParcelaVenda() != null) {
//            movimenta.setOrigemMovimento(OrigemMovimento.VENDA);
//            movimenta.setIdOrigem(pagamento.getParcelaVenda().getVenda().getId());
//        }

            movimenta.setReceberPagar(pagamento);
            pagamento.getMovimentacoes().add(movimenta);
//            movimentoFacade.salvar(movimenta);
        }
    }

    public void efetuarPagamento(ReceberPagar pagamento) throws Exception {
        movimentaCaixaConta(pagamento);
    }

    public void pagoPacrcial(ReceberPagar pagoParcial) throws Exception {
        pagoParcial.setValorTotalPagar(pagoParcial.getValorLiquidar());
        pagoParcial.setStatus(StatusReceberPagar.PAGA);
        em.merge(pagoParcial);

        ReceberPagar outraParte = new ReceberPagar(pagoParcial);
        outraParte.setStatus(StatusReceberPagar.PROCESSAMENTO);
        outraParte.setValorTotalPagar(pagoParcial.getValorRestate());
        em.merge(outraParte);
    }

    public void movimentaCaixaConta(ReceberPagar pagamento) throws Exception {
        if (pagamento.getTipoReceberPagar().equals(TipoReceberPagar.PAGAR)) {
            if (pagamento.getStatus().equals(StatusReceberPagar.PAGA)) {
                if (pagamento.getConta() == null) {
                    pagamento.setConta(localizarContaCaixa());
                }
                pagamento.getConta().setSaldoDebitoConta(
                        pagamento.getConta().getSaldoDebitoConta()
                                .add(pagamento.getValorLiquidar()));

            }
        } else if (pagamento.getTipoReceberPagar().equals(TipoReceberPagar.RECEBER)) {
            if (pagamento.getStatus().equals(StatusReceberPagar.PAGA)) {
                if (pagamento.getConta() == null) {
                    pagamento.setConta(localizarContaCaixa());
                }
                pagamento.getConta().setSaldoCreditoConta(
                        pagamento.getConta().getSaldoCreditoConta()
                                .add(pagamento.getValorLiquidar()));

            }
        }
        gerarMovimentacao(pagamento);

    }

    public ContaBancaria localizarContaCaixa() throws Exception {
        if (!listarContas().isEmpty()) {
            return listarContas().get(0);
        } else {
            Banco banco = new Banco();
            banco.setNomeBanco("Caixa Interno");
            banco = bancoFacade.salvar(banco);
            ContaBancaria conta = new ContaBancaria();
            conta.setBanco(banco);
            conta.setNumeroConta("0");
            conta.setAgenciaConta("0");
            conta.setCaixaInterno(true);
            return conta;
        }
    }

    public List<ReceberPagar> listaFiltroPagar(String filtro, String atributo, String atributo1) {
        String hql = "from ReceberPagar where tipo = 'PAGAR' and (" + filtro + " = '" + atributo + "' or " + filtro + " = '" + atributo1 + "')";
        Query q = getEntityManager().createQuery(hql);
        return q.getResultList() != null ? q.getResultList() : new ArrayList<ReceberPagar>();
    }

    public List<ReceberPagar> listaFiltroPagar(String filtro, String atributo) {
        String hql = "from ReceberPagar where tipo = 'PAGAR' and " + filtro + " = '" + atributo + "'";
        Query q = getEntityManager().createQuery(hql);
        return q.getResultList() != null ? q.getResultList() : new ArrayList<ReceberPagar>();
    }

    public List<ReceberPagar> listaFiltroReceber(String filtro, String atributo, String atributo1) {
        String hql = "from ReceberPagar where tipo = 'RECEBER' and (" + filtro + " = '" + atributo + "' or " + filtro + " = '" + atributo1 + "')";
        Query q = getEntityManager().createQuery(hql);
        return q.getResultList() != null ? q.getResultList() : new ArrayList<ReceberPagar>();
    }

    public List<ReceberPagar> listaFiltroReceber(String filtro, String atributo) {
        String hql = "from ReceberPagar where tipo = 'RECEBER' and " + filtro + " = '" + atributo + "'";
        Query q = getEntityManager().createQuery(hql);
        return q.getResultList() != null ? q.getResultList() : new ArrayList<ReceberPagar>();
    }

    public ReceberPagarFacade() {
        super(ReceberPagar.class);
    }

    public List<ContaBancaria> listarContas() {
        Query q = getEntityManager().createNativeQuery("select * from contaBancaria where conta_caixa = true LIMIT 1", ContaBancaria.class);
        System.out.println(q.getResultList().isEmpty());
        return q.getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
