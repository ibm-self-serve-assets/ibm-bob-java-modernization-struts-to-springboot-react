<%@page import="com.pks.insurance.domain.User"%>
<%@page import="com.pks.insurance.domain.Vehicle,java.util.Map"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html>
<head>
<title>Final Quotation</title>
<script>
	window.history.forward(1);
</script>
</head>
<body>
	<fieldset>
		<legend>Final Quotation</legend>

		<table>
			<tr>
				<td>Name</td>
				<td><c:out value="${quote.customer.firstName} ${quote.customer.lastName }" default="Guest"/></td>
			</tr>
			<tr>
				<td>SSN</td>
				<td><c:out value="${quote.customer.ssn }" default="Guest"/></td>
			</tr>
			<tr>
				<td>Make</td>
				<td><c:out value="${quote.customer.vehicle.make }" default="NA"/></td>
			</tr>
			<tr>
				<td>Model</td>
				<td><c:out value="${quote.customer.vehicle.model }" default="NA"/></td>
			</tr>
			<tr>
				<td>Vehicle Reg No.</td>
				<td><c:out value="${quote.customer.vehicle.regNo }" default="NA"/></td>
			</tr>

		</table>
	</fieldset>
	<fieldset>
		<legend> Premium Summary </legend>
		<table>
			<tr>
				<td>Coverage Type</td>
				<td><c:out value="${quote.customer.vehicle.policyType }" default="NA"/></td>
			</tr>
			<tr>
				<td>Coverage Amount</td>
				<td><c:out value="$ ${quote.customer.vehicle.policyAmount }"/></td>
			</tr>
			<tr>
				<td>Monthly</td>
				<td><c:out value="$ ${quote.premiumMonthly }"/></td>
			</tr>
			<tr>
				<td>Quarterly</td>
				<td><c:out value="$ ${quote.premiumQuarterly }"/></td>
			</tr>
			<tr>
				<td>Annually</td>
				<td><c:out value="$ ${quote.premiumAnnually }"/></td>
			</tr>
			<tr />
			<tr>
				<td />
				<td><html:image src="template/images/print_f2.png"
					onclick="window.print()" alt="Print" title="Click to print" /></td>
			</tr>
		</table>
	</fieldset>
</body>
</html>