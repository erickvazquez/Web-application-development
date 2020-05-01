
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Editar examen</title>
        <%--Dependencias de ReactJS--%>
        <script src="https://unpkg.com/react@latest/umd/react.development.js" crossorigin="anonymous"></script>
        <script src="https://unpkg.com/react-dom@latest/umd/react-dom.development.js" crossorigin="anonymous"></script>
        <%--Dependencias de BabelJS--%>
        <script src="https://unpkg.com/babel-standalone@latest/babel.min.js" crossorigin="anonymous"></script>
        <%--Dependencias y recursos de Material UI y Material Icons--%>
        <script src="https://unpkg.com/@material-ui/core@3.9.3/umd/material-ui.development.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500" />
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
    </head>
    <body>
        <%--Componente para la edición de examenes--%>
        <div id="root"></div>
        <script src="Scripts/EditarExamen.js" type="text/babel"></script>
    </body>
</html>