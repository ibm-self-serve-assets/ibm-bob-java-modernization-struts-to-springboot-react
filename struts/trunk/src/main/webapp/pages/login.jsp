<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<link href="pages/template/css/login.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="template/js/validate">
<script>
	window.history.forward(1);
</script>

<div id="content-box">
	<div class="padding">
		<div id="element-box" class="login">
			<div class="t">
				<div class="t">
					<div class="t"></div>
				</div>
			</div>
			<div class="m">

				<h1>AutoInsurance Login</h1>

				<!-- Display system error messages -->
				<dl id="system-message">
					<dt class="error">Error</dt>
					<dd class="error message fade">
						<html:errors />
					</dd>
				</dl>
				<div id="section-box">
					<div class="t">
						<div class="t">
							<div class="t"></div>
						</div>
					</div>
					<div class="m">
						<!-- Creating form for login elements -->
						<html:form action="login" styleId="form-login"
							style="clear: both;">
							<p id="form-login-username">
								<label for="ssn">SSN</label>
								<html:text name="LoginForm" property="ssn" styleId="ssn"
									styleClass="inputbox" errorStyleClass="inputbox invalid"
									size="15" />
							</p>

							<p id="form-login-password">
								<label for="passwd">Password</label>
								<html:password name="LoginForm" property="passwd"
									errorStyleClass="inputbox invalid" styleId="passwd"
									styleClass="inputbox" size="15" />
							</p>
							<div class="button_holder">
								<div class="">
									<div>
										<span id="user" class="serr"></span>
									</div>
								</div>
							</div>
							<div class="button_holder">
								<div class="button1">
									<div class="next">
										<a onclick=""> Login</a>
									</div>
								</div>
							</div>
							<div class="clr"></div>
							<html:submit value="Login"
								style="border: 0; padding: 0; margin: 0; width: 0px; height: 0px;" />
						</html:form>
						<div class="clr"></div>
					</div>
					<div class="b">
						<div class="b">
							<div class="b"></div>
						</div>
					</div>
					<p>
						<html:link action="registerUser">New User</html:link>
					</p>
				</div>
				<div id="lock"></div>
				<div class="clr"></div>
			</div>
				<div class="b">
					<div class="b">
						<div class="b"></div>
					</div>
				</div>
		</div>
	</div>
</div>
