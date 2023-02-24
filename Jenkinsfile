pipeline {
    agent any
    environment {
        // Update below details to push the image to docker repository
        DOCKER_REGISTRY = ""
        DOCKER_IMAGE_NAME = "cake-manager"
        DOCKER_IMAGE_TAG = "latest"
        USERNAME = "<username>"
        PASSWORD = "<password>"
    }
    stages {
        stage("Build") {
            steps {
                sh "./gradlew build"
                sh "docker build -t ${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ."
            }
        }
        stage("Test") {
            steps {
                sh "./gradlew test"
            }
        }
        stage("Deploy") {
            steps {
                sh "docker login -u ${USERNAME} -p ${PASSWORD} ${DOCKER_REGISTRY}"
                sh "docker push ${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
                sh "docker-compose up -d"
            }
        }
    }
}
