package br.cesjf.simulation

import br.cesjf.dps.entities.Desenvolvedor;
import br.cesjf.dps.entities.Tarefa;
import br.ufjf.mmc.jynacore.JynaSimulableModel;
import br.ufjf.mmc.jynacore.JynaSimulation;
import br.ufjf.mmc.jynacore.JynaSimulationData;
import br.ufjf.mmc.jynacore.JynaSimulationMethod;
import br.ufjf.mmc.jynacore.JynaSimulationProfile;
import br.ufjf.mmc.jynacore.JynaValued;
import br.ufjf.mmc.jynacore.expression.Expression;
import br.ufjf.mmc.jynacore.expression.impl.DefaultExpression;
import br.ufjf.mmc.jynacore.expression.impl.DefaultNameExpression;
import br.ufjf.mmc.jynacore.expression.impl.DefaultNumberConstantExpression;
import br.ufjf.mmc.jynacore.impl.DefaultSimulationData;
import br.ufjf.mmc.jynacore.impl.DefaultSimulationProfile;
import br.ufjf.mmc.jynacore.metamodel.MetaModel;
import br.ufjf.mmc.jynacore.metamodel.MetaModelClass;
import br.ufjf.mmc.jynacore.metamodel.MetaModelClassAuxiliary;
import br.ufjf.mmc.jynacore.metamodel.MetaModelClassProperty;
import br.ufjf.mmc.jynacore.metamodel.MetaModelClassRate;
import br.ufjf.mmc.jynacore.metamodel.MetaModelClassStock;
import br.ufjf.mmc.jynacore.metamodel.MetaModelRelation;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModel;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelClass;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelClassAuxiliary;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelClassProperty;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelClassRate;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelClassStock;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelMultiRelation;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelSingleRelation;
import br.ufjf.mmc.jynacore.metamodel.instance.ClassInstanceItem;
import br.ufjf.mmc.jynacore.metamodel.instance.MetaModelInstance;
import br.ufjf.mmc.jynacore.metamodel.instance.impl.DefaultMetaModelInstance;
import br.ufjf.mmc.jynacore.metamodel.simulator.impl.DefaultMetaModelInstanceEulerMethod;
import br.ufjf.mmc.jynacore.metamodel.simulator.impl.DefaultMetaModelInstanceSimulation;

class SimulationController {

    def criarCenario(){
		//Modelo de Domínio
		def domainModel = new DefaultMetaModel()
		domainModel.setName("Projeto de Software Simples")

		//Classe Desenvolvedor
		def developer = new DefaultMetaModelClass()
		developer.setName("Heitor")

		def experience = new DefaultMetaModelClassProperty()
		experience.setName("experiência")
		experience.setDefaultValue(2.0)
		developer.put(experience)

		def productivity = new DefaultMetaModelClassAuxiliary()
		productivity.setName("Produtividade")
		productivity.setExpression(new DefaultNameExpression("experiência"))
		developer.put(productivity)

		domainModel.put(developer)

		//Classe Atividade
		def activity = new DefaultMetaModelClass()
		activity.setName("Atividade")

		def duration = new DefaultMetaModelClassProperty()
		duration.setName("duração")
		duration.setDefaultValue(25.0)
		activity.put(duration)

		def timeToConclude = new DefaultMetaModelClassStock()
		timeToConclude.setName("Tempo para concluir");
		timeToConclude.setExpression(new DefaultNameExpression("duração"))
		activity.put(timeToConclude)

		def production = new DefaultMetaModelClassAuxiliary()
		production.setName("Produção")
		production.setExpression(new DefaultNumberConstantExpression(1.0))
		activity.put(production)

		def work = new DefaultMetaModelClassRate()
		work.setName("Trabalho")
		work.setSource(timeToConclude)
		def workExp = new DefaultExpression()
		workExp.setLeftOperand(new DefaultNameExpression("Produção"))
		workExp.setRightOperand(new DefaultNameExpression("Tempo para concluir"))
		work.setExpression(workExp)
		activity.put(work)

		domainModel.put(activity)

		def team = new DefaultMetaModelSingleRelation()
		team.setName("Equipe")
		team.setTarget(developer)
		domainModel.put(team)

		def precedence = new DefaultMetaModelMultiRelation()
		precedence.setName("Precedente")
		precedence.setSource(activity)
		precedence.setTarget(activity)
		domainModel.put(precedence)

		def modelInstance = new DefaultMetaModelInstance()
		modelInstance.setMetaModel(domainModel)


		def simulation = new DefaultMetaModelInstanceSimulation()
		def profile = new DefaultSimulationProfile()
		def method = new DefaultMetaModelInstanceEulerMethod()
		JynaSimulableModel instance
		def data = new DefaultSimulationData()

		instance = modelInstance
		profile.setInitialTime(0.0)
		profile.setFinalTime(500.0)
		profile.setTimeLimits(1000, 10.0)
		int skip = 10

		simulation.setMethod(method)
		simulation.setProfile(profile)
		data.removeAll()
		data.clearAll()
		
		simulation.setModel(instance)
		simulation.setSimulationData(
				(JynaSimulationData) data)
		simulation.reset()

		data.register(1.0)
	}

    def index() {
		def desenvolvedores = Desenvolvedor.findAll()
		def tarefas = Tarefa.findAll()
		
		return [desenvolvedores:desenvolvedores, tarefas:tarefas]
	}
}
