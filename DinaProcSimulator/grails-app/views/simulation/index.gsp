<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <meta name="layout" content="main"/>
    <title>Insert title here</title>
  </head>
  <body>
    <div class="index">
      
      <g:select name="tarefas"
                from="${br.cesjf.dps.entities.Tarefa.list()}" 
                multiple="multiple" 
                optionKey="id" 
                size="5" 
                value="${tarefaInstance?.tarefa*.id}" class="many-to-many"
                />
    </div>
  </body>
</html>