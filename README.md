## Micronaut 4.4.2 Documentation

- [User Guide](https://docs.micronaut.io/4.4.2/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.4.2/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/4.4.2/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

## Google Cloud Run GitHub Workflow

Workflow file: [`.github/workflows/google-cloud-run.yml`](.github/workflows/google-cloud-run.yml)

### Workflow description
For pushes to the `master` branch, the workflow will:
1. Setup the build environment with respect to the selected java/graalvm version.
2. Setup of [Google Cloud CLI](https://cloud.google.com/sdk).
3. Authenticate docker to use [Google Container Registry (GCR)](https://cloud.google.com/container-registry/docs).
4. Build, tag and push Docker image with Micronaut application to GCR.
6. Deploy [Google Cloud Run](https://cloud.google.com/run) application.

### Dependencies on other GitHub Actions
- [Setup GraalVM `DeLaGuardo/setup-graalvm`](https://github.com/DeLaGuardo/setup-graalvm)
- [Setup Google Cloud CLI `google-github-actions/setup-gcloud`](https://github.com/google-github-actions/setup-gcloud)

### Setup
Add the following GitHub secrets:

| Name | Description |
| ---- | ----------- |
| GCLOUD_PROJECT_ID | Project id. |
| GCLOUD_SA_KEY | Service account key file. See more on [Creating and managing service accounts](https://cloud.google.com/iam/docs/creating-managing-service-accounts#iam-service-accounts-create-gcloud) and [Deployment permissions for CloudRun](https://cloud.google.com/run/docs/reference/iam/roles#additional-configuration) |
| GCLOUD_IMAGE_REPOSITORY | (Optional) Docker image repository in GCR. For image `[GCLOUD_REGION]/[GCLOUD_PROJECT_ID]/foo/bar:0.1`, the `foo` is an _image repository_. |

The workflow file also contains additional configuration options that are now configured to:

| Name | Description | Default value |
| ---- | ----------- | ------------- |
| GCLOUD_REGION | Region where the Cloud Run application will be created. See [Cloud Run Release Notes](https://cloud.google.com/run/docs/release-notes) to find out what regions are supported. | `europe-west3` |
| GCLOUD_GCR | Google Container Registry url. See [Overview of Container Registry](https://cloud.google.com/container-registry/docs/overview) to find out valid GCR endpoints. | `eu.gcr.io` |

### Verification
From the workflow step `Deploy Cloud Run` copy out url `https://eazyride-__________run.app` of the invoke endpoint:
```
Invoke endpoint:
https://eazyride-__________run.app
```

Call the api endpoint:
```
curl https://eazyride-__________run.app/eazyride
```


- [Micronaut Gradle Plugin documentation](https://micronaut-projects.github.io/micronaut-gradle-plugin/latest/)
- [GraalVM Gradle Plugin documentation](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html)
- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)
## Feature swagger-ui documentation

- [Micronaut Swagger UI documentation](https://micronaut-projects.github.io/micronaut-openapi/latest/guide/index.html)

- [https://swagger.io/tools/swagger-ui/](https://swagger.io/tools/swagger-ui/)


## Feature test-resources documentation

- [Micronaut Test Resources documentation](https://micronaut-projects.github.io/micronaut-test-resources/latest/guide/)


## Feature openapi documentation

- [Micronaut OpenAPI Support documentation](https://micronaut-projects.github.io/micronaut-openapi/latest/guide/index.html)

- [https://www.openapis.org](https://www.openapis.org)


## Feature jasync-sql documentation

- [Micronaut jasync-sql Asynchronous Database Drivers documentation](https://micronaut-projects.github.io/micronaut-sql/latest/guide/index.html#jasync)

- [https://github.com/jasync-sql/jasync-sql/wiki](https://github.com/jasync-sql/jasync-sql/wiki)


## Feature data-jdbc documentation

- [Micronaut Data JDBC documentation](https://micronaut-projects.github.io/micronaut-data/latest/guide/index.html#jdbc)


## Feature google-cloud-workflow-ci documentation

- [https://cloud.google.com/build/docs/building/build-java](https://cloud.google.com/build/docs/building/build-java)


## Feature jdbc-hikari documentation

- [Micronaut Hikari JDBC Connection Pool documentation](https://micronaut-projects.github.io/micronaut-sql/latest/guide/index.html#jdbc)


## Feature retry documentation

- [Micronaut Retry documentation](https://docs.micronaut.io/latest/guide/#retry)


## Feature openapi-explorer documentation

- [Micronaut OpenAPI Explorer View documentation](https://micronaut-projects.github.io/micronaut-openapi/latest/guide/#openapiExplorer)

- [https://github.com/Authress-Engineering/openapi-explorer](https://github.com/Authress-Engineering/openapi-explorer)


## Feature management documentation

- [Micronaut Management documentation](https://docs.micronaut.io/latest/guide/index.html#management)


## Feature micronaut-aot documentation

- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)


## Feature http-session documentation

- [Micronaut HTTP Sessions documentation](https://docs.micronaut.io/latest/guide/index.html#sessions)


## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#nettyHttpClient)


## Feature github-workflow-google-cloud-run documentation

- [https://docs.github.com/en/free-pro-team@latest/actions](https://docs.github.com/en/free-pro-team@latest/actions)


## Feature kotest documentation

- [Micronaut Test Kotest5 documentation](https://micronaut-projects.github.io/micronaut-test/latest/guide/#kotest5)

- [https://kotest.io/](https://kotest.io/)


## Feature serialization-jackson documentation

- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)


## Feature tracing-opentelemetry-exporter-logging documentation

- [Micronaut OpenTelemetry Exporter Logging documentation](http://localhost/micronaut-tracing/guide/index.html#opentelemetry)

- [https://opentelemetry.io](https://opentelemetry.io)


## Feature annotation-api documentation

- [https://jakarta.ee/specifications/annotations/](https://jakarta.ee/specifications/annotations/)


## Feature kapt documentation

- [Micronaut Kotlin Annotation Processing (KAPT) documentation](https://docs.micronaut.io/snapshot/guide/#kapt)

- [https://kotlinlang.org/docs/kapt.html](https://kotlinlang.org/docs/kapt.html)


