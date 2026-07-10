#!/bin/bash
# ============================================================
# deploy-to-openshift.sh
# Deploy all PKS Insurance microservices to OpenShift
# Usage: ./deploy-to-openshift.sh [staging|prod]
# ============================================================
set -euo pipefail

ENV="${1:-staging}"
NAMESPACE="insurance-${ENV}"
IMAGE_TAG="${IMAGE_TAG:-1.0.0}"
REGISTRY="${REGISTRY:-quay.io/pks-insurance}"

echo "============================================"
echo " Deploying PKS Insurance to OpenShift"
echo " Environment: $ENV | Namespace: $NAMESPACE"
echo "============================================"

# Login check
oc whoami || { echo "ERROR: Not logged into OpenShift. Run: oc login <server>"; exit 1; }

# Ensure namespace
oc new-project "$NAMESPACE" || echo "Namespace $NAMESPACE already exists."

# Apply all manifests
echo "Applying manifests..."
oc apply -f modernized/ocp-manifests/insurance-all-in-one.yaml -n "$NAMESPACE"

# Wait for PostgreSQL to be ready
echo "Waiting for PostgreSQL..."
oc rollout status deployment/postgres -n "$NAMESPACE" --timeout=3m

# Update image tags to current version
SERVICES=("user-service" "vehicle-service" "premium-service" "frontend")
for SVC in "${SERVICES[@]}"; do
  echo "Updating $SVC image to $IMAGE_TAG..."
  oc set image deployment/"$SVC" \
    "$SVC"="${REGISTRY}/${SVC}:${IMAGE_TAG}" \
    -n "$NAMESPACE"
done

# Wait for all deployments
for SVC in "${SERVICES[@]}"; do
  echo "Waiting for $SVC rollout..."
  oc rollout status deployment/"$SVC" -n "$NAMESPACE" --timeout=5m
done

echo ""
echo "============================================"
echo " Deployment complete!"
oc get routes -n "$NAMESPACE"
echo "============================================"
