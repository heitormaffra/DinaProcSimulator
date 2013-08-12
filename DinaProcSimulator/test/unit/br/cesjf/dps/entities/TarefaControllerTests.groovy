package br.cesjf.dps.entities



import org.junit.*
import grails.test.mixin.*

@TestFor(TarefaController)
@Mock(Tarefa)
class TarefaControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/tarefa/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.tarefaInstanceList.size() == 0
        assert model.tarefaInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.tarefaInstance != null
    }

    void testSave() {
        controller.save()

        assert model.tarefaInstance != null
        assert view == '/tarefa/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/tarefa/show/1'
        assert controller.flash.message != null
        assert Tarefa.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/tarefa/list'

        populateValidParams(params)
        def tarefa = new Tarefa(params)

        assert tarefa.save() != null

        params.id = tarefa.id

        def model = controller.show()

        assert model.tarefaInstance == tarefa
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/tarefa/list'

        populateValidParams(params)
        def tarefa = new Tarefa(params)

        assert tarefa.save() != null

        params.id = tarefa.id

        def model = controller.edit()

        assert model.tarefaInstance == tarefa
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/tarefa/list'

        response.reset()

        populateValidParams(params)
        def tarefa = new Tarefa(params)

        assert tarefa.save() != null

        // test invalid parameters in update
        params.id = tarefa.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/tarefa/edit"
        assert model.tarefaInstance != null

        tarefa.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/tarefa/show/$tarefa.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        tarefa.clearErrors()

        populateValidParams(params)
        params.id = tarefa.id
        params.version = -1
        controller.update()

        assert view == "/tarefa/edit"
        assert model.tarefaInstance != null
        assert model.tarefaInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/tarefa/list'

        response.reset()

        populateValidParams(params)
        def tarefa = new Tarefa(params)

        assert tarefa.save() != null
        assert Tarefa.count() == 1

        params.id = tarefa.id

        controller.delete()

        assert Tarefa.count() == 0
        assert Tarefa.get(tarefa.id) == null
        assert response.redirectedUrl == '/tarefa/list'
    }
}
