package com.sensedia.apix2018microserviconotificacoes.dto.topico.input;

import java.io.Serializable;
import java.util.List;

import com.google.cloud.Timestamp;
import com.sensedia.apix2018microserviconotificacoes.dto.enums.StatusRetornoEnum;
import com.sensedia.apix2018microserviconotificacoes.dto.topico.output.SolicitacaoPagamentoDetalheOutput;

public class SolicitacaoPagamentoInput implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Double valor;
	private StatusRetornoEnum status;
	private Timestamp dataCriacao;
	private String numeroCartao;
	private String nomeCartao;
	private String validadeCartao;
	private String cvvCartao;
	private String codigoPagamento;
	private String usuarioCPF;
	private String usuarioNome;
	private String usuarioTelefone;
	private String usuarioEmail;
	
	private List<SolicitacaoPagamentoDetalheOutput> detalhes;

	public SolicitacaoPagamentoInput() {
	}

	public SolicitacaoPagamentoInput(Long id, Double valor, StatusRetornoEnum status, Timestamp dataCriacao,
			String numeroCartao, String nomeCartao, String validadeCartao, String cvvCartao, String codigoPagamento,
			String usuarioCPF, String usuarioNome, String usuarioTelefone, String usuarioEmail) {
		this.id = id;
		this.valor = valor;
		this.status = status;
		this.dataCriacao = dataCriacao;
		this.numeroCartao = numeroCartao;
		this.nomeCartao = nomeCartao;
		this.validadeCartao = validadeCartao;
		this.cvvCartao = cvvCartao;
		this.codigoPagamento = codigoPagamento;
		this.usuarioCPF = usuarioCPF;
		this.usuarioNome = usuarioNome;
		this.usuarioTelefone = usuarioTelefone;
		this.usuarioEmail = usuarioEmail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public StatusRetornoEnum getStatus() {
		return status;
	}

	public void setStatus(StatusRetornoEnum status) {
		this.status = status;
	}

	public Timestamp getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Timestamp dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public String getNomeCartao() {
		return nomeCartao;
	}

	public void setNomeCartao(String nomeCartao) {
		this.nomeCartao = nomeCartao;
	}

	public String getValidadeCartao() {
		return validadeCartao;
	}

	public void setValidadeCartao(String validadeCartao) {
		this.validadeCartao = validadeCartao;
	}

	public String getCvvCartao() {
		return cvvCartao;
	}

	public void setCvvCartao(String cvvCartao) {
		this.cvvCartao = cvvCartao;
	}

	public String getCodigoPagamento() {
		return codigoPagamento;
	}

	public void setCodigoPagamento(String codigoPagamento) {
		this.codigoPagamento = codigoPagamento;
	}

	public String getUsuarioCPF() {
		return usuarioCPF;
	}

	public void setUsuarioCPF(String usuarioCPF) {
		this.usuarioCPF = usuarioCPF;
	}

	public String getUsuarioNome() {
		return usuarioNome;
	}

	public void setUsuarioNome(String usuarioNome) {
		this.usuarioNome = usuarioNome;
	}

	public String getUsuarioTelefone() {
		return usuarioTelefone;
	}

	public void setUsuarioTelefone(String usuarioTelefone) {
		this.usuarioTelefone = usuarioTelefone;
	}

	public String getUsuarioEmail() {
		return usuarioEmail;
	}

	public void setUsuarioEmail(String usuarioEmail) {
		this.usuarioEmail = usuarioEmail;
	}
	
	public Boolean isValid() {
		Long count = detalhes.stream().filter(detalhe -> detalhe.getStatus().equals(StatusRetornoEnum.ERROR) || detalhe.getStatus().equals(StatusRetornoEnum.FRAUDE)).count();
		return count == 0;
	}
}
