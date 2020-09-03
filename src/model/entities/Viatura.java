package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Viatura implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer eb;
	private String nome;
	private Integer odometro;
	private String categoria;
	private Date ano;
	private Date dataInsercao;
	private String tipo;
	private String situacao;
	
	private Subunidade subunidade;
	
	public Viatura() {
	}

	public Viatura(Integer id, Integer eb, String nome, Integer odometro, String categoria, Date ano, Date dataInsercao,
			String tipo, String situacao, Subunidade subunidade) {
		super();
		this.id = id;
		this.eb = eb;
		this.nome = nome;
		this.odometro = odometro;
		this.categoria = categoria;
		this.ano = ano;
		this.dataInsercao = dataInsercao;
		this.tipo = tipo;
		this.situacao = situacao;
		this.subunidade = subunidade;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEb() {
		return eb;
	}

	public void setEb(Integer eb) {
		this.eb = eb;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getOdometro() {
		return odometro;
	}

	public void setOdometro(Integer odometro) {
		this.odometro = odometro;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Date getAno() {
		return ano;
	}

	public void setAno(Date ano) {
		this.ano = ano;
	}

	public Date getDataInsercao() {
		return dataInsercao;
	}

	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Subunidade getSubunidade() {
		return subunidade;
	}

	public void setSubunidade(Subunidade subunidade) {
		this.subunidade = subunidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Viatura other = (Viatura) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Viatura [id=" + id + ", eb=" + eb + ", nome=" + nome + ", odometro=" + odometro + ", categoria="
				+ categoria + ", ano=" + ano + ", dataInsercao=" + dataInsercao + ", tipo=" + tipo + ", situacao="
				+ situacao + ", subunidade=" + subunidade + "]";
	}
}