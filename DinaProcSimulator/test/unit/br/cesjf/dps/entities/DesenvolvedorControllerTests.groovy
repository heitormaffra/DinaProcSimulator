package br.cesjf.dps.entities



import org.junit.*
import grails.test.mixin.*

@TestFor(DesenvolvedorController)
@Mock(Desenvolvedor)
class DesenvolvedorControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/desenvolvedor/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.desenvolvedorInstanceList.size() == 0
        assert model.desenvolvedorInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.desenvolvedorInstance != null
    }

    void testSave() {
        controller.save()

        assert model.desenvolvedorInstance != null
        assert view == '/desenvolvedor/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/desenvolvedor/show/1'
        assert controller.flash.message != null
        assert Desenvolvedor.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/desenvolvedor/list'

        populateValidParams(params)
        def desenvolvedor = new Desenvolvedor(params)

        assert desenvolvedor.save() != null

        params.id = desenvolvedor.id

        def model = controller.show()

        assert model.desenvolvedorInstance == desenvolvedor
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/desenvolvedor/list'

        populateValidParams(params)
        def desenvolvedor = new Desenvolvedor(params)

        assert desenvolvedor.save() != null

        params.id = desenvolvedor.id

        def model = controller.edit()

        assert model.desenvolvedorInstance == desenvolvedor
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/desenvolvedor/list'

        response.reset()

        populateValidParams(params)
        def desenvolvedor = new Desenvolvedor(params)

        assert desenvolvedor.save() != null

        // test invalid parameters in update
        params.id = desenvolvedor.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/desenvolvedor/edit"
        assert model.desenvolvedorInstance != null

        desenvolvedor.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/desenvolvedor/show/$desenvolvedor.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        desenvolvedor.clearErrors()

        populateValidParams(params)
        params.id = desenvolvedor.id
        params.version = -1
        controller.update()

        assert view == "/desenvolvedor/edit"
        assert model.desenvolvedorInstance != null
        assert model.desenvolvedorInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/desenvolvedor/list'

        response.reset()

        populateValidParams(params)
        def desenvolvedor = new Desenvolvedor(params)

        assert desenvolvedor.save() != null
        assert Desenvolvedor.count() == 1

        params.id = desenvolvedor.id

        controller.delete()

        assert Desenvolvedor.count() == 0
        assert Desenvolvedor.get(desenvolvedor.id) == null
        assert response.redirectedUrl == '/desenvolvedor/list'
    }
}
