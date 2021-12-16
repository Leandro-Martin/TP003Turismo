<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/partials/head.jsp"></jsp:include>
</head>
<body>

	<jsp:include page="/partials/nav.jsp"></jsp:include>

	<main class="container">

		<c:if test="${flash != null}">
			<div class="alert alert-danger">
				<p>
					<c:out value="${flash}" />
					<c:if test="${errors != null}">
						<ul>
							<c:forEach items="${errors}" var="entry">
								<li><c:out value="${entry.getValue()}"></c:out></li>
							</c:forEach>
						</ul>
					</c:if>
				</p>
			</div>
		</c:if>

		<div class="bg-light p-4 mb-3 rounded">
			<h1>Estas son las atracciones de la Tierra Media</h1>
		</div>

		<table class="table table-stripped table-hover">
			<thead>
				<tr>
					<th>Usuario</th>
					<th>Atracci&oacute;n</th>
					<th>Promoci&oacute;n</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listItinerario}" var="registro">
					<c:if test="${user.username == registro.getUsuarioNombre()}">
						<tr>
							<td><strong><c:out value="${registro.getUsuarioNombre()}"></c:out></strong>
							<td><c:out value="${registro.getAtraccionNombre()}"></c:out></td>
							<td><c:out value="${registro.getPromocionNombre()}"></c:out></td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>

	</main>

</body>
</html>