#!/bin/bash
# ============================================================
# install-jenkins-openshift.sh
# Installs Jenkins on OpenShift using the official Helm chart
# with configuration for Kubernetes agents and DevSecOps plugins
# ============================================================
set -euo pipefail

OCP_NAMESPACE="${OCP_NAMESPACE:-jenkins}"
JENKINS_ADMIN_PASS="${JENKINS_ADMIN_PASS:-changeme123}"
OCP_ROUTE_HOST="${OCP_ROUTE_HOST:-jenkins.apps.cluster.example.com}"

echo "============================================"
echo " Installing Jenkins on OpenShift"
echo " Namespace: $OCP_NAMESPACE"
echo "============================================"

# 1. Create namespace
oc new-project "$OCP_NAMESPACE" --description="Jenkins CI/CD" || \
  echo "Namespace $OCP_NAMESPACE already exists."

# 2. Add Helm repo
helm repo add jenkins https://charts.jenkins.io
helm repo update

# 3. Create Jenkins values file
cat > /tmp/jenkins-values.yaml << YAML
controller:
  adminPassword: "${JENKINS_ADMIN_PASS}"
  serviceType: ClusterIP
  resources:
    requests:
      cpu: "500m"
      memory: "512Mi"
    limits:
      cpu: "2000m"
      memory: "2Gi"
  installPlugins:
    - kubernetes:latest
    - workflow-aggregator:latest
    - git:latest
    - blueocean:latest
    - sonar:latest
    - owasp-dependency-check:latest
    - jacoco:latest
    - html-publisher:latest
    - slack:latest
    - credentials-binding:latest
    - docker-workflow:latest
    - pipeline-utility-steps:latest
    - dependency-check-jenkins-plugin:latest
    - junit:latest
    - publish-over-ssh:latest
  JCasC:
    configScripts:
      security: |
        jenkins:
          securityRealm:
            local:
              allowsSignup: false
          authorizationStrategy:
            roleBased:
              roles:
                global:
                  - name: "admin"
                    permissions:
                      - "Overall/Administer"
                  - name: "developer"
                    permissions:
                      - "Job/Build"
                      - "Job/Read"

agent:
  enabled: true
  resources:
    requests:
      cpu: "250m"
      memory: "256Mi"
    limits:
      cpu: "1000m"
      memory: "1Gi"

persistence:
  enabled: true
  size: 20Gi

serviceAccount:
  create: true
  name: jenkins
YAML

# 4. Install Jenkins via Helm
helm upgrade --install jenkins jenkins/jenkins \
  --namespace "$OCP_NAMESPACE" \
  --values /tmp/jenkins-values.yaml \
  --wait \
  --timeout 10m

echo "Jenkins installed. Waiting for pod ready..."
kubectl rollout status statefulset/jenkins -n "$OCP_NAMESPACE" --timeout=5m

# 5. Create OpenShift Route for Jenkins
oc expose svc/jenkins -n "$OCP_NAMESPACE" \
  --hostname="$OCP_ROUTE_HOST" \
  --name=jenkins-route || echo "Route may already exist."

# 6. Grant Jenkins SA permissions to deploy in insurance namespaces
for ns in insurance-staging insurance-prod; do
  oc new-project "$ns" || echo "Namespace $ns exists."
  oc adm policy add-role-to-user admin \
    system:serviceaccount:"${OCP_NAMESPACE}":jenkins \
    -n "$ns"
done

# 7. Retrieve admin password
ADMIN_PASS=$(kubectl exec --namespace "$OCP_NAMESPACE" -it svc/jenkins -c jenkins \
  -- /bin/cat /run/secrets/chart-admin-password 2>/dev/null || echo "$JENKINS_ADMIN_PASS")

echo ""
echo "============================================"
echo " Jenkins Installation Complete!"
echo " URL: https://$OCP_ROUTE_HOST"
echo " Admin password: $ADMIN_PASS"
echo "============================================"
