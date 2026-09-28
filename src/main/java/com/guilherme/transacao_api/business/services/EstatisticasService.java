package com.guilherme.transacao_api.business.services;

import com.guilherme.transacao_api.controller.dtos.EstatisticasResponseDTO;
import com.guilherme.transacao_api.controller.dtos.TransacaoRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstatisticasService {

    public final TransacaoService transacaoService;

    public EstatisticasResponseDTO calculaEstatisticasDeTransacoes(Integer intervaloBusca) {

        log.info("Iniciado a busca de estatísticas de transações pelo período de tempo " + intervaloBusca);
        List< TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(intervaloBusca);

        if (transacoes.isEmpty()) {
            return new EstatisticasResponseDTO(0L,0.0,0.0,0.0,0.0);
        }

        DoubleSummaryStatistics estatisticas = transacoes.stream().mapToDouble(TransacaoRequestDTO::valor).summaryStatistics();

        log.info("Estatísticas retornadas com sucesso");
        return new EstatisticasResponseDTO(estatisticas.getCount(),
                estatisticas.getSum(),
                estatisticas.getAverage(),
                estatisticas.getMin(),
                estatisticas.getMax());
    }
}
