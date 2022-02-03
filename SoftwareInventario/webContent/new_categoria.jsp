<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="head.jsp"%>
<body>
	<%@ include file="navigation.jsp"%>
	<div class="container">
		<h2 class="text-center">Nueva categoria</h2>
		<div class="row justify-content-center">
			<form action="categoriaServlet" method="POST" class="col-8">
				<input type="hidden" name="opcion" value="create">
				<div class="form-group">
					<label for="inputCat">Descripcion categoria</label> <input
						type="text" class="form-control" id="inputCat"
						placeholder="Ingresar categoria" name="descripCat">
				</div>
				<div class="d-flex justify-content-center mt-3">
					<button type="submit" class="btn btn-primary">Registrar
						categoria</button>
				</div>
			</form>
		</div>
	</div>
	<%@ include file="scripts.html"%>
</body>
</html>