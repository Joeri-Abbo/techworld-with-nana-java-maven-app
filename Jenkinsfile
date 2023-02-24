
pipeline {
    agent any
    tools {
        maven: "Maven"
    }
    stages {

        stage("increment version") {
            steps {
                script {
                    echo 'incrementing app version...'
                    sh 'mvn build-helper:parse-version versions:set -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion} versions:commit'
                    def matcher = readFile('pom.xml') =~ /<version>(.*)<\/version>/
                    def version = matcher[0][1]
                    env.IMAGE_NAME = env.IMAGE_NAME + "$version-$buildNumber"
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    echo "building the application..."
                    buildJar()
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    echo "building the docker image..."
                    buildImage(env.IMAGE_NAME)
                    dockerLogin()
                    dockerPush(env.IMAGE_NAME)
            }
        }

        stage("deploy") {
            environment {
                AWS_ACCESS_KEY_ID = credentials('jenkins_aws_access_key_id')
                AWS_SECRET_ACCESS_KEY = credentials('jenkins_aws_secret_access_key')
                APP_NAME = "java-maven-app"
            }
            steps {
                script {
                    echo "Deploy docker image..."
                    sh "envsubst < kubernetes/deployment.yaml kubectl apply -f -"
                    sh "envsubst < kubernetes/service.yaml kubectl apply -f -"
                }
            }
        }
    }   
}
