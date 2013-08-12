package br.cesjf.dps.entities

class Tarefa {

    String nomeTarefa
	Double tempoConcluirTarefa
	
	static hasMany = [desenvolvedores: Desenvolvedor]
	

    static constraints = {
    }
	
	String toString(){
		return nomeTarefa
	}
}
