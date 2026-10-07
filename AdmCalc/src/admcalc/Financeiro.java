package admcalc;

public class Financeiro {

    // Faturamento
    public double calcularFaturamento(double precoVenda, double quantidade) {
        if (precoVenda < 0 || quantidade < 0) {
            throw new IllegalArgumentException("Valores não podem ser negativos");
        }
        return precoVenda * quantidade;
    }

    // Lucro Bruto
    public double calcularLucroBruto(double receitaTotal, double cmv) {
        if (receitaTotal < 0 || cmv < 0) {
            throw new IllegalArgumentException("Valores não podem ser negativos");
        }
        return receitaTotal - cmv;
    }

    // Lucro Líquido
    public double calcularLucroLiquido(double lucroBruto, double despesas, double impostos) {
        if (lucroBruto < 0 || despesas < 0 || impostos < 0) {
            throw new IllegalArgumentException("Valores não podem ser negativos");
        }
        return lucroBruto - (despesas + impostos);
    }

    // Margem de Lucro Bruta
    public double calcularMargemLucroBruta(double lucroBruto, double receitaTotal) {
        if (receitaTotal == 0) {
            throw new IllegalArgumentException("Receita total não pode ser zero");
        }
        return (lucroBruto / receitaTotal) * 100;
    }

    // Margem de Lucro Líquida
    public double calcularMargemLucroLiquida(double lucroLiquido, double receitaTotal) {
        if (receitaTotal == 0) {
            throw new IllegalArgumentException("Receita total não pode ser zero");
        }
        return (lucroLiquido / receitaTotal) * 100;
    }

    // Ponto de Equilíbrio
    public double calcularPontoEquilibrio(double custosFixos, double margemContribuicao) {
        if (margemContribuicao == 0) {
            throw new IllegalArgumentException("Margem de contribuição não pode ser zero");
        }
        return custosFixos / margemContribuicao;
    }

    // Margem de Contribuição
    public double calcularMargemContribuicao(double receitaTotal, double custosVariaveis) {
        return receitaTotal - custosVariaveis;
    }

    // Ativo Total
    public double calcularAtivoTotal(double ativoCirculante, double ativoNaoCirculante) {
        return ativoCirculante + ativoNaoCirculante;
    }

    // Passivo Total
    public double calcularPassivoTotal(double passivoCirculante, double passivoNaoCirculante) {
        return passivoCirculante + passivoNaoCirculante;
    }

    // Patrimônio Líquido
    public double calcularPatrimonioLiquido(double ativoTotal, double passivoTotal) {
        return ativoTotal - passivoTotal;
    }

    // Capital de Giro
    public double calcularCapitalGiro(double ativoCirculante, double passivoCirculante) {
        return ativoCirculante - passivoCirculante;
    }

    // Liquidez Corrente
    public double calcularLiquidezCorrente(double ativoCirculante, double passivoCirculante) {
        if (passivoCirculante == 0) {
            throw new IllegalArgumentException("Passivo circulante não pode ser zero");
        }
        return ativoCirculante / passivoCirculante;
    }

    // Liquidez Seca
    public double calcularLiquidezSeca(double ativoCirculante, double estoque, double passivoCirculante) {
        if (passivoCirculante == 0) {
            throw new IllegalArgumentException("Passivo circulante não pode ser zero");
        }
        return (ativoCirculante - estoque) / passivoCirculante;
    }

    // Depreciação (Método Linear)
    public double calcularDepreciacaoAnual(double custoBem, double valorResidual, double vidaUtil) {
        if (vidaUtil == 0) {
            throw new IllegalArgumentException("Vida útil não pode ser zero");
        }
        return (custoBem - valorResidual) / vidaUtil;
    }

    // Custo Unitário
    public double calcularCustoUnitario(double custoTotal, double quantidadeProduzida) {
        if (quantidadeProduzida == 0) {
            throw new IllegalArgumentException("Quantidade produzida não pode ser zero");
        }
        return custoTotal / quantidadeProduzida;
    }

    // Mark-up de Preço
    public double calcularMarkup(double custoTotal, double margemDesejada) {
        if (margemDesejada >= 1) {
            throw new IllegalArgumentException("Margem desejada deve ser menor que 1 (100%)");
        }
        return custoTotal / (1 - margemDesejada);
    }

    // Custo Total
    public double calcularCustoTotal(double custosFixos, double custosVariaveis) {
        return custosFixos + custosVariaveis;
    }

    // Retorno sobre o Investimento (ROI)
    public double calcularROI(double ganhoInvestimento, double custoInvestimento) {
        if (custoInvestimento == 0) {
            throw new IllegalArgumentException("Custo do investimento não pode ser zero");
        }
        return ((ganhoInvestimento - custoInvestimento) / custoInvestimento) * 100;
    }

    // Valor Presente (VP)
    public double calcularValorPresente(double valorFuturo, double taxaDesconto, double periodo) {
        if (taxaDesconto <= 0 || periodo < 0) {
            throw new IllegalArgumentException("Taxa deve ser positiva e período não pode ser negativo");
        }
        return valorFuturo / Math.pow(1 + taxaDesconto, periodo);
    }

    // Valor Futuro (VF)
    public double calcularValorFuturo(double valorPresente, double taxaDesconto, double periodo) {
        if (taxaDesconto <= 0 || periodo < 0) {
            throw new IllegalArgumentException("Taxa deve ser positiva e período não pode ser negativo");
        }
        return valorPresente * Math.pow(1 + taxaDesconto, periodo);
    }

    // Taxa Interna de Retorno (TIR)
    public double calcularTIR(double[] fluxosDeCaixa) {
        if (fluxosDeCaixa == null || fluxosDeCaixa.length == 0) {
            throw new IllegalArgumentException("Fluxos de caixa não podem ser nulos ou vazios");
        }
        
        double taxa = 0.1; // Taxa inicial
        double erro = 0.0001; // Erro aceitável
        double npv;
        int maxIteracoes = 10000;
        int iteracao = 0;

        while (iteracao < maxIteracoes) {
            npv = 0.0;
            for (int t = 0; t < fluxosDeCaixa.length; t++) {
                npv += fluxosDeCaixa[t] / Math.pow(1 + taxa, t);
            }
            if (Math.abs(npv) < erro) {
                return taxa;
            }
            // Ajuste simples da taxa (poderia ser melhorado com Newton-Raphson)
            if (npv > 0) {
                taxa += 0.0001;
            } else {
                taxa -= 0.0001;
            }
            iteracao++;
        }
        throw new RuntimeException("Não foi possível calcular a TIR após " + maxIteracoes + " iterações");
    }
}