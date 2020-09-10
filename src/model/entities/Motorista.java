package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Motorista implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer idMilitar;
	private String pg;
	private String nome;
	private String categoriaCNH;
	private Date dataAniversario;
	private Date validadeCNH;
	private Integer numeroCNH;
	private String situacao;
	
	private Subunidade subunidade;
	
	public Motorista() {
	}
	
	public Motorista(Integer id, Integer idMilitar, String pg, String nome, String categoriaCNH, Date dataAniversario,
			Date validadeCNH, Integer numeroCNH, String situacao, Subunidade subunidade) {
		super();
		this.id = id;
		this.idMilitar = idMilitar;
		this.pg = pg;
		this.nome = nome;
		this.categoriaCNH = categoriaCNH;
		this.dataAniversario = dataAniversario;
		this.validadeCNH = validadeCNH;
		this.numeroCNH = numeroCNH;
		this.situacao = situacao;
		this.subunidade = subunidade;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdMilitar() {
		return idMilitar;
	}

	public void setIdMilitar(Integer idMilitar) {
		this.idMilitar = idMilitar;
	}
	
	public String getPg() {
		return pg;
	}

	public void setPg(String pg) {
		this.pg = pg;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCategoriaCNH() {
		return categoriaCNH;
	}

	public void setCategoriaCNH(String categoriaCNH) {
		this.categoriaCNH = categoriaCNH;
	}

	public Date getDataAniversario() {
		return dataAniversario;
	}

	public void setDataAniversario(Date dataAniversario) {
		this.dataAniversario = dataAniversario;
	}

	public Date getValidadeCNH() {
		return validadeCNH;
	}

	public void setValidadeCNH(Date validadeCNH) {
		this.validadeCNH = validadeCNH;
	}

	public Integer getNumeroCNH() {
		return numeroCNH;
	}

	public void setNumeroCNH(Integer numeroCNH) {
		this.numeroCNH = numeroCNH;
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
		Motorista other = (Motorista) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Motorista [id=" + id + ", idMilitar=" + idMilitar + ", pg=" + pg + ", nome=" + nome + ", categoriaCNH="
				+ categoriaCNH + ", dataAniversario=" + dataAniversario + ", ValidadeCNH=" + validadeCNH
				+ ", numeroCNH=" + numeroCNH + ", situacao=" + situacao + ", subunidade=" + subunidade + "]";
	}
}