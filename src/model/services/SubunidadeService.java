package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Subunidade;

public class SubunidadeService {
	public List<Subunidade> findAll(){
		//dados MOCK por enquanto
		List<Subunidade> list = new ArrayList<>();
		list.add(new Subunidade(1,"1a Companhia de Fuzileiros"));
		list.add(new Subunidade(2,"2a Companhia de Fuzileiros"));
		list.add(new Subunidade(3,"3a Companhia de Fuzileiros"));
		list.add(new Subunidade(4,"Companhia de Comando e Apoio"));
		list.add(new Subunidade(5,"Base Administrativa"));
		return list;
	}
}
