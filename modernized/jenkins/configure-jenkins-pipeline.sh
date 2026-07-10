#!/bin/bash
# ============================================================
# configure-jenkins-pipeline.sh
# Creates Jenkins pipeline jobs for all PKS Insurance services
# Requires: Jenkins CLI (jenkins-cli.jar), Jenkins URL, credentials
# ============================================================
set -euo pipefail

JENKINS_URL="${JENKINS_URL:-https://jenkins.apps.cluster.example.com}"
JENKINS_USER="${JENKINS_USER:-admin}"
JENKINS_PASS="${JENKINS_PASS:-changeme123}"
CLI_JAR="/tmp/jenkins-cli.jar"

echo "Downloading Jenkins CLI..."
curl -sSL "$JENKINS_URL/jnlpJars/jenkins-cli.jar" -o "$CLI_JAR"

SERVICES=("user-service" "vehicle-service" "premium-service")

for SERVICE in "${SERVICES[@]}"; do
  echo "Creating pipeline job: pks-insurance-$SERVICE"

  cat > /tmp/job-config.xml << XML
<?xml version='1.1' encoding='UTF-8'?>
<flow-definition plugin="workflow-job">
  <description>PKS Auto Insurance - $SERVICE CI/CD Pipeline</description>
  <definition class="org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition" plugin="workflow-cps">
    <scm class="hudson.plugins.git.GitSCM">
      <userRemoteConfigs>
        <hudson.plugins.git.UserRemoteConfig>
          <url>https://github.com/pks/auto-insurance.git</url>
          <credentialsId>github-credentials</credentialsId>
        </hudson.plugins.git.UserRemoteConfig>
      </userRemoteConfigs>
      <branches>
        <hudson.plugins.git.BranchSpec><name>*/main</name></hudson.plugins.git.BranchSpec>
      </branches>
    </scm>
    <scriptPath>modernized/jenkins/Jenkinsfile</scriptPath>
    <lightweight>true</lightweight>
  </definition>
  <triggers>
    <com.cloudbees.jenkins.GitHubPushTrigger plugin="github"/>
  </triggers>
  <properties>
    <hudson.model.ParametersDefinitionProperty>
      <parameterDefinitions>
        <hudson.model.StringParameterDefinition>
          <name>SERVICE_NAME</name>
          <defaultValue>$SERVICE</defaultValue>
        </hudson.model.StringParameterDefinition>
        <hudson.model.StringParameterDefinition>
          <name>VERSION</name>
          <defaultValue>1.0.0</defaultValue>
        </hudson.model.StringParameterDefinition>
        <hudson.model.BooleanParameterDefinition>
          <name>DEPLOY_PROD</name>
          <defaultValue>false</defaultValue>
        </hudson.model.BooleanParameterDefinition>
      </parameterDefinitions>
    </hudson.model.ParametersDefinitionProperty>
  </properties>
</flow-definition>
XML

  java -jar "$CLI_JAR" -s "$JENKINS_URL" \
    -auth "$JENKINS_USER:$JENKINS_PASS" \
    create-job "pks-insurance-${SERVICE}" < /tmp/job-config.xml \
    && echo "Created: pks-insurance-${SERVICE}" \
    || java -jar "$CLI_JAR" -s "$JENKINS_URL" \
         -auth "$JENKINS_USER:$JENKINS_PASS" \
         update-job "pks-insurance-${SERVICE}" < /tmp/job-config.xml \
    && echo "Updated: pks-insurance-${SERVICE}"
done

echo ""
echo "All pipeline jobs configured in Jenkins: $JENKINS_URL"
