package com.guilherme.transacao_api.business.services;

import com.guilherme.transacao_api.controller.dtos.TransacaoRequestDTO;
import com.guilherme.transacao_api.infrastructure.exception.UnprocessableEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoService {

    private final List<TransacaoRequestDTO> transacoes = new ArrayList<>();

    public void adicionaTransacao(TransacaoRequestDTO dto) {

        log.info("Iniciado o processamento de gravar transações");

        if (dto.dataHora().isAfter(OffsetDateTime.now())) {
            log.error("Data e hora maiores que a data e hora atual");
            throw new UnprocessableEntity("Data e hora maiores que a data e hora atual");
        }
        if (dto.valor() < 0) {
            log.error("Valor não pode ser negativo");
            throw new UnprocessableEntity("Valor não pode ser negativo");
        }

        transacoes.add(dto);
    }

    public void limparTransacoes() {
        transacoes.clear();
    }

    public List<TransacaoRequestDTO> buscarTransacoes(Integer intervaloBusca) {

        OffsetDateTime intervalo = OffsetDateTime.now().minusSeconds(intervaloBusca);

        return transacoes.stream()
                .filter(transacao -> transacao.dataHora()
                                .isAfter(intervalo)).toList();
    }
}
