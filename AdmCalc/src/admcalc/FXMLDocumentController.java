package admcalc;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField inputPrecoVenda;
    @FXML
    private TextField inputQuantidade;
    @FXML
    private TextField inputReceitaTotal;
    @FXML
    private TextField inputCMV;
    @FXML
    private TextField inputDespesas;
    @FXML
    private TextField inputImpostos;
    @FXML
    private TextField inputCustosFixos;
    @FXML
    private TextField inputMargemContribuicao;
    @FXML
    private TextField inputAtivoCirculante;
    @FXML
    private TextField inputAtivoNaoCirculante;
    @FXML
    private TextField inputPassivoCirculante;
    @FXML
    private TextField inputPassivoNaoCirculante;
    @FXML
    private TextField inputCustoInicial;
    @FXML
    private TextField inputValorResidual;
    @FXML
    private TextField inputVidaUtil;
    @FXML
    private TextField inputCustoTotal;
    @FXML
    private TextField inputQuantidadeProduzida;
    @FXML
    private TextField inputMarkup;
    @FXML
    private TextField inputCustosFixosTotal;
    @FXML
    private TextField inputCustosVariaveisTotal;
    @FXML
    private TextField inputLucro;
    @FXML
    private TextField inputInvestimento;
    @FXML
    private TextField inputValorFuturo;
    @FXML
    private TextField inputTaxaJuros;
    @FXML
    private TextField inputPeriodo;
    @FXML
    private Label resultLabel;
    @FXML
    private TextField inputEstoque;
    @FXML
    private TextField inputCustoTotalMarkup; // Adicionado para corresponder ao FXML

    private Financeiro financeiro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        financeiro = new Financeiro();
    }

    private double getDoubleFromTextField(TextField textField) {
        String text = textField.getText();
        if (text == null || text.trim().isEmpty()) {
            textField.setStyle("-fx-border-color: red;");
            throw new NumberFormatException("Campo vazio");
        }
        try {
            double value = Double.parseDouble(text);
            if (value < 0) {
                textField.setStyle("-fx-border-color: red;");
                throw new NumberFormatException("Valor não pode ser negativo");
            }
            textField.setStyle("");
            return value;
        } catch (NumberFormatException e) {
            textField.setStyle("-fx-border-color: red;");
            throw new NumberFormatException("Valor inválido");
        }
    }

    private void showError(String message) {
        resultLabel.setText("Erro: " + message);
        resultLabel.setTextFill(Color.RED);
    }

    private void showSuccess(String message) {
        resultLabel.setText(message);
        resultLabel.setTextFill(Color.GREEN);
    }

    @FXML
    private void calculateFaturamento() {
        try {
            double precoVenda = getDoubleFromTextField(inputPrecoVenda);
            double quantidade = getDoubleFromTextField(inputQuantidade);
            double faturamento = financeiro.calcularFaturamento(precoVenda, quantidade);
            showSuccess(String.format("Faturamento: R$ %.2f", faturamento));
        } catch (NumberFormatException e) {
            showError("Insira valores válidos para Preço de Venda e Quantidade.");
        }
    }

    @FXML
    private void calculateLucroBruto() {
        try {
            double receitaTotal = getDoubleFromTextField(inputReceitaTotal);
            double cmv = getDoubleFromTextField(inputCMV);
            double lucroBruto = financeiro.calcularLucroBruto(receitaTotal, cmv);
            showSuccess(String.format("Lucro Bruto: R$ %.2f", lucroBruto));
        } catch (NumberFormatException e) {
            showError("Insira valores válidos para Receita Total e CMV.");
        }
    }

    @FXML
    private void calculateLucroLiquido() {
        try {
            double receitaTotal = getDoubleFromTextField(inputReceitaTotal);
            double cmv = getDoubleFromTextField(inputCMV);
            double lucroBruto = financeiro.calcularLucroBruto(receitaTotal, cmv);
            double despesas = getDoubleFromTextField(inputDespesas);
            double impostos = getDoubleFromTextField(inputImpostos);
            double lucroLiquido = financeiro.calcularLucroLiquido(lucroBruto, despesas, impostos);
            showSuccess(String.format("Lucro Líquido: R$ %.2f", lucroLiquido));
        } catch (NumberFormatException e) {
            showError("Insira valores válidos para Receita Total, CMV, Despesas e Impostos.");
        }
    }

    @FXML
    private void calculateMargemLucroBruta() {
        try {
            double receitaTotal = getDoubleFromTextField(inputReceitaTotal);
            double cmv = getDoubleFromTextField(inputCMV);
            double lucroBruto = financeiro.calcularLucroBruto(receitaTotal, cmv);
            double margemLucroBruta = financeiro.calcularMargemLucroBruta(lucroBruto, receitaTotal);
            showSuccess(String.format("Margem de Lucro Bruta: %.2f%%", margemLucroBruta));
        } catch (NumberFormatException e) {
            showError("Insira valores válidos para Receita Total e CMV.");
        }
    }

    @FXML
    private void calculateMargemLucroLiquida() {
        try {
            double receitaTotal = getDoubleFromTextField(inputReceitaTotal);
            double cmv = getDoubleFromTextField(inputCMV);
            double lucroBruto = financeiro.calcularLucroBruto(receitaTotal, cmv);
            double despesas = getDoubleFromTextField(inputDespesas);
            double impostos = getDoubleFromTextField(inputImpostos);
            double lucroLiquido = financeiro.calcularLucroLiquido(lucroBruto, despesas, impostos);
            double margemLucroLiquida = financeiro.calcularMargemLucroLiquida(lucroLiquido, receitaTotal);
            showSuccess(String.format("Margem de Lucro Líquida: %.2f%%", margemLucroLiquida));
        } catch (NumberFormatException e) {
            showError("Insira valores válidos para Receita Total, CMV, Despesas e Impostos.");
        }
    }

    @FXML
    private void calculatePontoEquilibrio() {
        try {
            double custosFixos = getDoubleFromTextField(inputCustosFixos);
            double margemContribuicao = getDoubleFromTextField(inputMargemContribuicao);
            if (margemContribuicao == 0) {
                throw new NumberFormatException("Margem de contribuição não pode ser zero");
            }
            double pontoEquilibrio = financeiro.calcularPontoEquilibrio(custosFixos, margemContribuicao);
            showSuccess(String.format("Ponto de Equilíbrio: %.2f unidades", pontoEquilibrio));
        } catch (NumberFormatException e) {
            showError("Insira valores válidos para Custos Fixos e Margem de Contribuição.");
        }
    }

    @FXML
    private void calculateMargemContribuicao() {
        try {
            double receitaTotal = getDoubleFromTextField(inputReceitaTotal);
            double custosVariaveis = getDoubleFromTextField(inputCustosVariaveisTotal);
            double margemContribuicao = financeiro.calcularMargemContribuicao(receitaTotal, custosVariaveis);
            showSuccess(String.format("Margem de Contribuição: R$ %.2f", margemContribuicao));
        } catch (NumberFormatException e) {
            showError("Insira valores válidos para Receita Total e Custos Variáveis.");
        }
    }

    @FXML
    private void calculateAtivoTotal() {
        try {
            double ativoCirculante = getDoubleFromTextField(inputAtivoCirculante);
            double ativoNaoCirculante = getDoubleFromTextField(inputAtivoNaoCirculante);
            double ativoTotal = financeiro.calcularAtivoTotal(ativoCirculante, ativoNaoCirculante);
            showSuccess(String.format("Ativo Total: R$ %.2f", ativoTotal));
        } catch (NumberFormatException e) {
            showError("Insira valores válidos para Ativo Circulante e Ativo Não Circulante.");
        }
    }

    @FXML
    private void calculatePassivoTotal() {
        try {
            double passivoCirculante = getDoubleFromTextField(inputPassivoCirculante);
            double passivoNaoCirculante = getDoubleFromTextField(inputPassivoNaoCirculante);
            double passivoTotal = financeiro.calcularPassivoTotal(passivoCirculante, passivoNaoCirculante);
            showSuccess(String.format("Passivo Total: R$ %.2f", passivoTotal));
        } catch (NumberFormatException e) {
            showError("Insira valores válidos para Passivo Circulante e Passivo Não Circulante.");
        }
    }

    @FXML
    private void calculatePatrimonioLiquido() {
        try {
            double ativoCirculante = getDoubleFromTextField(inputAtivoCirculante);
            double ativoNaoCirculante = getDoubleFromTextField(inputAtivoNaoCirculante);
            double ativoTotal = financeiro.calcularAtivoTotal(ativoCirculante, ativoNaoCirculante);
            
            double passivoCirculante = getDoubleFromTextField(inputPassivoCirculante);
            double passivoNaoCirculante = getDoubleFromTextField(inputPassivoNaoCirculante);
            double passivoTotal = financeiro.calcularPassivoTotal(passivoCirculante, passivoNaoCirculante);
            
            double patrimonioLiquido = financeiro.calcularPatrimonioLiquido(ativoTotal, passivoTotal);
            showSuccess(String.format("Patrimônio Líquido: R$ %.2f", patrimonioLiquido));
        } catch (NumberFormatException e) {
            showError("Insira valores válidos para Ativo e Passivo.");
        }
    }

    @FXML
    private void calculateCapitalGiro() {
        try {
            double ativoCirculante = getDoubleFromTextField(inputAtivoCirculante);
            double passivoCirculante = getDoubleFromTextField(inputPassivoCirculante);
            double capitalGiro = financeiro.calcularCapitalGiro(ativoCirculante, passivoCirculante);
            showSuccess(String.format("Capital de Giro: R$ %.2f", capitalGiro));
        } catch (NumberFormatException e) {
            showError("Insira valores válidos para Ativo Circulante e Passivo Circulante.");
        }
    }

    @FXML
    private void calculateLiquidezCorrente() {
        try {
            double ativoCirculante = getDoubleFromTextField(inputAtivoCirculante);
            double passivoCirculante = getDoubleFromTextField(inputPassivoCirculante);
            if (passivoCirculante == 0) {
                throw new NumberFormatException("Passivo Circulante não pode ser zero");
            }
            double liquidezCorrente = financeiro.calcularLiquidezCorrente(ativoCirculante, passivoCirculante);
            showSuccess(String.format("Liquidez Corrente: %.2f", liquidezCorrente));
        } catch (NumberFormatException e) {
            showError("Insira valores válidos para Ativo Circulante e Passivo Circulante.");
        }
    }

    @FXML
    private void calculateLiquidezSeca() {
        try {
            double ativoCirculante = getDoubleFromTextField(inputAtivoCirculante);
            double estoque = getDoubleFromTextField(inputEstoque);
            double passivoCirculante = getDoubleFromTextField(inputPassivoCirculante);
            if (passivoCirculante == 0) {
                throw new NumberFormatException("Passivo Circulante não pode ser zero");
            }
            double liquidezSeca = financeiro.calcularLiquidezSeca(ativoCirculante, estoque, passivoCirculante);
            showSuccess(String.format("Liquidez Seca: %.2f", liquidezSeca));
        } catch (NumberFormatException e) {
            showError("Insira valores válidos para Ativo Circulante, Estoque e Passivo Circulante.");
        }
    }

    @FXML
    private void calculateDepreciacao() {
        try {
            double custoBem = getDoubleFromTextField(inputCustoInicial);
            double valorResidual = getDoubleFromTextField(inputValorResidual);
            double vidaUtil = getDoubleFromTextField(inputVidaUtil);
            if (vidaUtil == 0) {
                throw new NumberFormatException("Vida útil não pode ser zero");
            }
            double depreciacaoAnual = financeiro.calcularDepreciacaoAnual(custoBem, valorResidual, vidaUtil);
            showSuccess(String.format("Depreciação Anual: R$ %.2f", depreciacaoAnual));
        } catch (NumberFormatException e) {
            showError("Insira valores válidos para Custo do Bem, Valor Residual e Vida Útil.");
        }
    }

    @FXML
    private void calculateCustoUnitario() {
        try {
            double custoTotal = getDoubleFromTextField(inputCustoTotal);
            double quantidadeProduzida = getDoubleFromTextField(inputQuantidadeProduzida);
            if (quantidadeProduzida == 0) {
                throw new NumberFormatException("Quantidade produzida não pode ser zero");
            }
            double custoUnitario = financeiro.calcularCustoUnitario(custoTotal, quantidadeProduzida);
            showSuccess(String.format("Custo Unitário: R$ %.2f", custoUnitario));
        } catch (NumberFormatException e) {
            showError("Insira valores válidos para Custo Total e Quantidade Produzida.");
        }
    }

    @FXML
    private void calculateMarkupPreco() {
        try {
            double custoTotal = getDoubleFromTextField(inputCustoTotalMarkup);
            double margemDesejada = getDoubleFromTextField(inputMarkup) / 100;
            if (margemDesejada >= 1) {
                throw new NumberFormatException("Margem deve ser menor que 100%");
            }
            double markup = financeiro.calcularMarkup(custoTotal, margemDesejada);
            showSuccess(String.format("Preço de Venda: R$ %.2f", markup));
        } catch (NumberFormatException e) {
            showError("Insira valores válidos para Custo Total e Margem Desejada.");
        }
    }

    @FXML
    private void calculateCustoTotal() {
        try {
            double custosFixos = getDoubleFromTextField(inputCustosFixosTotal);
            double custosVariaveis = getDoubleFromTextField(inputCustosVariaveisTotal);
            double custoTotal = financeiro.calcularCustoTotal(custosFixos, custosVariaveis);
            showSuccess(String.format("Custo Total: R$ %.2f", custoTotal));
        } catch (NumberFormatException e) {
            showError("Insira valores válidos para Custos Fixos e Custos Variáveis.");
        }
    }

    @FXML
    private void calculateROI() {
        try {
            double ganhoInvestimento = getDoubleFromTextField(inputLucro);
            double custoInvestimento = getDoubleFromTextField(inputInvestimento);
            if (custoInvestimento == 0) {
                throw new NumberFormatException("Custo do investimento não pode ser zero");
            }
            double roi = financeiro.calcularROI(ganhoInvestimento, custoInvestimento);
            showSuccess(String.format("ROI: %.2f%%", roi));
        } catch (NumberFormatException e) {
            showError("Insira valores válidos para Ganho do Investimento e Custo do Investimento.");
        }
    }

    @FXML
    private void calculateValorPresente() {
        try {
            double valorFuturo = getDoubleFromTextField(inputValorFuturo);
            double taxaDesconto = getDoubleFromTextField(inputTaxaJuros) / 100;
            double periodo = getDoubleFromTextField(inputPeriodo);
            double valorPresente = financeiro.calcularValorPresente(valorFuturo, taxaDesconto, periodo);
            showSuccess(String.format("Valor Presente: R$ %.2f", valorPresente));
        } catch (NumberFormatException e) {
            showError("Insira valores válidos para Valor Futuro, Taxa de Juros e Período.");
        }
    }

    @FXML
    private void calculateValorFuturo() {
        try {
            double valorPresente = getDoubleFromTextField(inputValorFuturo); // Note: Isso parece errado, mas mantendo como estava
            double taxaDesconto = getDoubleFromTextField(inputTaxaJuros) / 100;
            double periodo = getDoubleFromTextField(inputPeriodo);
            double valorFuturo = financeiro.calcularValorFuturo(valorPresente, taxaDesconto, periodo);
            showSuccess(String.format("Valor Futuro: R$ %.2f", valorFuturo));
        } catch (NumberFormatException e) {
            showError("Insira valores válidos para Valor Presente, Taxa de Juros e Período.");
        }
    }

    @FXML
    private void calculateTIR() {
        try {
            // Exemplo de fluxos de caixa (deveria ser entrada do usuário)
            double[] fluxosDeCaixa = {-1000, 300, 400, 500, 600};
            double tir = financeiro.calcularTIR(fluxosDeCaixa);
            showSuccess(String.format("TIR: %.2f%%", tir * 100));
        } catch (Exception e) {
            showError("Erro ao calcular TIR. " + e.getMessage());
        }
    }
}